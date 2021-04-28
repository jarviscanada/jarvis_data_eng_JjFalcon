package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public abstract class JdbcCrudDao<T extends Entity<Integer>> implements CrudRepository<T, Integer> {

  private static final Logger logger = LoggerFactory.getLogger(JdbcCrudDao.class);

  public abstract JdbcTemplate getJdbcTemplate();

  public abstract SimpleJdbcInsert getSimpleJdbcInsert();

  public abstract String getTableName();

  public abstract String getIdColumnName();

  abstract Class<T> getEntityClass();

  @Override
  public <S extends T> S save(S entity) {
    if (existsById(entity.getId())) {
      if (updateOne(entity) != 1) {
        throw new DataRetrievalFailureException("Unable to update quote");
      }
    } else {
      addOne(entity);
    }
    return entity;
  }

  private <S extends T> void addOne(S entity) {
    SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
    Number newID = getSimpleJdbcInsert().executeAndReturnKey(parameterSource);
    entity.setID(newID.intValue());
  }

  public abstract int updateOne(T entity);

  @Override
  public Optional<T> findById(Integer id) {
    Optional<T> entity = Optional.empty();
    String sqlQuery = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + "=?";
    try {
      return Optional.ofNullable(
          getJdbcTemplate()
              .queryForObject(sqlQuery, BeanPropertyRowMapper.newInstance(getEntityClass()), id));
    } catch (IncorrectResultSizeDataAccessException e) {
      logger.debug("Can't find trade id: " + id, e);
    }
    return entity;
  }

  /**
   * iterate through ids use findById and add to a list
   *
   * @param ids
   * @return
   */
  @Override
  public List<T> findAllById(Iterable<Integer> ids) {
    List<T> entities = new ArrayList<>();
    for (int id : ids) {
      entities.add(findById(id).orElseThrow(() -> new EntityNotFoundException("ID not found: " + id)));
    }
    return entities;
  }

  @Override
  public List<T> findAll() {
    String sqlQuery = "SELECT * FROM " + getTableName();
    return getJdbcTemplate().query(sqlQuery, BeanPropertyRowMapper.newInstance(getEntityClass()));
  }

  /**
   * find it, if found then it exist
   *
   * @param id
   * @return
   */
  @Override
  public boolean existsById(Integer id) {
    String sqlQuery = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + "=?";
    List<T> results =
        getJdbcTemplate().query(sqlQuery, BeanPropertyRowMapper.newInstance(getEntityClass()), id);
    return results.size() > 0;
  }

  @Override
  public void deleteById(Integer id) {
    String sqlQuery = "DELETE FROM " + getTableName() + " WHERE " + getIdColumnName() + "=?";
    getJdbcTemplate().update(sqlQuery, id);
  }

  @Override
  public void deleteAll() {
    String sqlQuery = "DELETE FROM " + getTableName();
    getJdbcTemplate().execute(sqlQuery);
  }

  public long count() {
    String sqlQuery = "SELECT COUNT(*) FROM " + getTableName();
    // to do
    //return getJdbcTemplate().update(sqlQuery, new SingleColumnRowMapper<Long>()).get(0);
    return 0;
  }
}
