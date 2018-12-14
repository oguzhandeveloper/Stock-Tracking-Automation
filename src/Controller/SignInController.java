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
        if(AccessFXML.personnelCurrent == null)
            return;
        if (AccessFXML.personnelCurrent.username != null && AccessFXML.personnelCurrent.password != null) {
            textUsernameField.setText(AccessFXML.personnelCurrent.username);
            textPasswordField.setText(AccessFXML.personnelCurrent.password);
        }
    }

    @FXML
    private void buttonSignUp_Click(ActionEvent event) {
        accessFXML.show("SignUp.fxml", "Sign Up", signInAnchorPane);
    }

    @FXML
    private void buttonSignIn_Click(ActionEvent event) {
        String username = textUsernameField.getText();
        String password = textPasswordField.getText();

        if (username.equals("") || password.equals("")) {
            accessFXML._modal("Error", "Username or Password Could Not Empty", "OKEY", signInAnchorPane);
            return;
        }
        
        DBHelper dp = new DBHelper();
        dp.Open();
        Personnel p = dp.getPersonnel(username, password);
        dp.Close();

        if (p == null) {
            accessFXML._modal("Error", "Username or Password is Wrong", "OKEY", signInAnchorPane);
            return;
        }
        
        accessFXML.personnelCurrent = p;
        if (p.Job.equals("Admin"))
            accessFXML.show("AdminScreen.fxml", "Admin Panel", signInAnchorPane);

    }

}
