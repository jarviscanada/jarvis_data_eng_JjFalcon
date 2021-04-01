package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.HashTag;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.model.UserMention;
import ca.jrvs.apps.twitter.util.JsonParser;
import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TwitterDao implements CrdDao<Tweet, String> {

  // TWITTER resource urls
  private static final String TWITTER_BASE_URI = "https://api.twitter.com/1.1/";
  private static final String TWITTER_POST_URI = TWITTER_BASE_URI + "statuses/update.json";
  private static final String TWITTER_SHOW_URI = TWITTER_BASE_URI + "statuses/show.json";
  private static final String TWITTER_DELETE_URI = TWITTER_BASE_URI + "statuses/destroy/";

  // TWITTER resource symbols
  private static final String QUERY_SYM = "?";
  private static final String AMPERSAND = "&";
  private static final String EQUAL = "=";

  // Response code
  private static final int HTTP_OK = 200;

  private HttpHelper httpHelper;
  private Logger logger = LoggerFactory.getLogger(TwitterDao.class);

  @Autowired
  public TwitterDao(HttpHelper httpHelper) {
    this.httpHelper = httpHelper;
  }

  private URI getTweetUri(Tweet tweet) throws URISyntaxException {
    String tempUriInput = "";
    String tweeterMessage = tweet.getTweeterMessage();
    UserMention[] tweeterMentions = tweet.getEntity().getUserMentions();
    HashTag[] tweeterHashtags = tweet.getEntity().getHashTags();
    Coordinates tweeterLocation = tweet.getLocation();

    PercentEscaper percentEscaper = new PercentEscaper("", false);

    // TODO: How to add tweeter mentions and hashtags to the message
    try {
      // add tweet message
      // URLEncoder class contains static methods for converting a String to the <CODE>application/x-www-form-urlencoded</CODE> MIME format.
      // POST https://api.twitter.com/1.1/statuses/update.json?status=MESSAGE
      tempUriInput =
          QUERY_SYM + "status" + EQUAL + URLEncoder.encode(tweeterMessage, StandardCharsets.UTF_8.name());
      // add tweet location
      if (tweeterLocation != null) {
        float longitude = tweet.getLocation().getCoordinates()[0];
        float latitude = tweet.getLocation().getCoordinates()[1];
        tempUriInput += AMPERSAND + "long" + URLEncoder
            .encode(Float.toString(longitude), StandardCharsets.UTF_8.name());
        tempUriInput += AMPERSAND + "lat" + URLEncoder
            .encode(Float.toString(latitude), StandardCharsets.UTF_8.name());
      }
    } catch (UnsupportedEncodingException e) {
      logger.error("Encoding not supported: " + StandardCharsets.UTF_8.name());
      throw new RuntimeException(e.getMessage());
    }
    return new URI(TWITTER_POST_URI + percentEscaper.escape(tempUriInput));
  }

  private Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode) {
    Tweet tweet = null;

    // checks response status
    int status = response.getStatusLine().getStatusCode();
    if (status != expectedStatusCode) {
      try {
        System.out.println(EntityUtils.toString(response.getEntity()));
      } catch (IOException e) {
        logger.error("Response has no entity");
      }
      throw new RuntimeException("Unexpected HTTP status: " + status);
    }
    if (response.getEntity() == null) {
      throw new RuntimeException("Empty response body");
    }

    // converts response entity to string
    String responseString;
    try {
      responseString = EntityUtils.toString(response.getEntity());
    } catch (IOException e) {
      throw new RuntimeException("Failed to convert JSON to string");
    }

    // deserializes the string format of the response to a Tweet object
    try {
      tweet = JsonParser.toObjectFromJson(responseString, Tweet.class);
    } catch (IOException e) {
      throw new RuntimeException("Unable to convert JSON string to a Tweet object");
    }
    return tweet;
  }

  @Override
  public Tweet create(Tweet tweet) {
    // POST https://api.twitter.com/1.1/statuses/update.json?status=MESSAGE
    URI tweeterUri;
    try {
      tweeterUri = getTweetUri(tweet);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Invalid tweet input", e);
    }

    // executes the HTTP Post request via httpHelper
    HttpResponse response = httpHelper.httpPost(tweeterUri);

    // validates and deserialize response to a Tweet object
    return parseResponseBody(response, HTTP_OK);
  }

  @Override
  public Tweet findById(String id) {
    //GET https://api.twitter.com/1.1/statuses/show.json?id=210462857140252672
    URI tweeterUri;
    String tempUriInput = QUERY_SYM + "id" + EQUAL + id;
    try {
      tweeterUri = new URI(TWITTER_SHOW_URI + tempUriInput);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("String could not be parsed as a URI", e);
    }

    // executes the HTTP Post request via httpHelper
    HttpResponse response = httpHelper.httpGet(tweeterUri);

    // validates and deserialize response to a Tweet object
    return parseResponseBody(response, HTTP_OK);
  }

  @Override
  public Tweet deleteById(String id) {
    //POST https://api.twitter.com/1.1/statuses/destroy/240854986559455234.json
    URI tweeterUri;
    String tempUriInput = id + ".json";
    try {
      tweeterUri = new URI(TWITTER_DELETE_URI + tempUriInput);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("String could not be parsed as URI", e);
    }
    // executes the HTTP Post request via httpHelper
    HttpResponse response = httpHelper.httpPost(tweeterUri);

    // validates and deserialize response to a Tweet object
    return parseResponseBody(response, HTTP_OK);
  }
}
