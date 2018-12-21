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
public class BuyStockTreeTable extends RecursiveTreeObject<BuyStockTreeTable> {

    public StringProperty productID;
    public StringProperty brand;
    public StringProperty definition;
    public StringProperty price;
    public StringProperty company;

    //Computer
    public BuyStockTreeTable(String productID, String brand, String definition, String price,
            String company) {
        this.productID = new SimpleStringProperty(productID);
        this.brand = new SimpleStringProperty(brand);
        this.definition = new SimpleStringProperty(definition);
        this.price = new SimpleStringProperty(price);
        this.company = new SimpleStringProperty(company);
       
    }

    public BuyStock getBuyProduct() {
        BuyStock p = new BuyStock();
        p.productID = Integer.parseInt(this.productID.getValue());
        p.brand = this.brand.getValue();
        p.definition =  this.definition.getValue();
        p.price = Double.parseDouble(this.price.getValue());
        p.company  =  this.company.getValue();
        return p;
    }
    
}
