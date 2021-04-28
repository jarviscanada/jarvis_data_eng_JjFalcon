package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class PositionDao {

  final String TABLE_NAME = "position";
  final String ID_COLUMN = "account_id";

  JdbcTemplate jdbcTemplate;
  SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public PositionDao(DataSource ds) {
    jdbcTemplate = new JdbcTemplate(ds);
    simpleJdbcInsert =
        new SimpleJdbcInsert(jdbcTemplate)
            .withTableName(TABLE_NAME)
            .usingGeneratedKeyColumns(ID_COLUMN);
  }

  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  public SimpleJdbcInsert getSimpleJdbcInsert() {
    return simpleJdbcInsert;
  }

  public String getTableName() {
    return TABLE_NAME;
  }

  public String getIdColumnName() {
    return ID_COLUMN;
  }

  Class<Position> getEntityClass() {
    return Position.class;
  }

 }