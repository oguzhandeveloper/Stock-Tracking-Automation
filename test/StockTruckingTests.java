/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Tüm İşlemlerin Yapıldığı Sınıf
 */
import Model.*;
import java.util.Date;
import javafx.scene.control.Alert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author OGUZHAN
 */
public class StockTruckingTests {

    public StockTruckingTests() {
    }

    /**
     * Bu test metodu Admin paneline girilmek üzere kullanıcı bilgileri
     * girilerek kullanıcı girişi yapılmaktadır. Test State = PASS
     */
    @Test
    public void SignIn() {
        String username = "oguzhan";
        String password = "1234";

        //Username veya Password yanlış ise giremez
        if (username.equals("") || password.equals("")) {
            fail("Username or Password Could Not Empty");
        }

        //Database bağlantı işlemleri
        Personnel p = null;
        try {
            DBHelper dp = new DBHelper();
            dp.Open();
            //Personel varsa ve şifre doğru ise geriye personel bilgilerini döndürür
            p = dp.getPersonnel(username, password);
            dp.Close();
        } catch (Exception e) {
            fail("SQL query error:\n" + e);
            return;
        }
        //Personel yok
        if (p == null) {
            fail("Username or Password is Wrong");
        }

        //Personnel işten çıkarılmış sisteme giremez.
        if (p.Active == 0) {
            fail("This Personnel Is Not Active!Please You Must See The Admin!");
        }
        //Giriş tanamlandı
        if (p.Job.equals("Admin")) {
            assertTrue("Admin Managment Panel Sign Ip", true);
        } else if (p.Job.equals("Sales Responsible")) {
            assertTrue("Sales Managment Panel Sign In", true);
        } else if (p.Job.equals("Department Manager")) {
            assertTrue("Department Manager Panel", true);
        } else {
            assertTrue("This personnel can't use the managment app now! Please TRY LATER!", false);
        }

    }

    /**
     * Bu Test Personel bilgileri üzerine verilen ürünü kişiye zimmetlemek için
     * kullanılır. Test State: Pass
     */
    @Test
    public void AssignAdd() {
        Assign r = new Assign();
        r.personnelID = 4; //Zimmetin atanacağı personelin ID'si "Önemli"
        r.productID = 10; //Zimmetlenecek olan ürünün ID'si  "Önemli"
        r.name = "Oğuzhan";  //"Önemli Değil"
        r.lastName = "Çetinkaya";  //"Önemli Değil
        r.brand = "Intel";    //"Önemli Değil"
        r.definition = "Intel Core i7-8700 4.60 GHz İşlemci";  //"Önemli Değil"

        try {
            DBHelper db = new DBHelper();
            db.Open();
            db.Insert(r);
            db.Close();
        } catch (Exception ex) {
            fail("SQL query error:\n" + ex);
            return;
        }
        assertTrue("Zimmetlem Başarılı", true);
    }

    /**
     * Bu Test verilen zimmetlerden birisini kişinin üzerindeki zimmeti
     * kaldırılmak için kullanılır. Test State: Pass
     */
    @Test
    public void RemoveAssign() {
        Assign r = new Assign(); //PersonnelID ve ProductID yeterlidir
        r.personnelID = 1;
        r.productID = 8;
        try {
            DBHelper db = new DBHelper();
            db.Open();
            db.RemoveAssign(r);
            db.Close();
        } catch (Exception ex) {
            fail("SQL query error:\n" + ex);
            return;
        }
    }

    /**
     * Bu test ürünleri satın almak için kullanılır 
     * Test State: Pass
     */
    @Test
    public void BuyProduct() {

        BuyStock bs = new BuyStock();

        bs.productID = 20;
        bs.brand = "LENOVO";
        bs.definition = "AIO 520 CORE İ5 8400T 1.7 GHZ 4 GB 1 TB 2 GB AMD R...";
        bs.price = 4999;
        bs.company = "Amazon";

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
            db.DeleteProductsBuy(bs); //BuyStock Nesnesi alır
            db.Insert(p);   //Product Nesnesi Alır
            db.Close();
        } catch (Exception ex) {
            fail("SQL query error:\n" + ex);
            return;
        }
        assertTrue("Ürün satın alma başarılıdır.", true);
    }

    /**
     * Bu test Stock'ta bulunan bir ürünü Atığa (Waste Storage) kaldırmak için
     * kullanılır.
     * Test State: Pass
     */
    @Test
    public void WasteStorageInStock() {
        Product p = new Product();
        p.productID = 8; //Sadece productID'si yeterlidir.

        try {
            DBHelper db = new DBHelper();
            db.Open();
            //Ürün atık deposuna kaldırılıyor.
            db.RemoveToWasteStorage(p);
            db.Close();
        } catch (Exception ex) {
            fail("SQL query error:\n" + ex);
            return;
        }
        assertTrue("Ürünü atık deposuna işlemi başarılıdır.", true);
    }

    /**
     *
     * Bu test Assings'ta bulunan bir ürünü(Zimmetli bir ürünü) Atığa (Waste
     * Storage) kaldırmak için kullanılır. 
     * Test State: Pass
     */
    @Test
    public void WasteStorageInAssigns() {
        Assign r = new Assign();
        //personnelID ve productID yeterlidir.
        r.personnelID = 2;
        r.productID = 7;
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
            fail("SQL query error:\n" + ex);
            return;
        }
        assertTrue("Ürünü atık deposuna işlemi başarılıdır.", true);
    }

}
