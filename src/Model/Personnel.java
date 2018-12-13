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
public class Personnel {
    public int ID;
    public String username;
    public String password;
    public String name;
    public String lastName;
    public String Job;
    public String Department;
    public int Active;

    @Override
    public String toString() {
        return "Personnel{" + "ID=" + ID + ", username=" + username + ", password=" + password + ", name=" + name + ", lastName=" + lastName + ", Job=" + Job + ", Department=" + Department + ", Active=" + Active + '}';
    }
}
