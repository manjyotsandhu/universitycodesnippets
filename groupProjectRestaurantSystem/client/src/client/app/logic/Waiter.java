/**
 * TODO LIST
  1.
  2.
 */

package client.app.logic;

import java.util.ArrayList;
import java.util.List;

import client.server.connection.ServerClient;

/****************************************************************************************************
 * Waiter Class is an extension to an account with different privileges.                           
 * Methods contained in the class are used to update,gather and check orders using                 
 * the server client with HTTP put and get commands.                                               
 *                                                                                                 
 * @author Youcef, Manjyot                                                                         
 ****************************************************************************************************
 */
public class Waiter extends Account {

  // Waiter fields
  private long id;
  private String name;

  // Orders and tables waiter is assigned to
  private ArrayList<Order> ordersAssigned = new ArrayList<>();
  private ArrayList<Integer> tablesAssigned = new ArrayList<>();
  private Order singleOrder = new Order();
 
  // Fields for server connection
  private ServerClient sercli = new ServerClient();
  private StringBuffer currResponse;
  private Orders allDatabaseOrders = new Orders();
 
  /**
   * Constructs a waiter object with a name and given id.
   * 
   * @param id
   *          The waiters id
   * @param name
   *          The waiters name
   */
  public Waiter(long id, String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * Takes an order and checks its current stage and payment status.
   * 
   * @param o An order
   * @return -5 If the order is 'delivered' and 'unpaid'
   *  -4 If the order is 'delivered and 'paid'
   *  -3 If the order is 'delivered'
   *  -2 If the order is 'Unconfirmed
   *  -1 If the order is not placed or unrecognised
   */ 
  public int checkUnpaid(Order o) {
    if (o.getOrderStage() > 4 && o.getTotalCost() != o.getTotalPaid()) {
      return 5;
    } else if (o.getOrderStage() > 4 && o.getTotalCost() <= o.getTotalPaid()) {
      return 4;
    } else if (o.getOrderStage() == 4) {
      return 3;
    } else if (o.getOrderStage() == 1) {
      return 2;
    } else
      return 1;
  }

  /**
   * Returns a list of all orders on the database
   * 
   * @return List of all orders
   */
  public List<Order> getOrdersFromDatabase() {
    System.out.println("1");
    currResponse = sercli
        .httpGet("http://shouganai.net:8080/restaurant/rest/orders");
    allDatabaseOrders = (Orders) sercli.convertXML(currResponse, Orders.class);
    return allDatabaseOrders.getOrders();
  }

  /**
   * Returns a list of all orders, ONLY assigned to the waiter
   * 
   * @return List of waiters orders, from database
   */
  public List<Order> getWaiterOrdersFromDatabase() {
    for (Order o : getOrdersFromDatabase()) {
      if (o.getWaiterId() == this.id) {
        ordersAssigned.add(o);
      }
    }
    return ordersAssigned;
  }
  /**
   * Returns a single order from the server based on a specific table number
   * 
   * @param tableno The table number of the order that is being returned
   * @return A table order based on the given table number provided 
   */
  public Order getTableOrder(int tableno) {
    Order tablesorder = new Order();
    for (Order o : getOrdersFromDatabase()) {
      if (o.getTableId() == tableno) {
        tablesorder = o;
      }
    }
    singleOrder = tablesorder;
    return tablesorder;
  }

  /**
   * Returns a single order based on the orders id
   * 
   * @param id The Orders id
   * @return Returns order with selected id from server
   */
  public Order getOrder(long id) {
    System.out.println("Printed id: " + id);
    currResponse = sercli.httpGet("http://shouganai.net:8080/restaurant/rest/orders/" + id);
    singleOrder = (Order) sercli.convertXML(currResponse, Order.class);
    return singleOrder;
  }

  /**
   * Cancels order by updating OrderStage and sending HTTP put request to server
   * 
   * @param id The id of order to be cancelled
   */
  public void cancelOrder(Order o) {
    singleOrder = o;
    singleOrder.setOrderStage(0);
    singleOrder.setWaiterId(0);
    sercli.httpPut("http://shouganai.net:8080/restaurant/rest/orders/" + singleOrder.getId(),singleOrder.getXML());
  }


  /**
   * Confirms order by updating OrderStage and sending HTTP put request to
   * server
   * 
   * @param id
   *          The id of order to be cancelled
   */
  public void confirmOrder(Order o) {
    singleOrder = o;
    singleOrder.setOrderStage(2);
    singleOrder.setWaiterId(id);
    sercli.httpPut(
        "http://shouganai.net:8080/restaurant/rest/orders/" + o.getId(),
        singleOrder.getXML());
  }

  /**
   * A Method which should be used only for testing purposes.It allows the waiter to manually 
   * configure attributes of an order directly on the server without having to use different interfaces.
   * 
   * @param o The id of order to be modified
   */
  public void resetOrder(Order o) {
    singleOrder = o;
    singleOrder.setOrderStage(4); // configure manual setting here
    singleOrder.setWaiterId(7);
    singleOrder.setTotalPaid(0.0);
    sercli.httpPut("http://shouganai.net:8080/restaurant/rest/orders/" + singleOrder.getId(),
        singleOrder.getXML());
  }

  /**
   *  updates OrderStage by sending HTTP put request to server marking orderstage as 4-'delivered'
   * @param o The order to be marked as delivered 
   */
  public void deliverOrder(Order o) {
    System.out.println(o.getId());
	singleOrder=o;
    singleOrder.setOrderStage(5);
    sercli.httpPut("http://shouganai.net:8080/restaurant/rest/orders/" + singleOrder.getId(),
        singleOrder.getXML());
  }
  /**
   * Gets the waiters id.
   * @return The id of waiter
   */
  public long getid() {
    return this.id;
  }

  /**
   * Returns a list of all the waiters assigned tables
   * @return List<Integer> List of all assigned tables
   */
  public List<Integer> getWaitersTables() {
    return this.tablesAssigned;
  }

  /**
   * Returns the name of the waiter
   * @return String Name of waiter
   */
  public String getName() {
    return this.name;
  }

 /**
  * Sets a waiters table by adding the table id to tablesAssigned array list and setting the orders waiterid as this
  * waiter 
  * @param tableId The table id to be assigned to the waiter
  */
  public void setWaitersTable(int tableId) {
    Order o = getTableOrder(tableId);
    singleOrder.setWaiterId(this.id);
    this.tablesAssigned.add(tableId);
    sercli.httpPut("http://shouganai.net:8080/restaurant/rest/orders/" + o.getId(),
    		singleOrder.getXML());
  }
}
