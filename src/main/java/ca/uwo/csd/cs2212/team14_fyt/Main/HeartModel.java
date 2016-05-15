
package ca.uwo.csd.cs2212.team14_fyt.Main;

import java.util.Calendar;

/**
 * 
 * HeartModel.java
 * Interface class that has methods to display the user's Heart Rate data.
 * 
 * @author Fyt Team
 * @version 1.0
 * @since March 20, 2016
 */
public interface HeartModel {
    void getDailyData(Calendar date);
    public int getPeakMin();
    public int getOutOfRangeMax();
    public int getOutOfRangeMin();
    public int getFatBurnMax();
    public int getFatBurnMin();
    public int getCardioMax();
    public int getCardioMin();
    public int getPeakMax();
    public int getOutOfRangeMinute();
    public int getFatBurnMinute();
    public int getCardioMinute();
    public int getPeakMinute();
    public int getRest();
}
