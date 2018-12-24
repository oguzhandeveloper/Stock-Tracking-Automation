/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import View.*;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OGUZHAN
 */
public class SignUpController implements Initializable {

    @FXML
    private JFXButton buttonSignIn;
    @FXML
    private JFXButton buttonSignUp;
    @FXML
    private JFXTextField textUsernameField;
    @FXML
    private JFXTextField textNameField;
    @FXML
    private JFXPasswordField textPasswordField;
    @FXML
    private JFXTextField textLastnameField;
    @FXML
    private JFXComboBox<String> comboBoxJob;
    @FXML
    private JFXComboBox<String> comboBoxDepartment;
    @FXML
    private AnchorPane signUpAnchorPane;

    View.AccessFXML accessFXML = new View.AccessFXML();
    @FXML
    private JFXPasswordField textPasswordConfirmField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        AccessFXML.ap = this.signUpAnchorPane;
        try {
            DBHelper db = new DBHelper();
            db.Open();
            ArrayList<Job> jobs = DBHelper.Jobs;
            ArrayList<Department> departments = DBHelper.Departments;

            String[] jobStrings = new String[jobs.size()];
            String[] departmentStrings = new String[departments.size()];
            //String[] jobStrings = new String[jobs.size()];
            for (int i = 0; i < jobs.size(); i++) {
                jobStrings[i] = jobs.get(i).name;
            }
            for (int i = 0; i < departments.size(); i++) {
                departmentStrings[i] = departments.get(i).name;
            }

            db.Close();
            comboBoxJob.getItems().addAll(jobStrings);
            comboBoxDepartment.getItems().addAll(departmentStrings);
        } catch (Exception e) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
            return;
        }
    }

    @FXML
    private void buttonSignIn_Click(ActionEvent event) {
        accessFXML.show("SignIn.fxml", "Sign In Panel", signUpAnchorPane);
        AccessFXML.personnelCurrent = null;
    }

    @FXML
    private void buttonSignUp_Click(ActionEvent event) {
        String username = textUsernameField.getText();
        String password = textPasswordField.getText();
        String passwordConfirm = textPasswordConfirmField.getText();
        String name = textNameField.getText();
        String lastName = textLastnameField.getText();
        String job = comboBoxJob.getValue();
        String department = comboBoxDepartment.getValue();

        //Herhangi bir alanın boş olup olmadığını kontrol ediyor.
        if (username.trim().equals("") || password.trim().equals("") || passwordConfirm.trim().equals("")
                || name.trim().equals("") || lastName.trim().equals("")
                || job.trim().equals("") || department.trim().equals("")) {
            accessFXML._modal("Missing", "You can not pass no space empty! Please Check!", "OKEY", signUpAnchorPane);
            return;
        }
        //İki Şifreninde aynı olup olmadığını kontrol ediyor
        if (!password.equals(passwordConfirm)) {
            accessFXML._modal("Error", "Password And Again Password is not same! Please Check!", "OKEY", signUpAnchorPane);
            return;
        }

        try {
            DBHelper db = new DBHelper();
            db.Open();

            Personnel p = db.PersonnelCheck(username);
            //Personel Zaten Var mı?
            if (p != null) {
                accessFXML._modal("Error", "This Username is Already Exist. You Can Not Sign Up Again! Please Check!", "OKEY", signUpAnchorPane);
                return;
            }

            Personnel p1 = new Personnel();
            p1.username = username;
            p1.password = password;
            p1.name = name;
            p1.lastName = lastName;
            p1.Department = department;
            p1.Job = job;
            p1.Active = 1;

            db.Insert(p1);
            db.Close();
            AccessFXML.personnelCurrent = p1;
        } catch (Exception e) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
            return;
        }

        accessFXML.show("SignIn.fxml", "Sign In Panel", signUpAnchorPane);
    }

    @FXML
    private void comboBoxJob_Click(ActionEvent event) {
    }

}
