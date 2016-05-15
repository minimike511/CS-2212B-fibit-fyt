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
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DataAccessDailySummary.java 
 * 
 * This class is one of several DataAccess classes that members of Team 14 can
 * use to access and request for data. Used to get data for Daily Summaries 
 * and Goals.
 * 
 * @author Fyt Team
 * @version 1.0
 * @since 2016-02-23
 */
public class DataAccessDailySummary implements DailyModel, Serializable{
    //============== Attributes ==========================

    //code to check whether success or fail
    private int statusCode;
    private RefreshTokens refresh;
    private OAuth2AccessToken accessToken;
    private FitbitOAuth20ServiceImpl service;
    private Response response;

    //Declaration for daily summary
    private int summaryActivityCalories;
    private int summaryCaloriesBMR;
    private int summaryCaloriesOut;
    private double summaryTotalDistance;
    private double summaryElevation;
    private int summaryFairlyActiveMinutes;
    private int summaryFloors;
    private int summaryLightlyActiveMinutes;
    private int summaryMarginalCalories;
    private int summarySedentaryMinutes;
    private int summarySteps;
    private int summaryVeryActiveMinutes;

    //Declaration for daily goals
    private int goalsActiveMinutes;
    private int goalsCaloriesOut;
    private double goalsDistance;
    private int goalsFloors;
    private int goalsSteps;

    //============== Constructors ==========================

    /**
     * Constructor DataAccessDailySummary
     * Gets all the tokens and service required for HTTPS request
     */
    public DataAccessDailySummary() {
        refresh = new RefreshTokens();
        accessToken = refresh.getAccessToken();
        service = refresh.getService();
    }

    /*
    GET https://api.fitbitcom/1/user/[user-id]/activities/date/[date].json
    user-id	The encoded ID of the user. Use "-" (dash) for current logged-in user.
    date	The date in the format yyyy-MM-dd
    */

