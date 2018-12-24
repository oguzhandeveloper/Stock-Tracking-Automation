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
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;
import View.*;

/**
 *
 * @author OGUZHAN
 */
public class TreeTableViewer {

    AccessFXML accessFXML = new AccessFXML();

    public void viewProduct(JFXTreeTableView<ProductTreeTable> treeTableView, boolean isWasteStorage) {
        //TreeViewTable içerisine eklenecek olan değişkenlerin columnları belirleniyor.
        JFXTreeTableColumn<ProductTreeTable, String> productIDColumn = new JFXTreeTableColumn<>("Product ID");
        productIDColumn.setPrefWidth(100);
        productIDColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProductTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProductTreeTable, String> param) {
                return param.getValue().getValue().productID;
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
        ArrayList<Product> products = null;
        try {
            DBHelper db = new DBHelper();
            db.Open();
            products = db.getProduct();

            db.Close();
        } catch (Exception e) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
            return;
        }

        //Orjinal Personnel sınıfını treetableview'ın alabileceği formata dönüştüryoruz.
        //isWaste == 0 && isWasteStorage == false ise stockta bulunanları yazdırıyor
        //isWaste == 1 && && isWasteStorage == true ise atık deposunda olanları yazdırıyor
        //c.isBelong == 0 olan zimmetlenmemiş stokta olan nesneleri getirir.
        for (Product p : products) {
            if (p.isWaste == 0 && p.isBelong == 0 && isWasteStorage == false) {
                productTreeTables.add(new ProductTreeTable(p.productID + "", p.brand, p.definition, p.price + "", p.company, p.isBelong + "", p.isWaste + "", p.purchaseDate));
            }
            if (p.isWaste == 1 && isWasteStorage == true) {
                productTreeTables.add(new ProductTreeTable(p.productID + "", p.brand, p.definition, p.price + "", p.company, p.isBelong + "", p.isWaste + "", p.purchaseDate));
            }
        }

        //içersindeki değerleri yazdırıken bağlı olması gereken root(yani değerleri neredem çekecek) değişkeni tanımlanıyor
        //ve treeviewtable column'ları ayarlanıyor.
        final TreeItem<ProductTreeTable> root = new RecursiveTreeItem<ProductTreeTable>(productTreeTables, RecursiveTreeObject::getChildren);

