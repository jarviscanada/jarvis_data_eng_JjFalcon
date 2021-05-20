package ca.jrvs.apps.trading.service;

import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.trading.config.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.TraderAccountView;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import java.time.LocalDate;
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
public class TraderAccountServiceTest {

  private TraderAccountView savedView;
  private Trader newTrader;

  @Autowired
  TraderAccountService traderAccountTestService;
  @Autowired
  QuoteDao quoteDao;
  @Autowired
  AccountDao accountDao;
  @Autowired
  TraderDao traderDao;
  @Autowired
  SecurityOrderDao securityOrderDao;

  @Before
  public void setUp() {
    newTrader = new Trader();
    newTrader.setID(1);
    newTrader.setFirstName("FirstName");
    newTrader.setLastName("LastName");
    newTrader.setCountry("Canada");
    newTrader.setEmail("myemail@gmail.com");
    newTrader.setDob(Date.valueOf(LocalDate.now()));
    //savedView = traderAccountTestService.createTraderAndAccount(newTrader);
  }

  @After
  public void cleanUp() {
    accountDao.deleteById(savedView.getAccount().getID());
    traderDao.deleteById(savedView.getTrader().getID());
  }

  @Test
  public void createTraderAndAccount() {
    savedView = traderAccountTestService.createTraderAndAccount(newTrader);
    assertEquals(newTrader.getID(), savedView.getTrader().getID());
  }

  // add an existing trader
  @Test(expected = IllegalArgumentException.class)
  public void createNewTraderAccountTwice() {
    createTraderAndAccount();
    createTraderAndAccount();
  }

  @Test
  public void deposit() {
    createTraderAndAccount();
    Account account = traderAccountTestService.deposit(1, 100.00);
    assertEquals(100.00, account.getAmount(), 0);
  }

  @Test
  public void withdraw() {
    deposit();
    Account account = traderAccountTestService.withdraw(1, 50.00);
    assertEquals(50.00, account.getAmount(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void withdrawNSF() {
    deposit();
    traderAccountTestService.withdraw(1, 5000.00);
  }
}