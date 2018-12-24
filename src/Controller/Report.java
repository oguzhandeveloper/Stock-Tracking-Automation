/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import View.*;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author OGUZHAN
 */
public class Report extends JFrame {

    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost:3306/stocktrackingdatabase?autoReconnect=true&useSSL=false&useUnicode=true&characterEncoding=utf8";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection conn = null;
    private Statement stmt = null;

    public Report() {

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setSize(900, 800);

        try {
            Class.forName(JDBC_DRIVER);
            Open();
        } catch (Exception e) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Database bağlantısını açar
     */
    public void Open() {
        try {
            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            //System.out.println("Creating statement...");
            stmt = conn.createStatement();

        } catch (SQLException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Database bağlantısını kapatır
     */
    public void Close() {
        try {
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Tüm zimmeti olan personnelleri listeler
     */
    public void ShowReport() {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("C:\\Users\\OGUZHAN\\Documents\\NetBeansProjects\\Stock Tracking Automation\\src\\View\\AssignReport.jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
            JRViewer viewer = new JRViewer(jasperPrint);
            viewer.setOpaque(true);
            viewer.setVisible(true);

            this.add(viewer);
            this.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sadece Tek bir personele ait olan zimmetleri listeler
     *
     * @param p Personnel Seçilmiş olan personeldir
     */
    public void ShowReport(Personnel p) {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load("C:\\Users\\OGUZHAN\\Documents\\NetBeansProjects\\Stock Tracking Automation\\src\\View\\AssignReport.jrxml");
            String query = "SELECT p.personnelID, p.name,p.lastname, pr.productID, pr.brand, pr.definition,\n"
                    + "a.assignDate FROM assign a \n"
                    + "INNER JOIN personnel p ON a.personnelID = p.personnelID\n"
                    + "INNER JOIN product pr ON a.productID = pr.productID\n"
                    + "WHERE a.active = 1 AND a.personnelID =" + p.personnelID + " ORDER BY a.assignDate ASC";
            JRDesignQuery jrQuery = new JRDesignQuery();
            jrQuery.setText(query);
            jasperDesign.setQuery(jrQuery);

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
            JRViewer viewer = new JRViewer(jasperPrint);
            viewer.setOpaque(true);
            viewer.setVisible(true);

            this.add(viewer);
            this.setVisible(true);

        } catch (Exception ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * Department üzerinden tüm zimmeti olan personnelleri listeler
     *
     * @param department Departman
     */
    public void ShowReport(String department) {

        try {
            JasperDesign jasperDesign = JRXmlLoader.load("C:\\Users\\OGUZHAN\\Documents\\NetBeansProjects\\Stock Tracking Automation\\src\\View\\AssignReport.jrxml");
            String query = "SELECT p.personnelID, p.name,p.lastname, pr.productID, pr.brand, pr.definition,\n"
                    + "a.assignDate FROM assign a \n"
                    + "INNER JOIN personnel p ON a.personnelID = p.personnelID\n"
                    + "INNER JOIN product pr ON a.productID = pr.productID\n"
                    + "INNER JOIN department d ON d.departmentID = p.departmentID \n"
                    + "WHERE a.active = 1 AND d.name ='" + department + "' ORDER BY a.assignDate ASC";
            JRDesignQuery jrQuery = new JRDesignQuery();
            jrQuery.setText(query);
            jasperDesign.setQuery(jrQuery);

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
            JRViewer viewer = new JRViewer(jasperPrint);
            viewer.setOpaque(true);
            viewer.setVisible(true);

            this.add(viewer);
            this.setVisible(true);

        } catch (Exception ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
