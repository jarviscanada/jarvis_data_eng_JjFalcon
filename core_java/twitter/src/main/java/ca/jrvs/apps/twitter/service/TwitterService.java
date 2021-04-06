package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class TwitterService implements Service {

  private TwitterDao twitterDao;

  /* MOVE ALL TO MAIN and replace with a constructor to implement spring!
  private static final String CONSUMER_KEY = System.getenv("consumerKey");
  private static final String CONSUMER_SECRET = System.getenv("consumerSecret");
  private static final String ACCESS_TOKEN = System.getenv("accessToken");
  private static final String TOKEN_SECRET = System.getenv("tokenSecret");

  private TwitterHttpHelper twitterHttpHelper;
  private TwitterDao twitterDao;

  private void setUp(){
    TwitterHttpHelper twitterHttpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
    twitterDao = new TwitterDao(twitterHttpHelper);
  }
   */

  @Autowired
  public TwitterService(TwitterDao twitterDao) {
    this.twitterDao = twitterDao;
  }


  private void validateTweet(Tweet tweet){
    final int TWITTER_LIMIT = 280;
    final Double LATITUDE_MAX = 90.0000;
    final Double LONGITUDE_MAX = 180.0000;
    final Double[] NORTH_POLE = {90.0000, 135.0000};

    String tweeterMessage = tweet.getTweeterMessage();
    Double latitude = tweet.getLocation().getLatitude();
    Double longitude = tweet.getLocation().getLongitude();
    if (tweeterMessage.length() > TWITTER_LIMIT){
      String tempMessage = tweeterMessage.substring(0,TWITTER_LIMIT-1);
      tweet.setTweeterMessage(tempMessage);
    }
    // joke
    if (tweet.getLocation().getCoordinates() != null) {
      if (Math.abs(latitude) > LATITUDE_MAX || Math.abs(longitude) > LONGITUDE_MAX) {
        // sets location as if Santa sent it :)
        Coordinates tempCoordinate = new Coordinates();
        String tempType = tweet.getLocation().getType();
        tempCoordinate.setCoordinates(NORTH_POLE);
        tempCoordinate.setType(tempType);
        tweet.setLocation(tempCoordinate);
      }
    }
  }

  private boolean isValidID(String id){
    if (!id.matches("[0-9]+")) {
      throw new IllegalArgumentException("ID:" + id + "must only contain numbers");
    }
    return true;
  }

  @Override
  public Tweet postTweet(Tweet tweet) {
    //setUp();
    validateTweet(tweet);
    return twitterDao.create(tweet);
  }

  @Override
  public Tweet showTweet(String id, String[] fields) {
    //setUp();
    if (isValidID(id)) {
      return twitterDao.findById(id);
    } else {
      throw new IllegalArgumentException("Not a valid ID");
    }
    // TODO:  what to do with fields.  what are they?
  }

  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    //setUp();
    List<Tweet> deletedTweets = new ArrayList<>();
    for (String id: ids){
      if (isValidID(id)) {
        deletedTweets.add(twitterDao.deleteById(id));
      } else {
        throw new IllegalArgumentException("Not a valid ID");
      }
    }
    return deletedTweets;
  }
}
