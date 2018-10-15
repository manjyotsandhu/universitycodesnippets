package client.app.logic;

import javax.xml.bind.annotation.XmlRootElement;

import client.server.connection.ServerClient;

//import java.util.ArrayList;
/**
 * 
 * @author amrit
 * Reservation class.
 */
@XmlRootElement(name = "Reservation")
public class Reservation {

  private long id;
  private String customerName;
  private String phoneNumber;
  private int numberOfPeople;
  private long timeOfReservation;
  private long tableid;
  //private ServerClient scli = new ServerClient();
  private StringBuffer currResponse = new StringBuffer();
  private ServerClient sercli = new ServerClient();
  
 /**
  * constructor for Reservation
  * @param takes id, customername, phonenumber, numberofpeople and time.
  * sets tableid to 1.
  */
  public Reservation(int id, String customerName, String phoneNumber, int numberOfPeople,  int time) {
    this.id = id;
    this.customerName = customerName;
    this.phoneNumber = phoneNumber;
    this.numberOfPeople = numberOfPeople;
    this.timeOfReservation = time;
    this.tableid = 1;
    
  }
  
  /**
   * empty constructor to be used when calling from database.
   */
  public Reservation() {
    
  }
  /**
   * gets customer name
   * @return String customer name
   */
  public String getCustomerName() {
    return customerName;
  }
  
  /**
   * sets customer name
   * @param name in String
   */
  public void setCustomerName(String name) {
    this.customerName = name;
  }
  
  /**
   * gets phone number
   * @return String of phone number
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }
  
  /**
   * sets phone number
   * @param String for number
   */
  public void setPhoneNumber(String number) {
    this.phoneNumber = number;
  }

  /**
   * gets number of people
   * @return int of people
   */
  public int getNumberOfPeople() {
    return numberOfPeople;
  }
  
  /**
   * set number of people
   * @param int of numberOfPeople
   */
  public void setNumberOfPeople(int numberOfPeople) {
    this.numberOfPeople = numberOfPeople;
  }

  /**
   * gets time/ date of reservation
   * @return long (unix timestamp)
   */
  public long getTimeOfReservation() {
    return timeOfReservation;
  }
  
  /**
   * sets time of reservation
   * @param time(unix timestamp)
   */
  public void setTimeOfReservation(long time) {
    this.timeOfReservation = time;
  }
  
  /**
   * gets id of reservation
   * @return long
   */
  public long getId() {
    return id;
  }
  /**
   * sets id 
   * @param id
   */
  public void setId(long id) {
    this.id = id;
  }
  
  /**
   * converts a reservation into xml to put on server.
   * @return Stringbuffer of the xml
   */
  public StringBuffer getXML() {
    StringBuffer answer = new StringBuffer();
    answer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> \n");
    answer.append("<Reservation>\n");
    answer.append("<customerName>" + this.getCustomerName() + "</customerName>\n");
    answer.append("<id>" + this.getId() + "</id>\n");
    answer.append("<numberOfPeople>" + this.getNumberOfPeople() + "</numberOfPeople>\n");
    answer.append("<phoneNumber>" + this.getPhoneNumber() + "</phoneNumber>\n");
    answer.append("<tableid>" + this.getTableid() + "</tableid>\n");
    answer.append("<timeOfReservation>" + this.getTimeOfReservation() + "</timeOfReservation>\n");    
    answer.append("</Reservation>\n");
    return answer;
  }
  
  /**
   * method to add a reservation to the server
   * @param newXML, converted reservation into xml
   * @return object Reservation thats been added to server
   */
  public Reservation confirmReservation(StringBuffer newXML) {
    Reservation new_reservation = new Reservation();
    currResponse = sercli.httpPostSB("http://shouganai.net:8080/restaurant/rest/reservation", newXML);
    new_reservation = (Reservation) sercli.convertXML(currResponse, Reservation.class);
    
    return new_reservation;
  }
  
  
  /**
   * get tableid of the reservation
   * @returns tableid
   */
  public long getTableid() {
    return tableid;
  }

  /**
   * set table id
   * @param long for id.
   */
  public void setTableid(long l) {
    this.tableid = l;
  }
  

}
