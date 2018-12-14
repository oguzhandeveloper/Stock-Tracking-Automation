/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author OGUZHAN
 */
public class ProductTreeTable extends RecursiveTreeObject<ProductTreeTable> {

    public StringProperty hardwareID;
    public StringProperty computerID;
    public StringProperty brand;
    public StringProperty definition;
    public StringProperty price;
    public StringProperty company;
    public StringProperty isBelong;
    public StringProperty isWaste;
    public StringProperty purchaseDate;
    
    public boolean isComputer;

    public ProductTreeTable(String computerID, String brand, String definition, String price,
            String company, String isBelong, String isWaste, Date purchaseDate,boolean isComputer) {
        this.computerID = new SimpleStringProperty(computerID);
        this.brand = new SimpleStringProperty(brand);
        this.definition = new SimpleStringProperty(definition);
        this.price = new SimpleStringProperty(price);
        this.company = new SimpleStringProperty(company);
        this.isBelong = new SimpleStringProperty(isBelong);
        this.isWaste = new SimpleStringProperty(isWaste);
        this.purchaseDate = new SimpleStringProperty(purchaseDate.toString());
        this.isComputer = isComputer;
    }

    public ProductTreeTable(String hardwareID, String computerID, String brand, String definition, String price,
            String company, String isBelong, String isWaste, Date purchaseDate,boolean isComputer) {
        this.hardwareID = new SimpleStringProperty(hardwareID);
        this.computerID = new SimpleStringProperty(computerID);
        this.brand = new SimpleStringProperty(brand);
        this.definition = new SimpleStringProperty(definition);
        this.price = new SimpleStringProperty(price);
        this.company = new SimpleStringProperty(company);
        this.isBelong = new SimpleStringProperty(isBelong);
        this.isWaste = new SimpleStringProperty(isWaste);
        this.purchaseDate = new SimpleStringProperty(purchaseDate.toString());
        this.isComputer = isComputer;
    }

}
