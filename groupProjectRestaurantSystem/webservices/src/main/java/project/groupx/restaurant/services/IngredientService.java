/**
 * The class IngredientService is all the business value for the Ingredient data structure. The
 * class contains all the methods that are called from the resource. The methods all work with
 * updating or receiving information about Ingredient from the database.
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
import project.groupx.restaurant.database.DatabaseClient;
import project.groupx.restaurant.exceptions.DBEntryNotFound;

public class IngredientService {

  private List<Ingredient> ingrL;
  private Ingredient ingr;
  private long longID;
  private ResultSet rs;
  private Connection conn;

  /**
   * gets a list of all the ingredients that are stored on the database.
   * 
   * @return list of Ingredients on the database.
   */
  public List<Ingredient> getIngredients() {
    ingrL = new ArrayList<>();
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "SELECT * FROM ingredients;");

    try {
      while (rs.next()) {
        ingr = new Ingredient();
        ingr.setId(rs.getLong(1));
        ingr.setName(rs.getString(2));
        ingr.setQuantity(rs.getLong(3));
        ingr.setExpiryDate(rs.getString(4));
        ingr.setAllergy(rs.getString(5));
        ingr.setPrice(rs.getDouble(6));
        ingrL.add(ingr);
      }
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ingrL;

  }

  /**
   * Takes an id to then retrieve the specific Ingredient from the database.
   * 
   * @param id specific id of Ingredient.
   * @return specific Ingredient from database.
   */
  public Ingredient getIngredient(long id) {
    ingr = new Ingredient();
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "SELECT * FROM ingredients WHERE id=" + id + ";");

    try {
      while (rs.next()) {
        ingr.setId(rs.getLong(1));
        ingr.setName(rs.getString(2));
        ingr.setQuantity(rs.getLong(3));
        ingr.setExpiryDate(rs.getString(4));
        ingr.setAllergy(rs.getString(5));
        ingr.setPrice(rs.getDouble(6));
      }
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ingr;
  }

  /**
   * gets the highest ID of ingredient from database to safely add a new Ingredient with a new ID.
   * 
   * @return next open id to be used.
   */
  public long getNextID() {
    longID = 0L;
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "SELECT id FROM ingredients ORDER BY id DESC LIMIT 1;");

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
   * adds a given Ingredient to the database without overwriting any other database object by always
   * generating an open ID.
   * 
   * @param addIngre Ingredient to add to the Database.
   * @return returns the given Ingredient to show success.
   */
  public Ingredient addIngredient(Ingredient addIngre) {
    addIngre.setId(this.getNextID());
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    DatabaseClient.executeQuery(conn,
        "INSERT INTO ingredients (id,name,quantity,expirydate,allergy,price) " + "VALUES ("
            + addIngre.getId() + ",'" + addIngre.getName() + "'," + addIngre.getQuantity() + ",'"
            + addIngre.getExpiryDate() + "','" + addIngre.getAllergy() + "'," + addIngre.getPrice() + ");");
    
    
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return addIngre;
  }

  /**
   * removes the Ingredient with the specific id from the database.
   * 
   * @param id id of the Ingredient to delete.
   */
  public void removeIngredient(long id) {
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    DatabaseClient.executeQuery(conn, "DELETE FROM ingredients WHERE id=" + id + ";");

    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
  
  /**
   * updates the given Ingredient with the new variables sent over XML by
   * the client.
   * 
   * @param ingre ingredient to update to
   * @return updated ingredient
   * @throws DBEntryNotFound if ID not found, throw error
   */
  public Ingredient updateIngredient(Ingredient ingre) throws DBEntryNotFound {
    Ingredient currIngr = getIngredient(ingre.getId());
    conn = DatabaseClient.getInstance().getConnection();
    if(currIngr.getId() == 0) {
      throw new DBEntryNotFound("ERROR: Ingredient ID not found");
    }
    
    rs = DatabaseClient.executeQuery(conn, 
        "UPDATE ingredients SET name='" + ingre.getName() 
        + "', quantity =" + ingre.getQuantity() + ", expirydate=" 
            + ingre.getExpiryDate() + ", allergy='" + ingre.getAllergy() + "', price=" + ingre.getPrice() + " WHERE id =" + currIngr.getId() + ";");
    
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return ingre;
  }
  
  /**
   * updates the given Ingredient if the menuitem is ordered with the ingredient, takes off one portion.
   * @param id id to subtract one from
   * @throws DBEntryNotFound id ingredient not found, throw error
   */
  public void orderIngredient(long id) throws DBEntryNotFound {
    Ingredient currIngr = getIngredient(id);
    conn = DatabaseClient.getInstance().getConnection();
    if(currIngr.getId() == 0) {
      throw new DBEntryNotFound("ERROR: Ingredient ID not found");
    }

    rs = DatabaseClient.executeQuery(conn, 
        "UPDATE ingredients SET quantity =" + (currIngr.getQuantity() - 1) + " WHERE id =" + currIngr.getId() + ";");
    
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
