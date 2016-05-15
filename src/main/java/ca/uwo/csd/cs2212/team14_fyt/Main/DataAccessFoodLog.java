package ca.uwo.csd.cs2212.team14_fyt.Main;

import com.github.scribejava.apis.service.FitbitOAuth20ServiceImpl;
import com.github.scribejava.core.exceptions.OAuthConnectionException;
import com.github.scribejava.core.exceptions.OAuthException;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * API used class to get Food Log from Fitbit
 * @author Fyt Team
 * @version 1.0
 * @since March 19, 2016
 */
public class DataAccessFoodLog implements FoodModel{
    
    //============== Attributes ==========================
    //code to check whether success or fail
    private int statusCode;
    private RefreshTokens refresh;
    private OAuth2AccessToken accessToken;
    private FitbitOAuth20ServiceImpl service;
    private Response response;
    
    private double summaryCalories = 0;
    private double summaryCarbs = 0;
    private double summaryFat = 0;
    private double summaryFiber = 0;
    private double summaryProtein = 0;
    private double summarySodium = 0;
    private double summaryWater = 0;
    
    private double[] logAmount = new double[0];
    private String[] logName = new String [0];
    private double[] logCalorie = new double[0];
    private double[] logCarbs = new double [0];
    private double[] logFat = new double[0];
    private double[] logFiber = new double[0];
    private double[] logProtein = new double [0];
    private double[] logSodium = new double [0]; 
    private boolean logCheck;
    
    
    public DataAccessFoodLog(){
        refresh = new RefreshTokens();
        accessToken = refresh.getAccessToken();
        service = refresh.getService();
    }
    
