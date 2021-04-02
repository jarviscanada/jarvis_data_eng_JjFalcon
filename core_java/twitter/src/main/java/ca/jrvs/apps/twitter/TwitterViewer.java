package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import java.net.URI;
import java.sql.SQLOutput;
import java.util.Random;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.HttpResponse;

public class TwitterViewer {

  private static final String CONSUMER_KEY = System.getenv("consumerKey");
  private static final String CONSUMER_SECRET = System.getenv("consumerSecret");
  private static final String ACCESS_TOKEN = System.getenv("accessToken");
  private static final String TOKEN_SECRET = System.getenv("tokenSecret");

  public static void main(String[] args) {

    // set-up authorization keys to allow this application to access Twitter API resources
    OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
    consumer.setTokenWithSecret(ACCESS_TOKEN, TOKEN_SECRET);

    Random random = new Random();
    // version allows for multiple testing of the same text since Twitter prohibits posting the same message.
    int version = random.nextInt(100);
    String tweeterMessage = "It's a marvelous day agent " + version + "!";

    Double[] coordinates = {43.595310, -79.640579};
    String type = "Degrees.Minutes";
    Coordinates mississauga = new Coordinates();
    mississauga.setCoordinates(coordinates);
    mississauga.setType(type);

    Tweet tweet = new Tweet();
    tweet.setTweeterMessage(tweeterMessage);
    tweet.setLocation(mississauga);

    TwitterHttpHelper twitterHttpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);

    TwitterDao twitterDao = new TwitterDao(twitterHttpHelper);
    // tests create method
    Tweet returnedTweet = twitterDao.create(tweet);

    // gets ID to delete later
    System.out.println("ID to delete: " + returnedTweet.getId());

    // tests findbyID method
    //Tweet returnedTweet = twitterDao.findById("1377624345250512903");

    // tests  deletebyID method
    // Tweet returnedTweet = twitterDao.deleteById(1377813262486634503);

    System.out.println(returnedTweet.getTweeterMessage());
  }
}
