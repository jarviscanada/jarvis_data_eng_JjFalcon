package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.TweetBuilder;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller {

  private static final String COORD_SEP = ":";
  private static final String COMMA = ",";
  private TwitterService twitterService;

  @Autowired
  public TwitterController(TwitterService twitterService) {
    this.twitterService = twitterService;
  }

  @Override
  public Tweet postTweet(String[] args) {
    System.out.println("USAGE: TwitterCLIApp post \"tweeterMessage\" \"latitude:longitude\"");
    if (args.length != 3){
      throw new IllegalArgumentException("Missing Argument");
    }

    String tweeterMessage = args[1];
    String locationCoordinates = args[2];
    String[] coordinates = locationCoordinates.split(COORD_SEP);

    Double latitude = null;
    Double longitude = null;
    try {
      latitude = Double.parseDouble(coordinates[0]);
      longitude = Double.parseDouble(coordinates[1]);
    } catch (IllegalArgumentException e){
      throw new IllegalArgumentException("Invalid Coordinate Formats");
    }

    Tweet postTweet = TweetBuilder.buildTweet(tweeterMessage, latitude, longitude);
    return twitterService.postTweet(postTweet);
  }

  @Override
  public Tweet showTweet(String[] args) {
    System.out.println("USAGE: TwitterCLIApp show \"tweetID\"");
    if (args.length != 2){
      throw new IllegalArgumentException("Missing Argument");
    }

    String showID = args[1];
    return twitterService.showTweet(showID, null);
  }

  @Override
  public List<Tweet> deleteTweet(String[] args) {
    System.out.println("USAGE: TwitterCLIApp delete \"[id1,id2,..]\" ");
    int argumentLength = args.length;
    if (argumentLength != 2){
      throw new IllegalArgumentException("Missing Argument");
    }
    String ids = args[1];
    String[] deleteIDs = ids.split(COMMA);

    return twitterService.deleteTweets(deleteIDs);
  }
}
