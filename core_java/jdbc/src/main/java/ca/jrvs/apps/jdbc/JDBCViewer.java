package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

// Test to confirm:  psql -h localhost -U postgres -d hplussport
public class JDBCViewer {

  public static void main(String[] args) {
    DBConnectionManager dcm = new DBConnectionManager("localhost", "hplussport", "postgres",
        "password");
    try {
      Connection connection = dcm.getConnection();
      CustomerDAO customerDAO = new CustomerDAO(connection);
      // TODO: Options for different testing

      customerDAO.findAllSorted(20).forEach(System.out::println);
      System.out.println("Paged");
      for(int i=1;i<3;i++){
        System.out.println("Page number: " + i);
        customerDAO.findAllPaged(10, i).forEach(System.out::println);
      }

    } catch (SQLException e) {
      // replace with a logger
      e.printStackTrace();
    }
  }
}
