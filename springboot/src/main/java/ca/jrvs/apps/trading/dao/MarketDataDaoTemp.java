package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.util.JsonParser;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;

// Old code based on Twitter and not using JSONObject
public class MarketDataDaoTemp implements CrudRepository<IexQuote, String> {

  // https://cloud.iexapis.com/v1/stock/market/batch?symbols=aapl,msft&types=quote&token={YOUR_TOKEN}
  private static final String IEX_BATCH_PATH = "stock/market/batch?symbols=";
  private final String IEX_BATCH_URL;
  private Logger logger = LoggerFactory.getLogger(MarketDataDaoTemp.class);
  private HttpClientConnectionManager httpClientConnectionManager;
  private String token;

  public MarketDataDaoTemp(
      HttpClientConnectionManager httpClientConnectionManager, MarketDataConfig marketDataConfig) {
    this.httpClientConnectionManager = httpClientConnectionManager;
    IEX_BATCH_URL = marketDataConfig.getHost() + IEX_BATCH_PATH;
    token = marketDataConfig.getToken();
  }

  /**
   * Gets an IexQuote (with helper method finaAllById)
   *
   * @param ticker to get quote of
   * @throws IllegalArgumentException if a give ticker is invalid
   * @throws DataRetrievalFailureException if HTTP request failed
   * @return the quote of the given ticker
   */
  @Override
  public Optional<IexQuote> findById(String ticker) {
    // https://cloud.iexapis.com/v1/stock/market/batch?symbols=aapl&types=quote&token={YOUR_TOKEN}
    Optional<IexQuote> iexQuote;
    List<IexQuote> quotes = (List<IexQuote>) findAllById(Collections.singletonList(ticker));

    if (quotes.size() == 0) {
      return Optional.empty();
    } else if (quotes.size() == 1) {
      iexQuote = Optional.of(quotes.get(0));
    } else {
      throw new DataRetrievalFailureException("Unexpected number of quotes");
    }
    return iexQuote;
  }

  @Override
  public List<IexQuote> findAllById(Iterable<String> tickers) {
    String tempUrl = IEX_BATCH_URL;

    // separate each tickers with a comma
    // https://cloud.iexapis.com/v1/stock/market/batch?symbols=aapl,msft&types=quote&token={YOUR_TOKEN})
    StringJoiner joiner = new StringJoiner(",");

    for (String ticker : tickers) {
      joiner.add(ticker);
    }

    tempUrl += joiner.toString() + "&types=quote" + "&token=" + token;
    HttpResponse response = executeHttpGet(tempUrl);

    return Arrays.asList(
        parseResponseBody(response, IexQuote[].class)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Symbols were found on IEX")));
  }

  private <T> Optional<T> parseResponseBody(HttpResponse response, Class<T> clazz) {
    // move status code check to executeHttp
    try {
      System.out.println("********* PROBLEM **********");
      System.out.println(EntityUtils.toString(response.getEntity()));
      return Optional.of(
          JsonParser.toObjectFromJson(EntityUtils.toString(response.getEntity()), clazz));
    } catch (IOException e) {
      throw new RuntimeException("Unable to convert JSON string to a JSON object" + '\n' + e);
    }
  }

  private HttpResponse executeHttpGet(String url) {
    try {
      HttpResponse response = getHttpClient().execute(new HttpGet(url));

      if (response != null) {
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode >= 200 && statusCode < 300) {
          // System.out.println("********** TEST **********");
          // System.out.println(EntityUtils.toString(response.getEntity()));
          return response;
        }
      }
    } catch (IOException e) {
      throw new DataRetrievalFailureException("Unable to retrieve data " + '\n' + e);
    }
    return null;
  }

  CloseableHttpClient getHttpClient() {
    return HttpClients.custom()
        .setConnectionManager(httpClientConnectionManager)
        .setConnectionManagerShared(true)
        .build();
  }

  @Override
  public <S extends IexQuote> S save(S s) {
    return null;
  }

  @Override
  public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
    return null;
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
  public void deleteById(String s) {}

  @Override
  public void delete(IexQuote iexQuote) {}

  @Override
  public void deleteAll(Iterable<? extends IexQuote> iterable) {}

  @Override
  public void deleteAll() {}
}
