package project.groupx.restaurant.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer {
  private long cust_id;
  private int callingWaiter;
  private long tableID;
  
  /**
   * Constructor which initialises a customer instance
   */
  public Customer() {
    this.cust_id = 0;
    this.callingWaiter = 0;
    this.tableID = 1;
  }
  
  /**
   * @return long The table ID
   */
  public long getTableID() {
    return tableID;
  }
  
  public void setTableID(long tableID) {
    this.tableID = tableID;
  }
  
  /**
   * @return int The callingWaiter status
   */
  public int getCallingWaiter() {
    return callingWaiter;
  }


  /**
   * @param callingWaiter the callingWaiter to set
   */
  public void setCallingWaiter(int callingWaiter) {
    this.callingWaiter = callingWaiter;
  }


  /**
   * @return the cust_id
   */
  public long getCust_id() {
    return cust_id;
  }


  /**
   * @param cust_id the cust_id to set
   */
  public void setCust_id(long cust_id) {
    this.cust_id = cust_id;
  }

  
}
