package ca.uwo.csd.cs2212.team14_fyt.Main;

import java.util.Calendar;

/**
 * Class to test the WaterLog without using the API
 * 
 * @author Fyt Team
 * @version 1.0
 * @since March 19, 2016
 */
public class TestWaterLog implements WaterLogModel{

    private int[] waterLog = {500, 800, 900};
    
    @Override
    public void getWaterLog(Calendar date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /** 
     * Method to get the water logged
     * @return int This method returns the vale of the private variable waterLog
     */
    public int[] getWaterLog() {
        return waterLog;
    }
     public int getWaterLogElement(int index){
        return waterLog[index];
    }
    
     /**
     * logEntryGen is a toString method that uses the getter methods above
     * to construct a "log item" for the water Log detail screen.
     * 
     * @param index
     * @return log entry String, ending with a newline character
     */
    public String logEntryGen(int index) {
	return "Log" + (index+1) + ": " + String.valueOf(this.getWaterLogElement(index)) + "mL"+ '\n';
    }
    
    /**
     * multiLogGen uses the logEntryGen to build all items in the array.
     * WARNING: Assumes that all arrays contain the same number of elements.
     * 
     * @return String s, which is all log entries concatenated together.
     */
    public String multiLogGen() {
        String s = "";
        for(int i = 0; i < this.getWaterLog().length; i++) {
            s = s + this.logEntryGen(i);
        }
        return s;
    }
    
}
