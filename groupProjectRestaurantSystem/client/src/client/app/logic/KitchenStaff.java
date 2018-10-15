package client.app.logic;

import java.util.ArrayList;
import java.util.List;

import client.server.connection.ServerClient;


/**
 * @author Ghadah.
 *
 */
public class KitchenStaff extends Account {


  // Database variables
  private ServerClient scli = new ServerClient();
  private StringBuffer currResponse;
  private Orders allDatabaseOrders = new Orders();
  private Order order = new Order();

  public KitchenStaff() {


  }

  /**
   * Returns a List of orders retrieved from Database.
   */
  public List<Order> getOrdersFromDatabase() {

    // retrieving orders list from database
    currResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/orders/");
    allDatabaseOrders = (Orders) scli.convertXML(currResponse, Orders.class);

    return allDatabaseOrders.getOrders();
  }

  /**
   * prints the status of a single order.
   * 
   * @param orderOb.
   * @return String of order id followed by its stage.
   */

  public String checkOrderStatus(Order orderOb) {
    return orderOb.getId() + " " + orderOb.getOrderStage();
  }

  /**
   * checks the status of all the allOrders in the ArrayList, and prints orderNumber along with its
   * current status.
   * 
   * @param orders list of orders to check.
   * @return list of order stages.
   */

  public List<String> checkAllallOrders(List<Order> orders) {
    List<String> orderlist = new ArrayList<String>();
    if (orders.isEmpty()) {
      return orderlist;
    } else {
      for (Order or : orders) {
        orderlist.add(or.getOrderStage() + "");
        return orderlist;
      }
    }
    return orderlist;
  }

  /**
   * Returns the order for the passed id from Database.
   * 
   * @param id of order.
   * @return actual Order from Database
   */

  public Order getOrder(long id) {
    currResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/orders/" + id);
    order = (Order) scli.convertXML(currResponse, Order.class);
    return order;
  }

  /**
   * changes the status of a given Order to stage 3.
   * 
   * @param id of order to change stage.
   */
  public void startOrder(long id) {
    order.setOrderStage(3);
    scli.httpPut("http://shouganai.net:8080/restaurant/rest/orders/" + id, order.getXML());
  }

  /**
   * changes the status of a given Order to stage 4 to state that its been cooked.
   * 
   * @param id of order to change stage.
   */
  public void MarkAsCooked(long id) {
    order.setOrderStage(4);
    scli.httpPut("http://shouganai.net:8080/restaurant/rest/orders/" + id, order.getXML());
  }

}
