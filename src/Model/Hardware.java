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
public class Hardware {
    public int hardwareID;
    public String brand;
    public String definition;
    public double price;
    public String company;
    public int isBelong;
    public int isWaste;
    public int computerID;
    public Date purchaseDate;

    @Override
    public String toString() {
        return "Hardware{" + "hardwareID=" + hardwareID + 
                ", brand=" + brand + ", definition=" +
                definition + ", price=" + price + 
                ", company=" + company + ", isBelong=" + 
                isBelong + ", isWaste=" + isWaste + ", computerID="
                + computerID + ", purchaseDate=" + purchaseDate + '}';
    }
    
    
    
}
