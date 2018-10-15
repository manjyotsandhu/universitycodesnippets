package project.groupx.restaurant.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * model of a table on the database.
 * @author amrit
 *
 */
@XmlRootElement(name = "Table")
public class Table {
  private long tableid;
  private int status;
  private int size;
  
  /**
   * empty constructor for table
   */
  public Table() {}
  
  /**
   * get tableid
   * @return long(id)
   */
  public long getTableID() {
    return tableid;
  }
  
  /**
   * sets tableid
   * @param long (id)
   */
  public void setTableID(long l) {
    this.tableid = l;
  }
  
  /**
   * gets status
   * @return int
   */
  public int getStatus() {
    return status;
  }
  
  /**
   * sets status of table
   * @param status
   */
  public void setStatus(int status) {
    this.status = status;
  }
  
  /**
   * gets size
   * @return int
   */
  public int getSize() {
    return size;
  }
  
  /**
   * set size
   * @param int size
   */
  public void setSize(int size) {
    this.size = size;
  }
  
}
