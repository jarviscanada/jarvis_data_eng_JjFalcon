package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.TraderAccountView;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraderAccountService {

  private TraderDao traderDao;
  private AccountDao accountDao;
  private PositionDao positionDao;
  private SecurityOrderDao securityOrderDao;

  @Autowired
  public TraderAccountService(TraderDao traderDao, AccountDao accountDao,
      SecurityOrderDao securityOrderDao, PositionDao positionDao) {
    this.traderDao = traderDao;
    this.accountDao = accountDao;
    this.positionDao = positionDao;
    this.securityOrderDao = securityOrderDao;
  }

  public TraderAccountView createTraderAndAccount(Trader newTrader) {

    if (doesExists(newTrader)) {
      throw new IllegalArgumentException("Account already exist");
    } else if (!isNotNull(newTrader)) {
      throw new IllegalArgumentException("Encountered empty fields");
    } else {
      Account newAccount = new Account();
      newTrader = traderDao.save(newTrader);
      newAccount.setTraderId(newTrader.getID());
      newAccount.setAmount(0.00);
      newAccount = accountDao.save(newAccount);
      return new TraderAccountView(newTrader, newAccount);
    }
  }

  // check if trader exist using email address
  private boolean doesExists(Trader trader) {
    for (Trader traderMember : traderDao.findAll()) {
      if (trader.getEmail().toLowerCase().equals(traderMember.getEmail().toLowerCase())) {
        return true;
      }
    }
    return false;
  }

  // check for null values
  private boolean isNotNull(Trader trader) {
    String firstName = trader.getFirstName();
    String lastName = trader.getLastName();
    Date dob = trader.getDob();
    String country = trader.getCountry();
    String email = trader.getEmail();

    return !(firstName == null || lastName == null || dob == null || country == null | email == null);
  }

  public void deleteTraderById(int id) {
    Trader traderToDelete = traderDao.findById(id).orElseThrow(
        () -> new EntityNotFoundException("No Trader with that ID exists!")
    );
    Account accountToDelete = accountDao.findById(id).orElseThrow(
        () -> new EntityNotFoundException("No account with that ID exists!")
    );
    List<Position> accountPositions = getAccountPositions(id);
    if (accountToDelete.getAmount() != 0 || hasOpenPositions(accountPositions)) {
      throw new IllegalStateException("Account is still active and can't be deleted");
    } else {
      for (SecurityOrder order : securityOrderDao.findAllById(id)) {
        securityOrderDao.deleteById(order.getID());
      }
      accountDao.deleteById(accountToDelete.getID());
      traderDao.deleteById(traderToDelete.getID());
    }
  }

  private List<Position> getAccountPositions(int id) {
    List<Position> positions = new ArrayList<>();
    for (Position position : positionDao.findAllById(id)) {
      if (id == position.getID()) {
        positions.add(position);
      }
    }
    return positions;
  }

  private boolean hasOpenPositions(List<Position> positionList) {
    for (Position position : positionList) {
      if (position.getPosition() != 0) {
        return true;
      }
    }
    return false;
  }

  public Account deposit(int accountId, double depositAmount) {
    if (depositAmount <= 0) {
      throw new IllegalArgumentException("Invalid Amount");
    }
    Optional<Account> accountOpt = accountDao.findById(accountId);
    Account account;
    if (accountOpt.isPresent()) {
      account = accountOpt.get();
      account.setAmount(account.getAmount() + depositAmount);
      accountDao.updateOne(account);
      return account;
    } else {
      throw new EntityNotFoundException("Account does not exist");
    }
  }

  public Account withdraw(int accountId, double withdrawAmount) {
    if (withdrawAmount <= 0) {
      throw new IllegalArgumentException("Invalid Amount");
    }
    Account account = accountDao.findById(accountId).orElseThrow(
        () -> new EntityNotFoundException("Account does not exist")
    );
    if (withdrawAmount > account.getAmount()) {
      throw new IllegalArgumentException("Insufficient Funds");
    }
    account.setAmount(account.getAmount() - withdrawAmount);
    accountDao.updateOne(account);
    return account;
  }

}
