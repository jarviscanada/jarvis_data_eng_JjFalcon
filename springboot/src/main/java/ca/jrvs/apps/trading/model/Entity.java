package ca.jrvs.apps.trading.model;

public interface Entity<T> {
  T getID();
  void setID(T id);
}
