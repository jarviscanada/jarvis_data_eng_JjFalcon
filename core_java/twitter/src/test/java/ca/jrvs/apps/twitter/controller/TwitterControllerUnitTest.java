package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.TweetBuilder;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {

  @Mock
  TwitterService twitterService;

  @InjectMocks
  TwitterController twitterController;

  @Test
  public void postTweet() {
    when(twitterService.postTweet(notNull()))
        .thenReturn(TweetBuilder.buildTweet("Good Morning Agent 009", 43.595310, -79.640579));

    try {
      twitterController.postTweet(new String[]{"post", "text only"});
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    try {
      twitterController.postTweet(new String[]{"post", "test", "101"});
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    try {
      twitterController.postTweet(new String[]{"post", "test", "18:32:12"});
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    try {
      twitterController.postTweet(new String[]{"post", "test", ":32"});
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    String tweeterMessage = "It's a marvelous day #agent444";
    String[] args = {"post", tweeterMessage, "43.595310:-79.640579"};
    Tweet returnedTweet = twitterController.postTweet(args);
    assertNotNull(returnedTweet);
    assertNotNull(returnedTweet.getTweeterMessage());
  }

  @Test
  public void showTweet() {
    when(twitterService.showTweet(notNull(), any()))
        .thenReturn(TweetBuilder.buildTweet("Good Morning Agent 006", 43.595310, -79.640579));

    try {
      twitterController.showTweet(new String[]{"show", "text only", "etc", "etc"});
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    try {
      twitterController.showTweet(new String[]{"show"});
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    String[] args = {"show", "1379420134557687809"};
    Tweet returnedTweet = twitterController.showTweet(args);
    assertNotNull(returnedTweet);
    assertNotNull(returnedTweet.getTweeterMessage());
  }

  @Test
  public void deleteTweet() {
    when(twitterService.postTweet(notNull()))
        .thenReturn(TweetBuilder.buildTweet("Good Morning Agent 001", 43.595310, -79.640579));

    try {
      twitterController.deleteTweet(new String[]{"delete", "1378185319594524675", "1378185319594524675"});
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    String[] args = {"delete", "1379433783204610052" + "," + "1378061563484123139"};
    List<Tweet> returnedTweets = twitterController.deleteTweet(args);
    assertNotNull(returnedTweets);
  }
}