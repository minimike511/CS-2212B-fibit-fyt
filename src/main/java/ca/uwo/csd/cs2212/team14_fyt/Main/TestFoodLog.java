package ca.uwo.csd.cs2212.team14_fyt.Main;

import java.util.Calendar;

/**
 * TestFoodLog.java
 * 
 * The TestFoodLog class creates canned data for testing purposes, used
 * to test the functionality of the FoodWater detail screen.
 * 
 * @author Fyt Team
 * @version 1.0
 * @since March 17, 2016
 */
public class TestFoodLog implements FoodModel{
    private double summaryCalories = 1600.0;
    private double summaryCarbs = 23.98;
    private double summaryFat = 120.2;
    private double summaryFiber = 25.43;
    private double summaryProtein = 51.33;
    private double summarySodium = 800.33;
    private double summaryWater = 1800.00;
    
    private double[] logAmount = {1,2,3};
    private String[] logName = {"Bread", "Egg","Cookie"};
    private double[] logCalorie = {100,200,500};
    private double[] logCarbs = {98,98,152};
    private double[] logFat = {5.9, 45.9, 34.0};
    private double[] logFiber = {4,5,8};
    private double[] logProtein = {12,65,5};
    private double[] logSodium = {0,65,86};
    private boolean logCheck = true;
    
    public TestFoodLog(){}

    public void getFoodLog(Calendar date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Method to return the number of calories the user consumed
     * @return double This returns the value from the private variable summaryCalories 
     */
    public double getSummaryCalories() {
        return summaryCalories;
    }

    /**
     * Method to return the number of carbs the user consumed
     * @return double This returns the value from the private variable summaryCarbs 
     */
    public double getSummaryCarbs() {
        return summaryCarbs;
    }

    /**
     * Method to return the amount of fat the user consumed
     * @return double This returns the value from the private variable summaryFat
     */
    public double getSummaryFat() {
        return summaryFat;
    }

    /**
     * Method to return the amount of fiber the user consumed
     * @return double This returns the value from the private variable summaryFiber 
     */
    public double getSummaryFiber() {
        return summaryFiber;
    }

    /**
     * Method to return the amount of protein the user consumed
     * @return double This returns the value from the private variable summaryCProtein
     */
    public double getSummaryProtein() {
        return summaryProtein;
    }

    /**
     * Method to return the amount of sodium the user consumed
     * @return double This returns the value from the private variable summarySodium
     */
    public double getSummarySodium() {
        return summarySodium;
    }

    /**
     * Method to return the amount of water the user consumed
     * @return double This returns the value from the private variable summaryWater
     */
    public double getSummaryWater() {
        return summaryWater;
    }
    
    /**
     * Method to return the amount of logs the user entered
     * @return double[] This returns the value of the private variable logAmount
     */
    public double[] getLogAmount() {
        return logAmount;
    }

    /**
     * Method to return the name of the logs the user entered
     * @return double[] This returns the value of the private variable logName
     */
    public String[] getLogName() {
        return logName;
    }

    /**
     * Method to return the amount of calories the user entered
     * @return double[] This returns the value of the private variable logCalorie
     */
    public double[] getLogCalorie() {
        return logCalorie;
    }

    /**
     * Method to return the amount of carbs the user entered
     * @return double[] This returns the value of the private variable logCarbs
     */
    public double[] getLogCarbs() {
        return logCarbs;
    }

    /**
     * Method to return the amount of fat the user entered
     * @return double[] This returns the value of the private variable logFat
     */
    public double[] getLogFat() {
        return logFat;
    }

    /**
     * Method to return the amount of fiber the user entered
     * @return double[] This returns the value of the private variable logFiber
     */
    public double[] getLogFiber() {
        return logFiber;
    }

    /**
     * Method to return the amount of protein the user entered
     * @return double[] This returns the value of the private variable logProtein
     */
    public double[] getLogProtein() {
        return logProtein;
    }

    /**
     * Method to return the amount of sodium the user entered
     * @return double[] This returns the value of the private variable logSodium
     */
    public double[] getLogSodium() {
        return logSodium;
    }

    /**
     * Method to check if the log was entered 
     * @return boolean This returns the value of the private variable logCheck
     */
    public boolean getLogCheck() {
        return logCheck;
    }
    
    /**
     * Method to get the log amount element from the index
     * @param index
     * @return double This returns the value of logAmount with parameter index
     */
    public double getLogAmountElement(int index) {
        return logAmount[index];
    }

    /**
     * Method to get the log name element from the index
     * @param index
     * @return double This returns the value of logName with parameter index
     */
    public String getLogNameElement(int index) {
        return logName[index];
    }

    /**
     * Method to get the log calorie element from the index
     * @param index
     * @return double This returns the value of logCalorie with parameter index
     */
    public double getLogCalorieElement(int index) {
        return logCalorie[index];
    }

    /**
     * Method to get the log carbs element from the index
     * @param index
     * @return double This returns the value of logCarbs with parameter index
     */
    public double getLogCarbsElement(int index) {
        return logCarbs[index];
    }

    /**
     * Method to get the log fat element from the index
     * @param index
     * @return double This returns the value of logFat with parameter index
     */
    public double getLogFatElement(int index) {
        return logFat[index];
    }

    /**
     * Method to get the log fiber element from the index
     * @param index
     * @return double This returns the value of logFiber with parameter index
     */
    public double getLogFiberElement(int index) {
        return logFiber[index];
    }

    /**
     * Method to get the log protein element from the index
     * @param index
     * @return double This returns the value of logProtein with parameter index
     */
    public double getLogProteinElement(int index) {
        return logProtein[index];
    }

    /**
     * Method to get the log sodium element from the index
     * @param index
     * @return double This returns the value of logSodium with parameter index
     */
    public double getLogSodiumElement(int index) {
        return logSodium[index];
    }
    
    /**
     * logEntryGen is a toString method that uses the getter methods above
     * to construct a "log item" for the Food Log detail screen.
     * 
     * @param index
     * @return log entry String, ending with a newline character
     */
    public String logEntryGen(int index) {
	return "Amount\tName\n" + this.getLogAmountElement(index) +'\t' + this.getLogNameElement(index) 
                + '\n' + "Calories\tCarbs\tFat\tFiber\tProtein\tSodium\n"
                + this.getLogCalorieElement(index) + '\t' + this.getLogCarbsElement(index) 
                + '\t' + this.getLogFatElement(index) + '\t' + this.getLogFiberElement(index) 
                + '\t' + this.getLogProteinElement(index) + '\t' + this.getLogSodiumElement(index) + '\n' + '\n';
    }
    
    /**
     * multiLogGen uses the logEntryGen to build all items in the array.
     * WARNING: Assumes that all arrays contain the same number of elements.
     * 
     * @return String s, which is all log entries concatenated together.
     */
    public String multiLogGen() {
        String s = "";
        for(int i = 0; i < this.getLogAmount().length; i++) {
            s = s + this.logEntryGen(i);
        }
        return s;
    }
}
