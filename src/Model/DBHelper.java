/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import View.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
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

    AccessFXML accessFXML = new AccessFXML();

    public DBHelper() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception e) {
            accessFXML._modal("Database Connection Error", "Database is not available", "OKEY", null);
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
            accessFXML._modal("Database Connection Error", "Failed to open database.", "OKEY", null);
        }
    }

    public void Close() {
        try {
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            accessFXML._modal("Database Connection Error", "Failed to close database.", "OKEY", null);
        }
    }

    public Personnel getPersonnel(String username, String password) {

        ArrayList<Personnel> personnels = this.getPersonnels("");

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
                    + "SET username = '" + username + "', name = '" + name + "', lastname ='" + lastName + "', jobID =" + jobID + ","
                    + " departmentID =" + departmentID + ", active =" + Active
                    + " WHERE personnelID = " + p.personnelID;
            stmt.executeUpdate(sql); // DML

        } catch (SQLException e) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
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
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
            return;
        }

    }

    public Personnel ProfileCheck(int personnelID, String password) {
        Personnel p = null;
        try {
            String sql = "SELECT * FROM personnel \n"
                    + "WHERE personnelID = " + personnelID + " AND password = '" + password + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                p = new Personnel();
                p.personnelID = rs.getInt("personnelID");
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
            }

            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    public Personnel PersonnelCheck(String username) {

        ArrayList<Personnel> personnels = this.getPersonnels("");

        for (Personnel p : personnels) {
            if (username.equals(p.username)) {
                return p;
            }
        }

        return null;
    }

    public ArrayList<Product> getProduct() {
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            String sql;
            sql = "SELECT * FROM product";
            ResultSet rs = stmt.executeQuery(sql); // DML
            // stmt.executeUpdate(sql); // DDL
            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Display values
                Product p = new Product();
                p.productID = rs.getInt("productID");
                p.brand = rs.getString("brand");
                p.definition = rs.getString("definition");
                p.price = rs.getDouble("price");
                p.isBelong = rs.getInt("isBelong");
                p.isWaste = rs.getInt("isWaste");
                p.purchaseDate = rs.getDate("purchaseDate");
                int companyID = rs.getInt("companyID");
                String company = null;
                for (Company co : DBHelper.Companies) {
                    if (companyID == co.companyID) {
                        company = co.name;
                        break;
                    }
                }
                p.company = company;
                products.add(p);
            }
            //STEP 6: Clean-up environment
            rs.close();
        } catch (SQLException e) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
        }

        return products;
    }

    public ArrayList<Assign> getAssigns(String department) {
        ArrayList<Assign> assigns = new ArrayList<Assign>();

        try {
            String sqlPerson = "SELECT a.personnelID, a.productID, a.assignDate, a.active, \n"
                    + "ps.name, ps.lastname, p.brand , p.definition, p.price, ps.departmentID \n"
                    + "FROM personnel ps \n"
                    + "INNER JOIN assign a ON ps.personnelID = a.personnelID\n"
                    + "INNER JOIN product p ON a.productID = p.productID\n"
                    + "WHERE a.active = 1";
            ResultSet rs = stmt.executeQuery(sqlPerson); // DML
            // stmt.executeUpdate(sql); // DDL
            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Display values

                int departmentID = rs.getInt("departmentID");
                String departmentString = "";

                for (Department d : DBHelper.Departments) {
                    if (departmentID == d.deparmentID) {
                        departmentString = d.name;
                    }
                }

                Assign a = new Assign();
                a.personnelID = rs.getInt("personnelID");
                a.productID = rs.getInt("productID");
                a.assignDate = rs.getDate("assignDate");
                a.name = rs.getString("name");
                a.lastName = rs.getString("lastname");
                a.brand = rs.getString("brand");
                a.definition = rs.getString("definition");
                a.price = rs.getDouble("price");
                a.active = rs.getInt("active");

                if (department.equals("")) {
                    assigns.add(a);
                } else if (department.equals(departmentString)) {
                    assigns.add(a);
                }

            }
            //STEP 6: Clean-up environment
            rs.close();
        } catch (SQLException e) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
        }

        return assigns;
    }

    public void Insert(Assign a) {

        int personnelID = a.personnelID;
        int productID = a.productID;
        Date dt = new Date();

        java.text.SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);

        try {

            String sql = "INSERT INTO assign(personnelID, productID, assignDate, active) VALUES("
                    + personnelID + "," + productID + ",'" + currentTime + "', 1)";
            stmt.executeUpdate(sql); // DML
            String sqlUpdateProduct = "UPDATE product\n"
                    + "SET isBelong = 1\n"
                    + "WHERE productID = " + productID;
            stmt.executeUpdate(sqlUpdateProduct);

        } catch (SQLException e) {

            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
        }
    }

    public void RemoveAssign(Assign r) {

        try {
            String sql = "UPDATE assign\n"
                    + "SET active = 0 \n"
                    + "WHERE personnelID =" + r.personnelID + " AND productID = " + r.productID;
            stmt.executeUpdate(sql);

            sql = "UPDATE product \n"
                    + "SET isBelong = 0 \n"
                    + "WHERE productID = " + r.productID;
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
        }
    }

    public void RemoveToWasteStorage(Product p) {

        try {
            String sql = "UPDATE product\n"
                    + "SET isBelong = 0, isWaste = 1\n"
                    + "WHERE  productID = " + p.productID;
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
        }
    }

    public void RemoveFromWasteStorage(Product p) {

        try {
            String sql = "UPDATE product\n"
                    + "SET isWaste = 0\n"
                    + "WHERE  productID = " + p.productID;
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
        }
    }

    public ArrayList<Product> getProductsInAssign(Personnel p) {
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            String sql = "SELECT * FROM product p \n"
                    + "INNER JOIN assign a ON a.productID = p.productID \n"
                    + "WHERE a.active = 1 AND a.personnelID = " + p.personnelID;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Product pr = new Product();
                pr.productID = rs.getInt("productID");
                pr.brand = rs.getString("brand");
                pr.definition = rs.getString("definition");
                pr.price = rs.getDouble("price");
                pr.isBelong = rs.getInt("isBelong");
                pr.isWaste = rs.getInt("isWaste");
                pr.purchaseDate = rs.getDate("purchaseDate");
                int companyID = rs.getInt("companyID");
                String company = null;
                for (Company co : DBHelper.Companies) {
                    if (companyID == co.companyID) {
                        company = co.name;
                        break;
                    }
                }
                pr.company = company;
                products.add(pr);
            }

        } catch (SQLException e) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
        }

        return products;
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
        } catch (SQLException e) {

            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
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
        } catch (SQLException e) {

            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
        }
        return jobs;
    }

    public ArrayList<Company> getCompanies() {
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
        } catch (SQLException e) {

            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
        }

        return companies;
    }

    public ArrayList<Personnel> getPersonnels(String departmentName) {
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
                p.personnelID = rs.getInt("personnelID");
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

                if (departmentName.equals("")) {
                    personnels.add(p);
                } else if (departmentName.equals(p.Department)) {
                    personnels.add(p);
                }

            }
            //STEP 6: Clean-up environment
            rs.close();
        } catch (SQLException e) {

            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
        }
        return personnels;
    }

    public void Insert(Product p) {
        int companyID = 0;
        for (Company co : DBHelper.Companies) {
            if (p.company == co.name) {
                companyID = co.companyID;
                break;
            }
        }
        java.text.SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(p.purchaseDate);
        try {
            String sql = "INSERT INTO product(productID,brand, definition,price, companyID, isBelong, isWaste, purchaseDate)\n"
                    + "VALUES(" + p.productID + ",'" + p.brand + "','" + p.definition + "'," + p.price + "," + companyID
                    + "," + p.isBelong + ", " + p.isWaste + " ,'" + currentTime + "')";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////BUY////////////////////////////////////////
    public ArrayList<BuyStock> getProducts() {
        ArrayList<BuyStock> products = new ArrayList<BuyStock>();
        try {
            String sql = "SELECT * FROM buystock";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                BuyStock bs = new BuyStock();
                bs.productID = rs.getInt("productID");
                bs.brand = rs.getString("brand");
                bs.definition = rs.getString("definition");
                bs.price = rs.getDouble("price");
                int companyID = rs.getInt("companyID");
                String company = null;
                for (Company co : DBHelper.Companies) {
                    if (companyID == co.companyID) {
                        company = co.name;
                        break;
                    }
                }
                bs.company = company;
                products.add(bs);
            }
        } catch (SQLException e) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
        }

        return products;
    }

    public void DeleteProductsBuy(BuyStock bs) {
        try {
            String sql = "DELETE FROM buystock WHERE productID = " + bs.productID;
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
        }
    }

}
