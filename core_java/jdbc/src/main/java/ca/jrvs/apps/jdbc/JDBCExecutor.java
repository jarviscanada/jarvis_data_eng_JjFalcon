package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.SQLException;


// Test to confirm:  psql -h localhost -U postgres -d hplussport
public class JDBCExecutor {

  public static void main(String[] args) {
    DBConnectionManager dcm = new DBConnectionManager("localhost", "hplussport", "postgres",
        "password");
    try {
      Connection connection = dcm.getConnection();
      /* Testing executing statement
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM CUSTOMER");
       */

      CustomerDAO customerDAO = new CustomerDAO(connection);
      // OrderDAO orderDAO = new OrderDAO(connection);

      /* Testing for Inserting Data
      Customer customer = new Customer();
      customer.setFirstName("George");
      customer.setLastName("Washington");
      customer.setEmail("george.washington@wh.gov");
      customer.setPhone("(555) 555-6543");
      customer.setAddress("1234 Main St");
      customer.setCity("Mount Vernon");
      customer.setState("VA");
      customer.setZipCode("22121");
      customerDAO.create(customer);
       */

      /* Testing for Reading Data based on given ID
      Customer customer = customerDAO.findById(1000);
      // Alternatively, could use a toString method on customer
      System.out.println(customer.getFirstName() + " " + customer.getLastName());
       */

      /* Testing for Updating Data based on given ID
      Customer customer = customerDAO.findById(10000);
      System.out.println(customer.getFirstName() + " " + customer.getLastName() + " " +
          customer.getEmail());
      customer.setEmail("gwashington@wh.gov");
      customer = customerDAO.update(customer);
      System.out.println(customer.getFirstName() + " " + customer.getLastName() + " " +
          customer.getEmail());
       */

      /* Testing CRUD
      Customer customer = new Customer();
      customer.setFirstName("John");
      customer.setLastName("Adams");
      customer.setEmail("jadams.wh.gov");
      customer.setAddress("1234 Main St");
      customer.setCity("Arlington");
      customer.setState("VA");
      customer.setPhone("(555) 555-9845");
      customer.setZipCode("01234");

      Customer dbCustomer = customerDAO.create(customer);
      System.out.println(dbCustomer);
      dbCustomer = customerDAO.findById(dbCustomer.getId());
      System.out.println(dbCustomer);
      dbCustomer.setEmail("john.adams@wh.gov");
      dbCustomer = customerDAO.update(dbCustomer);
      System.out.println(dbCustomer);
      customerDAO.delete(dbCustomer.getId());
       */

      /* Testing Order Table
      Order order = orderDAO.findById(1000);
      System.out.println(order);
       */

      /* Testing for executing stored procedure
      List<Order> orders = orderDAO.getOrdersForCustomer(789);
      orders.forEach(System.out::println);
       */

      customerDAO.findAllSorted(20).forEach(System.out::println);
      System.out.println("Paged");
      for(int i=1;i<3;i++){
        System.out.println("Page number: " + i);
        customerDAO.findAllPaged(10, i).forEach(System.out::println);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
