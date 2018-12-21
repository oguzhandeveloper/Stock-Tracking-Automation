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

    public StringProperty productID;
    public StringProperty brand;
    public StringProperty definition;
    public StringProperty price;
    public StringProperty company;
    public StringProperty isBelong;
    public StringProperty isWaste;
    public StringProperty purchaseDate;

    //Computer
    public ProductTreeTable(String productID, String brand, String definition, String price,
            String company, String isBelong, String isWaste, Date purchaseDate) {
        this.productID = new SimpleStringProperty(productID);
        this.brand = new SimpleStringProperty(brand);
        this.definition = new SimpleStringProperty(definition);
        this.price = new SimpleStringProperty(price);
        this.company = new SimpleStringProperty(company);
        this.isBelong = new SimpleStringProperty(isBelong);
        this.isWaste = new SimpleStringProperty(isWaste);
        this.purchaseDate = new SimpleStringProperty(purchaseDate.toString());
    }

    public Product getProduct() {
        Product p = new Product();
        p.productID = Integer.parseInt(this.productID.getValue());
        p.brand = this.brand.getValue();
        p.definition =  this.definition.getValue();
        p.price = Double.parseDouble(this.price.getValue());
        p.company  =  this.company.getValue();
        p.isBelong  =  Integer.parseInt(this.isBelong.getValue());
        p.isWaste  =  Integer.parseInt(this.isWaste.getValue());
        p.purchaseDate = new Date();
        return p;
    }

}
