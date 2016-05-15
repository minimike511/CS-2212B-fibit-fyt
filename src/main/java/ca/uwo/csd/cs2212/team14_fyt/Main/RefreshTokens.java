package ca.uwo.csd.cs2212.team14_fyt.Main;

import java.io.*;

import com.github.scribejava.apis.FitbitApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.exceptions.OAuthException;
import com.github.scribejava.core.model.*; //Request Verb
import com.github.scribejava.apis.service.FitbitOAuth20ServiceImpl;

/**
 * This class is used to refresh Team 14's tokens when they expire
 * 
 * @author Fyt Team
 * @version 1.0
 * @since 2016-02-12
 */
public class RefreshTokens {

    //code to check whether success or fail used for TEST
    private int statusCode;

    private OAuth2AccessToken accessToken;
    private FitbitOAuth20ServiceImpl service;

    // This will reference one line at a time
    private String line = null;
    private String accessTokenItself = null;
    private String tokenType = null;
    private String refreshToken = null;
    private Long expiresIn = null;
    String rawResponse = null;

    /**
     * Constructor RefreshTokens gets the tokens from API.dat file
     * and runs a test to see if current TwitterToken has expired or not.
     * if it expired then it refresh TwitterToken
     */
    public RefreshTokens() {
        //read credentials from a file
        BufferedReader bufferedReader = null;

        //This is the only scope you have access to currently
        String scope = "activity%20heartrate%20nutrition";

        try {
            //String for Team14 credentials and TwitterToken location
            //leave it for case when TwitterToken expires
           // String team14Tokens = getClass().getResource("/Login/Team14Tokens.txt").getPath();

           //Strings with service credentials.
           String clientID = "227DXM";
           String apiKey = "cce121dbbe08aba328c9cc648cbd92a1";
           String apiSecret = "78eca710a190616679e14c83852b042a";

            /* leave it for case when RefreshToken expires
            FileReader fileReader = new FileReader(team14Tokens);
            bufferedReader = new BufferedReader(fileReader);

            accessTokenItself = bufferedReader.readLine();
            tokenType = bufferedReader.readLine();
            refreshToken = bufferedReader.readLine();
            expiresIn = Long.parseLong(bufferedReader.readLine());
            rawResponse = bufferedReader.readLine();
            */

            ObjectInputStream in = new ObjectInputStream(new FileInputStream("API.dat"));
            accessToken = (OAuth2AccessToken) in.readObject();
            in.close();

            accessTokenItself = accessToken.getToken();
            tokenType = accessToken.getToken();
            refreshToken = accessToken.getRefreshToken();
            expiresIn = accessToken.getExpiresIn();
            rawResponse = accessToken.getRawResponse();

            //  Create the Fitbit service - you will ask this to ask for access/refresh pairs
            //     and to add authorization information to the requests to the API
            service = (FitbitOAuth20ServiceImpl) new ServiceBuilder()
                    .apiKey(clientID)       //fitbit uses the clientID here
                    .apiSecret(apiSecret)
                    .callback("http://localhost:8080")
                    .scope(scope)
                    .grantType("authorization_code")
                    .build(FitbitApi20.instance());

        //  The access RefreshToken contains everything you will need to authenticate your requests
        //  It can expire - at which point you will use the refresh TwitterToken to refresh it
        //  See: https://dev.fitbit.com/docs/oauth2/#refreshing-tokens
        //    I have authenticated and given you the contents of the response to use

            /* leave it for case when RefreshToken expires
        accessToken = new OAuth2AccessToken(
                accessTokenItself,
                tokenType,
                refreshToken,
                expiresIn,
                rawResponse);
                */


        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file\n" + ex.getMessage());
            System.exit(1);

        }catch(ClassNotFoundException e){
            System.out.println(
                    "Class not found");
            System.exit(1);
        } catch (IOException ex) {
            System.out.println(
                    "Error reading/write file\n" + ex.getMessage());
            System.exit(1);
        } finally {
            try {
                if (bufferedReader != null)
                    // Always close files.
                    bufferedReader.close();
            } catch (Exception e) {
                System.out.println(
                        "Error closing file\n" + e.getMessage());
            }
        }
        // IF YOU DO NOT SAVE THE CURRENTLY ACTIVE TOKEN INFO YOU WILL NOT BE ABLE TO REFRESH
        //   - contact Beth if this happens and she can reissue you a fresh set
        write();
    }//end constructor\

    /**
     * public void methods it refresh tokens if it's expired
     * and runs a private void methods called write() to store
     * the new refreshed tokens
     */
    public void refresh(){
        accessToken = service.refreshOAuth2AccessToken(accessToken);
        //refreshing following values with new accessToken
        accessTokenItself = accessToken.getToken();
        tokenType = accessToken.getToken();
        refreshToken = accessToken.getRefreshToken();
        expiresIn = accessToken.getExpiresIn();
        rawResponse = accessToken.getRawResponse();
        //write and save refreshed tokens
        write();
    }

    /**
     * private void method that stores
     * new refreshed tokens to API.dat
     */
    private void write(){

        /****************************************************************************
         * Some of the commented codes are for case when the API.dat tokens are expired and
         * requires new tokens from Beth
         ****************************************************************************/
        //BufferedWriter bufferedWriter = null;
        try {
            /* Leave it for case when TwitterToken gets expired
            String team14Tokens = getClass().getResource("/Login/Team14Tokens.txt").getPath();
            FileWriter fileWriter;
            fileWriter = new FileWriter(team14Tokens);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(accessToken.getToken());
            bufferedWriter.newLine();
            bufferedWriter.write(accessToken.getTokenType());
            bufferedWriter.newLine();
            bufferedWriter.write(accessToken.getRefreshToken());
            bufferedWriter.newLine();
            bufferedWriter.write(accessToken.getExpiresIn().toString());
            bufferedWriter.newLine();
            bufferedWriter.write(accessToken.getRawResponse());
            bufferedWriter.newLine();
            bufferedWriter.close();
            */
            //Save the TwitterToken in API.dat file
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("API.dat"));
            out.writeObject(accessToken);
            out.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file\n" + ex.getMessage());
        } catch (IOException ex) {
            System.out.println(
                    "Error reading/write file\n" + ex.getMessage());
        }
        /*
        finally {
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (Exception e) {
                System.out.println(
                        "Error closing file\n" + e.getMessage());
            }
        }//end try
        */
    }

    //=======Getter Methods=============

    /**
     * getter methods for service
     * @return FitbitOAuth20ServiceImpl
     */
    public FitbitOAuth20ServiceImpl getService() {
        return service;
    }

    /**
     * getter method for AccessToken
     * @return OAuth2AccessToken,
     */
    public OAuth2AccessToken getAccessToken() {return accessToken;}
    //=======End of Getter Methods==============
}//end class
