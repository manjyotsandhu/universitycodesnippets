package client.app.logic;

import java.util.*;

import javax.xml.bind.annotation.XmlRootElement;

import client.server.connection.ServerClient;

/**
 * Class to represent a customer (the client) in the restaurant management
 * system. Has methods to make calls to a waiter.
 * 
 * @author manjyot
 *
 */
@XmlRootElement(name = "customer")
public class Customer extends Account {

  // Details of the customer
  private String surname;
  private List<Account> emails = new ArrayList<>();
  public String email;
  private int callingWaiter;
  private long cust_id;
  private long tableID;

  // For the server connection
  private ServerClient sercli = new ServerClient();
  private StringBuffer currResponse = new StringBuffer();

  /**
   * Empty constructor for customer, used in menu UI
   */
  public Customer() {
    this.callingWaiter = 0;
    this.cust_id = 0;
    this.tableID = 1;
  }

  /**
   * Constructor to assign all relevant customer account information to the
   * customer instance
   * 
   * @param ID
   * @param name
   * @param surname
   * @param email
   * @param password
   */
  public Customer(long ID, String name, String surname, String email,
      String password) {
    super();
    this.setAccountID(ID);
    this.setAccountType(1);
    this.setEmail(email);
    this.setSurname(surname);
    this.setPassword(password);
    this.setName(name);
    this.callingWaiter = 0;
    this.cust_id = ID;
    this.tableID = 1;
  }

  
  /**
   * Gets the currently assigned table for the customer
   * @return long Table the customer is assigned to
   */
  public long getTableID() {
    return tableID;
  }

  /**
   * Sets the table for the customer
   * @param tableID The new table
   */
  public void setTableID(long tableID) {
    this.tableID = tableID;
  }

  /**
   * Method to retrieve Customer surname
   * 
   * @return String Surname
   */
  public String getSurname() {
    return surname;
  }

  /**
   * Method to set Customer surname
   * 
   * @param surname
   */
  public void setSurname(String surname) {
    this.surname = surname;
  }

  public Customer(List<Account> cust) {
    this.emails = cust;
  }

  public List<Account> getEmails() {
    return emails;
  }

  /**
   * Returns customer calling waiter status
   * 
   * @return int Current calling status
   */
  public int getCallingWaiter() {
    return callingWaiter;
  }

  /**
   * Sets customer calling waiter status
   * 
   * @param callingWaiter
   *          the new calling status to set
   */
  public void setCallingWaiter(int callingWaiter) {
    this.callingWaiter = callingWaiter;
  }

  /**
   * Returns the customer id
   * 
   * @return long Customer id
   */
  public long getCust_id() {
    return cust_id;
  }

  /**
   * Sets the current customer id
   * 
   * @param long
   *          The new customer id to be to set
   */
  public void setCust_id(long cust_id) {
    this.cust_id = cust_id;
  }

  /**
   * Updates a specific customer in the database, according to the current
   * instance
   * 
   * @param cust_id
   *          Id of customer to modify
   */
  public int updateCustomer() {
    return sercli.httpPut(
        "http://shouganai.net:8080/restaurant/rest/customers/alter",
        this.getXML());
  }

  /**
   * Gets an instance of a customer from the server
   * @param id ID of the customer to retrieve
   * @return Customer The customer from the database
   */
  public Customer getCustomer(long id) {
    currResponse = sercli
        .httpGet("http://shouganai.net:8080/restaurant/rest/customers/" + id);
    return (Customer) sercli.convertXML(currResponse, Customer.class);
  }
  
  /**
   * Creates a new customer and adds it to the database
   * @return int Response code returned to represent failure or success
   */
  public int makeCustomer() {
    return sercli.httpPost("http://shouganai.net:8080/restaurant/rest/customers", this.getXML());
  }

  /**
   * Instance of customer converted to XML format.
   */
  public StringBuffer getXML() {
    StringBuffer answer = new StringBuffer();
    answer.append(
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> \n");
    answer.append("<customer>\n");
    answer.append("<callingWaiter>" + this.callingWaiter + "</callingWaiter> \n");
    answer.append("<cust_id>" + this.cust_id + "</cust_id> \n");
    answer.append("<tableID>" + this.tableID + "</tableID> \n");
    answer.append("</customer> \n");

    return answer;
  }

}
