package ca.uwo.csd.cs2212.team14_fyt.Twitter;

import javax.swing.JOptionPane;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * @author Michael Park
 * @version 1.0
 * This class is used to tweet(post) only.
 */
public class Tweet {

    //Declaration
    private TwitterToken twitterToken;

    /**
     * Constructor init tweittertoken variable
     */
    public Tweet(){
        try{
            twitterToken = new TwitterToken();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    /**
     * This private method is used to post something to twitter
     * @param tweet, a string that is going to be posted
     */
    public void writeTweet (String tweet){
        try {
            twitterToken.getTwitter().updateStatus(tweet);
        } catch (TwitterException e) {
            JOptionPane.showMessageDialog(null, "No Text! Write something!", "fyt - ERROR",JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
