/**
 * The class Ingredient is built as a model of how the Ingredient class looks like. It is almost identical to
 * the Ingredient class in the client.
 */

package project.groupx.restaurant.model;

/**
 * @author Johannes Herforth.
 */

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Ingredient {
  private long id;
  private String name;
  private long quantity;
  private String expiryDate;
  private Date currD;
  private String allergy;
  private Double price;
  
  /**
   * Empty Constructor.
   */
  public Ingredient(){

  }
  
  /**
   * Constructor to create new Ingredients.
   * @param id 
   * @param name
   * @param quantity
   */
  public Ingredient(long id, String name, long quantity) {
    currD = new Date();
    this.id = id;
    this.name = name;
    this.quantity = quantity;
    this.expiryDate = currD.toString();
  }
  
  /**
   * Constructor to create new Ingredients. This constructor is only used when retrieving
   * items from the database.
   * @param id id of the Ingredient.
   * @param name Name of the Ingredient.
   * @param quantity Quantity of the Ingredient.
   * @param expiryDate Expiry date for the Ingredient.
   */
  //for use only in getters
  public Ingredient(long id, String name, long quantity, String expiryDate) {
    this.id = id;
    this.name = name;
    this.quantity = quantity;
    this.expiryDate = expiryDate;  
  }
  
  /**
   * get the ID of the Ingredient.
   * @return id
   */
  public long getId() {
    return id;
  }

  /**
   * set the ID of the Ingredient.
   * @param id id of long format.
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * get the name of the Ingredient.
   * @return name
   */
  public String getName() {
    return name;
  }
  
  /**
   * set the name of the Ingredient.
   * @param name name of String format.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * get the Quantity of the Ingredient.
   * @return quantity 
   */
  public long getQuantity() {
    return quantity;
  }

  /**
   * set the quantity of the Ingredient.
   * @param quantity quantity of long format.
   */
  public void setQuantity(long quantity) {
    this.quantity = quantity;
  }

  /**
   * get the expiry date of the Ingredient.
   * @return expiry date.
   */
  public String getExpiryDate() {
    return expiryDate;
  }

  /**
   * set the expiry date of the Ingredient.
   * @param expiryDate expiry date of String format.
   */
  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }
  
  /**
   * get the allergies of the Ingredient.
   * @return allergies in String format.
   */
  public String getAllergy() {
    return allergy;
  }
  /**
   * set the allergies of the Ingredient.
   * @param allergy that the Ingredient has.
   */
  public void setAllergy(String allergy) {
    this.allergy = allergy;
  }

  /**
   * gets the current price of the Ingredient
   * @return price 
   */
  public Double getPrice() {
    return price;
  }
  /**
   * sets the price of the ingredient
   * @param price price to set to
   */
  public void setPrice(Double price) {
    this.price = price;
  }
  
}
