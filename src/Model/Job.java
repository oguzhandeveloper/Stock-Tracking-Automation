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
public class Job {
    public int jobID;
    public String name;

    @Override
    public String toString() {
        return "Job{" + "jobID=" + jobID + ", name=" + name + '}';
    }
}
