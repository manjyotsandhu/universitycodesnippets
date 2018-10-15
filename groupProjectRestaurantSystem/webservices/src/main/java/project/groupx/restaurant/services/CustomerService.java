/**
 * The class CustomerService is all the business value for the Customer data structure. The class
 * contains all the methods that are called from the resource. The methods all work with updating or 
 * receiving information about Customers from the database.
 */
package project.groupx.restaurant.services;

/**
 * @author Johannes Herforth.
 * @author Manjyot Sandhu
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import project.groupx.restaurant.model.Customer;
import project.groupx.restaurant.database.DatabaseClient;
import project.groupx.restaurant.exceptions.DBEntryNotFound;

public class CustomerService {

  private List<Customer> customers;
  private Customer currCustomer;
  private long longID;
  private ResultSet rs;
  private Connection conn;

  /**
   * gets a list of all the Customers that are stored on the database.
   * 
   * @return list of Customers on the database.
   */
  public List<Customer> getCustomers() {
    customers = new ArrayList<>();
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "SELECT * FROM customers;");

    try {
      while (rs.next()) {
        currCustomer = new Customer();
        currCustomer.setCust_id(rs.getLong(1));
        currCustomer.setCallingWaiter(rs.getInt(2));
        currCustomer.setTableID(rs.getLong(3));

        customers.add(currCustomer);
      }
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return customers;

  }

  /**
   * Takes an id to then retrieve the specific Customer from the database.
   * 
   * @param id
   *          specific id of Customer.
   * @return specific Customer from database.
   */
  public Customer getCustomer(long id) {
    currCustomer = new Customer();
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn,
        "SELECT * FROM customers WHERE id=" + id + ";");

    try {
      while (rs.next()) {
        currCustomer.setCust_id(rs.getLong(1));
        currCustomer.setCallingWaiter(rs.getInt(2));
        currCustomer.setTableID(rs.getLong(3));
      }
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return currCustomer;
  }

  /**
   * gets the highest ID of customer from database to safely add a new customer
   * with a new ID.
   * 
   * @return next open id to be used.
   */
  public long getNextID() {
    longID = 0L;
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn,
        "SELECT id FROM customers ORDER BY id DESC LIMIT 1;");

    try {
      rs.next();
      longID = rs.getLong(1);
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return longID + 1;
  }

  /**
   * The method adds a customer to the database
   * 
   * @param addCustomer
   *          The customer to be added
   * @return Customer The newly added customer
   */
  public Customer addCustomer(Customer addCustomer) throws DBEntryNotFound {
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    DatabaseClient.executeQuery(conn,
        "INSERT INTO customers " + "(id,callingwaiter,tableid) VALUES ("
            + addCustomer.getCust_id() + "," + addCustomer.getCallingWaiter()
            + "," + addCustomer.getTableID() + ");");

    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return addCustomer;
  }

  /**
   * removes the Customer with the specific id from the database.
   * 
   * @param id
   *          id of the Customer to delete.
   */
  public void removeCustomer(long id) {
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    DatabaseClient.executeQuery(conn,
        "DELETE FROM customers WHERE id=" + id + ";");

    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * The method updates a customer in the database
   * 
   * @param uCustomer
   *          The customer to be updated
   * 
   * @return Customer The newly modified customer
   */
  public Customer updateCustomer(Customer uCustomer) throws DBEntryNotFound {
    Customer dbCustomer = getCustomer(uCustomer.getCust_id());

    if (dbCustomer.getCust_id() == 0) {
      throw new DBEntryNotFound("ERROR: Customer ID not found");
    }

    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn,
        "UPDATE customers set id=" + uCustomer.getCust_id()
            + ", callingwaiter = " + uCustomer.getCallingWaiter()
            + ", tableid = " + uCustomer.getTableID() + "WHERE id ="
            + uCustomer.getCust_id() + ";");

    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return uCustomer;

  }

}
