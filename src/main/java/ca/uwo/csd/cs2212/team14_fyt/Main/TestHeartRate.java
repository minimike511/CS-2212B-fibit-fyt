package ca.uwo.csd.cs2212.team14_fyt.Main;
import java.io.*;
import java.util.Calendar;

/**
 * Class to test whether the user's heart rate
 * is within the specified zones
 * 
 * @author Fyt Team
 * @version 1.0
 * @since 2016-02-28.
 */
public class TestHeartRate implements HeartModel,Serializable {

    //declaration and init for data
    private int outOfRangeMax = 94;
    private int outOfRangeMin = 30;
    private int outOfRangeMinute = 400;
    private int fatBurnMax = 131;
    private int fatBurnMin = 94;
    private int fatBurnMinute = 120;
    private int cardioMax = 159;
    private int cardioMin = 131;
    private int cardioMinute = 30;
    private int peakMax = 220;
    private int peakMin = 159;
    private int peakMinute = 20;
    private int rest = 70;

    /**
     * Creates an empty constructor
     */
    public TestHeartRate(){}
    
    /**
     * Method to get the daily data
     */
    public void getDailyData(Calendar date){}

    //======= Getter Methods =====================
    /**
     * Method to get the minimum value of
     * the peak heart rate zone
     * @return int This returns the value of the private variable peakMin
     */
    public int getPeakMin() {return peakMin;}
    
    /**
     * Method to get the maximum value of
     * the out of range heart rate zone
     * @return int This returns the value of the private variable outOfRangeMax
     */
    public int getOutOfRangeMax() {return outOfRangeMax;}
    
    /**
     * Method to get the minimum value of
     * the out of range heart rate zone
     * @return int This returns the value of the private variable outOfRangeMin
     */
    public int getOutOfRangeMin() {return outOfRangeMin;}
    
    /**
     * Method to get the maximum value of 
     * the fat burn heart rate zone
     * @return int This returns the value of the private variable fatBurnMax
     */
    public int getFatBurnMax() {return fatBurnMax;}
    
    /**
     * Method to get the minimum value of
     * the fat burn heart rate zone
     * @return int This returns the value of the private variable fatBurnMin
     */
    public int getFatBurnMin() {return fatBurnMin;}
    
    /**
     * Method to get the maximum value of
     * the cardio heart rate zone
     * @return int This returns the value of the private variable cardioMax
     */
    public int getCardioMax() {return cardioMax;}
    /**
     * Method to get the minimum value of
     * the cardio heart rate zone
     * @return int This returns the value of the private variable cardioMin
     */
    public int getCardioMin() {return cardioMin;}
    /**
     * Method to get the maximum value of 
     * the peak heart rate zone
     * @return int This returns the value of the private variable peakMax
     */
    public int getPeakMax() {return peakMax;}
    /**
     * Method to get the minute value of 
     * the out of range heart rate zone
     * @return outOfRangeMinute as an int
     */
    public int getOutOfRangeMinute() {
        return outOfRangeMinute;
    }

     /**
     * Method to get the minute value of 
     * the fatburn heart rate zone
     * @return fateBurnMinute as an int
     */
    public int getFatBurnMinute() {
        return fatBurnMinute;
    }

     /**
     * Method to get the minute value of 
     * the cardio heart rate zone
     * @return cardioMinute as an int
     */
    public int getCardioMinute() {
        return cardioMinute;
    }

    /**
     * Method to get the minute value of 
     * the peak heart rate zone
     * @return peakMinute as an int
     */
    public int getPeakMinute() {
        return peakMinute;
    }
    
    /**
     * Method to get the BPM value of 
     * the rest heart rate zone
     * @return rest as an int
     */
    public int getRest(){
        return rest;
    }
    //====== End of Getter Methods ================
}
