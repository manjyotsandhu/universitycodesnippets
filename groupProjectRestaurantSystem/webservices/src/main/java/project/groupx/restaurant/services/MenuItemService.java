/**
 * The class MenuItemService is all the business value for the MenuItem data structure. The class
 * contains all the methods that are called from the resource. The methods all work with updating or
 * receiving information about MenuItem from the database.
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
import project.groupx.restaurant.database.DatabaseClient;

public class MenuItemService {

  private List<MenuItem> mitems;
  private MenuItem mItem;
  private long longID;
  private ResultSet rs;
  private Connection conn;
  private IngredientService ingrser = new IngredientService();

  /**
   * gets a list of all the MenuItems that are stored on the database.
   * 
   * @return list of MenuItems on the database.
   */
  public List<MenuItem> getMenuItems() {
    mitems = new ArrayList<>();
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "SELECT * FROM menuitem ORDER BY id ASC ;");
    // added ORDER BY
    try {
      while (rs.next()) {
        mItem = new MenuItem();
        mItem.setId(rs.getLong(1));
        mItem.setName(rs.getString(2));
        mItem.setPrice(rs.getDouble(3));
        mItem.setIngredientsList(rs.getString(4));
        mItem.setCalories(rs.getInt(5));
        mItem.setVegeDish(rs.getBoolean(6));
        mItem.setHalal(rs.getBoolean(7));
        mItem.setKosher(rs.getBoolean(8));
        mItem.setAvailable(rs.getBoolean(9));
        mItem.setOnMenu(rs.getBoolean(10));
        mItem.setAllergy(rs.getString(11));
        mItem.setSpecialOffer(rs.getDouble(12));
        mItem.setPictureUrl(rs.getString(13));
        mItem.setType(rs.getInt(14));

        mitems.add(mItem);
      }
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return mitems;

  }

  /**
   * Takes an id to then retrieve the specific MenuItem from the database.
   * 
   * @param id specific id of MenuItem.
   * @return specific MenuItem from database.
   */
  public MenuItem getMenuItem(long id) {
    mItem = new MenuItem();
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "SELECT * FROM menuitem WHERE id=" + id + ";");

    try {
      while (rs.next()) {
        mItem.setId(rs.getLong(1));
        mItem.setName(rs.getString(2));
        mItem.setPrice(rs.getDouble(3));
        mItem.setIngredientsList(rs.getString(4));
        mItem.setCalories(rs.getInt(5));
        mItem.setVegeDish(rs.getBoolean(6));
        mItem.setHalal(rs.getBoolean(7));
        mItem.setKosher(rs.getBoolean(8));
        mItem.setAvailable(rs.getBoolean(9));
        mItem.setOnMenu(rs.getBoolean(10));
        mItem.setAllergy(rs.getString(11));
        mItem.setSpecialOffer(rs.getDouble(12));
        mItem.setPictureUrl(rs.getString(13));
        mItem.setType(rs.getInt(14));
      }
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return mItem;
  }

  /**
   * gets the highest ID of MenuItem from database to safely add a new MenuItem with a new ID.
   * 
   * @return next open id to be used.
   */
  public long getNextID() {
    longID = 0L;
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "SELECT id FROM menuitem ORDER BY id DESC LIMIT 1;");

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
   * adds a given Menu Item to the database without overwriting any other database object by always
   * generating an open ID.
   * 
   * @param addMItem MenuItem to add to the Database.
   * @return returns the given MenuItem to show success.
   */
  public MenuItem addMenuItem(MenuItem addMItem) {
    addMItem.setId(this.getNextID());
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    DatabaseClient.executeQuery(conn,
        "INSERT INTO menuitem (id,name,price,ingredientslist,calories,vegedish,"
            + "ishalal,iskosher,isavailable,isonmenu,allergy,specialoffer,picture,type) " + "VALUES ("
            + addMItem.getId() + ",'" + addMItem.getName() + "'," + addMItem.getPrice() + ",'"
            + addMItem.getIngredientsList() + "', " + addMItem.getCalories() + ", "
            + addMItem.isVegeDish() + ", " + addMItem.isHalal() + ", " + addMItem.isKosher() + ", "
            + addMItem.isAvailable() + ", " + addMItem.isOnMenu() + ", '" + addMItem.getAllergy()
            + "', " + addMItem.getSpecialOffer() + ", '" +  addMItem.getPictureUrl() + "', "+ addMItem.getType() + ");");

    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return addMItem;
  }

  /**
   * removes the Menu Item with the specific id from the database.
   * 
   * @param id id of the MenuItem to delete.
   */
  public void removeMenuItem(long id) {
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    DatabaseClient.executeQuery(conn, "DELETE FROM menuitem WHERE id=" + id + ";");

    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * method to get a sorted list based on a given attribute of the MenuItem (from lowest to
   * highest).
   * 
   * @param variable attribute that the list should be sorted by.
   * @return list of sorted MenuItems based on attribute.
   */
  public List<MenuItem> sortBy(String variable) {
    mitems = new ArrayList<>();
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "SELECT * FROM menuitem ORDER BY " + variable + ";");

    try {
      conn.close();
      while (rs.next()) {
        mItem = new MenuItem();
        mItem.setId(rs.getLong(1));
        mItem.setName(rs.getString(2));
        mItem.setPrice(rs.getDouble(3));
        mItem.setIngredientsList(rs.getString(4));
        mItem.setCalories(rs.getInt(5));
        mItem.setVegeDish(rs.getBoolean(6));
        mItem.setHalal(rs.getBoolean(7));
        mItem.setKosher(rs.getBoolean(8));
        mItem.setAvailable(rs.getBoolean(9));
        mItem.setOnMenu(rs.getBoolean(10));
        mItem.setAllergy(rs.getString(11));
        mItem.setSpecialOffer(rs.getDouble(12));
        mItem.setPictureUrl(rs.getString(13));
        mItem.setType(rs.getInt(14));

        mitems.add(mItem);
      }
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return mitems;

  }

  /**
   * method to get a list of MenuItems that are of a specific type (eg. isHalal, isOnMenu, isKosher
   * etc)
   * 
   * @param type type you want to check that exists.
   * @return MenuItem list that only contains the type.
   */
  public List<MenuItem> getItemType(String type) {
    mitems = new ArrayList<>();
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "SELECT * FROM menuitem WHERE " + type + "= true;");

    try {
      conn.close();
      while (rs.next()) {
        mItem = new MenuItem();
        mItem.setId(rs.getLong(1));
        mItem.setName(rs.getString(2));
        mItem.setPrice(rs.getDouble(3));
        mItem.setIngredientsList(rs.getString(4));
        mItem.setCalories(rs.getInt(5));
        mItem.setVegeDish(rs.getBoolean(6));
        mItem.setHalal(rs.getBoolean(7));
        mItem.setKosher(rs.getBoolean(8));
        mItem.setAvailable(rs.getBoolean(9));
        mItem.setOnMenu(rs.getBoolean(10));
        mItem.setAllergy(rs.getString(11));
        mItem.setSpecialOffer(rs.getDouble(12));
        mItem.setPictureUrl(rs.getString(13));
        mItem.setType(rs.getInt(14));

        mitems.add(mItem);
      }
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return mitems;
  }
  /**
   * edit the onMenu of a given menuitem by the ID. these changes are final
   * and cannot be undone to the database.
   * @param item new item details with the given ID
   * @return new item
   */
  public MenuItem alterToMenu(long item) {
    MenuItem currItem = getMenuItem(item);
    boolean onMenu = currItem.isOnMenu();

    if (onMenu == true) {
      currItem.setOnMenu(false);
    } else {
      currItem.setOnMenu(true);
    }


    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn,
        "UPDATE menuitem set isonmenu='" + onMenu + "' WHERE id =" + currItem.getId() + ";");

    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return currItem;
  }
  /**
   * changes the special offer multiplier to a new one
   * @param item menuitem to change the special offer price
   * @param discount new discount multiplier (0.6 = 40% off)
   * @return new item with special offer (if successful)
   */
  public MenuItem alterSpecialOffer(long item, double discount) {
    MenuItem currItem = getMenuItem(item);
    double ingrCost = this.costOfIngredients(currItem.getIngredientsList());
    System.out.println(currItem.getPrice() + " : " + discount + " : " + (ingrCost * 1.6));
    //check to see if at least 60% profit margin
    if((discount*currItem.getPrice()) < (ingrCost * 1.6)) {
      return new MenuItem();
    }

    currItem.setSpecialOffer(discount);
    

    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "UPDATE menuitem set specialoffer="
        + currItem.getSpecialOffer() + " WHERE id =" + currItem.getId() + ";");

    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return currItem;
  }

  /**
   * edit the values of a given menuitem by the ID. these changes are final
   * and cannot be undone to the database.
   * @param item new item details with the given ID
   * @return new item
   */
  public MenuItem alterItem(MenuItem item) {
    
    MenuItem sp = this.alterSpecialOffer(item.getId(), item.getSpecialOffer());
    double ingrCost = this.costOfIngredients(item.getIngredientsList());
    System.out.println(sp.getId() + " : " + (ingrCost * 1.6) + " : " + item.getPrice());
    if(sp.getId() == 0 | (ingrCost * 1.6) > item.getPrice() ) {
      return new MenuItem();
    }
    
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    DatabaseClient.executeQuery(conn,
        "UPDATE menuitem set name='" + item.getName() + "', price=" + item.getPrice()
            + ", ingredientslist='" + item.getIngredientsList() + "', isonmenu='" + item.isOnMenu() + "', picture='" + item.getPictureUrl()
            + "' WHERE id=" + item.getId() + ";");
    // UPDATE menuitem set name='...', price=..., ingredientslist='...', isonmenu='...' WHERE
    // id=item.getId();

    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return item;
  }
  
  
  /**
   * convert a string of ingredient IDs into an arraylist of the actual objects.
   * This is used to convert the string that is stored in the database into
   * actual java Ingredient objects so that the price can be added up of all
   * ingredients.
   * 
   * @param ingrString string of ingredient IDs
   * @return cost of all ingredients
   */
  private Double costOfIngredients(String ingrString) {
    String[] ingrids = new String[50];
    List<Ingredient> ingrList = new ArrayList<>();
    ingrids = ingrString.split(",");
    int arraylength = ingrids.length;
    double cost = 0.0;

    for (int i = 0; i < arraylength; i++) {
      if (ingrids[i].equals("")) {
      }
      ingrList.add(ingrser.getIngredient(Long.parseLong(ingrids[i].trim())));
      cost = cost + ingrList.get(i).getPrice();
    }

    return cost;
  }
  
  
}
