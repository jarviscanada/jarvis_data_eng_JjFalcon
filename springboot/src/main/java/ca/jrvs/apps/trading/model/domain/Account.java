package ca.jrvs.apps.trading.model.domain;

import ca.jrvs.apps.trading.model.Entity;

public class Account implements Entity<Integer> {

  private int id;
  private int traderId;
  private double amount;

  @Override
  public Integer getID() {
    return id;
  }

  @Override
  public void setID(Integer id) {
    this.id = id;
  }

  public Integer getTraderId() {
    return traderId;
  }

  public void setTraderId(Integer traderId) {
    this.traderId = traderId;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  @Override
  public String toString() {
    return "Account{"
        + "id=" + id
        + ", traderId=" + traderId
        + ", amount=" + amount
        + '}';
  }
}
