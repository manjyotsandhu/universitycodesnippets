package client.app.logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Arrays;

import javax.xml.bind.annotation.XmlRootElement;

import client.server.connection.ServerClient;

/***********************************************************************************************************
 * Order Class is used to represent an instance of an order matching the attributes stored 
 * in the database and server.                         
 * Methods contained in the class are mainly used to place,gather and parse orders and there attributes.            
 * This class also relies on the server client with HTTP post commands.                                               
 *                                                                                                 
 * @author Manjyot,Youcef,johannes                                                                        
 ************************************************************************************************************
 */
@XmlRootElement
public class Order {

  private long id;
  private int tableId;
  private long waiterId;
  private long timeOfOrder;
  private List<MenuItem> order = new ArrayList<MenuItem>();
  private boolean isConfirmed;
  private double totalPaid;
  private int orderStage;
  private double totalCost;
  private String menuItemList;

  private static ServerClient scli = new ServerClient();

  /*
   * NOTE ABOUT STAGES: 
   * 0 - Cancelled
   * 1 - Placed Unconfirmed 
   * 2 - Waiter approved 
   * 3- Cooking 
   * 4 - Delivered 
   * 5 - Delivered unpaid
   * 6- Delivered Paid
   */

  /**
   * Empty constructor, to be used in Orders class
   */
  public Order() {
    this.waiterId = 0;
    this.tableId = 0;
    this.orderStage = 1;
    this.menuItemList = "";
    this.timeOfOrder = System.currentTimeMillis();
    this.totalPaid = 0;
  }

  /**
   * Constructor creates an order with a single Menu Item.
   */
  public Order(MenuItem item, long waiterId, int tableId) {
    getOrder().add(item);
    this.waiterId = waiterId;
    this.tableId = tableId;
    this.totalPaid = 0;
    this.timeOfOrder = System.currentTimeMillis();
    this.isConfirmed = false;
    this.id = 0;
    this.orderStage = 1;
    this.totalCost = calculateOrderTotal();

  }

  /**
   * Constructor creates an order list for multiple Menu Items.
   */
  public Order(ArrayList<MenuItem> items, long waiterId, int tableId) {
    for (MenuItem item : items) {
      this.order.add(item);
    }
    this.waiterId = waiterId;
    this.tableId = tableId;
    this.totalPaid = 0;
    this.timeOfOrder = new Date().getTime();
    this.timeOfOrder = System.currentTimeMillis();
    this.isConfirmed = false;
    this.orderStage = 1;
    this.totalCost = calculateOrderTotal();
  }

  /**
   * Adds a menu item to an order
   * @param id Id of the menu item to add
   * @param cost Cost of the menu item to add
   */
  public void addMenuItem(long id, double cost) {
    this.menuItemList = id + "," + menuItemList;
    this.totalCost = totalCost + cost;
  }

  /**
   * Gets the list of menu items in the order
   * @return String List of menu items
   */
  public String getMenuItemList() {
    return menuItemList;
  }

  /**
   * Sets the string list of menu items
   * @param list List of menu items
   */
  public void setMenuItemList(String list) {
    this.menuItemList=list;
  }

  /**
   * Returns the ID of the order
   * @return long The order ID
   */
  public long getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * @return the tableId
   */
  public int getTableId() {
    return tableId;
  }

  /**
   * @param tableId
   *          the tableId to set
   */
  public void setTableId(int tableId) {
    this.tableId = tableId;
  }

  /**
   * @return the waiterId
   */
  public long getWaiterId() {
    return waiterId;
  }

  /**
   * @param waiterId
   *          the waiterId to set
   */
  public void setWaiterId(long waiterId) {
    this.waiterId = waiterId;
  }

  /**
   * @return the order
   */
  public List<MenuItem> getOrder() {
    return order;
  }

  /**
   * @param order
   *          the order to set
   */
  public void setOrder(ArrayList<MenuItem> order) {
    this.order = order;
  }

  /**
   * @return the isConfirmed
   */
  public boolean isConfirmed() {
    return isConfirmed;
  }

  /**
   * @param isConfirmed
   *          the isConfirmed to set
   */
  public void setConfirmed(boolean isConfirmed) {
    this.isConfirmed = isConfirmed;
  }

  /**
   * @return the totalPaid
   */
  public double getTotalPaid() {
    return totalPaid;
  }

  /**
   * @param totalPaid
   *          the totalPaid to set
   */
  public void setTotalPaid(double totalPaid) {
    this.totalPaid = totalPaid;
  }

  /**
   * @return the orderStage
   */
  public int getOrderStage() {
    return orderStage;
  }

