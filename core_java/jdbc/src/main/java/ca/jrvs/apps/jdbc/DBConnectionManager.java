package ca.jrvs.apps.jdbc;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnectionManager {
  private final String url;
  private final Properties properties;

  public DBConnectionManager(String host, String dbName, String userName, String password) {
    this.url = "jdbc:postgresql://" + host + "/" + dbName;
    this.properties = new Properties();
    this.properties.setProperty("user", userName);
    this.properties.setProperty("password", password);
  }

  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(this.url, this.properties);
  }

}
