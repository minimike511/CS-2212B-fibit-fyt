package ca.uwo.csd.cs2212.team14_fyt.Main;

import java.io.*;
import java.util.Calendar;

/**
 * TestDataDaily provides fake test data for other coders 
 * to use while testing without being involved with API.dat
 * 
 * @author Fyt Team
 * @version 1.0
 * @since 2016-02-26
 */
public class TestDataDaily implements DailyModel, Serializable {

    //Declaration for daily summary
    private int summaryActivityCalories = 614;
    private int summaryCaloriesBMR = 1018;
    private int summaryCaloriesOut = 2551;
    private double summaryTotalDistance = 3.63;
    private double summaryElevation = 73.15;
    private int summaryFairlyActiveMinutes = 11;
    private int summaryFloors = 24;
    private int summaryLightlyActiveMinutes = 137;
    private int summaryMarginalCalories = 315;
    private int summarySedentaryMinutes = 759;
    private int summarySteps = 110;
    private int summaryVeryActiveMinutes = 4;


    //Declaration for daily goals
    private int goalsActiveMinutes = 240;
    private int goalsCaloriesOut = 2551;
    private double goalsDistance = 8.05;
    private int goalsFloors = 30;
    private int goalsSteps = 1000;

    /**
     * TestDataDaily constructor does nothing, an empty constructor
     */
    public TestDataDaily(){}
    
    /**
     * Method to get the daily data
     */
    public void getDailyData(Calendar date){}

    /**
     * Method to get the user's goal
     * for steps taken
     * @return int This returns the private variable goalsSteps
     */
    public int getGoalsSteps() {
        return goalsSteps;
    }

    /**
     * Method to get the number of 
     * activity calories the user burned
     * @return int This returns the private variable summaryActivityCalories
     */
    public int getSummaryActivityCalories() {
        return summaryActivityCalories;
    }

    /**
     * Method to get the number of
     * BMR calories the user burned
     * @return int This returns the private variable summaryCaloriesBMR
     */
    public int getSummaryCaloriesBMR() {
        return summaryCaloriesBMR;
    }

    /**
     * Method to get the number of
     * calories overall the user burned
     * @return int This returns the private variable summaryCaloriesOut
     */
    public int getSummaryCaloriesOut() {
        return summaryCaloriesOut;
    }

    /**
     * Method to get the total distance
     * the user has travelled
     * @return double This returns the private variable summaryTotalDistance
     */
    public double getSummaryTotalDistance() {
        return summaryTotalDistance;
    }

    /**
     * Method to get the total elevation
     * the user has climbed
     * @return double This returns the private variable summaryElevation
     */
    public double getSummaryElevation() {
        return summaryElevation;
    }

    /**
     * Method to get the number of 
     * fairly active minutes the user has
     * @return int This returns the private variable summaryFairlyActiveMinutes
     */
    public int getSummaryFairlyActiveMinutes() {
        return summaryFairlyActiveMinutes;
    }

    /**
     * Method to get the number of
     * floors the user has climbed
     * @return int This returns the private variable summaryFloors
     */
    public int getSummaryFloors() {
        return summaryFloors;
    }

    /**
     * Method to get the number of 
     * lightly active minutes the user has
     * @return int This method returns the private variable summaryLightlyActiveMinutes
     */
    public int getSummaryLightlyActiveMinutes() {
        return summaryLightlyActiveMinutes;
    }

    /**
     * Method to get the number of 
     * marginal calories the user has burned
     * @return int This method returns the private variable summaryMarginalCalories
     */
    public int getSummaryMarginalCalories() {
        return summaryMarginalCalories;
    }

    /**
     * Method to get the number of
     * sedentary minutes the user has
     * @return int This method returns the private variable summarySedentaryMinutes
     */
    public int getSummarySedentaryMinutes() {
        return summarySedentaryMinutes;
    }

    /**
     * Method to get the number of 
     * steps the user has taken
     * @return int This returns the private variable summarySteps
     */
    public int getSummarySteps() {
        return summarySteps;
    }

    /**
     * Method to get the number of 
     * very active minutes the user has 
     * @return int This returns the private variable summaryVeryActiveMinutes
     */
    public int getSummaryVeryActiveMinutes() {
        return summaryVeryActiveMinutes;
    }

    /**
     * Method to get the user's goal
     * for active minutes
     * @return int This returns the private variable goalsActiveMinutes
     */
    public int getGoalsActiveMinutes() {
        return goalsActiveMinutes;
    }

    /**
     * Method to get the user's goal 
     * for calories burned
     * @return int This returns the private variable goalsCaloriesOut
     */
    public int getGoalsCaloriesOut() {
        return goalsCaloriesOut;
    }

    /**
     * Method to get the user's goal
     * for distance travelled
     * @return double This returns the private variable goalsDistance
     */
    public double getGoalsDistance() {
        return goalsDistance;
    }

    /**
     * Method to get the user's goal
     * for floors climbed
     * @return int This returns the private variable goalsFloors
     */
    public int getGoalsFloors() {
        return goalsFloors;
    }
}

