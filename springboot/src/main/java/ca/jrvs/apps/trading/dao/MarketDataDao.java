package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.util.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {

  // https://cloud.iexapis.com/v1/stock/market/batch?symbols=aapl,msft&types=quote&token={YOUR_TOKEN}
  private static final String IEX_BATCH_PATH = "stock/market/batch?symbols=";
  private final String IEX_BATCH_URL;
  private Logger logger = LoggerFactory.getLogger(MarketDataDaoTemp.class);
  private HttpClientConnectionManager httpClientConnectionManager;
  private String token;

  @Autowired
  public MarketDataDao(
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
   * @return IexQuote if it exists
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
   */
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

    // HTTP response
    String response =
        executeHttpGet(tempUrl).orElseThrow(() -> new IllegalArgumentException("Invalid ticker"));

    // Array of JSON documents
    JSONObject IexQuotesJson = new JSONObject(response);

    // Get number of documents
    if (IexQuotesJson.length() == 0) {
      throw new IllegalArgumentException("Invalid ticker");
    }

    return parseResponseBody(IexQuotesJson);
  }

  private List<IexQuote> parseResponseBody(JSONObject jsonObject) {
    // iterate and convert/parse each quote to a IexQuote using org.json
    List<IexQuote> quotes = new ArrayList<>();
    Iterator<String> keys = jsonObject.keys();

    do {
      // https://devqa.io/how-to-parse-json-in-java/
      String newJson = jsonObject.get(keys.next()).toString();
      // System.out.println("********** TEST **********");
      // System.out.println(newJson);
      try {
        IexQuote quote = JsonParser.toObjectFromJson(newJson, IexQuote.class);
        quotes.add(quote);
      } catch (IOException e) {
        throw new DataRetrievalFailureException("Couldn't convert JSON to object");
      }
    } while (keys.hasNext());

    return quotes;
  }

  /**
   * Executes a get method and return http entity/body as as string
   *
   * <p>Use EntityUtils.toString() to process HTTP entity
   *
   * @param url resource URL
   * @return http response body for successful status codes 200 - 299
   * @throws DataRetrievalFailureException if HTTP failed
   */
  private Optional<String> executeHttpGet(String url) {
    try {
      HttpResponse response = getHttpClient().execute(new HttpGet(url));

      if (response != null) {
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode >= 200 && statusCode < 300) {
          // System.out.println("********** TEST **********");
          // System.out.println(EntityUtils.toString(response.getEntity()));
          return Optional.ofNullable(EntityUtils.toString(response.getEntity()));
        }
      }
    } catch (IOException e) {
      throw new DataRetrievalFailureException("Unable to retrieve data " + '\n' + e);
    }
    return Optional.empty();
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
