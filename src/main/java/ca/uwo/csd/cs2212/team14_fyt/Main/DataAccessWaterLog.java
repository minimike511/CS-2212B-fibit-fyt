/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * Class to access the water log from the Fitbit API
 * 
 * @author Fyt Team
 * @version 1.0
 * @since March 18, 2016
 */
public class DataAccessWaterLog implements WaterLogModel{
    
    //============== Attributes ==========================
    //code to check whether success or fail
    private int statusCode;
    private RefreshTokens refresh;
    private OAuth2AccessToken accessToken;
    private FitbitOAuth20ServiceImpl service;
    private Response response;
    
    private int [] waterLog = new int[0];
    private boolean logCheck;
    
    public DataAccessWaterLog(){
        refresh = new RefreshTokens();
        accessToken = refresh.getAccessToken();
        service = refresh.getService();
    }
    
    
     public void getWaterLog(Calendar date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = dateFormat.format(date.getTime());

        String url = "https://api.fitbit.com/1/user/3WGW2P/foods/log/water/date/" + formatDate + ".json";
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
                System.out.println("Success! Got the Water Log");
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
        try{
            JSONObject json = (JSONObject) parser.parse(data);
            JSONArray log = (JSONArray) json.get("water");
             if(log.size() > 0){
                 logCheck = true;
                 waterLog = new int [log.size()];
                 for(int i = 0; i < log.size(); i++){
                    waterLog[i] = Integer.parseInt(((JSONObject)(log.get(i))).get("amount").toString());
                 }
             }else{
                 logCheck = false;
                 return;
             }
        } catch (ParseException e) {
            e.printStackTrace();
        }
     }

     /**
      * Returns the array of waterlogs
      * @return waterLogg array 
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
