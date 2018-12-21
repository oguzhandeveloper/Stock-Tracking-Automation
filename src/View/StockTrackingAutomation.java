/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.*;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author OGUZHAN
 */
public class StockTrackingAutomation extends Application {

    
    @Override
    public void start(Stage stage) throws Exception {
        String fxmlName = "SignIn.fxml";
        String fxmlTitle = "Sign In Panel";
        
        Parent root = FXMLLoader.load(getClass().getResource(fxmlName));

        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle(fxmlTitle);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /*
        DBHelper db = new DBHelper();
        db.Open();
        Personnel p = db.getPersonnel("oguzhan", "1234");
        System.out.println(p);
        db.Close();
        */
        DBHelper db = new DBHelper();
        db.Open();
        /*ArrayList<Responsibility> responsibilities = db.getResponsibilities();
        for(Responsibility r: responsibilities)
            System.out.println(r);*/
        /*Personnel pr = new Personnel();
        pr.personnelID = 1;
        ArrayList<Product> products= db.getProductsInAssign(pr);
        for(Product p: products)
            System.out.println(p);
        db.Close();*/
        
        
        launch(args);
    }

}
