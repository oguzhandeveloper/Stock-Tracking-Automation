/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import View.*;
import Controller.*;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

/**
 *
 * @author OGUZHAN
 */
public class TreeTableViewer {

    public void viewPersonnels(JFXTreeTableView<PersonnelTreeTable> treeTableViewPersonnels) {
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

        DBHelper db = new DBHelper();
        db.Open();
        ArrayList<Personnel> personnels = db.getPersonnels();
        db.Close();

        //Orjinal Personnel sınıfını treetableview'ın alabileceği formata dönüştüryoruz.
        for (Personnel p : personnels) {
            personnelsTreeTables.add(new PersonnelTreeTable(p.ID + "", p.name, p.lastName, p.Job, p.Department, p.Active + ""));
        }

        //içersindeki değerleri yazdırıken bağlı olması gereken root(yani değerleri neredem çekecek) değişkeni tanımlanıyor
        //ve treeviewtable column'ları ayarlanıyor.
        final TreeItem<PersonnelTreeTable> root = new RecursiveTreeItem<PersonnelTreeTable>(personnelsTreeTables, RecursiveTreeObject::getChildren);
        treeTableViewPersonnels.getColumns().setAll(personnelIDColumn, personnelNameColumn, personnelLastNameColumn,
                personnelJobColumn, personnelDepartmentColumn, personnelActiveColumn);
        treeTableViewPersonnels.setRoot(root);
        treeTableViewPersonnels.setShowRoot(false);

    }

    public void viewProduct(JFXTreeTableView<ProductTreeTable> treeTableView, boolean isComputer, boolean isWasteStorage) {
        //TreeViewTable içerisine eklenecek olan değişkenlerin columnları belirleniyor.
        JFXTreeTableColumn<ProductTreeTable, String> hardwareIDColumn = new JFXTreeTableColumn<>("Hardware ID");
        hardwareIDColumn.setPrefWidth(100);
        hardwareIDColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductTreeTable, String> param) {
                return param.getValue().getValue().hardwareID;
            }
        });
        JFXTreeTableColumn<ProductTreeTable, String> computerIDColumn = new JFXTreeTableColumn<>("Computer ID");
        computerIDColumn.setPrefWidth(100);
        computerIDColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductTreeTable, String> param) {
                return param.getValue().getValue().computerID;
            }
        });

        JFXTreeTableColumn<ProductTreeTable, String> brandColumn = new JFXTreeTableColumn<>("Brand");
        brandColumn.setPrefWidth(100);
        brandColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductTreeTable, String> param) {
                return param.getValue().getValue().brand;
            }
        });

        JFXTreeTableColumn<ProductTreeTable, String> definitionColumn = new JFXTreeTableColumn<>("Definition");
        definitionColumn.setPrefWidth(150);
        definitionColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductTreeTable, String> param) {
                return param.getValue().getValue().definition;
            }
        });
        JFXTreeTableColumn<ProductTreeTable, String> priceColumn = new JFXTreeTableColumn<>("Price");
        priceColumn.setPrefWidth(100);
        priceColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductTreeTable, String> param) {
                return param.getValue().getValue().price;
            }
        });
        JFXTreeTableColumn<ProductTreeTable, String> companyColumn = new JFXTreeTableColumn<>("Company");
        companyColumn.setPrefWidth(100);
        companyColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductTreeTable, String> param) {
                return param.getValue().getValue().company;
            }
        });
        JFXTreeTableColumn<ProductTreeTable, String> isBelongColumn = new JFXTreeTableColumn<>("Is Belong");
        isBelongColumn.setPrefWidth(100);
        isBelongColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductTreeTable, String> param) {
                return param.getValue().getValue().isBelong;
            }
        });

        JFXTreeTableColumn<ProductTreeTable, String> purchaseDateColumn = new JFXTreeTableColumn<>("Purchase Date");
        purchaseDateColumn.setPrefWidth(100);
        purchaseDateColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductTreeTable, String> param) {
                return param.getValue().getValue().purchaseDate;
            }
        });

        //treetableview içersine değerler eklenmeden önce değerler arası erişimin(ilişkilerin nasıl olacağı) yapısı belirleniyor.
        ObservableList<ProductTreeTable> productTreeTables = FXCollections.observableArrayList();

        DBHelper db = new DBHelper();
        db.Open();
        ArrayList<Computer> computers = null;
        ArrayList<Hardware> hardwares = null;
        if (isComputer) {
            computers = db.getComputersInStock();
        } else {
            hardwares = db.getHardwaresInStock();
        }
        db.Close();

        //Orjinal Personnel sınıfını treetableview'ın alabileceği formata dönüştüryoruz.
        //isWaste == 0 && isWasteStorage == false ise stockta bulunanları yazdırıyor
        //isWaste == 1 && && isWasteStorage == true ise atık deposunda olanları yazdırıyor
        //c.isBelong == 0 olan zimmetlenmemiş stokta olan nesneleri getirir.
        if (isComputer) {
            for (Computer c : computers) {
                if (c.isWaste == 0 && c.isBelong == 0 && isWasteStorage == false) {
                    productTreeTables.add(new ProductTreeTable(c.computerID + "", c.brand, c.definition, c.price + "", c.company, c.isBelong + "", c.isWaste + "", c.purchaseDate, isComputer));
                }
                if (c.isWaste == 1 && isWasteStorage == true) {
                    productTreeTables.add(new ProductTreeTable(c.computerID + "", c.brand, c.definition, c.price + "", c.company, c.isBelong + "", c.isWaste + "", c.purchaseDate, isComputer));
                }
            }
        } else {
            for (Hardware h : hardwares) {
                if (h.isWaste == 0 && isWasteStorage == false) {
                    productTreeTables.add(new ProductTreeTable(h.hardwareID + "", h.computerID + "", h.brand, h.definition, h.price + "", h.company, h.isBelong + "", h.isWaste + "", h.purchaseDate, isComputer));
                }
                if (h.isWaste == 1 && isWasteStorage == true) {
                    productTreeTables.add(new ProductTreeTable(h.hardwareID + "", h.computerID + "", h.brand, h.definition, h.price + "", h.company, h.isBelong + "", h.isWaste + "", h.purchaseDate, isComputer));
                }
            }
        }

        //içersindeki değerleri yazdırıken bağlı olması gereken root(yani değerleri neredem çekecek) değişkeni tanımlanıyor
        //ve treeviewtable column'ları ayarlanıyor.
        final TreeItem<ProductTreeTable> root = new RecursiveTreeItem<ProductTreeTable>(productTreeTables, RecursiveTreeObject::getChildren);
        if (isComputer) {
            treeTableView.getColumns().setAll(computerIDColumn, brandColumn, definitionColumn,
                    priceColumn, companyColumn, isBelongColumn, purchaseDateColumn);
        } else {
            treeTableView.getColumns().setAll(hardwareIDColumn, brandColumn, definitionColumn,
                    priceColumn, companyColumn, isBelongColumn, purchaseDateColumn);
        }

        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);

    }

}
