package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterControllerIntTest {

  TwitterController twitterController;

  @Before
  public void setUp() {
    // Adjusted since I forgot to do the test prior to the CLIApp implementation
    final String CONSUMER_KEY = System.getenv("consumerKey");
    final String CONSUMER_SECRET = System.getenv("consumerSecret");
    final String ACCESS_TOKEN = System.getenv("accessToken");
    final String TOKEN_SECRET = System.getenv("tokenSecret");

    TwitterHttpHelper twitterHttpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET,
        ACCESS_TOKEN, TOKEN_SECRET);
    TwitterDao twitterDao = new TwitterDao(twitterHttpHelper);
    TwitterService twitterService = new TwitterService(twitterDao);
    twitterController = new TwitterController(twitterService);
  }

  @Test
  public void postTweet() throws JsonProcessingException {
    String tweeterMessage = "It's a marvelous day #agent123";
    String[] args = {"post", tweeterMessage, "43.595310:-79.640579"};
    Tweet returnedTweet = twitterController.postTweet(args);
    System.out.println(JsonParser.toJason(returnedTweet, true, true));

    assertEquals("agent123", returnedTweet.getEntity().getHashTags()[0].getHashTagText());
  }

  @Test
  public void showTweet() throws JsonProcessingException {
    String id = "1379420134557687809";
    String[] args = {"show", id};
    Tweet returnedTweet = twitterController.showTweet(args);
    System.out.println(JsonParser.toJason(returnedTweet, true, true));

    assertEquals(id, returnedTweet.getIdString());
  }

  //String[] args = {"delete", "1379434970268135430" + "," + "1379434934230605835" };
  @Test
  public void deleteTweet() {
    String[] args = {"delete", "1379433783204610052" + "," + "1378061563484123139"};
    List<Tweet> returnedTweets = twitterController.deleteTweet(args);
    //System.out.println(returnedTweets.forEach(JsonParser::toJason(this, true, true));
    assertNotNull(returnedTweets);
  }
}