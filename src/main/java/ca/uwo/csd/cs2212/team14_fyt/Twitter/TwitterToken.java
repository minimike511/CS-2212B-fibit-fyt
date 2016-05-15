package ca.uwo.csd.cs2212.team14_fyt.Twitter;


import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @version  0.1
 * @author Michael Park
 *  Following class is a accesstoken class for twitter using OAuth1.0a
 */
public class TwitterToken {

    //Declaration
    private Twitter twitter;

    /**
     *  Constructor that builds twitter
     * @throws TwitterException
     */
    public TwitterToken() throws TwitterException {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("op4sr0TWeGoHnrTiwyvWab1lS")
                .setOAuthConsumerSecret("L8rpasR1hSOY3lBBSdmj7a1iYC5yDdm14GQpQQVxlEDtgkXOmT")
                .setOAuthAccessToken("708329676406706178-iWqmnDnn5nPizutfX2Icowrp2zGukAz")
                .setOAuthAccessTokenSecret("BtsvfmnVSkXM0qp0gQBYSXrG5n62QMVAllHrO1zm3UB9m");

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    /**
     * getter methods for twitter
     * @return twitter
     */
    public Twitter getTwitter(){
        return twitter;
    }
}
