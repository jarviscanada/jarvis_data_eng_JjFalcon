package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetBuilder;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

  @Mock
  TwitterDao twitterDao;

  @InjectMocks
  TwitterService twitterService;

  @Test
  public void postTweet() {
    when(twitterDao.create(any()))
        .thenReturn(TweetBuilder.buildTweet("Good Morning Agent 009", 43.595310, -79.640579));

    try {
      twitterService.postTweet(TweetBuilder.buildTweet("Bad Coordinates", 1000.0, 1000.0));
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    Tweet returnedTweet = twitterService
        .postTweet(TweetBuilder.buildTweet("Good Morning Agent 099", 43.595310, -79.640579));
    assertNotNull(returnedTweet);
    assertNotNull(returnedTweet.getTweeterMessage());
  }

  @Test
  public void showTweet() {
    String tweeterMessage = "Good Morning Agent 009";
    Double lat = 43.595310;
    Double lon = -79.640579;

    when(twitterDao.findById(any()))
        .thenReturn(TweetBuilder.buildTweet(tweeterMessage, lat, lon));

    try {
      twitterService.showTweet("1342013455BABY76879", null);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    Tweet returnedTweet = twitterService.showTweet("1379420134557687809", null);
    assertNotNull(returnedTweet);
    assertNotNull(returnedTweet.getTweeterMessage());
  }

  @Test
  public void deleteTweets() {
    String tweeterMessage = "Good Morning Agent 009";
    Double lat = 43.595310;
    Double lon = -79.640579;

    when(twitterDao.deleteById(any()))
        .thenReturn(TweetBuilder.buildTweet(tweeterMessage, lat, lon));

    String[] goodIDs = {"1378185319594524675", "1378187944083722244"};
    String[] badIDs = {"137818594BABY524675", "13781440GOAT83722244"};
    try {
      twitterService.deleteTweets(badIDs);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    List<Tweet> returnedTweets = twitterService.deleteTweets(goodIDs);
    assertNotNull(returnedTweets);
  }
}