/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 * Database'den gelen veya Database'e gidecek olan zimmetleri taşımak için
 * kullanılan sınıf
 *
 * @author OGUZHAN
 */
public class Assign {

    public int personnelID;
    public int productID;
    public Date assignDate;

    public String name;
    public String lastName;
    public String brand;
    public String definition;
    public double price;
    public int active;

    @Override
    public String toString() {
        return "Assign{" + "personnelID=" + personnelID + ", AssignDate=" + assignDate + ", name=" + name + ", lastName=" + lastName + ", brand=" + brand + ", definition=" + definition + ", price=" + price + ", active= " + active + '}';
    }

}
