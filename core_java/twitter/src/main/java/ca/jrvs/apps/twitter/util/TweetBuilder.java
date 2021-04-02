package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;

public class TweetBuilder {
  // TODO: Separate the @mentions and #hashtags

  public static Tweet buildTweet(String tweeterMessage, Double latitude, Double longitude){
    Tweet newTweet = new Tweet();
    Double[] coordinates = {latitude, longitude};
    Coordinates location = new Coordinates();
    String type = "Degrees.Minutes";
    location.setCoordinates(coordinates);
    location.setType(type);

    newTweet.setTweeterMessage(tweeterMessage);
    newTweet.setLocation(location);

    return newTweet;

  }
}
