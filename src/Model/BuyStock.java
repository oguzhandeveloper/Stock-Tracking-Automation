/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author OGUZHAN
 */
public class BuyStock {

    public int productID;
    public String brand;
    public String definition;
    public double price;
    public String company;

    @Override
    public String toString() {
        return "buyProduct{" + "productID=" + productID + ", brand=" + brand + ", definition=" + definition + ", price=" + price + ", company=" + company + '}';
    }

}
