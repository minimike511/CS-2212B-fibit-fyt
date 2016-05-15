package ca.uwo.csd.cs2212.team14_fyt.Main;

import com.github.scribejava.apis.service.FitbitOAuth20ServiceImpl;
import com.github.scribejava.core.exceptions.OAuthConnectionException;
import com.github.scribejava.core.exceptions.OAuthException;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;

/**
 * Class to access the lifetime data from the Fitbit API
 * 
 * @author Fyt Team
 * @version 1.0
 * @since March 20, 2016
 */
public class DataAccessLifetime implements LifetimeModel{

    //code to check whether success or fail
    private RefreshTokens refresh;
    private OAuth2AccessToken accessToken;
    private FitbitOAuth20ServiceImpl service;
    private Response response;
    private int statusCode;

    //declare and init variables for lifetime totals
    private int lifetimeSteps = 0;
    private int lifetimeFloors = 0;
    private double lifetimeDistance = 0.0;

    //declare and init variables for lifetime bests
    private int bestSteps = 0;
    private double bestFloors = 0.0;
    private double bestDistance = 0.0;
    private String stepsDate;
    private String floorsDate;
    private String distanceDate;

    public DataAccessLifetime() {
        refresh = new RefreshTokens();
        accessToken = refresh.getAccessToken();
        service = refresh.getService();
    }

    public void getLifetimeData() {
        String url = "https://api.fitbit.com/1/user/3WGW2P/activities.json";

        OAuthRequest request = new OAuthRequest(Verb.GET, url, service);
        service.signRequest(accessToken, request);

        try {
            //  This actually sends the request:
            response = request.send();
        } catch (OAuthConnectionException e) {
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
                System.out.println("Success! Got Lifetime goals!");
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
                    JOptionPane.showMessageDialog(null, "Token Expired. Requires new token from Beth", "fyt - ERROR",JOptionPane.INFORMATION_MESSAGE);
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
            JSONObject best = (JSONObject) ((JSONObject) json.get("best")).get("total");
            JSONObject lifetime = (JSONObject) ((JSONObject) json.get("lifetime")).get("total");
            
            distanceDate = ((JSONObject) best.get("distance")).get("date").toString();
            try {
                bestDistance = (Double) ((JSONObject) best.get("distance")).get("value");
            } catch (ClassCastException e) {
                bestDistance = Integer.parseInt(((JSONObject) best.get("distance")).get("value").toString());
            }
            floorsDate = ((JSONObject) best.get("floors")).get("date").toString();
            try {
                bestFloors = (Double) ((JSONObject) best.get("floors")).get("value");
            } catch (ClassCastException e) {
                bestFloors = Integer.parseInt(((JSONObject) best.get("floors")).get("value").toString());
            }
            bestSteps = Integer.parseInt(((JSONObject) best.get("steps")).get("value").toString());
            stepsDate = ((JSONObject) best.get("steps")).get("date").toString();

            try {
                lifetimeDistance = (Double) (lifetime.get("distance"));
            } catch (ClassCastException e) {
                lifetimeDistance = Integer.parseInt(lifetime.get("distance").toString());
            }
            lifetimeFloors = Integer.parseInt(lifetime.get("floors").toString());
            lifetimeSteps = Integer.parseInt(lifetime.get("steps").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    //===================== Getter methods for total ===================
    
    /**
     * Method to return the user's lifetime steps taken
     * @return int This method returns the value of lifetimeSteps from the API
     */
    public int getLifetimeSteps() {
        return lifetimeSteps;
    }

    /**
     * Method to return the user's lifetime floors climbed
     * @return int This method returns the value of lifetimeFloors from the API
     */
    public int getLifetimeFloors() {
        return lifetimeFloors;
    }

    /**
     * Method to return the user's lifetime distance traveled
     * @return double This method returns the value of lifetimeDistance from the API
     */
    public double getLifetimeDistance() {
        return lifetimeDistance;
    }
    //=================== End of total getter methods ==================

    //=================== Getter methods for best ======================
    
    /**
     * Method to return the user's best days for steps taken
     * @return int This method returns the value of bestSteps from the API
     */
    public int getBestSteps() {
        return bestSteps;
    }

    /**
     * Method to return the user's best days for floors climbed
     * @return int This method returns the value of bestFloors from the API
     */
    public double getBestFloors() {
        return bestFloors;
    }

    /**
     * Method to return the user's best days for distance traveled
     * @return double This method returns the value of bestDistance from the API
     */
    public double getBestDistance() {
        return bestDistance;
    }
    //=================== End of best methods ===========================

    /**
     * Method to return the date the user achieved their best steps taken
     * @return String This method returns the value of stepsDate from the API
     */
    public String getStepsDate() {
        return stepsDate;
    }

    /**
     * Method to return the date the user achieved their best floors climbed
     * @return String This method returns the value of floorsDate from the API
     */
    public String getFloorsDate() {
        return floorsDate;
    }

    /**
     * Method to return the date the user achieved their best distance traveled
     * @return String This method returns the value of distanceDate from the API
     */
    public String getDistanceDate() {
        return distanceDate;
    }
}
