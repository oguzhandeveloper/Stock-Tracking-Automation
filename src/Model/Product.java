/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author OGUZHAN
 */
public class Product {
    public int productID;
    public String brand;
    public String definition;
    public double price;
    public String company;
    public int isBelong;
    public int isWaste;
    public Date purchaseDate;

    @Override
    public String toString() {
        return "Product{" + "productID=" + productID + ", brand="
                + brand + ", definition=" + definition
                + ", price=" + price + ", company=" + company 
                + ", isBelong=" + isBelong + ", isWaste=" + isWaste 
                + ", purchaseDate=" + purchaseDate + '}';
    }
    
}
