package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class QuoteService {

  private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

  private QuoteDao quoteDao;
  private MarketDataDao marketDataDao;

  @Autowired
  public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
    this.quoteDao = quoteDao;
    this.marketDataDao = marketDataDao;
  }

  public IexQuote findIexQuoteByTicker(String ticker) {
    return marketDataDao
        .findById(ticker)
        .orElseThrow(() -> new IllegalArgumentException(ticker + " is invalid"));
  }

  /**
   * Update quote table against IEX source
   * - get all quotes from the db
   * - foreach ticker get iexQuote
   * - convert iexQuote to quote entity
   * - persist quote to db
   *
   */
  public List<Quote> updateMarketData(){
    Iterable<Quote> dbQuotes = quoteDao.findAll();
    // get all quotes
    List<String> tickers = new ArrayList<>();
    for (Quote quote: dbQuotes) {
      tickers.add(quote.getTicker());
    }

    Iterable<IexQuote> iexQuotes = marketDataDao.findAllById(tickers);
    // update/save dbQuotes with the iexQuote values
    List<Quote> newQuotes = new ArrayList<>();
    for (IexQuote iexQuote: iexQuotes){
      Quote quote = buildQuoteFromIexQuote(iexQuote);
      newQuotes.add(quote);
    }
    return newQuotes;
  }

  /**
   * Helper method.  Map a IexQuote to a Quote entity.
   * Note:  iexQuote.getLastprice() == null if the stock market is closed.
   * Make sure set a default value for number fields(s).
   *
   * @param iexQuote
   * @return
   */
  protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote){
    Quote quote = new Quote();
    quote.setTicker(iexQuote.getSymbol());
    quote.setBidPrice(iexQuote.getIexBidPrice());
    quote.setBidSize(iexQuote.getIexBidSize());
    quote.setAskPrice(iexQuote.getIexAskPrice());
    quote.setAskSize(iexQuote.getIexAskSize());

    if (iexQuote.isIsUSMarketOpen()) {
      quote.setLastPrice(iexQuote.getLatestPrice());
    } else {
      quote.setLastPrice(null);
    }
    return quote;
  }

  /**
   * Validate (against IEX) and save given tickers to quote table.
   *
   * - Get iexQuote(s)
   * - convert each iexQuote to Quote entity
   * - persist the quote to db
   *
   * @param tickers a list of tickers/symbols
   * @throws IllegalArgumentException if ticker is not found from IEX
   * @return
   */
  public List<Quote> saveQuotes(List<String> tickers){
    List<Quote> newQuotes = new ArrayList<>();
    for (String ticker : tickers ) {
      newQuotes.add(saveQuote(ticker));
    }
    return newQuotes;
  }

  /**
   * Helper method
   * @param ticker to get the quote to save
   * @return saved Quote
   */
  public Quote saveQuote(String ticker){
    IexQuote iexQuote = marketDataDao.findById(ticker).orElseThrow(() ->
        new IllegalArgumentException("Unable to retrieve quote for:  " + ticker));
    return quoteDao.save(buildQuoteFromIexQuote(iexQuote));
  }

  public Quote saveQuote(Quote quote){
    return quoteDao.save(quote);
  }

  public List<Quote> findAllQuotes() {
    return quoteDao.findAll();
  }
}
