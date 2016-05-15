package ca.uwo.csd.cs2212.team14_fyt.Main;

import java.util.Calendar;

/**
 * DailyModel.java
 * Interface class that has methods to display the user's Daily data.
 * 
 * @author Fyt Team
 * @version 1.0
 * @since March 20, 2016
 */
public interface DailyModel {
    void getDailyData(Calendar date);
    public int getSummaryActivityCalories();
    public int getSummaryCaloriesBMR();
    public int getSummaryCaloriesOut();
    public double getSummaryTotalDistance();
    public double getSummaryElevation();
    public int getSummaryFairlyActiveMinutes();
    public int getSummaryFloors();
    public int getSummaryLightlyActiveMinutes();
    public int getSummaryMarginalCalories();
    public int getSummarySedentaryMinutes();
    public int getSummarySteps();
    public int getSummaryVeryActiveMinutes();
    public int getGoalsSteps();
    public int getGoalsActiveMinutes();
    public int getGoalsCaloriesOut();
    public double getGoalsDistance();
    public int getGoalsFloors();
}
