package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Entities;
import ca.jrvs.apps.twitter.model.HashTag;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.EntityBuilder;
import ca.jrvs.apps.twitter.util.JsonParser;
import ca.jrvs.apps.twitter.util.TweetBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;

public class TwitterDaoTest {

  private TwitterDao twitterDao;

  @Before
  public void setUp() throws Exception {
    final String CONSUMER_KEY = System.getenv("consumerKey");
    final String CONSUMER_SECRET = System.getenv("consumerSecret");
    final String ACCESS_TOKEN = System.getenv("accessToken");
    final String TOKEN_SECRET = System.getenv("tokenSecret");

    TwitterHttpHelper twitterHttpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
    twitterDao = new TwitterDao(twitterHttpHelper);
  }

  @Test
  public void create() throws Exception {
    String tweeterMessage = "It's a marvelous day #agent007 @flyehye";
    double lat = 43.595310;
    double lon = -79.640579;
    Tweet newTweet = TweetBuilder.buildTweet(tweeterMessage, lat, lon);

    /* TODO: How to add tweeter mentions and hashtags from the message
    String hashTagText = "hashtag01";
    int[] hashTagIndex = {1,2};
    HashTag hashTag = new HashTag();
    hashTag.setHashTagText(hashTagText);
    hashTag.setIndexArray(hashTagIndex);
    HashTag[] hashTags = {hashTag};
    Entities entity = EntityBuilder.buildEntity(hashTags, null);
    newTweet.setEntity(entity);
    */

    // test if the object is properly created
    System.out.println(JsonParser.toJason(newTweet, true, true));

    assertEquals(tweeterMessage, newTweet.getTweeterMessage());
    assertNotNull(newTweet.getLocation());
    assertEquals(lat, newTweet.getLocation().getLatitude(),0);
    assertEquals(lon, newTweet.getLocation().getLongitude(),0);

  }

  @Test
  public void findById() throws Exception {
    // ACTUAL TWEET:  This is a sample tweet #flye #hye @flyehye!
    String id = "1378020791854448640";
    Tweet returnedTweet = twitterDao.findById(id);
    System.out.println(JsonParser.toJason(returnedTweet, true, true));

    assertEquals(id, returnedTweet.getIdString());
    assertEquals("flyehye", returnedTweet.getEntity().getUserMentions()[0].getScreenName());
  }

  // deletebyID is the same as findById
}