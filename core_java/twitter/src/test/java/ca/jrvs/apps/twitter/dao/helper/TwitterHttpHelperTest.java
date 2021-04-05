package ca.jrvs.apps.twitter.dao.helper;

import static org.junit.Assert.*;

import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TwitterHttpHelperTest {

  private static final String CONSUMER_KEY = System.getenv("consumerKey");
  private static final String CONSUMER_SECRET = System.getenv("consumerSecret");
  private static final String ACCESS_TOKEN = System.getenv("accessToken");
  private static final String TOKEN_SECRET = System.getenv("tokenSecret");

  @Test
  public void httpPost() throws URISyntaxException, IOException {
    // set-up authorization keys to allow this application to access Twitter API resources
    OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
    consumer.setTokenWithSecret(ACCESS_TOKEN, TOKEN_SECRET);

    String tweeterMessage = "What a fabulous day!";

    // PercentEscaper is a UnicodeEscaper that escapes some set of Java characters using a UTF-8 based percent encoding scheme.
    PercentEscaper percentEscaper = new PercentEscaper("", false);

    TwitterHttpHelper twitterHttpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
    HttpResponse response = twitterHttpHelper.httpPost(new URI("https://api.twitter.com/1.1/statuses/update.json?status="
        + percentEscaper.escape(tweeterMessage)));
    System.out.println(EntityUtils.toString(response.getEntity()));

  }
}