    //============== Methods ==========================

    
    /**
     * This public void method requests
     *  daily summary data from fitbit
     * @param date a Calendar type, and input date goes here
     */
    public void getDailyData(Calendar date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar today = Calendar.getInstance();
        String formatDate = dateFormat.format(date.getTime());

        String url = "https://api.fitbit.com/1/user/3WGW2P/activities/date/" + formatDate + ".json";

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
                System.out.println("Success got Daily Summary and Goals!");
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
            JSONObject json = (JSONObject) parser.parse(data);
            JSONObject summary = (JSONObject) json.get("summary");
            JSONObject goals = (JSONObject) json.get("goals");

            //setting summary
            try {
                //try get the data as double
                summaryElevation = (Double) summary.get("elevation");
            }catch (ClassCastException e) {
                //if the data is 0 and gives exception, the cast it as long
                summaryElevation = (Long) summary.get("elevation");
            }
            try {
                //try get the data as double
                summaryTotalDistance = (Double)((JSONObject) ((JSONArray) summary.get("distances")).get(0)).get("distance");
            }catch(ClassCastException e){
                //if the data is 0 and gives exception, the cast it as long
                summaryTotalDistance = (Long)((JSONObject) ((JSONArray) summary.get("distances")).get(0)).get("distance");
            }
            summaryActivityCalories = Integer.parseInt(summary.get("activityCalories").toString());
            summaryCaloriesBMR = Integer.parseInt(summary.get("caloriesBMR").toString());
            summaryCaloriesOut = Integer.parseInt(summary.get("caloriesOut").toString());
            summaryFloors = Integer.parseInt(summary.get("floors").toString());
            summaryLightlyActiveMinutes = Integer.parseInt(summary.get("lightlyActiveMinutes").toString());
            summaryMarginalCalories = Integer.parseInt(summary.get("marginalCalories").toString());
            summarySedentaryMinutes = Integer.parseInt(summary.get("sedentaryMinutes").toString());
            summarySteps = Integer.parseInt(summary.get("steps").toString());
            summaryFairlyActiveMinutes = Integer.parseInt(summary.get("fairlyActiveMinutes").toString());
            summaryVeryActiveMinutes = Integer.parseInt(summary.get("veryActiveMinutes").toString());

            //setting goals
            try {
                //get the data as double
                goalsDistance = (Double)goals.get("distance");
            }catch (ClassCastException e){
                //if the data is 0 then get it as long
                goalsDistance = (Long)goals.get("distance");
            }
            goalsSteps = Integer.parseInt(goals.get("steps").toString());
            goalsFloors= Integer.parseInt(goals.get("floors").toString());
            goalsCaloriesOut = Integer.parseInt(goals.get("caloriesOut").toString());
            goalsActiveMinutes = Integer.parseInt(goals.get("activeMinutes").toString());

        }catch (ParseException e) {
            e.printStackTrace();
        }
    }
    //===================Summary getter methods============================================================//
    /**
     * getter method for Summary Activity Calories
     * @return Summary Activity Calories in int
     */
    public int getSummaryActivityCalories() {return summaryActivityCalories;}

    /**
     * getter method for Summary Calories BMR
     * @return Summary Calories BMR in int
     */
    public int getSummaryCaloriesBMR() {return summaryCaloriesBMR;}

    /**
     * getter method for Summary Calories Out
     * @return Summary Calories Out in int
     */
    public int getSummaryCaloriesOut() {return summaryCaloriesOut;}

    /**
     * getter method for Summary Total Distance
     * @return Summary Total Distance in double
     */
    public double getSummaryTotalDistance() {return summaryTotalDistance;}

    /**
     * getter method for Summary Elevation
     * @return Summary Elevation in double
     */
    public double getSummaryElevation() {return summaryElevation;}

    /**
     * getter method for Summary Fairly Active Minutes
     * @return Summary Fairly Active Minutes in int
     */
    public int getSummaryFairlyActiveMinutes() {return summaryFairlyActiveMinutes;}

    /**
     * getter method for Summary Floors
     * @return Summary Floors in int
     */
    public int getSummaryFloors() {return summaryFloors;}

    /**
     * getter methods for Summary Light Active Minutes
     * @return Summary Ligth Active Minutes in int
     */
    public int getSummaryLightlyActiveMinutes() {return summaryLightlyActiveMinutes;}

    /**
     * getter method for Summary Marginal Calories
     * @return Summary Marginal Calories in int
     */
    public int getSummaryMarginalCalories() {return summaryMarginalCalories;}

    /**
     * getter method for Summary Sedentary Minutes
     * @return Summary Sedentary Minutes in int
     */
    public int getSummarySedentaryMinutes() {return summarySedentaryMinutes;}

    /**
     * getter method for Summary Steps
     * @return Summary steps in int
     */
    public int getSummarySteps() {return summarySteps;}

    /**
     * getter method for Summary Very Active Minutes
     * @return SummaryVeryActiveMinutes in int
     */
    public int getSummaryVeryActiveMinutes() {return summaryVeryActiveMinutes;}
    //======================= End of Summary Getter Methods==================================================//

    //=================== Goals getter methods============================================================//
    /**
     * getGoalSteps method
     * @return daily goal steps in int
     */
    public int getGoalsSteps() {return goalsSteps;}

    /**
     * getGoalsActiveMinutes method
     * @return daily goal active minutes in int
     */
    public int getGoalsActiveMinutes() {return goalsActiveMinutes;}

    /**
     * getGoalsCaloriesOut mmethos
     * @return daily goal calories out in int
     */
    public int getGoalsCaloriesOut() {return goalsCaloriesOut;}

    /**
     * getGoalsDistance method
     * @return daily goal distance in double
     */
    public double getGoalsDistance() {return goalsDistance;}

    /**
     * getGoalsfloors method
     * @return daily goal floors in int
     */
    public int getGoalsFloors() {return goalsFloors;}
    //======================= End of Goals getter methods==================================================//
}
