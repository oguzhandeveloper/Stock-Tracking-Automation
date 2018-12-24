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

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXTreeTableView;
import java.util.ArrayList;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SortEvent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OGUZHAN
 */
public class DepartmentManagerController implements Initializable {

   

    TreeTableViewer treeTableViewer = new TreeTableViewer();

    //AccessFXML
    AccessFXML accessFXML = new AccessFXML();

    //isTurnPersonnel is who has a turn
    public boolean isTurnPersonnel;
    @FXML
    private AnchorPane anchorPaneDepartment;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewPersonnels();
        AccessFXML.ap = this.anchorPaneDepartment;

        UpdateTop();

    }

    /**
     * En üste bulunan kişisel alanı günceller
     */
    public void UpdateTop() {
        labelWindowName.setText(AccessFXML.personnelCurrent.name + " " + AccessFXML.personnelCurrent.lastName);
        labelWindowJob.setText(AccessFXML.personnelCurrent.Job);
    }

    /**
     * Personnel Panelinde personnelleri tabview'a yüklemek için
     */
    public void viewPersonnels() {
        treeTableViewer.viewPersonnels(treeTableViewPersonnels, AccessFXML.personnelCurrent.Department);
    }

    /**
     * Assigns Paneldeki zimmetleri günceller
     */
    public void viewAssignsProducts() {
        treeTableViewer.viewAssignAll(treeTableViewResposibility, AccessFXML.personnelCurrent.Department);
    }

    /**
     * Personnel Panel içerisinde seçilmiş olan nesneyi geriye döndürür
     *
     * @return item Geriye dönen liste içerisinde seçilmiş olan nesne
     */
    public TreeItem<PersonnelTreeTable> isSelectedItemPersonnel() {
        TreeItem<PersonnelTreeTable> item = treeTableViewPersonnels.getSelectionModel().getSelectedItem();
        isNullItem(item);
        return item;
    }

    /**
     * Assings Panel içerisinde seçilmiş olan nesneyi geriye döndürür
     *
     * @return item Geriye dönen liste içerisinde seçilmiş olan nesne
     */
    public TreeItem<AssignTreeTable> isSelectedItemAssign() {
        TreeItem<AssignTreeTable> item = treeTableViewResposibility.getSelectionModel().getSelectedItem();
        isNullItem(item);
        return item;
    }

    /**
     * Bir treeTableView nesnesi içersinde bir nesnenin seçilip seçilmediğini
     * kontrol etmek için kullanılır. Nesne seçilmiş ise bir işlem yapmaz.
     * Seçilmemiş ise ekrana bir hata mesajı döndürür. Bu fonksiyon kullanıldığı
     * yerden sonraki işlemler için herhangi bir değer döndürmez. Kullanıldığı
     * yerde TreeItem<T> item nesnesi işlemi durdurmak için tekrar kontrol
     * edilmelidir.
     *
     * @param <T> TreeItem<T> RecursiveTreeObject<T> class'ından kalıtılmış olan
     * nesne tipinde işlem gerçekleştirmek için
     * @param t TreeItem<T> nesnesi
     */
    private <T> void isNullItem(TreeItem<T> t) {
        if (t == null) {
            accessFXML._modal("Error", "No Items Selected! Please Select The Item In Table!", "OKEY", anchorPaneDepartment);
        }
    }

    /**
     * Personnel Panel içerisinde bulunan textField ve toggleButton temizler
     */
    public void cleanPersonnelTexts() {
        textFieldPersonnelID.setText("");
        textFieldDepartment.setText("");
        textFieldJob.setText("");
        textFieldLastName.setText("");
        textFieldName.setText("");
        toggleButtonActive.setSelected(false);
    }

    /**
     * Profile Panelindeki bilileri günceller
     */
    private void uploadProfile() {
        Personnel p = AccessFXML.personnelCurrent;
        textFieldProfilePersonnelID.setText(p.personnelID + "");
        textFieldProfileUsername.setText(p.username);
        textFieldProfileName.setText(p.name);
        textFieldProfileLastName.setText(p.lastName);

        ArrayList<Job> jobs = null;
        ArrayList<Department> departments = null;

        try {
            DBHelper db = new DBHelper();
            db.Open();
            jobs = DBHelper.Jobs;
            departments = DBHelper.Departments;
            db.Close();
        } catch (Exception ex) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + ex, "OKEY", null);
            return;
        }

        String[] jobStrings = new String[jobs.size()];
        String[] departmentStrings = new String[departments.size()];
        //String[] jobStrings = new String[jobs.size()];
        for (int i = 0; i < jobs.size(); i++) {
            jobStrings[i] = jobs.get(i).name;
        }
        for (int i = 0; i < departments.size(); i++) {
            departmentStrings[i] = departments.get(i).name;
        }

        comboBoxProfileJob.getItems().addAll(jobStrings);
        comboBoxProfileDepartment.getItems().addAll(departmentStrings);
        comboBoxProfileJob.setValue(p.Job);
        comboBoxProfileDepartment.setValue(p.Department);
    }

    /**
     * Çıkış yapar ve girişe gidilir
     * @param event 
     */
    @FXML
    void buttonExit_Click(ActionEvent event) {
        AccessFXML.personnelCurrent = null;
        accessFXML.show("SignIn.fxml", "Sign In", anchorPaneDepartment);
    }

    ///////////////////////////////////////////////////////////////////////////
    /////////////////////////////LEFT VBOX PANEL///////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Profile Paneline girmemize Yarar
     * @param event 
     */
    @FXML
    void buttonProfile_Click(ActionEvent event) {
        SingleSelectionModel<Tab> selectionModel = tabPanePersonnel.getSelectionModel();
        selectionModel.select(tabProfile);
        uploadProfile();
    }

    /**
     * Personnel Paneline gitmemize yarar
     * @param event 
     */
    @FXML
    private void buttonPersonnel_Click(ActionEvent event) {
        SingleSelectionModel<Tab> selectionModel = tabPanePersonnel.getSelectionModel();
        selectionModel.select(tabPersonnel);
        cleanPersonnelTexts();
        viewPersonnels();
    }
    
    /**
     * Assign Paneline gitmemize yarar
     * @param event 
     */
    @FXML
    void buttonAssigns_Click(ActionEvent event) {
        SingleSelectionModel<Tab> selectionModel = tabPanePersonnel.getSelectionModel();
        selectionModel.select(tabresponsibilities);
        viewAssignsProducts();
    }

    ///////////////////////////////////////////////////////////////////////////
    /////////////////////////////PERSONNEL PANEL///////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Personnel panelinde treeTableView'da seçilen personeli ilgili
     * bileşenlere ekler
     * @param event 
     */
    @FXML
    private void treeTableViewPersonnels_Click(MouseEvent event) {
        TreeItem<PersonnelTreeTable> item = treeTableViewPersonnels.getSelectionModel().getSelectedItem();
        PersonnelTreeTable ptt = item.getValue();
        textFieldPersonnelID.setText(ptt.personnelID.getValue());
        textFieldName.setText(ptt.name.getValue());
        textFieldLastName.setText(ptt.lastName.getValue());
        textFieldJob.setText(ptt.Job.getValue());
        textFieldDepartment.setText(ptt.Department.getValue());
        toggleButtonActive.setSelected(ptt.Active.getValue().equals("1"));

    }

    /**
     * Aynı departman altında bulunan bürün personnelerin zimmetlerini
     * rapor olarak çıkartır.
     * @param event 
     */
    @FXML
    void buttonAllPersonnelReports_Click(ActionEvent event) {
        //TODO
        Report report = new Report();
        report.ShowReport(AccessFXML.personnelCurrent.Department);
    }

    /**
     * Seçilen personnelin zimmetlerini rapor olarak çıkartır
     * @param event 
     */
    @FXML
    void buttonPersonnelReport_Click(ActionEvent event) {
        TreeItem<PersonnelTreeTable> item = isSelectedItemPersonnel();
        if (item == null) {
            return;
        }
        PersonnelTreeTable ptt = item.getValue();
        Personnel p = ptt.returnPersonnel();
        Report report = new Report();
        report.ShowReport(p);
    }

    ///////////////////////////////////////////////////////////////////////////
    /////////////////////////////PROFILE PANEL///////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Girilen Profile bilgilerini günceller
     * @param event 
     */
    @FXML
    void buttonProfileUpdate_Click(ActionEvent event) {
        int personnelID = Integer.parseInt(textFieldProfilePersonnelID.getText());
        String username = textFieldProfileUsername.getText();
        String name = textFieldProfileName.getText();
        String lastName = textFieldProfileLastName.getText();
        String job = comboBoxProfileJob.getValue();
        String department = comboBoxProfileDepartment.getValue();
        String password = textPasswordFieldProfileConfirm.getText();
        try {
            DBHelper db = new DBHelper();
            db.Open();
            Personnel p = db.ProfileCheck(personnelID, password);

            if (p == null) {
                accessFXML._modal("Error", "Password Is Wrong!", "OKEY", anchorPaneDepartment);
                return;
            }

            Personnel pr = new Personnel();
            pr.personnelID = personnelID;
            pr.name = name;
            pr.username = username;
            pr.lastName = lastName;
            pr.Department = department;
            pr.Job = job;
            pr.Active = 1;

            db.Update(pr);
            db.Close();
            AccessFXML.personnelCurrent = pr;
        } catch (Exception ex) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + ex, "OKEY", null);
            return;
        }

        uploadProfile();
        UpdateTop();
        textPasswordFieldProfileConfirm.setText("");
    }

    ///////////////////////////////////////////////////////////////////////////
    /////////////////////////////ASSIGNS PANEL///////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Assigns Panel içerisnde bulunan ürünleri yeniler
     * @param event 
     */
    @FXML
    void buttonProductResponsibility_Click(ActionEvent event) {
        viewAssignsProducts();

    }
    
    ///////////////////////////////////////////////////////////////////////////
    /////////////////////////////COMPONENTS///////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
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
    private JFXButton buttonPersonnelReport;

    @FXML
    private JFXButton buttonAllPersonnelReports;

    @FXML
    private JFXTreeTableView<PersonnelTreeTable> treeTableViewPersonnels;

    @FXML
    private Tab tabresponsibilities;

    @FXML
    private JFXTreeTableView<AssignTreeTable> treeTableViewResposibility;

    @FXML
    private JFXButton buttonProductResponsibility;

    @FXML
    private Tab tabProfile;

    @FXML
    private JFXTextField textFieldProfileUsername;

    @FXML
    private JFXTextField textFieldProfileName;

    @FXML
    private JFXPasswordField textPasswordFieldProfileConfirm;

    @FXML
    private JFXTextField textFieldProfileLastName;

    @FXML
    private JFXComboBox<String> comboBoxProfileJob;

    @FXML
    private JFXComboBox<String> comboBoxProfileDepartment;

    @FXML
    private JFXButton buttonProfileUpdate;

    @FXML
    private JFXTextField textFieldProfilePersonnelID;

    @FXML
    private Label labelWindowJob;

    @FXML
    private Label labelWindowName;

    @FXML
    private JFXButton buttonExit;

    @FXML
    private JFXButton buttonPersonnel;

    @FXML
    private JFXButton buttonAssigns;

    @FXML
    private JFXButton buttonProfile;

}
