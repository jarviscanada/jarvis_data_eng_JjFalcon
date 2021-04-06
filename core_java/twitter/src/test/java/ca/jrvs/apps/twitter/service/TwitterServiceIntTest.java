package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonParser;
import ca.jrvs.apps.twitter.util.TweetBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterServiceIntTest {

  TwitterService twitterService;

  @Before
  public void setUp() throws Exception {
    // Adjusted since I forgot to do the test prior to the CLIApp implementation
    final String CONSUMER_KEY = System.getenv("consumerKey");
    final String CONSUMER_SECRET = System.getenv("consumerSecret");
    final String ACCESS_TOKEN = System.getenv("accessToken");
    final String TOKEN_SECRET = System.getenv("tokenSecret");

    TwitterHttpHelper twitterHttpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
    TwitterDao twitterDao = new TwitterDao(twitterHttpHelper);
    twitterService = new TwitterService(twitterDao);

  }

  @Test
  public void postTweet() throws JsonProcessingException {
    String tweeterMessage = "It's a marvelous day #agent009 @flyehye";
    double lat = 43.595310;
    double lon = -79.640579;
    Tweet newTweet = TweetBuilder.buildTweet(tweeterMessage, lat, lon);

    Tweet returnedTweet = twitterService.postTweet(newTweet);
    System.out.println(JsonParser.toJason(returnedTweet, true, true));

    assertEquals("flyehye", returnedTweet.getEntity().getUserMentions()[0].getScreenName());
  }

  @Test
  public void showTweet() throws JsonProcessingException {
    String id = "1378020791854448640";
    Tweet returnedTweet = twitterService.showTweet(id, null);
    System.out.println(JsonParser.toJason(returnedTweet, true, true));

    assertEquals(id, returnedTweet.getIdString());
    assertEquals("flyehye", returnedTweet.getEntity().getUserMentions()[0].getScreenName());
  }

  @Test
  public void deleteTweets() throws JsonProcessingException {
    String[] ids = {"1378020791854448640"};
    List<Tweet> returnedTweets = twitterService.deleteTweets(ids);
    //System.out.println(returnedTweets.forEach(JsonParser::toJason(this, true, true));
  }
}