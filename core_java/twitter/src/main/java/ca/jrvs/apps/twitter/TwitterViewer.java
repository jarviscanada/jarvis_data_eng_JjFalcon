package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.TweetBuilder;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

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
    //String tweeterMessage = "What a fabulous day Agent: " + version + "!";
    String longText280 = "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia.  For more information about this, call:  Agent";
    String tweeterMessage = longText280 + version + "!";
    Double latitude = 43.595310;
    Double longitude = -79.640579;

    Tweet tweet = TweetBuilder.buildTweet(tweeterMessage, latitude, longitude);

    TwitterHttpHelper twitterHttpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
    TwitterDao twitterDao = new TwitterDao(twitterHttpHelper);
    // tests create method
    // Tweet returnedTweet = twitterDao.create(tweet);
    TwitterService twitterService = new TwitterService(twitterDao);
    Tweet returnedTweet = twitterService.postTweet(tweet);

    // gets ID to delete later
    System.out.println("ID to delete: " + returnedTweet.getId());

    // tests findbyID method
    //Tweet returnedTweet = twitterDao.findById("1377624345250512903");

    // tests  deletebyID method
    // Tweet returnedTweet = twitterDao.deleteById(1377813262486634503);

    System.out.println(returnedTweet.getTweeterMessage());
  }
}
