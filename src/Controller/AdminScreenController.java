/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author OGUZHAN
 */
public class AdminScreenController implements Initializable {

    @FXML
    private JFXButton buttonPersonnel;
    @FXML
    private Tab tabPersonnel;
    @FXML
    private TabPane tabPanePersonnel;
    @FXML
    private JFXButton buttonStock;
    @FXML
    private JFXButton buttonWasteStorage;
    @FXML
    private JFXButton buttonResponsibilities;
    @FXML
    private Tab tabStock;
    @FXML
    private Tab tabWasteStorage;
    @FXML
    private Tab tabAssignResponsibility;
    @FXML
    private Tab tabReport;
    @FXML
    private Tab tabRemoveResponsibility;
    @FXML
    private Tab tabresponsibilities;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buttonPersonnel_Click(ActionEvent event) {
        SingleSelectionModel<Tab> selectionModel = tabPanePersonnel.getSelectionModel();
        selectionModel.select(tabPersonnel);
    }

    @FXML
    private void buttonStock_Click(ActionEvent event) {
        SingleSelectionModel<Tab> selectionModel = tabPanePersonnel.getSelectionModel();
        selectionModel.select(tabStock);
    }

    @FXML
    private void buttonWasteStorage_Click(ActionEvent event) {
        SingleSelectionModel<Tab> selectionModel = tabPanePersonnel.getSelectionModel();
        selectionModel.select(tabWasteStorage);
    }

    @FXML
    private void buttonResponsibilities_Click(ActionEvent event) {
        SingleSelectionModel<Tab> selectionModel = tabPanePersonnel.getSelectionModel();
        selectionModel.select(tabresponsibilities);
    }
    
}
