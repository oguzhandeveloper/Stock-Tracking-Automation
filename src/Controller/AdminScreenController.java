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
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author OGUZHAN
 */
public class AdminScreenController implements Initializable {

    ///TreeViews TreeTableView içerisine eklenecek olanları ayarlar
    TreeTableViewer treeTableViewer = new TreeTableViewer();

    //AccessFXML
    AccessFXML accessFXML = new AccessFXML();

    //isTurnPersonnel is who has a turn
    public boolean isTurnPersonnel;
    @FXML
    private JFXTextField textFieldBuyBrand;
    @FXML
    private JFXTextField textFieldBuyDefinition;
    @FXML
    private JFXTextField textFieldBuyPrice;
    @FXML
    private JFXComboBox<String> comboBoxCompanyBuy;
    @FXML
    private JFXButton buttonBuyProductAdd;
    

    /**
     * Initialize
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewPersonnels();
        AccessFXML.ap = this.anchorPaneAdmin;

        UpdateTop();
        if (AccessFXML.personnelCurrent.Job.equals("Sales Responsible")) {
            buttonUpdatePersonnel.setVisible(false);
        }
        initializeEvent();
    }

    /**
     * Panelin en üstündeki alanı güncellemek için
     */
    public void UpdateTop() {
        labelWindowName.setText(AccessFXML.personnelCurrent.name + " " + AccessFXML.personnelCurrent.lastName);
        labelWindowJob.setText(AccessFXML.personnelCurrent.Job);
    }

    /**
     * Personnel Panelinde personnelleri tabview'a yüklemek için
     */
    public void viewPersonnels() {
        treeTableViewer.viewPersonnels(treeTableViewPersonnels, "");
    }

    /**
     * Stock Panelinde produc'ları tabview'a yüklemek için
     */
    public void viewProductsInStock() {
        treeTableViewer.viewProduct(treeTableViewStock, false);
    }

    /**
     * Waste Storage Panelinde produc'ları tabview'a yüklemek için
     */
    public void viewProductsInWasteStorage() {
        treeTableViewer.viewProduct(treeTableViewWasteStorage, true);
    }

    /**
     * Assings (Zimmetler) olduğu panelde product'ları yüklemek için
     */
    public void viewAssignsProducts() {
        treeTableViewer.viewAssignAll(treeTableViewResposibility, "");
    }

    /**
     * AssingAdd (Zimmet atama) olduğu panelde product'ları yüklemek için
     */
    public void viewAssignAddProducts() {
        treeTableViewer.viewAssignAddProduct(treeTableViewResposibilityAssign);
    }

    /**
     * Seçilen personnelin üstüne zimmetli tüm ürünleri görmek için
     *
     * @param p Seçilen Personnel
     */
    public void viewAssignInPersonnel(Personnel p) {
        treeTableViewer.viewAssignInRemoveAssign(treeTableViewRemoveAssign, p);
    }

    /**
     * AssingAdd (Zimmet atama) olduğu panelde personnel'lerı yüklemek için
     */
    public void viewAssignAddPersonnels() {
        treeTableViewer.viewAssignAddPersonnel(treeTableViewResposibilityAssign);
    }

    /**
     * Buy Panel içerisindeki satın alınmayan ürünleri listeler
     */
    public void viewBuyProduct() {
        treeTableViewer.viewBuyProduct(treeTableViewBuy);
    }

