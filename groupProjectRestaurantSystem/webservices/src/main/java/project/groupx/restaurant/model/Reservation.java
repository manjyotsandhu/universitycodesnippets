/**
 * model of a reservation on the database.
 */
package project.groupx.restaurant.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Johannes Herforth
 *
 */
@XmlRootElement(name = "Reservation")
public class Reservation {
  private long id;
  private int tableid;
  private String customerName;
  private String phoneNumber;
  private int numberOfPeople;
  private long timeOfReservation;
  
  /**
   * empty constructor for reservation
   */
  public Reservation() {}

  /**
   * get id of reservation
   * @return long
   */
  public long getId() {
    return id;
  }
  
  /**
   * set id
   * @param long
   */
  public void setId(long l) {
    this.id = l;
  }

  /**
   * get table id of reservation
   * @return int
   */
  public int getTableid() {
    return tableid;
  }

  /**
   * sets table id
   * @param tableid
   */
  public void setTableid(int tableid) {
    this.tableid = tableid;
  }

  /**
   * get customer name
   * @return String
   */
  public String getCustomerName() {
    return customerName;
  }

  /**
   * sets customername
   * @param customerName
   */
  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  /**
   * get phone number
   * @return string
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * set phone number
   * @param phoneNumber
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * get number of people for reservation
   * @return int
   */
  public int getNumberOfPeople() {
    return numberOfPeople;
  }

  /**
   * set number of people
   * @param numberOfPeople
   */
  public void setNumberOfPeople(int numberOfPeople) {
    this.numberOfPeople = numberOfPeople;
  }

  /**
   * get time of reservation
   * @return long unixtimestamp
   */
  public long getTimeOfReservation() {
    return timeOfReservation;
  }

  /**
   * set time of reservation
   * @param timeOfReservation (timestamp)
   */
  public void setTimeOfReservation(long timeOfReservation) {
    this.timeOfReservation = timeOfReservation;
  }
  
  
}
