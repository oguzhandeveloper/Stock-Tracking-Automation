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
public class AssignFullTreeTable extends RecursiveTreeObject<AssignFullTreeTable> {

    public StringProperty personnelID;
    public StringProperty name;
    public StringProperty lastName;
    public StringProperty Job;
    public StringProperty Department;
    public StringProperty Active;

    public StringProperty productID;
    public StringProperty brand;
    public StringProperty definition;
    public StringProperty price;
    public StringProperty company;
    public StringProperty isBelong;
    public StringProperty isWaste;
    public StringProperty purchaseDate;

    //Personnel
    public AssignFullTreeTable(String personnelID, String name, String lastName, String Job, String Department, String Active) {
        this.personnelID = new SimpleStringProperty(personnelID);
        this.name = new SimpleStringProperty(name);
        this.lastName = new SimpleStringProperty(lastName);
        this.Job = new SimpleStringProperty(Job);
        this.Department = new SimpleStringProperty(Department);
        this.Active = new SimpleStringProperty(Active);
    }

    //Product 
    public AssignFullTreeTable(String productID, String brand, String definition, String price,
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

    public Assign getResponsibilityAll() {
        Assign r = new Assign();
        r.personnelID = Integer.parseInt(this.personnelID.getValue());
        r.productID = Integer.parseInt(this.productID.getValue());
        r.assignDate= new Date();
        r.name = this.name.getValue();
        r.lastName= this.lastName.getValue();
        r.brand = this.brand.getValue();
        r.definition = this.definition.getValue();
        r.price= Double.parseDouble(this.price.getValue());
        r.active= Integer.parseInt(this.Active.getValue());
        return r;
    }
    
    public Assign getResponsibilityProduct(){
        Assign r = new Assign();
        
        r.productID = Integer.parseInt(this.productID.getValue());
        r.assignDate= new Date();
        r.brand = this.brand.getValue();
        r.definition = this.definition.getValue();
        r.price= Double.parseDouble(this.price.getValue());
        return r;
    }
}
