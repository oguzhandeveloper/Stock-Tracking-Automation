/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author OGUZHAN
 */
public class DBHelper {

    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost:3306/stocktrackingdatabase?autoReconnect=true&useSSL=false&useUnicode=true&characterEncoding=utf8";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection conn = null;
    private Statement stmt = null;

    public static ArrayList<Job> Jobs;
    public static ArrayList<Department> Departments;
    public static ArrayList<Company> Companies;

    public DBHelper() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void Open() {
        try {
            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            //System.out.println("Creating statement...");
            stmt = conn.createStatement();

            DBHelper.Jobs = this.getJobs();
            DBHelper.Departments = this.getDepartments();
            DBHelper.Companies = this.getCompanies();

        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void Close() {
        try {
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public Personnel getPersonnel(String username, String password) {

        ArrayList<Personnel> personnels = this.getPersonnels();

        for (Personnel p : personnels) {
            if (username.equals(p.username) && password.equals(p.password)) {
                return p;
            }
        }
        return null;
    }

    public void Update(Personnel p) {
        String username = p.username;
        String password = p.password;
        String name = p.name;
        String lastName = p.lastName;
        String job = p.Job;
        String department = p.Department;
        int Active = p.Active;

        int departmentID = 0;
        int jobID = 0;

        for (Department d : DBHelper.Departments) {
            if (department.equals(d.name)) {
                departmentID = d.deparmentID;
            }
        }
        for (Job j : DBHelper.Jobs) {
            if (job.equals(j.name)) {
                jobID = j.jobID;
            }
        }

        try {
            /*
            UPDATE table_name
            SET column1 = value1, column2 = value2, ...
            WHERE condition;
             */
            String sql;
            sql = "UPDATE personnel "
                    + "SET name = '"+name+"', lastname ='"+lastName+"', jobID ="+jobID+","
                    + " departmentID ="+departmentID+", active ="+Active
                    + " WHERE personnelID = "+p.ID;
            stmt.executeUpdate(sql); // DML

        } catch (SQLException e) {
            System.err.println(e);
            return;
        }

    }

    public void Insert(Personnel p) {
        String username = p.username;
        String password = p.password;
        String name = p.name;
        String lastName = p.lastName;
        String job = p.Job;
        String department = p.Department;
        int Active = p.Active;

        int departmentID = 0;
        int jobID = 0;

        for (Department d : DBHelper.Departments) {
            if (department.equals(d.name)) {
                departmentID = d.deparmentID;
            }
        }
        for (Job j : DBHelper.Jobs) {
            if (job.equals(j.name)) {
                jobID = j.jobID;
            }
        }

        try {
            String sql;
            sql = "INSERT INTO personnel(username,password,name,lastname,jobID,departmentID,active) VALUES('"
                    + username + "','" + password + "','" + name + "','" + lastName + "'," + jobID + "," + departmentID + "," + Active + ")";
            stmt.executeUpdate(sql); // DML

        } catch (SQLException e) {
            System.out.println(e);
            return;
        }

    }

    public Personnel PersonnelCheck(String username) {

        ArrayList<Personnel> personnels = this.getPersonnels();

        for (Personnel p : personnels) {
            if (username.equals(p.username)) {
                return p;
            }
        }
        
        return null;
    }

    
    
    public ArrayList<Hardware> getHardwaresInStock(){
        ArrayList<Hardware> hardwares = new ArrayList<Hardware>();
        try {
            String sql;
            sql = "SELECT * FROM hardware";
            ResultSet rs = stmt.executeQuery(sql); // DML
            // stmt.executeUpdate(sql); // DDL
            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Display values
                Hardware h = new Hardware();
                h.hardwareID = rs.getInt("hardwareID");
                h.brand = rs.getString("brand");
                h.definition = rs.getString("definition");
                h.price = rs.getDouble("price");
                h.isBelong = rs.getInt("isBelong");
                h.isWaste = rs.getInt("isWaste");
                h.purchaseDate = rs.getDate("purchaseDate");
                h.computerID = rs.getInt("computerID");
                int companyID = rs.getInt("companyID");
                String company = null;
                for(Company c: DBHelper.Companies){
                    if(companyID == c.companyID)
                    {
                        company = c.name;
                        break;
                    }
                }
                h.company = company;
                hardwares.add(h);                
            }
            //STEP 6: Clean-up environment
            rs.close();
        } catch (SQLException ex) {
           
            System.err.println(ex);
        }

        return hardwares;
    }
    
    public ArrayList<Computer> getComputersInStock(){
       ArrayList<Computer> computers = new ArrayList<Computer>();
        try {
            String sql;
            sql = "SELECT * FROM computer";
            ResultSet rs = stmt.executeQuery(sql); // DML
            // stmt.executeUpdate(sql); // DDL
            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Display values
                Computer c = new Computer();
                c.computerID = rs.getInt("computerID");
                c.brand = rs.getString("brand");
                c.definition = rs.getString("definition");
                c.price = rs.getDouble("price");
                c.isBelong = rs.getInt("isBelong");
                c.isWaste = rs.getInt("isWaste");
                c.purchaseDate = rs.getDate("purchaseDate");
                int companyID = rs.getInt("companyID");
                String company = null;
                for(Company co: DBHelper.Companies){
                    if(companyID == co.companyID)
                    {
                        company = co.name;
                        break;
                    }
                }
                c.company = company;
                computers.add(c);                
            }
            //STEP 6: Clean-up environment
            rs.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return computers;
    }
    
    public ArrayList<Department> getDepartments() {
        ArrayList<Department> departments = new ArrayList<Department>();

        try {
            String sql;
            sql = "SELECT * FROM department";
            ResultSet rs = stmt.executeQuery(sql); // DML
            // stmt.executeUpdate(sql); // DDL
            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Display values
                Department d = new Department();
                d.deparmentID = rs.getInt("departmentID");
                d.name = rs.getString("name");
                departments.add(d);
            }
            //STEP 6: Clean-up environment
            rs.close();
        } catch (SQLException ex) {
            
            System.err.println(ex);
        }

        return departments;
    }

    public ArrayList<Job> getJobs() {
        ArrayList<Job> jobs = new ArrayList<Job>();

        try {
            String sql;
            sql = "SELECT * FROM job";
            ResultSet rs = stmt.executeQuery(sql); // DML
            // stmt.executeUpdate(sql); // DDL
            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Display values
                Job j = new Job();
                j.jobID = rs.getInt("jobID");
                j.name = rs.getString("name");
                jobs.add(j);
            }
            //STEP 6: Clean-up environment
            rs.close();
        } catch (SQLException ex) {
            
            System.err.println(ex);
        }

        return jobs;
    }
    
    public ArrayList<Company> getCompanies(){
        ArrayList<Company> companies = new ArrayList<Company>();

        try {
            String sql;
            sql = "SELECT * FROM company";
            ResultSet rs = stmt.executeQuery(sql); // DML
            // stmt.executeUpdate(sql); // DDL
            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Display values
                Company c = new Company();
               c.companyID = rs.getInt("companyID");
               c.name = rs.getString("name");
               companies.add(c);
            }
            //STEP 6: Clean-up environment
            rs.close();
        } catch (SQLException ex) {
            
            System.err.println(ex);
        }

        return companies;
    }

    public ArrayList<Personnel> getPersonnels() {
        ArrayList<Personnel> personnels = new ArrayList<Personnel>();
        try {
            String sql;
            sql = "SELECT * FROM personnel";
            ResultSet rs = stmt.executeQuery(sql); // DML
            // stmt.executeUpdate(sql); // DDL
            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Display values
                Personnel p = new Personnel();
                p.ID = rs.getInt("personnelID");
                p.username = rs.getString("username");
                p.password = rs.getString("password");
                p.name = rs.getString("name");
                p.lastName = rs.getString("lastname");
                p.Active = rs.getInt("active");
                int jobID = rs.getInt("jobID");
                int departmentID = rs.getInt("departmentID");

                for (Department d : DBHelper.Departments) {
                    if (departmentID == d.deparmentID) {
                        p.Department = d.name;
                    }
                }
                for (Job j : DBHelper.Jobs) {
                    if (jobID == j.jobID) {
                        p.Job = j.name;
                    }
                }
                personnels.add(p);

            }
            //STEP 6: Clean-up environment
            rs.close();
        } catch (SQLException ex) {
            
            System.err.println(ex);
        }
        return personnels;
    }

}
