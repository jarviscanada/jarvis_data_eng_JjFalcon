package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TwitterCLIApp {

  private static final String CONSUMER_KEY = System.getenv("consumerKey");
  private static final String CONSUMER_SECRET = System.getenv("consumerSecret");
  private static final String ACCESS_TOKEN = System.getenv("accessToken");
  private static final String TOKEN_SECRET = System.getenv("tokenSecret");

  private static final String USAGE = "USAGE:  TwitterCLIApp post|show|delete [options]";
  private Controller controller;
  private Logger logger = LoggerFactory.getLogger(TwitterDao.class);

  public TwitterCLIApp(TwitterController controller) {
    this.controller = controller;
  }

  public void printTweet(Tweet tweet) {
    try {
      System.out.println(JsonParser.toJason(tweet, true, true));
    } catch (JsonProcessingException e) {
      logger.error(e.getMessage());
      throw new RuntimeException(e.getMessage());
    }
  }

  public void run(String[] args) {
    if (args.length < 2) {
      throw new IllegalArgumentException(USAGE);
    }
    switch (args[0].toLowerCase()) {
      case "post":
        printTweet(controller.postTweet(args));
        break;
      case "show":
        printTweet(controller.showTweet(args));
        break;
      case "delete":
        controller.deleteTweet(args).forEach(this::printTweet);
        break;
      default:
        throw new IllegalArgumentException(USAGE);
    }
  }

  public static void main(String[] args) {
    TwitterHttpHelper twitterHttpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET,
        ACCESS_TOKEN, TOKEN_SECRET);
    TwitterDao twitterDao = new TwitterDao(twitterHttpHelper);
    TwitterService twitterService = new TwitterService(twitterDao);
    TwitterController twitterController = new TwitterController(twitterService);
    TwitterCLIApp twitterCLIApp = new TwitterCLIApp(twitterController);

    twitterCLIApp.run(args);
  }
}

