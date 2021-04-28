package ca.jrvs.apps.trading.service;

import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.trading.config.TestConfig;
import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteServiceTest {

  @Autowired
  private QuoteService quoteService;
  @Autowired
  private QuoteDao quoteDao;
  @Autowired
  private MarketDataDao marketDataDao;

  List<String> tickers;
  Quote aapl;
  Quote msft;
  Quote amd;

  @Before
  public void setUp() {
    //quoteDao.deleteAll();
    tickers = Arrays.asList("aapl", "msft", "amd");
    quoteService = new QuoteService(quoteDao, marketDataDao);
    aapl = new Quote();
    msft = new Quote();
    amd = new Quote();

    aapl.setID("aapl");
    aapl.setTicker("aapl");
    aapl.setLastPrice(100d);
    aapl.setAskPrice(110d);
    aapl.setAskSize(10);
    aapl.setBidPrice(50d);
    aapl.setBidSize(10);

    msft.setID("msft");
    msft.setTicker("msft");
    msft.setLastPrice(200d);
    msft.setAskPrice(210d);
    msft.setAskSize(20);
    msft.setBidPrice(100d);
    msft.setBidSize(20);

    amd.setID("amd");
    amd.setTicker("amd");
    amd.setLastPrice(300d);
    amd.setAskPrice(310d);
    amd.setAskSize(30);
    amd.setBidPrice(150d);
    amd.setBidSize(30);
  }

  @Test
  public void findIexQuoteByTicker() {
    saveQuotesByTickers();
    IexQuote foundIexQuote = quoteService.findIexQuoteByTicker("aapl");
    assertEquals("AAPL", foundIexQuote.getSymbol());
  }

  @Test
  public void updateMarketData() {
    saveQuotesByTickers();
    quoteService.updateMarketData();
  }

  @Test
  public void saveQuotesByTickers() {
    List<Quote> quotes = quoteService.saveQuotes(tickers);
    assertEquals(3, quotes.size());
  }

  @Test
  public void saveQuoteByQuote() {
    Quote savedQuote = quoteService.saveQuote("aapl");
    assertEquals("AAPL", savedQuote.getTicker());
  }

  @Test
  public void findAllQuotes() {
    Iterable<Quote> quotes = quoteService.findAllQuotes();
    quotes.forEach(Assert::assertNotNull);
  }
}