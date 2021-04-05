package ca.jrvs.apps.twitter.spring;

import ca.jrvs.apps.twitter.TwitterCLIApp;
import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class TwitterCLIBean {

  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(TwitterCLIBean.class);
    TwitterCLIApp app = context.getBean(TwitterCLIApp.class);
    app.run(args);
  }

  @Bean
  public TwitterCLIApp twitterCLIApp(TwitterController controller) {
    return new TwitterCLIApp(controller);
  }

  @Bean
  public TwitterController controller(TwitterService service) {
    return new TwitterController(service);
  }

  @Bean
  public TwitterService service(TwitterDao twitterDao)  {
    return new TwitterService(twitterDao);
  }

  @Bean
  public TwitterDao twitterDao(TwitterHttpHelper twitterHttpHelper){
    return new TwitterDao(twitterHttpHelper);
  }

  @Bean
  TwitterHttpHelper twitterHttpHelper() {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    return new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
  }
}
