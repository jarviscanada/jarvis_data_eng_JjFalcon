package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.config.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={TestConfig.class})
public class QuoteDaoTest {

  private QuoteDao quoteDao;

  private Quote savedQuote;

  @Before
  public void insertOne() {
    savedQuote.setAskPrice(10d);
    savedQuote.setAskSize(10);
    savedQuote.setBidPrice(10.2d);
    savedQuote.setBidSize(10);
    savedQuote.setID("aapl");
    savedQuote.setLastPrice(10.1d);
    quoteDao.save(savedQuote);
  }

  @After
  public void deleteOne() {
    quoteDao.deleteById(savedQuote.getId());
  }

  @Test
  public void save() {
    quoteDao.save(savedQuote);
    assertTrue(quoteDao.existsById("aapl"));
  }

  @Test
  public void saveAll() {
    Quote quote01 = new Quote();
    Quote quote02 = new Quote();
    quote01.setID("abcd");
    quote01.setBidPrice(10d);
    quote01.setLastPrice(12d);
    quote02.setID("efgh");
    quote02.setBidPrice(12d);
    quote02.setLastPrice(11d);
    List<Quote> quotes = Arrays.asList(quote01, quote02);
    quoteDao.saveAll(quotes);
    assertTrue(quoteDao.existsById("abcd"));
    assertTrue(quoteDao.existsById("efgh"));
  }

  @Test
  public void findAll() {
    int items = 0;
    saveAll();
    Iterable<Quote> quotes = quoteDao.findAll();
//    if (quotes instanceof Collection) {
//      items =  ((Collection<?>) quotes).size();
//    }
    for (Quote quote : quotes) {
      items ++;
    }
    assertEquals(2, items);
  }

  @Test
  public void findById() {
    Quote foundQuote = quoteDao.findById("aapl").orElseThrow(
        () -> new IllegalArgumentException("Symbol not found"));
    assertEquals("aapl", foundQuote.getId());
  }


  @Test
  public void count() {
    saveAll();
    long rows = quoteDao.count();
    assertEquals(2, rows);
  }

  @Test
  public void deleteById() {
    saveAll();
    quoteDao.deleteById("abcd");
    assertFalse(quoteDao.existsById("abcd"));
  }
}