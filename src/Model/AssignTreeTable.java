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
public class AssignTreeTable extends RecursiveTreeObject<AssignTreeTable> {

    
    public StringProperty personnelID;
    public StringProperty productID;
    public StringProperty assignDate;
   
    public StringProperty name;
    public StringProperty lastName;
    public StringProperty brand;
    public StringProperty definition;
    public StringProperty price;
    public StringProperty active;

  
    /**
     * 
     * @param personnelID Personnel ID Number
     * @param productID Product ID Number
     * @param responsibilityDate Responsibility Record Date
     * @param name Personnel Name
     * @param lastName Personnel Last name
     * @param brand Product brand
     * @param definition Product definition
     * @param price Product price
     * @param active is Resposibility active?
     */
    public AssignTreeTable(String personnelID, String productID, Date responsibilityDate,String name, String lastName, String brand, String definition, String price, String active) {
        this.personnelID = new SimpleStringProperty(personnelID);
        this.productID = new SimpleStringProperty(productID);
        this.assignDate = new SimpleStringProperty(responsibilityDate.toString());
        this.name = new SimpleStringProperty(name);
        this.lastName = new SimpleStringProperty(lastName);
        this.brand = new SimpleStringProperty(brand);
        this.definition = new SimpleStringProperty(definition);
        this.price = new SimpleStringProperty(price);
        this.active = new SimpleStringProperty(active);
    }
    
    public Assign getResponsibility(){
        Assign r = new Assign();
        r.personnelID = Integer.parseInt(this.personnelID.getValue());
        r.productID = Integer.parseInt(this.productID.getValue());
        r.assignDate= new Date();
        r.name = this.name.getValue();
        r.lastName= this.lastName.getValue();
        r.brand = this.brand.getValue();
        r.definition = this.definition.getValue();
        r.price= Double.parseDouble(this.price.getValue());
        r.active= Integer.parseInt(this.active.getValue());
        return r;
    }
}
