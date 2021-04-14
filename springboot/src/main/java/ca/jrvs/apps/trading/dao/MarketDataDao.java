package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.util.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;

// String = ID
public class MarketDataDao implements CrudRepository<IexQuote, String> {

  private static final String IEX_BATCH_PATH = "stock/market/quote/?symbols=%s&types=quote&token=";
  private final String IEX_BATCH_URL;
  private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
  private HttpClientConnectionManager httpClientConnectionManager;

  public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager, MarketDataConfig marketDataConfig) {
    this.httpClientConnectionManager = httpClientConnectionManager;
    IEX_BATCH_URL = marketDataConfig.getHost() + IEX_BATCH_PATH + marketDataConfig.getToken();
  }

  @Override
  public <S extends IexQuote> S save(S s) {
    return null;
  }

  @Override
  public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  /**
   * Gets an IexQuote (with helper method finaAllById)
   * @param ticker
   * @throws IllegalArgumentException if a give ticker is invalid
   * @throws DataRetrievalFailureException if HTTP request failed
   * @return
   */
  @Override
  public Optional<IexQuote> findById(String ticker) {
    Optional<IexQuote> iexQuote;
    List<IexQuote> quotes = findAllById(Collections.singletonList(ticker));

    if (quotes.size() == 0) {
      return Optional.empty();
    } else if (quotes.size() == 1) {
      iexQuote = Optional.of(quotes.get(0));
    } else {
      throw new DataRetrievalFailureException("Unexpected number of quotes");
    }
    return iexQuote;
  }

  /**
   * Gets quotes from Iex.
   *
   * @param tickers is a list of tickers
   * @return a list of IexQuote objects
   * @throws IllegalArgumentException if any ticker is invalid or tickers is empty
   *
   */
  @Override
  public List<IexQuote> findAllById(Iterable<String> tickers) {
    List<IexQuote> iexQuotes = new ArrayList<>();
    // loop through the tickers and add valid ones

    String response = executeHttpGet(IEX_BATCH_URL).orElseThrow(() -> new IllegalArgumentException("Invalid ticker "));
    //Array of JSON documents
    JSONObject IexQuotesJson = new JSONObject(response);
    //Get number of documents
    if (IexQuotesJson.length() == 0) {
      throw new IllegalArgumentException("Invalid ticker");
    }
    return iexQuotes;
  }

  private <T> Optional<T> parseResponse(HttpResponse response, Class<T> clazz) {
    if (response != null){
      int statusCode = response.getStatusLine().getStatusCode();
      // successful http status code
      if (statusCode >= 200 && statusCode < 300) {
        try {
          return Optional.of(JsonParser.toObjectFromJson(EntityUtils.toString(response.getEntity()), clazz));
        } catch (IOException e) {
          throw new RuntimeException("Unable to convert JSON string to a Tweet object");
        }
      }
    }
    return Optional.empty();
  }

  /**
   * Executes a get method and return http entity/body as as string
   *
   * Use EntityUtils.toString() to process HTTP entity
   *
   * @param url resource URL
   * @return http response body for successful status codes 200 - 299
   * @throws DataRetrievalFailureException if HTTP failed
   */
  private Optional<String> executeHttpGet(String url) {
    try {
      HttpResponse response = getHttpClient().execute(new HttpGet(url));
      // parse here **************
      return Optional.ofNullable(EntityUtils.toString(response.getEntity()));
    } catch (IOException e) {
      throw new DataRetrievalFailureException(e.getMessage());
    }
  }

  CloseableHttpClient getHttpClient() {
    return HttpClients.custom().setConnectionManager(httpClientConnectionManager)
        .setConnectionManagerShared(true).build();
  }

  @Override
  public boolean existsById(String s) {
    return false;
  }

  @Override
  public Iterable<IexQuote> findAll() {

    return null;
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void deleteById(String s) {

  }

  @Override
  public void delete(IexQuote iexQuote) {

  }

  @Override
  public void deleteAll(Iterable<? extends IexQuote> iterable) {

  }

  @Override
  public void deleteAll() {

  }
}
