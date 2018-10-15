/**
 * The class OrderService is all the business value for the Order data structure. The class contains
 * all the methods that are called from the resource. The methods all work with updating or
 * receiving information about Order from the database.
 */
package project.groupx.restaurant.services;

/**
 * @author Johannes Herforth.
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import project.groupx.restaurant.model.Ingredient;
import project.groupx.restaurant.model.MenuItem;
import project.groupx.restaurant.model.Order;
import project.groupx.restaurant.database.DatabaseClient;
import project.groupx.restaurant.exceptions.DBEntryNotFound;

public class OrderService {

  private List<Order> orders;
  private Order currOrder;
  private long longID;
  private ResultSet rs;
  private Connection conn;
  private MenuItemService mser = new MenuItemService();
  private IngredientService ing = new IngredientService();

  /**
   * gets a list of all the Orders that are stored on the database.
   * 
   * @return list of Orders on the database.
   */
  public List<Order> getOrders() {
    orders = new ArrayList<>();
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "SELECT * FROM orders;");

    try {
      while (rs.next()) {
        currOrder = new Order();
        currOrder.setId(rs.getLong(1));
        currOrder.setTableId(rs.getInt(2));
        currOrder.setTimeOfOrder(rs.getLong(3));
        currOrder.setMenuItemList(rs.getString(4));
        currOrder.setTotalCost(rs.getDouble(5));
        currOrder.setTotalPaid(rs.getDouble(6));
        currOrder.setOrderStage(rs.getInt(7));
        currOrder.setWaiterId(rs.getInt(8));

        orders.add(currOrder);
      }
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return orders;

  }

  /**
   * Takes an id to then retrieve the specific Order from the database.
   * 
   * @param id specific id of Order.
   * @return specific Order from database.
   */
  public Order getOrder(long id) {
    currOrder = new Order();
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "SELECT * FROM orders WHERE id=" + id + ";");

    try {
      while (rs.next()) {
        currOrder.setId(rs.getLong(1));
        currOrder.setTableId(rs.getInt(2));
        currOrder.setTimeOfOrder(rs.getLong(3));
        currOrder.setMenuItemList(rs.getString(4));
        currOrder.setTotalCost(rs.getDouble(5));
        currOrder.setTotalPaid(rs.getDouble(6));
        currOrder.setOrderStage(rs.getInt(7));
        currOrder.setWaiterId(rs.getInt(8));
      }
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return currOrder;
  }

  /**
   * gets the highest ID of order from database to safely add a new Order with a new ID.
   * 
   * @return next open id to be used.
   */
  public long getNextID() {
    longID = 0L;
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "SELECT id FROM orders ORDER BY id DESC LIMIT 1;");

    try {
      
      rs.next();
      longID = rs.getLong(1);
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return longID + 1;
  }

  /**
   * adds a given Order to the database without overwriting any other database object by always
   * generating an open ID.
   * 
   * @param addOrder Order to add to the Database.
   * @return returns the given Order to show success.
   * @throws DBEntryNotFound if Item not found, throw exception
   */
  public Order addOrder(Order addOrder) throws DBEntryNotFound {
    addOrder.setId(this.getNextID());
    List<MenuItem> toAdd = new ArrayList<>();
    toAdd = this.stringToMenuItem(addOrder.getMenuItemList());
    
    for(MenuItem item : toAdd) { //for each menuitem
      for(Long i : item.stringToList()) { //order each ingredient once
        ing.orderIngredient(i);
        Ingredient currIngr = ing.getIngredient(i);
        if(currIngr.getQuantity() == 0) { //if ingredient quantity hits 0, remove item from availability
          item.setAvailable(false);
          mser.alterItem(item);
        }
      }
    }
    
    //System.out.println(addOrder.getMenuItemList());
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    DatabaseClient.executeQuery(conn,
        "INSERT INTO orders "
            + "(id,tableid,timeoforder,menuitemlist,totalcost,totalpaid,orderstage,waiterid) VALUES ("
            + addOrder.getId() + "," + addOrder.getTableId() + "," + addOrder.getTimeOfOrder()
            + ",'" + addOrder.getMenuItemList() + "', " + addOrder.getTotalCost() + ", "
            + addOrder.getTotalPaid() + ", " + addOrder.getOrderStage() + ", "
            + addOrder.getWaiterId() + ");");

    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return addOrder;
  }

  /**
   * removes the Order with the specific id from the database.
   * 
   * @param id id of the Order to delete.
   */
  public void removeOrder(long id) {
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    DatabaseClient.executeQuery(conn, "DELETE FROM orders WHERE id=" + id + ";");

    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * updates the order for new/updated items
   * @param uOrder order with new and updated items
   * @return successful new order
   * @throws DBEntryNotFound if id not found, throw exception
   */
  public Order updateOrder(Order uOrder) throws DBEntryNotFound {
    Order dbOrder = getOrder(uOrder.getId());

    if (dbOrder.getId() == 0) {
      throw new DBEntryNotFound("ERROR: Order ID not found");
    }

    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn,
        "UPDATE orders set tableid=" + uOrder.getTableId() + ", menuitemlist = '"
            + uOrder.getMenuItemList() + "', totalcost = " + uOrder.getTotalCost()
            + ", totalpaid = " + uOrder.getTotalPaid() + ", orderstage = " + uOrder.getOrderStage()
            + ", waiterid = " + uOrder.getWaiterId() + " WHERE id=" + uOrder.getId() + ";");

    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return uOrder;
  }
  
  /**
   * convert a string of menuitem IDs into an arraylist of the actual objects.
   * This is used to convert the string that is stored in the database into
   * actual java menuitem objects.
   * 
   * @param ingrString string of ingredient IDs
   * @return cost of all ingredients
   */
  private List<MenuItem> stringToMenuItem(String ingrString) {
    String[] ingrids = new String[50];
    List<MenuItem> mitemList = new ArrayList<>();
    ingrids = ingrString.split(",");
    int arraylength = ingrids.length;

    for (int i = 0; i < arraylength; i++) {
      if (ingrids[i].equals("")) {
      }
      mitemList.add(mser.getMenuItem(Long.parseLong(ingrids[i].trim())));
      
    }

    return mitemList;
  }
}
