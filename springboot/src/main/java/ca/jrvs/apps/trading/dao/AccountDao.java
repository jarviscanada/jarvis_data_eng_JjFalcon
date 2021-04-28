package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao extends JdbcCrudDao<Account> {

  final String TABLE_NAME = "account";
  final String ID_COLUMN = "id";

  JdbcTemplate jdbcTemplate;
  SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public AccountDao(DataSource ds) {
    jdbcTemplate = new JdbcTemplate(ds);
    simpleJdbcInsert =
        new SimpleJdbcInsert(jdbcTemplate)
            .withTableName(TABLE_NAME)
            .usingGeneratedKeyColumns(ID_COLUMN);
  }

  @Override
  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  @Override
  public SimpleJdbcInsert getSimpleJdbcInsert() {
    return simpleJdbcInsert;
  }

  @Override
  public String getTableName() {
    return TABLE_NAME;
  }

  @Override
  public String getIdColumnName() {
    return ID_COLUMN;
  }

  @Override
  Class<Account> getEntityClass() {
    return Account.class;
  }

  @Override
  public int updateOne(Account entity) {
    String sqlQuery = "UPDATE " + getTableName() + " SET amount=? WHERE " + getIdColumnName() + "=?";
    return jdbcTemplate.update(sqlQuery, entity.getAmount(), entity.getID());
  }

  @Override
  public <S extends Account> Iterable<S> saveAll(Iterable<S> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void delete(Account account) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends Account> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }
}
