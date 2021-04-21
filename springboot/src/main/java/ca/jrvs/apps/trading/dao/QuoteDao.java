package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteDao implements CrudRepository<Quote, String> {

  // https://www.programmersought.com/article/221665717/
  // JdbcTemplate vs NamedParamenterJdbcTemplate
  private JdbcTemplate jdbcTemplate;
  private NamedParameterJdbcTemplate namedTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  private static final String ID_COLUMN_NAME = "ticker";
  private static final String TABLE_NAME = "quote";
  private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);

  @Autowired
  public QuoteDao(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
    namedTemplate = new NamedParameterJdbcTemplate(dataSource);
    simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
  }

  /**
   * hint: http://bit.ly/2sDz8hg
   *
   * @param quote to save
   * @return quote that was added successfully
   */
  @Override
  public Quote save(Quote quote) {
    if (existsById(quote.getTicker())) {
      int updateRowNo = updateOne(quote);
      if (updateRowNo != 1) {
        logger.error("Unable to save the ticker to quote.");
        throw new DataRetrievalFailureException("Unable to save the ticker to quote.");
      }
    } else {
      addOne(quote);
    }
    return quote;
  }

  private int updateOne(Quote quote) {
    final String SQL_UPDATE =
        "Update quote SET last_price=?, bid_price=?, bid_size=?, ask_price=?, ask_size=? WHERE TICKER=?";
    return jdbcTemplate.update(SQL_UPDATE, makeUpdateValues(quote));
  }

  /**
   * helper method that makes sql update values objects
   *
   * @param quote to be updated
   * @return UPDATE_SQL values
   */
  private Object[] makeUpdateValues(Quote quote) {
    return new Object[] {
      quote.getLastPrice().toString() + ",",
      quote.getBidPrice().toString() + ",",
      quote.getBidSize().toString() + ",",
      quote.getAskPrice().toString() + ",",
      quote.getAskSize().toString() + ",",
      quote.getTicker()
    };
  }

  @Override
  public <S extends Quote> Iterable<S> saveAll(Iterable<S> quotes) {
    quotes.forEach(this::save);
    return quotes;
  }

  private void addOne(Quote quote) {
    SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
    int row = simpleJdbcInsert.execute(parameterSource);
    if (row != 1) {
      throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, row);
    }
  }

  @Override
  public Iterable<Quote> findAll() {
    final String SQL_SELECTALL = "SELECT * FROM " + TABLE_NAME;
    return jdbcTemplate.query(SQL_SELECTALL, new BeanPropertyRowMapper<>(Quote.class));
  }

  /**
   * Find a quote by ticker
   *
   * @param ticker name
   * @return quote or Optional.empty if not found
   */
  @Override
  public Optional<Quote> findById(String ticker) {
    final String SQL_SELECTONE =
        "SELECT * FROM " + TABLE_NAME + " WHERE" + ID_COLUMN_NAME + " = :ticker";
    List<Quote> quotes =
        namedTemplate.query(
            SQL_SELECTONE,
            new MapSqlParameterSource("ticker", ticker),
            new BeanPropertyRowMapper<>(Quote.class));

    if (quotes.size() != 1) {
      logger.error("Unexpected result.");
      throw new IncorrectResultSizeDataAccessException("Unexpected result.", 1, quotes.size());
    } else {
      return Optional.of(quotes.get(0));
    }
  }

  @Override
  public boolean existsById(String ticker) {
    final String SQL_SELECTONE =
        "SELECT * FROM " + TABLE_NAME + " WHERE" + ID_COLUMN_NAME + " = :ticker";
    return namedTemplate
            .query(
                SQL_SELECTONE,
                new MapSqlParameterSource("ticker", ticker),
                BeanPropertyRowMapper.newInstance(Quote.class))
            .size()
        > 0;
  }

  @Override
  public long count() {
    final String SQL_COUNT = "SELECT COUNT(*) FROM " + TABLE_NAME;
    return jdbcTemplate.query(SQL_COUNT, new SingleColumnRowMapper<Long>()).get(0);
  }

  @Override
  public void deleteById(String ticker) {
    final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE ticker = :ticker";
    namedTemplate.update(SQL_DELETE, new MapSqlParameterSource("ticker", ticker));
  }

  @Override
  public Iterable<Quote> findAllById(Iterable<String> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void delete(Quote quote) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends Quote> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll() {
    throw new UnsupportedOperationException("Not implemented");
  }
}