    /**
     * Stock Panel içersinde seçilmiş olan nesneyi geriye döndürür
     *
     * @return item Geriye dönen liste içerisinde seçilmiş olan nesne
     */
    public TreeItem<ProductTreeTable> isSelectedItemStock() {
        TreeItem<ProductTreeTable> item = treeTableViewStock.getSelectionModel().getSelectedItem();
        isNullItem(item);
        return item;
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
     * AssingAdd Panel içerisinde seçilmiş olan nesneyi geriye döndürür
     *
     * @return item Geriye dönen liste içerisinde seçilmiş olan nesne
     */
    public TreeItem<AssignFullTreeTable> isSelectedItemAssignAdd() {
        TreeItem<AssignFullTreeTable> item = treeTableViewResposibilityAssign.getSelectionModel().getSelectedItem();
        isNullItem(item);
        return item;
    }

    /**
     * Remove Assign Panel içerisinde seçilmiş olan nesneyi geriye döndürür
     *
     * @return item Geriye dönen liste içerisinde seçilmiş olan nesne
     */
    public TreeItem<AssignFullTreeTable> isSelectedItemRemoveAssign() {
        TreeItem<AssignFullTreeTable> item = treeTableViewRemoveAssign.getSelectionModel().getSelectedItem();
        isNullItem(item);
        return item;
    }

    /**
     * Waste Storage Panel içerisinde seçilmiş olan nesneyi geriye döndürür
     *
     * @return item Geriye dönen liste içerisinde seçilmiş olan nesne
     */
    public TreeItem<ProductTreeTable> isSelectedWasteStorage() {
        TreeItem<ProductTreeTable> item = treeTableViewWasteStorage.getSelectionModel().getSelectedItem();
        isNullItem(item);
        return item;
    }

    /**
     * BUY Panel içerisinde seçilmiş olan nesneyi geriye döndürür
     *
     * @return item Geriye dönen liste içerisinde seçilmiş olan nesne
     */
    public TreeItem<BuyStockTreeTable> isSelectedBuyProduct() {
        TreeItem<BuyStockTreeTable> item = treeTableViewBuy.getSelectionModel().getSelectedItem();
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
            accessFXML._modal("Error", "No Items Selected! Please Select The Item In Table!", "OKEY", anchorPaneAdmin);
        }
    }

