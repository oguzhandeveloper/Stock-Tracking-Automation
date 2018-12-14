/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.*;
import Model.*;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.layout.BorderPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 *
 * @author OGUZHAN
 */
public class AdminScreenController implements Initializable {

    @FXML
    private AnchorPane anchorPaneAdmin;
    @FXML
    private TabPane tabPanePersonnel;
    @FXML
    private Tab tabPersonnel;
    @FXML
    private JFXTextField textFieldPersonnelID;
    @FXML
    private JFXTextField textFieldName;
    @FXML
    private JFXTextField textFieldLastName;
    @FXML
    private JFXTextField textFieldJob;
    @FXML
    private JFXTextField textFieldDepartment;
    @FXML
    private JFXToggleButton toggleButtonActive;
    @FXML
    private JFXButton buttonUpdatePersonnel;
    @FXML
    private JFXButton buttonResponsibilityRemove;
    @FXML
    private JFXButton buttonPersonnelReport;
    @FXML
    private JFXTreeTableView<PersonnelTreeTable> treeTableViewPersonnels;
    @FXML
    private JFXButton buttonResponsibilityAdd;
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
    @FXML
    private JFXButton buttonPersonnel;
    @FXML
    private JFXButton buttonStock;
    @FXML
    private JFXButton buttonWasteStorage;
    @FXML
    private JFXButton buttonResponsibilities;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewPersonnels();
    }

    public void viewPersonnels() {
        //TreeViewTable içerisine eklenecek olan değişkenlerin columnları belirleniyor.
        JFXTreeTableColumn<PersonnelTreeTable, String> personnelIDColumn = new JFXTreeTableColumn<>("ID");
        personnelIDColumn.setPrefWidth(50);
        personnelIDColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PersonnelTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PersonnelTreeTable, String> param) {
                return param.getValue().getValue().ID;
            }
        });
        JFXTreeTableColumn<PersonnelTreeTable, String> personnelNameColumn = new JFXTreeTableColumn<>("Name");
        personnelNameColumn.setPrefWidth(150);
        personnelNameColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PersonnelTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PersonnelTreeTable, String> param) {
                return param.getValue().getValue().name;
            }
        });
        JFXTreeTableColumn<PersonnelTreeTable, String> personnelLastNameColumn = new JFXTreeTableColumn<>("Last Name");
        personnelLastNameColumn.setPrefWidth(150);
        personnelLastNameColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PersonnelTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PersonnelTreeTable, String> param) {
                return param.getValue().getValue().lastName;
            }
        });
        
        JFXTreeTableColumn<PersonnelTreeTable, String> personnelJobColumn = new JFXTreeTableColumn<>("Job");
        personnelJobColumn.setPrefWidth(150);
        personnelJobColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PersonnelTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PersonnelTreeTable, String> param) {
                return param.getValue().getValue().Job;
            }
        });
        JFXTreeTableColumn<PersonnelTreeTable, String> personnelDepartmentColumn = new JFXTreeTableColumn<>("Department");
        personnelDepartmentColumn.setPrefWidth(150);
        personnelDepartmentColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PersonnelTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PersonnelTreeTable, String> param) {
                return param.getValue().getValue().Department;
            }
        });
        JFXTreeTableColumn<PersonnelTreeTable, String> personnelActiveColumn = new JFXTreeTableColumn<>("Active");
        personnelActiveColumn.setPrefWidth(70);
        personnelActiveColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PersonnelTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PersonnelTreeTable, String> param) {
                return param.getValue().getValue().Active;
            }
        });
        
        //treetableview içersine değerler eklenmeden önce değerler arası erişimin(ilişkilerin nasıl olacağı) yapısı belirleniyor.
        ObservableList<PersonnelTreeTable> personnelsTreeTables = FXCollections.observableArrayList();
        
        DBHelper db  = new DBHelper();
        db.Open();
        ArrayList<Personnel> personnels = db.getPersonnels();
        db.Close();
        
        //Orjinal Personnel sınıfını treetableview'ın alabileceği formata dönüştüryoruz.
        for(Personnel p: personnels){
            personnelsTreeTables.add(new PersonnelTreeTable(p.ID+"", p.name, p.lastName, p.Job, p.Department,p.Active+""));
        }
        
        //içersindeki değerleri yazdırıken bağlı olması gereken root(yani değerleri neredem çekecek) değişkeni tanımlanıyor
        //ve treeviewtable column'ları ayarlanıyor.
        final TreeItem<PersonnelTreeTable> root = new RecursiveTreeItem<PersonnelTreeTable>(personnelsTreeTables, RecursiveTreeObject::getChildren);
        treeTableViewPersonnels.getColumns().setAll(personnelIDColumn,personnelNameColumn,personnelLastNameColumn,
                personnelJobColumn,personnelDepartmentColumn,personnelActiveColumn);
        treeTableViewPersonnels.setRoot(root);
        treeTableViewPersonnels.setShowRoot(false);
        
        
    }

    @FXML
    private void buttonUpdatePersonnel_Click(ActionEvent event) {
        String name = textFieldName.getText();
        String lastname = textFieldLastName.getText();
        int personnelID = Integer.parseInt(textFieldPersonnelID.getText());
        String job = textFieldJob.getText();
        String department = textFieldDepartment.getText();
        int active = toggleButtonActive.isSelected()? 1: 0;
        
        Personnel person = new Personnel();
        person.ID = personnelID;
        person.name = name;
        person.lastName = lastname;
        person.Job = job;
        person.Department = department;
        person.Active = active;
        
        DBHelper db  = new DBHelper();
        db.Open();
        db.Update(person);
        db.Close();
        viewPersonnels();
        
    }

    @FXML
    private void buttonResponsibilityRemove_Click(ActionEvent event) {

    }

    @FXML
    private void buttonPersonnelReport_Click(ActionEvent event) {

    }

    @FXML
    private void buttonResponsibilityAdd_Click(ActionEvent event) {

    }

    @FXML
    private void buttonPersonnel_Click(ActionEvent event) {
        SingleSelectionModel<Tab> selectionModel = tabPanePersonnel.getSelectionModel();
        selectionModel.select(tabPersonnel);
        viewPersonnels();
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

    @FXML
    private void treeTableViewPersonnels_Click(MouseEvent event) {
        TreeItem<PersonnelTreeTable> item = treeTableViewPersonnels.getSelectionModel().getSelectedItem();
        PersonnelTreeTable ptt= item.getValue();
        textFieldPersonnelID.setText(ptt.ID.getValue());
        textFieldName.setText(ptt.name.getValue());
        textFieldLastName.setText(ptt.lastName.getValue());
        textFieldJob.setText(ptt.Job.getValue());
        textFieldDepartment.setText(ptt.Department.getValue());
        toggleButtonActive.setSelected(ptt.Active.getValue().equals("1"));
        
    }

}
