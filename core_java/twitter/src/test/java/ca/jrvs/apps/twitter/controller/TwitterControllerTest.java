package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.JsonParser;
import ca.jrvs.apps.twitter.util.TweetBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterControllerTest {

  TwitterController twitterController;

  @Before
  public void setUp() throws Exception {
    // Adjusted since I forgot to do the test prior to the CLIApp implementation
    final String CONSUMER_KEY = System.getenv("consumerKey");
    final String CONSUMER_SECRET = System.getenv("consumerSecret");
    final String ACCESS_TOKEN = System.getenv("accessToken");
    final String TOKEN_SECRET = System.getenv("tokenSecret");

    TwitterHttpHelper twitterHttpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
    TwitterDao twitterDao = new TwitterDao(twitterHttpHelper);
    TwitterService twitterService = new TwitterService(twitterDao);
    twitterController = new TwitterController(twitterService);
  }

  @Test
  public void postTweet() throws JsonProcessingException {
    String tweeterMessage = "It's a marvelous day #agent700 @flyehye";
    String[] args = {"post", tweeterMessage, "43.595310:-79.640579"};
    Tweet returnedTweet = twitterController.postTweet(args);
    System.out.println(JsonParser.toJason(returnedTweet, true, true));

    assertEquals("flyehye", returnedTweet.getEntity().getUserMentions()[0].getScreenName());
  }

  @Test
  public void showTweet() throws JsonProcessingException {
    String id = "1378020791854448640";
    String[] args = {"show", id};
    Tweet returnedTweet = twitterController.showTweet(args);
    System.out.println(JsonParser.toJason(returnedTweet, true, true));

    assertEquals(id, returnedTweet.getIdString());
    assertEquals("flyehye", returnedTweet.getEntity().getUserMentions()[0].getScreenName());
  }

  @Test
  public void deleteTweet() {
    String[] args = {"delete", "5096020791854448326", "2387020791854448640" };
    List<Tweet> returnedTweet = twitterController.deleteTweet(args);
    //System.out.println(returnedTweets.forEach(JsonParser::toJason(this, true, true));
  }
}