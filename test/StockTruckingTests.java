/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Tüm İşlemlerin Yapıldığı Sınıf
 */
import Model.*;
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

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    /**
     * TEST CASE 1 Girişi test etmektedir.
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
        DBHelper dp = new DBHelper();
        dp.Open();
        //Personel varsa ve şifre doğru ise geriye personel bilgilerini döndürür
        Personnel p = dp.getPersonnel(username, password);
        dp.Close();
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
}
