package ca.jrvs.apps.twitter.dao.helper;

import java.net.URI;
import org.apache.http.HttpResponse;

public interface HttpHelper {

  /**
   * Execute a HTTP Post call
   * @param uri Twitter Resource
   * @return response
   */
  HttpResponse httpPost(URI uri);

  /**
   * Execute a HTTP Get call
   * @param uri Twitter Resource
   * @return response
   */
  HttpResponse httpGet(URI uri);
}
