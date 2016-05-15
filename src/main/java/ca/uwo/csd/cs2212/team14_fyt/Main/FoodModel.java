package ca.uwo.csd.cs2212.team14_fyt.Main;

import java.util.Calendar;

/**
 * FoodModel.java
 * 
 * FoodModel is an interface for displaying the information from the user's
 * nutritional intake as obtained by the Fitbit API.
 * 
 * @author Fyt Team
 * @version 1.0
 * @since March 19, 2016
 */
public interface FoodModel {
    void getFoodLog(Calendar date);
    public double getSummaryCalories();
    public double getSummaryCarbs();
    public double getSummaryFat();
    public double getSummaryFiber();
    public double getSummaryProtein();
    public double getSummarySodium();
    public double getSummaryWater();
    public double[] getLogAmount();
    public String[] getLogName();
    public double[] getLogCalorie();
    public double[] getLogCarbs();
    public double[] getLogFat();
    public double[] getLogFiber();
    public double[] getLogProtein();
    public double[] getLogSodium();
    public boolean getLogCheck();

    public double getLogAmountElement(int index);
    public String getLogNameElement(int index);
    public double getLogCalorieElement(int index);
    public double getLogCarbsElement(int index);
    public double getLogFatElement(int index);
    public double getLogFiberElement(int index);
    public double getLogProteinElement(int index);
    public double getLogSodiumElement(int index);
    
    public String logEntryGen(int index);
    public String multiLogGen();
}
