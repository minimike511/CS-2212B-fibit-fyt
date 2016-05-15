package ca.uwo.csd.cs2212.team14_fyt.Main;

import java.io.*;

/**
 * Class to set the variables shown on the goal screen
 * 
 * @author Fyt Team
 * @version 1.0
 * @since March 21, 2016
 */
public class GoalVariables implements Serializable {
    
    private int intensity;
    private int age;
    private int index;
    
    public GoalVariables() {
        //this.intensity = 100;
        //this.age = 20;
        //this.index = 0;
    }
    
    /**
     * Method to set the intensity the user selects
     * @param intensityValue 
     */
    public void setIntensity(int intensityValue) {
        this.intensity = intensityValue;
    }
    
    /**
     * Method to set the age to the age the user selects
     * @param ageValue 
     */
    public void setAge(int ageValue) {
        this.age = ageValue;
    }
    
    /**
     * Method to set the index value to the value the use selects
     * @param indexValue 
     */
    public void setIndex(int indexValue) {
        this.index = indexValue;
    }
    
    /**
     * Method to return the intensity the user selects
     * @return int This method returns the value of the private variable intensity
     */
    public int getIntensity() {
        return intensity;
    }
    
    /**
     * Method to return the age the user selects
     * @return int This method returns the value of the private variable age
     */
    public int getAge() {
        return age;
    }
    
    /**
     * Method to return the index the user selects
     * @return int This method returns the value of the private variable index
     */
    public int getIndex() {
        return index;
    }
    
    /**
     * Method to save the user's selections on the goals page
     * @throws Exception 
     */
    public void save() throws Exception {
        ObjectOutputStream out = new ObjectOutputStream(
        new FileOutputStream("goal_variables",false));
        out.writeObject(this);
        out.close();
    }
    
    /**
     * Method to load the user's selections from the goals page
     * @return GoalVariables This method returns the stored variables in GoalVariables
     * @throws Exception 
     */
    public static GoalVariables load() throws Exception {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("goal_variables"));
        GoalVariables c = (GoalVariables)in.readObject();
        in.close();
        return c;
    }
    
}