        treeTableView.getColumns().setAll(productIDColumn, brandColumn, definitionColumn,
                priceColumn, companyColumn, isBelongColumn, purchaseDateColumn);

        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);

    }

    public void viewAssignInRemoveAssign(JFXTreeTableView<AssignFullTreeTable> treeTableView, Personnel p) {
        //TreeViewTable içerisine eklenecek olan değişkenlerin columnları belirleniyor.
        JFXTreeTableColumn<AssignFullTreeTable, String> productIDColumn = new JFXTreeTableColumn<>("Product ID");
        productIDColumn.setPrefWidth(100);
        productIDColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String> param) {
                return param.getValue().getValue().productID;
            }
        });

        JFXTreeTableColumn<AssignFullTreeTable, String> brandColumn = new JFXTreeTableColumn<>("Brand");
        brandColumn.setPrefWidth(100);
        brandColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String> param) {
                return param.getValue().getValue().brand;
            }
        });

        JFXTreeTableColumn<AssignFullTreeTable, String> definitionColumn = new JFXTreeTableColumn<>("Definition");
        definitionColumn.setPrefWidth(150);
        definitionColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String> param) {
                return param.getValue().getValue().definition;
            }
        });
        JFXTreeTableColumn<AssignFullTreeTable, String> priceColumn = new JFXTreeTableColumn<>("Price");
        priceColumn.setPrefWidth(100);
        priceColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String> param) {
                return param.getValue().getValue().price;
            }
        });
        JFXTreeTableColumn<AssignFullTreeTable, String> companyColumn = new JFXTreeTableColumn<>("Company");
        companyColumn.setPrefWidth(100);
        companyColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String> param) {
                return param.getValue().getValue().company;
            }
        });
        JFXTreeTableColumn<AssignFullTreeTable, String> isBelongColumn = new JFXTreeTableColumn<>("Is Belong");
        isBelongColumn.setPrefWidth(100);
        isBelongColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String> param) {
                return param.getValue().getValue().isBelong;
            }
        });

        JFXTreeTableColumn<AssignFullTreeTable, String> purchaseDateColumn = new JFXTreeTableColumn<>("Purchase Date");
        purchaseDateColumn.setPrefWidth(100);
        purchaseDateColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String> param) {
                return param.getValue().getValue().purchaseDate;
            }
        });

        //treetableview içersine değerler eklenmeden önce değerler arası erişimin(ilişkilerin nasıl olacağı) yapısı belirleniyor.
        ObservableList<AssignFullTreeTable> assignObservableTreeTables = FXCollections.observableArrayList();
        ArrayList<Product> products = null;
        try {
            DBHelper db = new DBHelper();
            db.Open();
            products = db.getProductsInAssign(p);

            db.Close();
        } catch (Exception e) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
            return;
        }

        //Orjinal Personnel sınıfını treetableview'ın alabileceği formata dönüştüryoruz.
        //isWaste == 0 && isWasteStorage == false ise stockta bulunanları yazdırıyor
        //isWaste == 1 && && isWasteStorage == true ise atık deposunda olanları yazdırıyor
        //c.isBelong == 0 olan zimmetlenmemiş stokta olan nesneleri getirir.
        for (Product pr : products) {
            if (pr.isWaste == 0 && pr.isBelong == 1) {
                assignObservableTreeTables.add(new AssignFullTreeTable(pr.productID + "", pr.brand, pr.definition, pr.price + "", pr.company, pr.isBelong + "", pr.isWaste + "", pr.purchaseDate));
            }
        }

        //içersindeki değerleri yazdırıken bağlı olması gereken root(yani değerleri neredem çekecek) değişkeni tanımlanıyor
        //ve treeviewtable column'ları ayarlanıyor.
        final TreeItem<AssignFullTreeTable> root = new RecursiveTreeItem<AssignFullTreeTable>(assignObservableTreeTables, RecursiveTreeObject::getChildren);

        treeTableView.getColumns().setAll(productIDColumn, brandColumn, definitionColumn,
                priceColumn, companyColumn, isBelongColumn, purchaseDateColumn);

        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);
    }

    public void viewAssignAddPersonnel(JFXTreeTableView<AssignFullTreeTable> treeTableView) {
        //TreeViewTable içerisine eklenecek olan değişkenlerin columnları belirleniyor.
        JFXTreeTableColumn<AssignFullTreeTable, String> personnelIDColumn = new JFXTreeTableColumn<>("Personnel ID");
        personnelIDColumn.setPrefWidth(50);
        personnelIDColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String> param) {
                return param.getValue().getValue().personnelID;
            }
        });
        JFXTreeTableColumn<AssignFullTreeTable, String> personnelNameColumn = new JFXTreeTableColumn<>("Name");
        personnelNameColumn.setPrefWidth(150);
        personnelNameColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String> param) {
                return param.getValue().getValue().name;
            }
        });
        JFXTreeTableColumn<AssignFullTreeTable, String> personnelLastNameColumn = new JFXTreeTableColumn<>("Last Name");
        personnelLastNameColumn.setPrefWidth(150);
        personnelLastNameColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String> param) {
                return param.getValue().getValue().lastName;
            }
        });

        JFXTreeTableColumn<AssignFullTreeTable, String> personnelJobColumn = new JFXTreeTableColumn<>("Job");
        personnelJobColumn.setPrefWidth(150);
        personnelJobColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String> param) {
                return param.getValue().getValue().Job;
            }
        });
        JFXTreeTableColumn<AssignFullTreeTable, String> personnelDepartmentColumn = new JFXTreeTableColumn<>("Department");
        personnelDepartmentColumn.setPrefWidth(150);
        personnelDepartmentColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String> param) {
                return param.getValue().getValue().Department;
            }
        });
        JFXTreeTableColumn<AssignFullTreeTable, String> personnelActiveColumn = new JFXTreeTableColumn<>("Active");
        personnelActiveColumn.setPrefWidth(70);
        personnelActiveColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String> param) {
                return param.getValue().getValue().Active;
            }
        });

        //treetableview içersine değerler eklenmeden önce değerler arası erişimin(ilişkilerin nasıl olacağı) yapısı belirleniyor.
        ObservableList<AssignFullTreeTable> personnelsTreeTables = FXCollections.observableArrayList();
        ArrayList<Personnel> personnels = null;
        try {
            DBHelper db = new DBHelper();
            db.Open();
            personnels = db.getPersonnels("");
            db.Close();
        } catch (Exception e) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
            return;
        }

        //Orjinal Personnel sınıfını treetableview'ın alabileceği formata dönüştüryoruz.
        for (Personnel p : personnels) {
            if (p.Active == 1) {
                personnelsTreeTables.add(new AssignFullTreeTable(p.personnelID + "", p.name, p.lastName, p.Job, p.Department, p.Active + ""));
            }
        }

        //içersindeki değerleri yazdırıken bağlı olması gereken root(yani değerleri nereden çekecek) değişkeni tanımlanıyor
        //ve treeviewtable column'ları ayarlanıyor.
        final TreeItem<AssignFullTreeTable> root = new RecursiveTreeItem<AssignFullTreeTable>(personnelsTreeTables, RecursiveTreeObject::getChildren);
        treeTableView.getColumns().setAll(personnelIDColumn, personnelNameColumn, personnelLastNameColumn,
                personnelJobColumn, personnelDepartmentColumn, personnelActiveColumn);
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);
    }

    public void viewAssignAddProduct(JFXTreeTableView<AssignFullTreeTable> treeTableView) {
        //TreeViewTable içerisine eklenecek olan değişkenlerin columnları belirleniyor.
        JFXTreeTableColumn<AssignFullTreeTable, String> productIDColumn = new JFXTreeTableColumn<>("Product ID");
        productIDColumn.setPrefWidth(100);
        productIDColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String> param) {
                return param.getValue().getValue().productID;
            }
        });

        JFXTreeTableColumn<AssignFullTreeTable, String> brandColumn = new JFXTreeTableColumn<>("Brand");
        brandColumn.setPrefWidth(100);
        brandColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String> param) {
                return param.getValue().getValue().brand;
            }
        });

        JFXTreeTableColumn<AssignFullTreeTable, String> definitionColumn = new JFXTreeTableColumn<>("Definition");
        definitionColumn.setPrefWidth(150);
        definitionColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String> param) {
                return param.getValue().getValue().definition;
            }
        });
        JFXTreeTableColumn<AssignFullTreeTable, String> priceColumn = new JFXTreeTableColumn<>("Price");
        priceColumn.setPrefWidth(100);
        priceColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String> param) {
                return param.getValue().getValue().price;
            }
        });
        JFXTreeTableColumn<AssignFullTreeTable, String> companyColumn = new JFXTreeTableColumn<>("Company");
        companyColumn.setPrefWidth(100);
        companyColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String> param) {
                return param.getValue().getValue().company;
            }
        });
        JFXTreeTableColumn<AssignFullTreeTable, String> isBelongColumn = new JFXTreeTableColumn<>("Is Belong");
        isBelongColumn.setPrefWidth(100);
        isBelongColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String> param) {
                return param.getValue().getValue().isBelong;
            }
        });

        JFXTreeTableColumn<AssignFullTreeTable, String> purchaseDateColumn = new JFXTreeTableColumn<>("Purchase Date");
        purchaseDateColumn.setPrefWidth(100);
        purchaseDateColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignFullTreeTable, String> param) {
                return param.getValue().getValue().purchaseDate;
            }
        });

        //treetableview içersine değerler eklenmeden önce değerler arası erişimin(ilişkilerin nasıl olacağı) yapısı belirleniyor.
        ObservableList<AssignFullTreeTable> AssignAddTreeTables = FXCollections.observableArrayList();
        ArrayList<Product> products = null;
        try {
            DBHelper db = new DBHelper();
            db.Open();
            products = db.getProduct();

            db.Close();
        } catch (Exception e) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
            return;
        }

        //Orjinal Personnel sınıfını treetableview'ın alabileceği formata dönüştüryoruz.
        //isWaste == 0 && isWasteStorage == false ise stockta bulunanları yazdırıyor
        //isWaste == 1 && && isWasteStorage == true ise atık deposunda olanları yazdırıyor
        //c.isBelong == 0 olan zimmetlenmemiş stokta olan nesneleri getirir.
        for (Product p : products) {
            if (p.isWaste == 0 && p.isBelong == 0) {
                AssignAddTreeTables.add(new AssignFullTreeTable(p.productID + "", p.brand, p.definition, p.price + "", p.company, p.isBelong + "", p.isWaste + "", p.purchaseDate));
            }
        }

        //içersindeki değerleri yazdırıken bağlı olması gereken root(yani değerleri neredem çekecek) değişkeni tanımlanıyor
        //ve treeviewtable column'ları ayarlanıyor.
        final TreeItem<AssignFullTreeTable> root = new RecursiveTreeItem<AssignFullTreeTable>(AssignAddTreeTables, RecursiveTreeObject::getChildren);

        treeTableView.getColumns().setAll(productIDColumn, brandColumn, definitionColumn,
                priceColumn, companyColumn, isBelongColumn, purchaseDateColumn);

        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);
    }

    public void viewBuyProduct(JFXTreeTableView<BuyStockTreeTable> treeTableView) {
        //TreeViewTable içerisine eklenecek olan değişkenlerin columnları belirleniyor.
        JFXTreeTableColumn<BuyStockTreeTable, String> productIDColumn = new JFXTreeTableColumn<>("Product ID");
        productIDColumn.setPrefWidth(100);
        productIDColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BuyStockTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BuyStockTreeTable, String> param) {
                return param.getValue().getValue().productID;
            }
        });

        JFXTreeTableColumn<BuyStockTreeTable, String> brandColumn = new JFXTreeTableColumn<>("Brand");
        brandColumn.setPrefWidth(100);
        brandColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BuyStockTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BuyStockTreeTable, String> param) {
                return param.getValue().getValue().brand;
            }
        });

        JFXTreeTableColumn<BuyStockTreeTable, String> definitionColumn = new JFXTreeTableColumn<>("Definition");
        definitionColumn.setPrefWidth(350);
        definitionColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BuyStockTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BuyStockTreeTable, String> param) {
                return param.getValue().getValue().definition;
            }
        });
        JFXTreeTableColumn<BuyStockTreeTable, String> priceColumn = new JFXTreeTableColumn<>("Price");
        priceColumn.setPrefWidth(100);
        priceColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BuyStockTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BuyStockTreeTable, String> param) {
                return param.getValue().getValue().price;
            }
        });
        JFXTreeTableColumn<BuyStockTreeTable, String> companyColumn = new JFXTreeTableColumn<>("Company");
        companyColumn.setPrefWidth(100);
        companyColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BuyStockTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BuyStockTreeTable, String> param) {
                return param.getValue().getValue().company;
            }
        });

        //treetableview içersine değerler eklenmeden önce değerler arası erişimin(ilişkilerin nasıl olacağı) yapısı belirleniyor.
        ObservableList<BuyStockTreeTable> productTreeTables = FXCollections.observableArrayList();
        ArrayList<BuyStock> products = null;
        try {
            DBHelper db = new DBHelper();
            db.Open();
            products = db.getProducts();

            db.Close();
        } catch (Exception e) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
            return;
        }

        //Orjinal Personnel sınıfını treetableview'ın alabileceği formata dönüştüryoruz.
        //isWaste == 0 && isWasteStorage == false ise stockta bulunanları yazdırıyor
        //isWaste == 1 && && isWasteStorage == true ise atık deposunda olanları yazdırıyor
        //c.isBelong == 0 olan zimmetlenmemiş stokta olan nesneleri getirir.
        for (BuyStock p : products) {
            productTreeTables.add(new BuyStockTreeTable(p.productID + "", p.brand, p.definition, p.price + "", p.company));
        }

        //içersindeki değerleri yazdırıken bağlı olması gereken root(yani değerleri neredem çekecek) değişkeni tanımlanıyor
        //ve treeviewtable column'ları ayarlanıyor.
        final TreeItem<BuyStockTreeTable> root = new RecursiveTreeItem<BuyStockTreeTable>(productTreeTables, RecursiveTreeObject::getChildren);

        treeTableView.getColumns().setAll(productIDColumn, brandColumn, definitionColumn,
                priceColumn, companyColumn);

        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);

    }

    public void viewPersonnels(JFXTreeTableView<PersonnelTreeTable> treeTableViewPersonnels, String Department) {
        //TreeViewTable içerisine eklenecek olan değişkenlerin columnları belirleniyor.
        JFXTreeTableColumn<PersonnelTreeTable, String> personnelIDColumn = new JFXTreeTableColumn<>("Personnel ID");
        personnelIDColumn.setPrefWidth(50);
        personnelIDColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PersonnelTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PersonnelTreeTable, String> param) {
                return param.getValue().getValue().personnelID;
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
        ArrayList<Personnel> personnels = null;
        try {
            DBHelper db = new DBHelper();
            db.Open();
            personnels = db.getPersonnels(Department);
            db.Close();
        } catch (Exception e) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
            return;
        }

        //Orjinal Personnel sınıfını treetableview'ın alabileceği formata dönüştüryoruz.
        for (Personnel p : personnels) {

            personnelsTreeTables.add(new PersonnelTreeTable(p.personnelID + "", p.name, p.lastName, p.Job, p.Department, p.Active + ""));

        }

        //içersindeki değerleri yazdırıken bağlı olması gereken root(yani değerleri neredem çekecek) değişkeni tanımlanıyor
        //ve treeviewtable column'ları ayarlanıyor.
        final TreeItem<PersonnelTreeTable> root = new RecursiveTreeItem<PersonnelTreeTable>(personnelsTreeTables, RecursiveTreeObject::getChildren);
        treeTableViewPersonnels.getColumns().setAll(personnelIDColumn, personnelNameColumn, personnelLastNameColumn,
                personnelJobColumn, personnelDepartmentColumn, personnelActiveColumn);
        treeTableViewPersonnels.setRoot(root);
        treeTableViewPersonnels.setShowRoot(false);

    }

    public void viewAssignAll(JFXTreeTableView<AssignTreeTable> treeTableView, String Department) {
        //TreeViewTable içerisine eklenecek olan değişkenlerin columnları belirleniyor.
        JFXTreeTableColumn<AssignTreeTable, String> productIDColumn = new JFXTreeTableColumn<>("Product ID");
        productIDColumn.setPrefWidth(100);
        productIDColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignTreeTable, String> param) {
                return param.getValue().getValue().productID;
            }
        });

        JFXTreeTableColumn<AssignTreeTable, String> brandColumn = new JFXTreeTableColumn<>("Brand");
        brandColumn.setPrefWidth(100);
        brandColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignTreeTable, String> param) {
                return param.getValue().getValue().brand;
            }
        });

        JFXTreeTableColumn<AssignTreeTable, String> definitionColumn = new JFXTreeTableColumn<>("Definition");
        definitionColumn.setPrefWidth(150);
        definitionColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignTreeTable, String> param) {
                return param.getValue().getValue().definition;
            }
        });
        JFXTreeTableColumn<AssignTreeTable, String> priceColumn = new JFXTreeTableColumn<>("Price");
        priceColumn.setPrefWidth(100);
        priceColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignTreeTable, String> param) {
                return param.getValue().getValue().price;
            }
        });
        JFXTreeTableColumn<AssignTreeTable, String> DateColumn = new JFXTreeTableColumn<>("Assign Date");
        DateColumn.setPrefWidth(100);
        DateColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignTreeTable, String> param) {
                return param.getValue().getValue().assignDate;
            }
        });
        JFXTreeTableColumn<AssignTreeTable, String> personnelIDColumn = new JFXTreeTableColumn<>("Personnel ID");
        personnelIDColumn.setPrefWidth(100);
        personnelIDColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignTreeTable, String> param) {
                return param.getValue().getValue().personnelID;
            }
        });
        JFXTreeTableColumn<AssignTreeTable, String> nameColumn = new JFXTreeTableColumn<>("Name");
        nameColumn.setPrefWidth(100);
        nameColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignTreeTable, String> param) {
                return param.getValue().getValue().name;
            }
        });
        JFXTreeTableColumn<AssignTreeTable, String> lastnameColumn = new JFXTreeTableColumn<>("Last Name");
        lastnameColumn.setPrefWidth(100);
        lastnameColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignTreeTable, String> param) {
                return param.getValue().getValue().lastName;
            }
        });

        JFXTreeTableColumn<AssignTreeTable, String> activeColumn = new JFXTreeTableColumn<>("Active");
        activeColumn.setPrefWidth(100);
        activeColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AssignTreeTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AssignTreeTable, String> param) {
                return param.getValue().getValue().active;
            }
        });

        //treetableview içersine değerler eklenmeden önce değerler arası erişimin(ilişkilerin nasıl olacağı) yapısı belirleniyor.
        ObservableList<AssignTreeTable> assignsTreeTables = FXCollections.observableArrayList();
        ArrayList<Assign> assigns = null;

        try {
            DBHelper db = new DBHelper();
            db.Open();

            assigns = db.getAssigns(Department);

            db.Close();
        } catch (Exception e) {
            accessFXML._modal("Database SQLException Error", "SQL query error:\n" + e, "OKEY", null);
            return;
        }

        //Orjinal Personnel sınıfını treetableview'ın alabileceği formata dönüştüryoruz.
        //isWaste == 0 && isWasteStorage == false ise stockta bulunanları yazdırıyor
        //isWaste == 1 && && isWasteStorage == true ise atık deposunda olanları yazdırıyor
        //c.isBelong == 0 olan zimmetlenmemiş stokta olan nesneleri getirir.
        for (Assign r : assigns) {
            assignsTreeTables.add(new AssignTreeTable(r.personnelID + "", r.productID + "", r.assignDate, r.name, r.lastName, r.brand, r.definition, r.price + "", r.active + ""));
        }

        //içersindeki değerleri yazdırıken bağlı olması gereken root(yani değerleri neredem çekecek) değişkeni tanımlanıyor
        //ve treeviewtable column'ları ayarlanıyor.
        final TreeItem<AssignTreeTable> root = new RecursiveTreeItem<AssignTreeTable>(assignsTreeTables, RecursiveTreeObject::getChildren);

        treeTableView.getColumns().setAll(personnelIDColumn, nameColumn, lastnameColumn, productIDColumn,
                brandColumn, definitionColumn, priceColumn, activeColumn, DateColumn);

        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);

    }

}
