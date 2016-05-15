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

/**
 * DataAccessHeartRate.java
 * <p>
 * This class is one of several DataAccess classes that members of Team 14 can
 * use to access and request for data. Used to get data for Heart Rate.
 *
 * @author Fyt Team
 * @version 1.0
 * @since 2016-02-25
 */
public class DataAccessHeartRate implements HeartModel, Serializable {

    //============== Attributes ==========================
    //code to check whether success or fail
    private int statusCode;
    private RefreshTokens refresh;
    private OAuth2AccessToken accessToken;
    private FitbitOAuth20ServiceImpl service;
    private Response response;

    //declaration
    private int outOfRangeMax;
    private int outOfRangeMin;
    private int outOfRangeMinute;
    private int fatBurnMax;
    private int fatBurnMin;
    private int fatBurnMinute;
    private int cardioMax;
    private int cardioMin;
    private int cardioMinute;
    private int peakMax;
    private int peakMin;
    private int peakMinute;
    private int rest;

    //============== Constructors ==========================

    /**
     * Constructor.
     * <p>
     * Takes no parameters.
     */
    public DataAccessHeartRate() {
        refresh = new RefreshTokens();
        accessToken = refresh.getAccessToken();
        service = refresh.getService();
    }

    /*
    GET https://api.fitbit.com/1/user/[user-id]/activities/heart/date/[date]/[period].json
    GET https://api.fitbit.com/1/user/[user-id]/activities/heart/date/[base-date]/[end-date].json
    user-id	The encoded ID of the user. Use "-" (dash) for current logged-in user.
    base-date	The range start date, in the format yyyy-MM-dd or today.
    end-date	The end date of the range.
    date	The end date of the period specified in the format yyyy-MM-dd or today.
    period	The range for which data will be returned. Options are 1d, 7d, 30d, 1w, 1m.
    */

    //============== Methods ==========================

    /**
     * Method that requests the daily summary data from Fitbit.
     */
    public void getDailyData(Calendar date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = dateFormat.format(date.getTime());

        String url = "https://api.fitbit.com/1/user/3WGW2P/activities/heart/date/" + formatDate + "/1d.json";

        OAuthRequest request = new OAuthRequest(Verb.GET, url, service);
        service.signRequest(accessToken, request);
        try {
            //  This actually sends the request:
            response = request.send();
        }catch(OAuthConnectionException e){
            JOptionPane.showMessageDialog(null, "No Internet Connection", "fyt - ERROR",JOptionPane.INFORMATION_MESSAGE);;
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
                System.out.println("Success! Got the Heart Rate");
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
            JSONArray activityHeart = (JSONArray) json.get("activities-heart");
            JSONObject value = (JSONObject) ((JSONObject) activityHeart.get(0)).get("value");
            JSONArray heartRateZone = (JSONArray) value.get("heartRateZones");

            //set heart rate data
            outOfRangeMax = Integer.parseInt((((JSONObject) heartRateZone.get(0)).get("max")).toString());
            outOfRangeMin = Integer.parseInt((((JSONObject) heartRateZone.get(0)).get("min")).toString());
            try{
                outOfRangeMinute = Integer.parseInt((((JSONObject) heartRateZone.get(0)).get("minutes")).toString());
            }catch (NullPointerException e){
                outOfRangeMinute = 0;
            }try{
                fatBurnMinute = Integer.parseInt((((JSONObject) heartRateZone.get(1)).get("minutes")).toString());
            }catch (NullPointerException e){
                fatBurnMinute = 0;;
            }try{
                cardioMinute = Integer.parseInt((((JSONObject) heartRateZone.get(2)).get("minutes")).toString());
            }catch(NullPointerException e){
                cardioMinute = 0;
            }try{
                peakMinute = Integer.parseInt((((JSONObject) heartRateZone.get(3)).get("minutes")).toString());
            }catch(NullPointerException e){
                peakMinute = 0;    
            }try{
                rest = Integer.parseInt(value.get("restingHeartRate").toString());
            }catch(NullPointerException e){
                rest = 0;
            }
            fatBurnMax = Integer.parseInt((((JSONObject) heartRateZone.get(1)).get("max")).toString());
            fatBurnMin = Integer.parseInt((((JSONObject) heartRateZone.get(1)).get("min")).toString());
            cardioMax = Integer.parseInt((((JSONObject) heartRateZone.get(2)).get("max")).toString());
            cardioMin = Integer.parseInt((((JSONObject) heartRateZone.get(2)).get("min")).toString());
            peakMax = Integer.parseInt((((JSONObject) heartRateZone.get(3)).get("max")).toString());
            peakMin = Integer.parseInt((((JSONObject) heartRateZone.get(3)).get("min")).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //============== Getter Methods ==========================
    /**
     * Getter method that returns the Out of Range Max Heart Rate
     *
     * @return outOfRangeMax as an int
     */
    public int getOutOfRangeMax() {
        return outOfRangeMax;
    }

    /**
     * Getter method that returns the Out of Range Min Heart Rate
     *
     * @return outOfRangeMin as an int
     */
    public int getOutOfRangeMin() {
        return outOfRangeMin;
    }

    /**
     * Getter method that returns the Max Fat Burned
     *
     * @return fatBurnMax as an int
     */
    public int getFatBurnMax() {
        return fatBurnMax;
    }

    /**
     * Getter method that returns the Min Fat Burned
     *
     * @return fatBurnMin as an int
     */
    public int getFatBurnMin() {
        return fatBurnMin;
    }

    /**
     * Getter method that returns the maximum value for Cardio
     *
     * @return cardioMax as an int
     */
    public int getCardioMax() {
        return cardioMax;
    }

    /**
     * Getter method that returns the minimum value for Cardio
     *
     * @return cardioMin as an int
     */
    public int getCardioMin() {
        return cardioMin;
    }

    /**
     * Getter method that returns the max Peak Heart Rate
     *
     * @return peakMax as an int
     */
    public int getPeakMax() {
        return peakMax;
    }

    /**
     * Getter method that returns the min Peak Heart Rate
     *
     * @return peakMin as an int
     */
    public int getPeakMin() {
        return peakMin;
    }
    
    /**
     * Getter method that returns the minute of outOfRange
     *
     * @return outOfRangeMinute as an int
     */
    public int getOutOfRangeMinute() {
        return outOfRangeMinute;
    }

     /**
     * Getter method that returns the minute of FatBurn
     *
     * @return fateBurnMinute as an int
     */
    public int getFatBurnMinute() {
        return fatBurnMinute;
    }

     /**
     * Getter method that returns the minute of cardio
     *
     * @return cardioMinute as an int
     */
    public int getCardioMinute() {
        return cardioMinute;
    }

    /**
     * Getter method that returns the minute of peak
     *
     * @return peakMinute as an int
     */
    public int getPeakMinute() {
        return peakMinute;
    }

     /**
     * Getter method that returns the rest heart rate
     *
     * @return rest heart rate as an int
     */
    public int getRest() {
        return rest;
    }
    //============ End of Getter Methods ======================
}