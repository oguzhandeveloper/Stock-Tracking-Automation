/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author OGUZHAN
 */
public class PersonnelTreeTable extends RecursiveTreeObject<PersonnelTreeTable> {

    public StringProperty personnelID;
    public StringProperty name;
    public StringProperty lastName;
    public StringProperty Job;
    public StringProperty Department;
    public StringProperty Active;

    public PersonnelTreeTable(String ID, String name, String lastName, String Job, String Department, String Active) {
        this.personnelID = new SimpleStringProperty(ID);
        this.name = new SimpleStringProperty(name);
        this.lastName = new SimpleStringProperty(lastName);
        this.Job = new SimpleStringProperty(Job);
        this.Department = new SimpleStringProperty(Department);
        this.Active = new SimpleStringProperty(Active);
    }

    public Personnel returnPersonnel() {
        Personnel p = new Personnel();
        p.personnelID = Integer.parseInt(this.personnelID.getValue());
        p.username = "";
        p.password = "";
        p.name = this.name.getValue();
        p.lastName = this.lastName.getValue();
        p.Job = this.Job.getValue();
        p.Department= this.Department.getValue();
        p.Active = Integer.parseInt(this.Active.getValue());
        return p;
    }

}
