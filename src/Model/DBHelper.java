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
    private final String DB_URL = "jdbc:mysql://localhost:3306/stocktruckingdatabase?autoReconnect=true&useSSL=false&useUnicode=true&characterEncoding=utf8";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection conn = null;
    private Statement stmt = null;

    public static ArrayList<Job> Jobs;
    public static ArrayList<Department> Departments;
    public static ArrayList<Company> Companies;

    AccessFXML accessFXML = new AccessFXML();

    public DBHelper() throws SQLException, ClassNotFoundException {

        Class.forName(JDBC_DRIVER);

    }

    public void Open() throws SQLException {

        //System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        //System.out.println("Creating statement...");
        stmt = conn.createStatement();

        DBHelper.Jobs = this.getJobs();
        DBHelper.Departments = this.getDepartments();
        DBHelper.Companies = this.getCompanies();

    }

    public void Close() throws SQLException {

        stmt.close();
        conn.close();

    }

    public Personnel getPersonnel(String username, String password) throws SQLException {

        ArrayList<Personnel> personnels = this.getPersonnels("");

        for (Personnel p : personnels) {
            if (username.equals(p.username) && password.equals(p.password)) {
                return p;
            }
        }
        return null;
    }

    public void Update(Personnel p) throws SQLException {
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

    }

    public void Insert(Personnel p) throws SQLException {
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

        String sql;
        sql = "INSERT INTO personnel(username,password,name,lastname,jobID,departmentID,active) VALUES('"
                + username + "','" + password + "','" + name + "','" + lastName + "'," + jobID + "," + departmentID + "," + Active + ")";
        stmt.executeUpdate(sql); // DML

    }

    public Personnel ProfileCheck(int personnelID, String password) throws SQLException {
        Personnel p = null;

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

        return p;
    }

    public Personnel PersonnelCheck(String username) throws SQLException {

        ArrayList<Personnel> personnels = this.getPersonnels("");

        for (Personnel p : personnels) {
            if (username.equals(p.username)) {
                return p;
            }
        }

        return null;
    }

    public ArrayList<Product> getProduct() throws SQLException {
        ArrayList<Product> products = new ArrayList<Product>();

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

        return products;
    }

    public ArrayList<Assign> getAssigns(String department) throws SQLException {
        ArrayList<Assign> assigns = new ArrayList<Assign>();
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

        return assigns;
    }

    public void Insert(Assign a) throws Exception {

        int personnelID = a.personnelID;
        int productID = a.productID;
        Date dt = new Date();

        java.text.SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);
        String sqlControl = "SELECT * FROM assign WHERE personnelID = "+a.personnelID+
                " AND productID = "+a.productID+" AND active = 1";
        ResultSet rs = stmt.executeQuery(sqlControl);
        while(rs.next()){
            throw  new Exception("Bu zimmetleme işlemi zaten yapılmış");
        }
        rs.close();
        String sql = "INSERT INTO assign(personnelID, productID, assignDate, active) VALUES("
                + personnelID + "," + productID + ",'" + currentTime + "', 1)";
        stmt.executeUpdate(sql); // DML
        String sqlUpdateProduct = "UPDATE product\n"
                + "SET isBelong = 1\n"
                + "WHERE productID = " + productID;
        stmt.executeUpdate(sqlUpdateProduct);

    }

    public void RemoveAssign(Assign r) throws SQLException, Exception {
        
        String sqlControl = "SELECT * FROM assign\n"
                + "WHERE personnelID =" + r.personnelID + " AND productID = " + r.productID +" AND active = 1";
        ResultSet rs = stmt.executeQuery(sqlControl);
        int ID = 0;
        while(rs.next()){
            ID = rs.getInt("personnelID");
        }
        rs.close();
        
        String sql = "DELETE FROM assign\n"
                + "WHERE personnelID =" + r.personnelID + " AND productID = " + r.productID +" AND active = 1" ;
        stmt.executeUpdate(sql);
        
        if(ID == 0){
            throw new Exception("Böyle bir zimmet bulunmamaktadır.");
        }

        sql = "UPDATE product \n"
                + "SET isBelong = 0 \n"
                + "WHERE productID = " + r.productID;
        stmt.executeUpdate(sql);

    }

    public void RemoveToWasteStorage(Product p) throws SQLException, Exception {
        String sqlControl = "SELECT * FROM product\n"
                + "WHERE isWaste = 1 AND productID = "+p.productID;
        ResultSet rs = stmt.executeQuery(sqlControl);
        while(rs.next()){
            throw new Exception("Bu ürün zaten atığa ayrılmış");
        }
        String sql = "UPDATE product\n"
                + "SET isBelong = 0, isWaste = 1\n"
                + "WHERE  productID = " + p.productID;
        stmt.executeUpdate(sql);

    }

    public void RemoveFromWasteStorage(Product p) throws SQLException {

        String sql = "UPDATE product\n"
                + "SET isWaste = 0\n"
                + "WHERE  productID = " + p.productID;
        stmt.executeUpdate(sql);

    }

    public ArrayList<Product> getProductsInAssign(Personnel p) throws SQLException {
        ArrayList<Product> products = new ArrayList<Product>();

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

        return products;
    }

    public ArrayList<Department> getDepartments() throws SQLException {
        ArrayList<Department> departments = new ArrayList<Department>();

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

        return departments;
    }

    public ArrayList<Job> getJobs() throws SQLException {
        ArrayList<Job> jobs = new ArrayList<Job>();

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

        return jobs;
    }

    public ArrayList<Company> getCompanies() throws SQLException {
        ArrayList<Company> companies = new ArrayList<Company>();

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

        return companies;
    }

    public ArrayList<Personnel> getPersonnels(String departmentName) throws SQLException {
        ArrayList<Personnel> personnels = new ArrayList<Personnel>();

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

        return personnels;
    }

    public void Insert(Product p) throws SQLException, Exception {
        
        String sqlControl = "SELECT * FROM product\n"+
                "WHERE productID = "+p.productID;
        ResultSet rs = stmt.executeQuery(sqlControl);
        while(rs.next()){
            throw new Exception("Bu ürün zaten eklenmiş");
        }
        
        int companyID = 1;
        ArrayList<Company> companies = this.getCompanies();
        
        for (Company co : companies) {
            if (p.company.equals(co.name)) {
                companyID = co.companyID;
                break;
            }
        }
        java.text.SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(p.purchaseDate);

        String sql = "INSERT INTO product(productID,brand, definition,price, companyID, isBelong, isWaste, purchaseDate)\n"
                + "VALUES(" + p.productID + ",'" + p.brand + "','" + p.definition + "'," + p.price + "," + companyID
                + "," + p.isBelong + ", " + p.isWaste + " ,'" + currentTime + "')";
        stmt.executeUpdate(sql);

    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////BUY////////////////////////////////////////
    public ArrayList<BuyStock> getProducts() throws SQLException {
        ArrayList<BuyStock> products = new ArrayList<BuyStock>();

        String sql = "SELECT * FROM buystock";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            BuyStock bs = new BuyStock();
            bs.productID = rs.getInt("productID");
            bs.brand = rs.getString("brand");
            bs.definition = rs.getString("definition");
            bs.price = rs.getDouble("price");
            int companyID = rs.getInt("companyID");
            
            String company = "";
            for (Company co : DBHelper.Companies) {
                if (companyID == co.companyID) {
                    company = co.name;
                    break;
                }
            }
            
            bs.company = company;
            products.add(bs);
        }

        return products;
    }

    public void DeleteProductsBuy(BuyStock bs) throws SQLException {

        String sql = "DELETE FROM buystock WHERE productID = " + bs.productID;
        stmt.executeUpdate(sql);

    }
    
    public void Insert(BuyStock bs) throws SQLException{
        
        
        int companyID = 1;
        ArrayList<Company> companies = this.getCompanies();
        
        for (Company co : companies) {
            if (bs.company.equals(co.name)) {
                companyID = co.companyID;
                break;
            }
        }

        String sql = "INSERT INTO buystock(productID,brand, definition,price, companyID)\n"
                + "VALUES(" + bs.productID + ",'" + bs.brand + "','" + bs.definition + "'," + bs.price + "," + companyID+ ")";
        stmt.executeUpdate(sql);

    }

}