  /**
   * @param orderStage
   *          the orderStage to set
   */
  public void setOrderStage(int orderStage) {
    this.orderStage = orderStage;
  }

  /**
   * @return the totalCost
   */
  public double getTotalCost() {
    return totalCost;
  }

  /**
   * @param totalCost
   *          the totalCost to set
   */
  public void setTotalCost(double totalCost) {
    this.totalCost = totalCost;
  }

  /**
   * Calculates the total price of an order
   * 
   * @return the total price of all Menu Items in each order.
   */
  public double calculateOrderTotal() {
    double total = 0;
    for (int i = 0; i < getOrder().size(); i++) {
      total += getOrder().get(i).getPrice();
    }
    return total;
  }

  /**
   * Adds a new Menu Item to the order
   * 
   * @param item
   *          The menu item to add
   */
  public void addToOrder(MenuItem item) {
    order.add(item);

  }

  /**
   * Returns the time of order
   * @return long The time the order was placed
   */
  public long getTimeOfOrder() {
    return timeOfOrder;
  }

  /**
   * Sets the time of the order
   * @param timeOfOrder The new time of order
   */
  public void setTimeOfOrder(long timeOfOrder) {
    this.timeOfOrder = timeOfOrder;
  }
  
   /**
   * Builds a string of menu items by connecting to the server and finding the corresponding string value
   * of each of the menu item id's
   * @param str a string formatted of menu item id's e.g. 1,2,3
   * @return The string value of the id's e.g 1=burger 2=chips, Burger chips is returned.
   */
  public String getMenuItemsString(String str) {
	  Menu m = new Menu();
	  MenuItem mi= new MenuItem();
	  String itemsNames="";
	  List<String> items = Arrays.asList(str.split("\\s*,\\s*"));
	  for (String temp:items){
	    try{
	    mi= m.getMenuItem(Long.valueOf(temp.trim()));
	    itemsNames=itemsNames+" "+mi.getName();
	    }catch(NumberFormatException e) {
	    	//This exception will occur if the string cannot be formatted
	    	//therefore nothing will be displayed.
	     }
	  }
    return itemsNames; 
  }
  
  /**
   * Gets the time of order in a human readable format.
   * @return string of time
   */
  public String getTOA() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM HH:mm:ss");
    Date returndate = new Date(this.getTimeOfOrder());
    return dateFormat.format(returndate);
  }

  /**
   * Converts fields into a XML format for the server
   * @return StringBuffer Contains XML format of the order instance
   */
  public StringBuffer getXML() {
    StringBuffer answer = new StringBuffer();
    answer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> \n");
    answer.append("<order>\n");
    answer.append("<id>" + this.id + "</id> \n");
    answer.append("<menuItemList>" + this.menuItemList + "</menuItemList> \n");
    answer.append("<orderStage>" + this.orderStage + "</orderStage> \n");
    answer.append("<tableId>" + this.tableId + "</tableId> \n");
    answer.append("<timeOfOrder>" + this.timeOfOrder + "</timeOfOrder> \n");
    answer.append("<totalCost>" + this.totalCost + "</totalCost> \n");
    answer.append("<totalPaid>" + this.totalPaid + "</totalPaid> \n");
    answer.append("<waiterId>" + this.waiterId + "</waiterId> \n");
    answer.append("</order> \n");

    return answer;
  }

  /**
   * Posts the current order XML to the database
   * @return int Response code returned to represent failure or success
   */
  public StringBuffer makeOrder() {
    return scli.httpPostSB("http://shouganai.net:8080/restaurant/rest/orders", this.getXML());
  }
  
  /**
   * Gets all of the orders from the database
   * @return List<Order> List of orders from database
   */
  public static List<Order> getAllOrders() {
    Orders allOrders = new Orders(); 
    StringBuffer currResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/orders");
    allOrders = (Orders) scli.convertXML(currResponse, Orders.class); 
    return allOrders.getOrders();
  }

  /**
   * refreshes the order of the current order (uses the same ID)
   * @return the new refreshed Order.
   */
  public Order refreshOrder() {
	    Order allOrders = new Order(); 
	    StringBuffer currResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/orders/" + this.getId());
	    allOrders = (Order) scli.convertXML(currResponse, Order.class); 
	    System.out.println(allOrders.getId() + " : " + this.getId());
	    return allOrders;
	  }
  
  /**
   * Removes a Menu Item from the order
   * 
   * @param item
   *          The menu item to remove
   */
  public void removeFromOrder(MenuItem item) {
    order.remove(item);
  }

}
