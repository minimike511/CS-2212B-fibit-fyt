package ca.uwo.csd.cs2212.team14_fyt.Main;

import java.util.Calendar;

/**
 * WaterLogModel.java
 * 
 * WaterLogModel is an interface for displaying the information from the user's
 * water logged as obtained by the Fitbit API.
 * 
 * @author Fyt Team
 * @version 1.0
 * @since March 19, 2016
 */
public interface WaterLogModel {
    
    public void getWaterLog(Calendar date);
    public int[] getWaterLog();
    public int getWaterLogElement(int index);
    public String logEntryGen(int index);
    public String multiLogGen();
    
}
