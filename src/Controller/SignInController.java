/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.*;
import Model.*;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OGUZHAN
 */
public class SignInController implements Initializable {

    @FXML
    private JFXTextField textUsernameField;
    @FXML
    private JFXPasswordField textPasswordField;

    View.AccessFXML accessFXML = new View.AccessFXML();
    @FXML
    private AnchorPane signInAnchorPane;
    @FXML
    private JFXButton buttonSignIn;
    @FXML
    private JFXButton buttonSignUp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (AccessFXML.personnelCurrent == null) {
            return;
        }
        if (AccessFXML.personnelCurrent.username != null && AccessFXML.personnelCurrent.password != null) {
            textUsernameField.setText(AccessFXML.personnelCurrent.username);
            textPasswordField.setText(AccessFXML.personnelCurrent.password);
        }
        AccessFXML.ap = this.signInAnchorPane;
    }

    /**
     * Sign Up Formuna girmek için kullanılır
     * @param event 
     */
    @FXML
    private void buttonSignUp_Click(ActionEvent event) {
        accessFXML.show("SignUp.fxml", "Sign Up Panel", signInAnchorPane);
    }

    /**
     * Giriş bilgileri girildikten sonra giriş yapılır. Ve ilgili panel yönlendirilir
     * @param event 
     */
    @FXML
    private void buttonSignIn_Click(ActionEvent event) {
        String username = textUsernameField.getText();
        String password = textPasswordField.getText();

        if (username.equals("") || password.equals("")) {
            accessFXML._modal("Error", "Username or Password Could Not Empty", "OKEY", signInAnchorPane);
            return;
        }
        Personnel p = null;
        try {
            DBHelper dp = new DBHelper();
            dp.Open();
            p = dp.getPersonnel(username, password);
            dp.Close();
        } catch (Exception ex) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + ex, "OKEY", signInAnchorPane);
            return;
        }

        if (p == null) {
            accessFXML._modal("Error", "Username or Password is Wrong", "OKEY", signInAnchorPane);
            return;
        }

        if (p.Active == 0) {
            accessFXML._modal("Error", "This Personnel Is Not Active!Please You Must See The Admin!", "OKEY", signInAnchorPane);
            return;
        }

        AccessFXML.personnelCurrent = p;

        if (p.Job.equals("Admin")) {
            accessFXML.show("AdminScreen.fxml", "Admin Managment Panel", signInAnchorPane);
        } else if (p.Job.equals("Sales Responsible")) {
            accessFXML.show("AdminScreen.fxml", "Sales Managment Panel", signInAnchorPane);
        } else if (p.Job.equals("Department Manager")) {
            accessFXML.show("DepartmentManagerScreen.fxml", "Department Manager Panel", signInAnchorPane);
        } else {
            accessFXML._modal("DEACTİVE", "This personnel can't use the managment app now! Please TRY LATER!", "OKEY", signInAnchorPane);

        }

    }

}