    /**
     * AssignAdd içerisinde bulunan textField alanlarını temizler.
     */
    public void cleanAssignAddTexts() {
        textFieldBrandResponsibilityAssign.setText("");
        textFieldDefinitionResponsibilityAssign.setText("");
        textFieldLastNameResponsibilityAssign.setText("");
        textFieldNameResponsibilityAssign.setText("");
        textFieldPersonnelIDResponsibilityAssign.setText("");
        textFieldProductIDResponsibilityAssign.setText("");
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
     * En üstte bulunan "EXIT" butonuna tıklandıktan sonra giriş sayfasına geri
     * dönülür
     *
     * @param event
     */
    @FXML
    private void buttonExit_Click(ActionEvent event) {
        AccessFXML.personnelCurrent = null;
        accessFXML.show("SignIn.fxml", "Sign In", anchorPaneAdmin);
    }

    /**
     * Personnel Panelinde bulunan treeTableView' da bir personele tıklanırsa
     * seçilmiş olur Seçilen bu nesneni ilgili textField gibi bilieşenlere
     * aktarılımasında kullanılır
     *
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
     * Buy Panelinde bulunan treeTableView' da bir zimmete tıklanırsa seçilmiş
     * olur Seçilen bu nesneni ilgili textField gibi bilieşenlere
     * aktarılımasında kullanılır
     *
     * @param event
     */
    @FXML
    void treeTableViewResposibilityAssign_Click(MouseEvent event) {
        TreeItem<AssignFullTreeTable> item = isSelectedItemAssignAdd();
        if (item == null) {
            return;
        }
        if (isTurnPersonnel) {
            AssignFullTreeTable ptt = item.getValue();
            textFieldPersonnelIDResponsibilityAssign.setText(ptt.personnelID.getValue());
            textFieldNameResponsibilityAssign.setText(ptt.name.getValue());
            textFieldLastNameResponsibilityAssign.setText(ptt.lastName.getValue());
        } else {
            AssignFullTreeTable ptt = item.getValue();
            textFieldBrandResponsibilityAssign.setText(ptt.brand.getValue());
            textFieldDefinitionResponsibilityAssign.setText(ptt.definition.getValue());
            textFieldProductIDResponsibilityAssign.setText(ptt.productID.getValue());
        }

    }

    /**
     * Profile Panelinde bulunan bileşenlerin her yeniden girişinde tekrar
     * doldurulması ve yenilenmesinde kullanılır.
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

    ///////////////////////////////////////////////////////////////////////////
    /////////////////////////////LEFT VBOX PANEL///////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Assign Paneline gidilmek için kullanılan metot Aynı zamanda içerisinde
     * bulunan treeTableView'da ki nesneleri günceller
     *
     * @param event
     */
    @FXML
    private void buttonAssigns_Click(ActionEvent event) {
        SingleSelectionModel<Tab> selectionModel = tabPanePersonnel.getSelectionModel();
        selectionModel.select(tabresponsibilities);
        viewAssignsProducts();
    }

    /**
     * Profile Paneline gidilmek için kullanılan metot Aynı zamanda içerisinde
     * bulunan treeTableView'da ki nesneleri günceller
     *
     * @param event
     */
    @FXML
    private void buttonProfile_Click(ActionEvent event) {
        SingleSelectionModel<Tab> selectionModel = tabPanePersonnel.getSelectionModel();
        selectionModel.select(tabProfile);
        uploadProfile();
    }

    /**
     * Personnel Paneline gidilmek için kullanılan metod Aynı zamanda içerisinde
     * bulunan treeTableView'da ki nesneleri günceller
     *
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
     * Stok Paneline gidilmek için kullanılan metot Aynı zamanda içerisinde
     * bulunan treeTableView'da ki nesneleri günceller
     *
     * @param event
     */
    @FXML
    private void buttonStock_Click(ActionEvent event) {
        SingleSelectionModel<Tab> selectionModel = tabPanePersonnel.getSelectionModel();
        selectionModel.select(tabStock);
        viewProductsInStock();
    }

    /**
     * BUY paneline gidilmek için kullanılır Ayrıca içerisndeki bilgileri
     * yeniler
     *
     * @param event
     */
    @FXML
    void buttonBuy_Click(ActionEvent event) {
        SingleSelectionModel<Tab> selectionModel = tabPanePersonnel.getSelectionModel();
        selectionModel.select(tabBuy);
        ArrayList<Company> companies = null;
        try{
        DBHelper db = new DBHelper();
        db.Open();
        companies = DBHelper.Companies;
        db.Close();
        if(companies == null)
            throw new Exception("Companies didn't load.");
        }catch(Exception e){
            accessFXML._modal("SQL Error", "Sql Querey Error: "+e, "OKEY", anchorPaneAdmin);
        }
        for(Company c: companies){
            comboBoxCompanyBuy.getItems().add(c.name);
        }
        viewBuyProduct();
    }

    /**
     * Waste Storage Paneline gidilmek için kullanılan metod Aynı zamanda
     * içerisinde bulunan treeTableView'da ki nesneleri günceller
     *
     * @param event
     */
    @FXML
    private void buttonWasteStorage_Click(ActionEvent event) {
        SingleSelectionModel<Tab> selectionModel = tabPanePersonnel.getSelectionModel();
        selectionModel.select(tabWasteStorage);
        viewProductsInWasteStorage();
    }

    ///////////////////////////////////////////////////////////////////////////
    /////////////////////////////PERSONNEL PANEL///////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Seçilen personelin üzerine bir zimmet eklemek istenirse kullanılacak
     * metod Personnelin seçilip seçilmediğide kontrol edilir
     *
     * @param event
     */
    @FXML
    private void buttonResponsibilityAdd_Click(ActionEvent event) {
        String id = textFieldPersonnelID.getText();
        TreeItem<PersonnelTreeTable> item = isSelectedItemPersonnel();
        if (id.trim().equals("")) {
            return;
        }

        PersonnelTreeTable ptt = item.getValue();
        isTurnPersonnel = false;
        cleanAssignAddTexts();

        textFieldPersonnelIDResponsibilityAssign.setText(ptt.personnelID.getValue());
        textFieldNameResponsibilityAssign.setText(ptt.name.getValue());
        textFieldLastNameResponsibilityAssign.setText(ptt.lastName.getValue());

        textFieldBrandResponsibilityAssign.setText("");
        textFieldDefinitionResponsibilityAssign.setText("");
        textFieldProductIDResponsibilityAssign.setText("");

        SingleSelectionModel<Tab> selectionModel = tabPanePersonnel.getSelectionModel();
        selectionModel.select(tabAssignResponsibility);

        viewAssignAddProducts();

    }

    /**
     * Seçilen personelin üzerinedeki bir zimmeti düşürmek istenilirse
     * kullanılacak metod Personnelin seçilip seçilmediğide kontrol edilir
     *
     * @param event
     */
    @FXML
    private void buttonResponsibilityRemove_Click(ActionEvent event) {
        TreeItem<PersonnelTreeTable> item = isSelectedItemPersonnel();
        String personnelID = textFieldPersonnelID.getText();
        String name = textFieldName.getText();
        String lastname = textFieldLastName.getText();
        if (personnelID.trim().equals("") /*&& item == null*/) {
            return;
        }

        /*PersonnelTreeTable ptt = item.getValue();

        Personnel p = ptt.returnPersonnel();*/
        Personnel p = new Personnel();
        p.personnelID = Integer.parseInt(personnelID);

        viewAssignInPersonnel(p);
        textFieldRemoveAssignPersonnelID.setText(personnelID);
        textFieldRemoveAssignName.setText(name);
        textFieldRemoveAssignLastName.setText(lastname);

        SingleSelectionModel<Tab> selectionModel = tabPanePersonnel.getSelectionModel();
        selectionModel.select(tabRemoveResponsibility);

    }

    /**
     * Personnel Panel içerisinde seçilen personelin herhangi bir değeri değiş-
     * tirildikten sonra database' de güncellemek için kullanılır
     *
     * @param event
     */
    @FXML
    private void buttonUpdatePersonnel_Click(ActionEvent event) {
        TreeItem<PersonnelTreeTable> item = isSelectedItemPersonnel();
        if (item == null) {
            return;
        }

        String name = textFieldName.getText();
        String lastname = textFieldLastName.getText();
        int personnelID = Integer.parseInt(textFieldPersonnelID.getText());
        String job = textFieldJob.getText();
        String department = textFieldDepartment.getText();
        int active = toggleButtonActive.isSelected() ? 1 : 0;

        Personnel person = new Personnel();
        person.personnelID = personnelID;
        person.name = name;
        person.lastName = lastname;
        person.Job = job;
        person.Department = department;
        person.Active = active;

        try {
            DBHelper db = new DBHelper();
            db.Open();
            db.Update(person);
            db.Close();
        } catch (Exception ex) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + ex, "OKEY", null);
            return;
        }
        viewPersonnels();

    }

    /**
     * Seçilen personle ait zimmetler ile birlikte rapor çıkartılır.
     *
     * @param event
     */
    @FXML
    private void buttonPersonnelReport_Click(ActionEvent event) {
        TreeItem<PersonnelTreeTable> item = isSelectedItemPersonnel();
        if (item == null) {
            return;
        }
        PersonnelTreeTable ptt = item.getValue();
        Personnel p = ptt.returnPersonnel();
        Report report = new Report();
        report.ShowReport(p);

    }

    /**
     * Tüm Personnel'lere ait zimmetler ile rapor çıkartılır
     *
     * @param event
     */
    @FXML
    private void buttonAllPersonnelReports_Click(ActionEvent event) {
        Report report = new Report();
        report.ShowReport();
    }

    ////////////////////////////////////////////////////////////////////////////
    /////////////////////////////STOCK PANEL////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Stock içerisndeki ürünleri yenilemek içimn kullanılır
     *
     * @param event
     */
    @FXML
    private void buttonProductStock_Click(ActionEvent event) {
        viewProductsInStock();
    }

    /**
     * Stok Panel içerisinde seçilen nesnenin bir personnele atamak için Assing
     * butonuna tıklanırsa AssingAdd Paneline gidilir ve orada product
     * bilgilerini ilgili textField' lara ekledikten sonra treeTableView
     * nesnesine Personnel'leri yükler
     *
     * @param event
     */
    @FXML
    void buttonResponsibilityStock_Click(ActionEvent event) {
        TreeItem<ProductTreeTable> item = isSelectedItemStock();
        if (item == null) {
            return;
        }

        ProductTreeTable ptt = item.getValue();
        viewAssignAddPersonnels();

        cleanAssignAddTexts();

        textFieldProductIDResponsibilityAssign.setText(ptt.productID.getValue());
        textFieldDefinitionResponsibilityAssign.setText(ptt.definition.getValue());
        textFieldBrandResponsibilityAssign.setText(ptt.brand.getValue());

        isTurnPersonnel = true;

        SingleSelectionModel<Tab> selectionModel = tabPanePersonnel.getSelectionModel();
        selectionModel.select(tabAssignResponsibility);
    }

    /**
     * Stock içerisnde seçilen nesnenin Waste Storage'e taşınaması
     * istenirse(yani atığa ayrılırsa) İlgili nesneyi treeTableView' dan çeker
     * ve atar.
     *
     * @param event
     */
    @FXML
    void buttonWasteStorageStock_Click(ActionEvent event) {
        TreeItem<ProductTreeTable> item = isSelectedItemStock();
        if (item == null) {
            return;
        }

        ProductTreeTable ptt = item.getValue();
        Product p = ptt.getProduct();

        try {
            DBHelper db = new DBHelper();
            db.Open();
            db.RemoveToWasteStorage(p);
            db.Close();
        } catch (Exception ex) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + ex, "OKEY", null);
            return;
        }
        viewProductsInStock();

    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////WASTE STORAGE PANEL/////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Waste Storage içerisndeki ürünleri yenilemek için kullanılır
     *
     * @param event
     */
    @FXML
    private void buttonProductWasteStorage_Click(ActionEvent event) {
        viewProductsInWasteStorage();
    }

    /**
     * Waste Storage içerisindeki nesneyi kaldırmak ve yeniden stocka eklenmesi
     * için kullanılır
     *
     * @param event
     */
    @FXML
    private void buttonRemoveFromWasteStorage_Click(ActionEvent event) {
        TreeItem<ProductTreeTable> item = isSelectedWasteStorage();
        if (item == null) {
            return;
        }

        ProductTreeTable ptt = item.getValue();
        Product p = ptt.getProduct();
        try {
            DBHelper db = new DBHelper();
            db.Open();
            db.RemoveFromWasteStorage(p);
            db.Close();
        } catch (Exception ex) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + ex, "OKEY", null);
            return;
        }

        viewProductsInWasteStorage();
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////////////////ASSIGNS PANEL/////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Assigns Panel içersindeki zimmetleri yenilemek içib kullanılır
     *
     * @param event
     */
    @FXML
    private void buttonProductResponsibility_Click(ActionEvent event) {
        viewAssignsProducts();
    }

    /**
     * Assigns içerisinde bulunan bir zimmeti kaldırmak için kullanılır
     *
     * @param event
     */
    @FXML
    void buttonResponsibilityRemoveResponsiblity_Click(ActionEvent event) {

        TreeItem<AssignTreeTable> item = isSelectedItemAssign();
        if (item == null) {
            return;
        }

        AssignTreeTable astt = item.getValue();
        Assign r = astt.getResponsibility();
        try {
            DBHelper db = new DBHelper();
            db.Open();
            db.RemoveAssign(r);
            db.Close();
        } catch (Exception ex) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + ex, "OKEY", null);
            return;
        }

        viewAssignsProducts();

    }

    /**
     * Assigns içerisinde bulunan bir zimmeti Waste Storage ' e kaldırmak için
     * kullanılır
     *
     * @param event
     */
    @FXML
    void buttonWasteStorageResponsibility_Click(ActionEvent event) {
        TreeItem<AssignTreeTable> item = isSelectedItemAssign();
        if (item == null) {
            return;
        }

        AssignTreeTable rtt = item.getValue();
        Assign r = rtt.getResponsibility();
        try {
            DBHelper db = new DBHelper();
            db.Open();
            //Ürünün zimmeti kaldırılıyor
            db.RemoveAssign(r);
            //Sonra ürün WasteStorage taşınıyor 
            Product p = new Product();
            p.productID = r.productID;
            db.RemoveToWasteStorage(p);
            db.Close();
        } catch (Exception ex) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + ex, "OKEY", null);
            return;
        }

        SingleSelectionModel<Tab> selectionModel = tabPanePersonnel.getSelectionModel();
        selectionModel.select(tabWasteStorage);
        viewProductsInWasteStorage();

    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////ASSIGN ADD PANEL////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Assign Add Panelinde Eğer seçilen personnel üzreinden zimmet eklemeye
     * gidildiyse listede gözükecek olan ürünleri yeniler
     */
    @FXML
    private void buttonProductResponsibilityAssign_Click(ActionEvent event) {
        if (!isTurnPersonnel) {
            viewAssignAddProducts();
        }
    }

    /**
     * Assign Add Panelinde seçilen personnel ve ürün varsa bunu personnele
     * zimmetler
     *
     * @param event
     */
    @FXML
    void buttonResponsibilityResponsibilityAssign_Click(ActionEvent event) {
        int personnelID = 0;
        int productID = 0;
        String name = null;
        String lastName = null;
        String brand = null;
        String definition = null;
        try {
            personnelID = Integer.parseInt(textFieldPersonnelIDResponsibilityAssign.getText());
            productID = Integer.parseInt(textFieldProductIDResponsibilityAssign.getText());
            name = textFieldNameResponsibilityAssign.getText();
            lastName = textFieldLastNameResponsibilityAssign.getText();
            brand = textFieldBrandResponsibilityAssign.getText();
            definition = textFieldDefinitionResponsibilityAssign.getText();
        } catch (Exception e) {
            accessFXML._modal("Error", "Value Type is Wrong", "OKEY", anchorPaneAdmin);
            return;
        }

        Assign r = new Assign();
        r.personnelID = personnelID;
        r.productID = productID;
        r.name = name;
        r.lastName = lastName;
        r.brand = brand;
        r.definition = definition;

        try {
            DBHelper db = new DBHelper();
            db.Open();
            db.Insert(r);
            db.Close();
        } catch (Exception ex) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + ex, "OKEY", null);
            return;
        }

        viewAssignsProducts();
        SingleSelectionModel<Tab> selectionModel = tabPanePersonnel.getSelectionModel();
        selectionModel.select(tabresponsibilities);

    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////REMOVE ASSIGN PANEL/////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Personnel Panel üzerinde bulunan Remove Assign butonundan gelindikten
     * sonra seçilen personnelin tüm zimmetleri gözükür ve istenilen zimmet
     * personnel üzerinden dürüşürülür
     *
     * @param event
     */
    @FXML
    void buttonRemoveAssignRemoveAssign_Click(ActionEvent event) {
        TreeItem<AssignFullTreeTable> item = isSelectedItemRemoveAssign();
        if (item == null || textFieldRemoveAssignPersonnelID.getText().equals("")) {
            return;
        }

        AssignFullTreeTable astt = item.getValue();
        Assign r = astt.getResponsibilityProduct();
        r.personnelID = Integer.parseInt(textFieldRemoveAssignPersonnelID.getText());
        try {
            DBHelper db = new DBHelper();
            db.Open();
            db.RemoveAssign(r);
            db.Close();
        } catch (Exception ex) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + ex, "OKEY", null);
            return;
        }

        SingleSelectionModel<Tab> selectionModel = tabPanePersonnel.getSelectionModel();
        selectionModel.select(tabresponsibilities);
        viewAssignsProducts();

    }

    ////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////PROFİLE PANEL/////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Profile Panelinde yeni bilgiler yazıldıktan sonra profile bilgilerini
     * günceller
     *
     * @param event
     */
    @FXML
    private void buttonProfileUpdate_Click(ActionEvent event) {
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
                accessFXML._modal("Error", "Password Is Wrong!", "OKEY", anchorPaneAdmin);
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

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////BUY PANEL///////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Seçilen ürünü satın almada kullanılan metot
     *
     * @param event
     */
    @FXML
    void buttonBuyProduct(ActionEvent event) {
        TreeItem<BuyStockTreeTable> item = isSelectedBuyProduct();
        if (item == null) {
            return;
        }
        BuyStockTreeTable bptt = item.getValue();
        BuyStock bs = bptt.getBuyProduct();

        Product p = new Product();
        p.productID = bs.productID;
        p.brand = bs.brand;
        p.definition = bs.definition;
        p.price = bs.price;
        p.company = bs.company;
        p.isBelong = 0;
        p.isWaste = 0;
        p.purchaseDate = new Date();

        try {
            DBHelper db = new DBHelper();
            db.Open();
            db.DeleteProductsBuy(bs);
            db.Insert(p);
            db.Close();
        } catch (Exception ex) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + ex, "OKEY", null);
            return;
        }
        viewBuyProduct();

    }
    
    
     @FXML
    private void buttonBuyProductAdd_Click(ActionEvent event) {
        String brand = "";
        String definition = "";
        double price = 0;
        String company = "";
        try{
           brand = textFieldBuyBrand.getText();
           definition = textFieldBuyDefinition.getText();
           company = comboBoxCompanyBuy.getValue();
           price = Double.parseDouble(textFieldBuyPrice.getText());
           
           if(brand.equals("") || definition.equals("") || company.equals("") || price == 0)
           {
               throw new Exception("Fields can't empty! Please Check!");
           }
           
        }catch(Exception ex){
            accessFXML._modal("Value Error", "Value type wrong or value not:\n" + ex, "OKEY", null);
            return;
        }
        
        BuyStock bs = new BuyStock();
        bs.brand= brand;
        bs.definition = definition;
        bs.price = price;
        bs.company = company;
        
        try{
            DBHelper db = new DBHelper();
            db.Open();
            db.Insert(bs);
            db.Close();
        }catch(Exception ex){
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + ex, "OKEY", null);
            return;
        }
        
        viewBuyProduct();
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////COMPONENTS///////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
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
    private JFXTreeTableView<ProductTreeTable> treeTableViewStock;
    @FXML
    private JFXButton buttonWasteStorageStock;
    @FXML
    private JFXButton buttonResponsibilityStock;
    @FXML
    private JFXTreeTableView<ProductTreeTable> treeTableViewWasteStorage;
    @FXML
    private JFXTreeTableView<AssignTreeTable> treeTableViewResposibility;
    @FXML
    private JFXButton buttonWasteStorageResponsibility;
    @FXML
    private JFXButton buttonResponsibilityRemoveResponsiblity;
    @FXML
    private JFXTextField textFieldPersonnelIDResponsibilityAssign;
    @FXML
    private JFXTextField textFieldNameResponsibilityAssign;
    @FXML
    private JFXTextField textFieldLastNameResponsibilityAssign;
    @FXML
    private JFXTextField textFieldBrandResponsibilityAssign;
    @FXML
    private JFXTextField textFieldDefinitionResponsibilityAssign;
    @FXML
    private JFXTreeTableView<AssignFullTreeTable> treeTableViewResposibilityAssign;
    @FXML
    private JFXButton buttonResponsibilityResponsibilityAssign;
    @FXML
    private JFXTextField textFieldRemoveAssignPersonnelID;
    @FXML
    private JFXTextField textFieldRemoveAssignName;
    @FXML
    private JFXTextField textFieldRemoveAssignLastName;
    @FXML
    private JFXTreeTableView<AssignFullTreeTable> treeTableViewRemoveAssign;
    @FXML
    private JFXButton buttonRemoveAssignRemoveAssign;

    @FXML
    private JFXButton buttonProductResponsibility;
    @FXML
    private JFXButton buttonProductResponsibilityAssign;
    @FXML
    private JFXButton buttonProductStock;
    @FXML
    private JFXButton buttonProductWasteStorage;
    @FXML
    private JFXTextField textFieldProductIDResponsibilityAssign;
    @FXML
    private JFXButton buttonRemoveFromWasteStorage;
    @FXML
    private JFXButton buttonAssigns;
    @FXML
    private Label labelWindowJob;
    @FXML
    private JFXButton buttonProfile;
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
    private Label labelWindowName;
    @FXML
    private JFXButton buttonBuy;
    @FXML
    private Tab tabBuy;
    @FXML
    private JFXTreeTableView<BuyStockTreeTable> treeTableViewBuy;
    @FXML
    private JFXButton buttonBuyProduct;
    @FXML
    private JFXButton buttonExit;
    @FXML
    private JFXButton buttonAllPersonnelReports;
    @FXML
    private JFXTextField textFieldPersonnelSearch;
    @FXML
    private JFXTextField textFieldStockSearch;
    @FXML
    private JFXTextField textFieldWasteStorageSearch;
    @FXML
    private JFXTextField textFieldAssignsSearch;
    @FXML
    private JFXTextField textFieldAssignAddSearch;
    @FXML
    private JFXTextField textFieldRemoveAssignSearch;
    @FXML
    private JFXTextField textFieldBuyProductSearch;

    public void initializeEvent() {

        textFieldPersonnelSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeTableViewPersonnels.setPredicate(new Predicate<TreeItem<PersonnelTreeTable>>() {
                    @Override
                    public boolean test(TreeItem<PersonnelTreeTable> t) {
                        Boolean flag = t.getValue().personnelID.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().name.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().lastName.getValue().toLowerCase().contains(newValue.toLowerCase())
                                || t.getValue().Department.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().Job.getValue().toLowerCase().contains(newValue.toLowerCase());
                        return flag;
                    }
                });
            }
        });

        textFieldStockSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeTableViewStock.setPredicate(new Predicate<TreeItem<ProductTreeTable>>() {
                    @Override
                    public boolean test(TreeItem<ProductTreeTable> t) {
                        Boolean flag = t.getValue().productID.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().brand.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().definition.getValue().toLowerCase().contains(newValue.toLowerCase())
                                || t.getValue().price.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().company.getValue().toLowerCase().contains(newValue.toLowerCase());
                        return flag;
                    }
                });
            }
        });

        textFieldWasteStorageSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeTableViewWasteStorage.setPredicate(new Predicate<TreeItem<ProductTreeTable>>() {
                    @Override
                    public boolean test(TreeItem<ProductTreeTable> t) {
                        Boolean flag = t.getValue().productID.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().brand.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().definition.getValue().toLowerCase().contains(newValue.toLowerCase())
                                || t.getValue().price.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().company.getValue().toLowerCase().contains(newValue.toLowerCase());
                        return flag;
                    }
                });
            }
        });

        textFieldAssignsSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeTableViewResposibility.setPredicate(new Predicate<TreeItem<AssignTreeTable>>() {
                    @Override
                    public boolean test(TreeItem<AssignTreeTable> t) {
                        Boolean flag = t.getValue().productID.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().brand.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().definition.getValue().toLowerCase().contains(newValue.toLowerCase())
                                || t.getValue().price.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().personnelID.getValue().toLowerCase().contains(newValue.toLowerCase())
                                || t.getValue().name.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().lastName.getValue().toLowerCase().contains(newValue.toLowerCase());
                        return flag;
                    }
                });
            }
        });

        textFieldAssignAddSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeTableViewResposibilityAssign.setPredicate(new Predicate<TreeItem<AssignFullTreeTable>>() {
                    @Override
                    public boolean test(TreeItem<AssignFullTreeTable> t) {
                        Boolean flag = t.getValue().productID != null ? t.getValue().productID.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().brand.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().definition.getValue().toLowerCase().contains(newValue.toLowerCase())
                                || t.getValue().price.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().company.getValue().toLowerCase().contains(newValue.toLowerCase()) : t.getValue().personnelID.getValue().toLowerCase().contains(newValue.toLowerCase())
                                || t.getValue().name.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().lastName.getValue().toLowerCase().contains(newValue.toLowerCase());
                        return flag;
                    }
                });
            }
        });

        textFieldRemoveAssignSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeTableViewRemoveAssign.setPredicate(new Predicate<TreeItem<AssignFullTreeTable>>() {
                    @Override
                    public boolean test(TreeItem<AssignFullTreeTable> t) {
                        Boolean flag = t.getValue().productID.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().brand.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().definition.getValue().toLowerCase().contains(newValue.toLowerCase())
                                || t.getValue().price.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().personnelID.getValue().toLowerCase().contains(newValue.toLowerCase())
                                || t.getValue().name.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().lastName.getValue().toLowerCase().contains(newValue.toLowerCase());
                        return flag;
                    }
                });
            }
        });

        textFieldBuyProductSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeTableViewBuy.setPredicate(new Predicate<TreeItem<BuyStockTreeTable>>() {
                    @Override
                    public boolean test(TreeItem<BuyStockTreeTable> t) {
                        Boolean flag = t.getValue().productID.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().brand.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().definition.getValue().toLowerCase().contains(newValue.toLowerCase())
                                || t.getValue().price.getValue().toLowerCase().contains(newValue.toLowerCase()) || t.getValue().company.getValue().toLowerCase().contains(newValue.toLowerCase());
                        return flag;
                    }
                });
            }
        });
    }

   

}
