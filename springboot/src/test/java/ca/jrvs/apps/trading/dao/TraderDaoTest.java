package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.trading.config.TestConfig;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.After;
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
public class TraderDaoTest {

  @Autowired private TraderDao traderDao;
  private Trader savedTrader;

  @Before
  public void insertOne() {
    savedTrader = new Trader();
    savedTrader.setID(9999);
    savedTrader.setFirstName("FirstName");
    savedTrader.setLastName("lastName");
    savedTrader.setCountry("Canada");
    savedTrader.setDob(new Date(System.currentTimeMillis()));
    savedTrader.setEmail("myemail@gmail.com");
    traderDao.save(savedTrader);
  }

  @After
  public void deleteOne() {
    traderDao.deleteById(savedTrader.getID());
  }

  @Test
  public void findAllById() {
    List<Trader> traders =
        Lists.newArrayList(traderDao.findAllById(Arrays.asList(savedTrader.getID(), -1)));
    assertEquals(1, traders.size());
    assertEquals(savedTrader.getCountry(), traders.get(0).getCountry());
  }
}
