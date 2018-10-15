package client.app.logic;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * A class to represent a table in the restaurant management system. Methods
 * included handle number of seats on a table, the reservation status and any
 * orders assigned to the table.
 * 
 * @author markharrison An abstraction of a Table
 * @author Amrit Kandola
 * @author manjyot
 */

@XmlRootElement(name = "Table")
public class Table {

  private long tableID;
  private int custNum;
  private int status;
  private int size;
  private ArrayList<Integer> orders = new ArrayList<Integer>();
  private ArrayList<Reservation> reservations = new ArrayList<Reservation>();

  /**
   * Constructor for a table
   * 
   * @param id
   *          The ID of the table
   * @param size
   *          The size of the table
   * @param orders
   *          An arraylist of order numbers for the table
   */
  public Table(long id, int size, ArrayList<Integer> orders) {
    for (Integer ints : orders) {
      this.orders.add(ints);
    }
    this.tableID = id;
    this.size = size;
    this.orders = orders;
    this.custNum = 0;
    this.status = 0;
    reservations = new ArrayList<Reservation>();
  }

  /**
   * Constructor for table which takes the table size
   * 
   * @param size
   *          Table size
   */
  public Table(int size) {
    this.size = size;
    this.custNum = 0;
    this.status = 0;

  }

  /**
   * Empty constructor for a table, initialises values
   */
  public Table() {
    this.tableID = 0;
    this.size = 0;
    this.status = 0;
  }

  /**
   * Gets all of the order numbers from this table
   * 
   * @return ArrayList<Integer> Order numbers assigned to table
   */
  public ArrayList<Integer> getOrders() {
    return this.orders;
  }

  /**
   * Gets the customer number of the customer assigned to table
   * 
   * @return int Customer ID on table
   */
  public int getCustNum() {
    return this.custNum;
  }

  /**
   * Sets the new customer number on the table
   * 
   * @param custNum
   *          New customer ID
   */
  public void setCustNum(int custNum) {
    this.custNum = custNum;
  }

  /**
   * Gets the current status of the table.
   * 
   * @return int The current status
   */
  public int getStatus() {
    return this.status;
  }

  /**
   * Adds a table reservation for the table
   * 
   * @param reservation
   *          The reservation to be added
   */
  public void addTableReservation(Reservation reservation) {
    if (isReservationNotConflicted(reservation.getTimeOfReservation())) {
      reservations.add(reservation);
    }
  }

  /**
   * Removes a table reservation for the table
   * 
   * @param reservation
   *          Reservation to be removed
   */
  public void removeTableReservation(Reservation reservation) {
    reservations.remove(reservations.indexOf(reservation));
  }

  /**
   * Gets all of the table reservations for the current table
   * 
   * @return ArrayList<Reservation> All reservations for this table
   */
  public ArrayList<Reservation> getTableReservations() {
    return this.reservations;
  }

  /**
   * Will iterate through the reservations list for the table and check to see
   * if the time and date interferes with another already existing reservation
   * 
   * @param time
   *          Time to check for conflict
   * @return boolean The result of the conflict check
   */
  public boolean isReservationNotConflicted(long time) {
    for (int i = 0; i < reservations.size(); i++) {
      // if reservation date conflicts with given date
      if (reservations.get(i).getTimeOfReservation() == time
          || reservations.get(i).getTimeOfReservation() == time + 100) {
        return false;
      }
    }
    return true;
  }

  /**
   * Sets the table as available, by setting the status to 0
   */
  public void setTableAvailable() {
    this.status = 0;
  }

  /**
   * Sets the table as closed, by setting the status to 1
   */
  public void setTableClosed() {
    this.status = 1;
  }

  /**
   * Sets the table as booked, by setting the status to 2
   */
  public void setTableBooked() {
    this.status = 2;
  }

  /**
   * Gets the current size of the table
   * 
   * @return int Number of seats on table
   */
  public int getSize() {
    return this.size;
  }

  /**
   * Returns the table ID
   * 
   * @return long ID of table
   */
  public long getTableID() {
    return tableID;
  }

  /**
   * Sets a new size for the table
   * 
   * @param newsize
   *          New number of seats at table
   */
  public void setSize(int newsize) {
    this.size = newsize;
  }

  /**
   * Sets a new table ID
   * 
   * @param tableId
   *          New ID to be set
   */
  public void setTableID(long tableId) {
    this.tableID = tableId;
  }

  /**
   * Sets a new status for the table
   * 
   * @param newstatus
   *          New status to be set
   */
  public void setStatus(int newstatus) {
    this.status = newstatus;
  }

  /**
   * Converts the class variables into an XML format, only gets tableId, size and status.
   * 
   * @return StringBuffer A string containing the XML format of the object
   */
  public StringBuffer getXML() {
    StringBuffer answer = new StringBuffer();
    answer.append(
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> \n");
    answer.append("<table>\n");
    answer.append("<tableId>" + this.tableID + "</tableId> \n");
    answer.append("<size>" + this.size + "</size> \n");
    answer.append("<status>" + this.status + "</status> \n");
    answer.append("</table> \n");

    return answer;
  }
}