    public void getFoodLog(Calendar date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = dateFormat.format(date.getTime());

        String url = "https://api.fitbit.com/1/user/3WGW2P/foods/log/date/" + formatDate + ".json";
    
        OAuthRequest request = new OAuthRequest(Verb.GET, url, service);
        service.signRequest(accessToken, request);
        try {
            //  This actually sends the request:
            response = request.send();
        }catch(OAuthConnectionException e){
            JOptionPane.showMessageDialog(null, "No Internet Connection", "fyt - ERROR",JOptionPane.INFORMATION_MESSAGE);
            System.exit(1);
        }
        //  The HTTP response from fitbit will be in HTTP format, meaning that it has a numeric code indicating
        //     whether is was successful (200) or not (400's or 500's), each code has a different meaning
        System.out.println();
        System.out.println("HTTP response code: " + response.getCode());
        statusCode = response.getCode();

        //checking whether got the data or not
        switch (statusCode) {
            case 200:
                System.out.println("Success! Got the Food Log");
                break;
            case 400:
                System.out.println("Bad Request - may have to talk to Beth");
                break;
            //Token expired and try refresh
            case 401:
                System.out.println("Likely Expired Token");
                System.out.println("Try to refresh");

                try {
                    // This uses the refresh TwitterToken to get a completely new accessToken object
                    //   See:  https://dev.fitbit.com/docs/oauth2/#refreshing-tokens
                    // This accessToken is now the current one, and the old ones will not work
                    //   again.  You should save the contents of accessToken.
                    refresh.refresh();
                    accessToken = refresh.getAccessToken();
                    service = refresh.getService();
                } catch (OAuthException e) {
                    JOptionPane.showMessageDialog(null, "Token Expired. Requires new token from Beth", "fyt_ERROR",JOptionPane.INFORMATION_MESSAGE);
                    System.exit(1);
                }
                // Now we can try to access the service again
                // Make sure you create a new OAuthRequest object each time!
                request = new OAuthRequest(Verb.GET, url, service);
                service.signRequest(accessToken, request);
                response = request.send();
                break;
            case 429:
                JOptionPane.showMessageDialog(null, "Refresh Limit Exceeded, try again 1hr later", "fyt_ERROR",JOptionPane.INFORMATION_MESSAGE);
                System.exit(1);
                break;
            default:
                System.out.println("HTTP response code: " + response.getCode());
                System.out.println("HTTP response body:\n" + response.getBody());
        }
        String data = response.getBody();

        //JSONParse to parse the response.getBody() and set the data using setter methods
        JSONParser parser = new JSONParser();
        try {
            //reaching to the inner object
            JSONObject json = (JSONObject) parser.parse(data);
            JSONArray log = (JSONArray) json.get("foods");
            JSONObject summary = (JSONObject)json.get("summary");
          
            if(log.size() > 0){
                logCheck = true;
                logAmount = new double[log.size()];
                logName = new String [log.size()];
                logCalorie = new double [log.size()];
                logCarbs = new double [log.size()];
                logFat = new double[log.size()];
                logFiber = new double[log.size()];
                logProtein = new double [log.size()];
                logSodium = new double[log.size()];
                for (int i = 0; i < log.size(); i++){
                    
                    logName[i] = ((JSONObject)((JSONObject)log.get(i)).get("loggedFood")).get("name").toString();
                    try {
                    //try get the data as double
                        logAmount[i] = (Double)((JSONObject)((JSONObject)log.get(i)).get("loggedFood")).get("amount");
                    }catch(ClassCastException e){
                    //if the data is 0 and gives exception, the cast it as long
                        logAmount[i] = (Long)((JSONObject)((JSONObject)log.get(i)).get("loggedFood")).get("amount");
                    }
                    try {
                    //try get the data as double
                        logCalorie[i] = (Double)((JSONObject)((JSONObject)log.get(i)).get("nutritionalValues")).get("calories");
                    }catch(ClassCastException e){
                    //if the data is 0 and gives exception, the cast it as long
                        logCalorie[i] = (Long)((JSONObject)((JSONObject)log.get(i)).get("nutritionalValues")).get("calories");
                    }
                    try {
                    //try get the data as double
                        logCarbs[i] = (Double)((JSONObject)((JSONObject)log.get(i)).get("nutritionalValues")).get("carbs");
                    }catch(ClassCastException e){
                    //if the data is 0 and gives exception, the cast it as long
                        logCarbs[i] = (Long)((JSONObject)((JSONObject)log.get(i)).get("nutritionalValues")).get("carbs");
                    }
                    try {
                    //try get the data as double
                        logFat[i] = (Double)((JSONObject)((JSONObject)log.get(i)).get("nutritionalValues")).get("fat");
                    }catch(ClassCastException e){
                    //if the data is 0 and gives exception, the cast it as long
                        logFat[i] = (Long)((JSONObject)((JSONObject)log.get(i)).get("nutritionalValues")).get("fat");
                    }
                    try {
                    //try get the data as double
                        logFiber[i] = (Double)((JSONObject)((JSONObject)log.get(i)).get("nutritionalValues")).get("fiber");
                    }catch(ClassCastException e){
                    //if the data is 0 and gives exception, the cast it as long
                        logFiber[i] = (Long)((JSONObject)((JSONObject)log.get(i)).get("nutritionalValues")).get("fiber");
                    }
                    try {
                    //try get the data as double
                        logProtein[i] = (Double)((JSONObject)((JSONObject)log.get(i)).get("nutritionalValues")).get("protein");
                    }catch(ClassCastException e){
                    //if the data is 0 and gives exception, the cast it as long
                        logProtein[i] = (Long)((JSONObject)((JSONObject)log.get(i)).get("nutritionalValues")).get("protein");
                    }
                    try {
                    //try get the data as double
                        logSodium[i] = (Double)((JSONObject)((JSONObject)log.get(i)).get("nutritionalValues")).get("sodium");
                    }catch(ClassCastException e){
                    //if the data is 0 and gives exception, the cast it as long
                        logSodium[i] = (Long)((JSONObject)((JSONObject)log.get(i)).get("nutritionalValues")).get("sodium");
                    }
                }
            }else{
                logCheck = false;
                return;
            }
            
           // setting summary data
            try {
                //try get the data as double
                summaryCalories = (Double)summary.get("calories");
            }catch(ClassCastException e){
                //if the data is 0 and gives exception, the cast it as long
                summaryCalories = (Long)summary.get("calories");
            }
            try {
                //try get the data as double
                summaryCarbs = (Double)summary.get("carbs");
            }catch(ClassCastException e){
                //if the data is 0 and gives exception, the cast it as long
                summaryCarbs = (Long)summary.get("carbs");
            }
            try {
                //try get the data as double
                 summaryFat = (Double)summary.get("fat");
            }catch(ClassCastException e){
                //if the data is 0 and gives exception, the cast it as long
                 summaryFat = (Long)summary.get("fat");
            }
            try {
                //try get the data as double
                summaryFiber = (Double)summary.get("fiber");
            }catch(ClassCastException e){
                //if the data is 0 and gives exception, the cast it as long
                summaryFiber = (Long)summary.get("fiber");
            }
            try {
                //try get the data as double
                summaryProtein = (Double)summary.get("protein");
            }catch(ClassCastException e){
                //if the data is 0 and gives exception, the cast it as long
                summaryProtein = (Long)summary.get("protein");
            }
            try {
                //try get the data as double
                summarySodium = (Double)summary.get("sodium");
            }catch(ClassCastException e){
                //if the data is 0 and gives exception, the cast it as long
                summarySodium = (Long)summary.get("sodium");
            }
            try {
                //try get the data as double
                summaryWater = (Double)summary.get("water");
            }catch(ClassCastException e){
                //if the data is 0 and gives exception, the cast it as long
                summaryWater = (Long)summary.get("water");
            }
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    //=========== Summary Getter Methods ============
    /**
     * Method to return the number of calories from the API
     * @return double This returns the value of summaryCalories from the API
     */
    public double getSummaryCalories() {
        return summaryCalories;
    }

    /**
     * Method to return the number of carbs from the API
     * @return double This returns the value of summaryCarbs from the API 
     */
    public double getSummaryCarbs() {
        return summaryCarbs;
    }

    /**
     * Method to return the amount of fat from the API
     * @return double This returns the value of summaryFat from the API
     */
    public double getSummaryFat() {
        return summaryFat;
    }

    /**
     * Method to return the amount of fiber from the API
     * @return double This returns the value of summaryFiber from the API
     */
    public double getSummaryFiber() {
        return summaryFiber;
    }

    /**
     * Method to return the amount of protein from the API
     * @return double This returns the value of summaryProtein from the API
     */
    public double getSummaryProtein() {
        return summaryProtein;
    }

    /**
     * Method to return the amount of fat from the API
     * @return double This returns the value of summaryFat from the API
     */
    public double getSummarySodium() {
        return summarySodium;
    }

    /**
     * Method to return the amount of water from the API
     * @return double This returns the value of summaryWater from the API
     */
    public double getSummaryWater() {
        return summaryWater;
    }
    // ========= End of Summary Getter ============

    /**
     * Method to return the amount of logs the user entered
     * @return double[] This returns the value of logAmount from the API
     */
    public double[] getLogAmount() {
        return logAmount;
    }

    /**
     * Method to return the name of the log
     * @return String[] This returns the value of logName from the API
     */
    public String[] getLogName() {
        return logName;
    }

    /**
     * Method to return the amount of calories logged 
     * @return double[] This returns the value of logCalorie from the API
     */
    public double[] getLogCalorie() {
        return logCalorie;
    }

    /**
     * Method to return the amount of carbs logged 
     * @return double[] This returns the value of logCarbs from the API
     */
    public double[] getLogCarbs() {
        return logCarbs;
    }

    /**
     * Method to return the amount of fat logged 
     * @return double[] This returns the value of logFat from the API
     */
    public double[] getLogFat() {
        return logFat;
    }

    /**
     * Method to return the amount of fiber logged 
     * @return double[] This returns the value of logFiber from the API
     */
    public double[] getLogFiber() {
        return logFiber;
    }

    /**
     * Method to return the amount of protein logged 
     * @return double[] This returns the value of logProtein from the API
     */
    public double[] getLogProtein() {
        return logProtein;
    }

    /**
     * Method to return the amount of sodium logged 
     * @return double[] This returns the value of logSodium from the API
     */
    public double[] getLogSodium() {
        return logSodium;
    }

    /**
     * Method to check if the log was entered 
     * @return boolean This returns the value of logCheck from the API
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
     * Method to get the log Fiber element from the index
     * @param index
     * @return double This returns the value of logFiber with parameter index
     */
    public double getLogFiberElement(int index) {
        return logFiber[index];
    }

    /**
     * Method to get the log protein element from the index
     * @param index
     * @return double This returns the value of logProtien with parameter index
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
