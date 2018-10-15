package client.app.logic;

import java.util.ArrayList;
import java.util.List;

import client.server.connection.ServerClient;

/**
 * A class to manage all the customers, when dealing with the database. Includes
 * methods to retrieve all customers from the database, or those who are making
 * a call.
 * 
 * @author manjyot
 *
 */
public class CustomerManager {

  // Used for server connection
  private ServerClient sercli = new ServerClient();
  private StringBuffer currResponse;
  private Customers allDatabaseCustomers;


  /**
   * Empty constructor
   */
  public CustomerManager() {

  }

  /**
   * Retrieves all of the customers who are currently making a call
   * 
   * @return List<Customer> containing the customers who are making a call
   */
  public List<Customer> getDbCustomersCalling() {
    List<Customer> allCustomers = getCustomersFromDatabase();
    List<Customer> allCustCalling = new ArrayList<Customer>();
    for (Customer c : allCustomers) {
      if (c.getCallingWaiter() == 1) {
        allCustCalling.add(c);
      }
    }
    return allCustCalling;
  }

  /**
   * Retrieves all of the customers from the database
   * 
   * @return List<Customer> of customers in database
   */
  public List<Customer> getCustomersFromDatabase() {
    List<Customer> allCustomers = new ArrayList<Customer>();
    currResponse = sercli
        .httpGet("http://shouganai.net:8080/restaurant/rest/customers");
    allDatabaseCustomers = (Customers) sercli.convertXML(currResponse,
        Customers.class);

    allCustomers = allDatabaseCustomers.getCustomers();
    return allCustomers;
  }
}
