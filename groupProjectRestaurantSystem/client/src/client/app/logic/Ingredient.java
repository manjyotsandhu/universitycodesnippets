package client.app.logic;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import client.server.connection.ServerClient;

/**
 * 
 * @author amrit
 *
 */
@XmlRootElement
public class Ingredient {

  private int id;
  private String allergy;
  private int expiryDate;
  private String name;
  private int quantity;
  private double price;
  private boolean containsMeat;
  private int calories;
  private static ServerClient scli = new ServerClient();

  /**
   * empty constructor
   */
  public Ingredient() {
  };

  /**
   * constructor with all the needed variables
   * 
   * @param id
   *          of ingredient
   * @param name
   *          of ingredient
   * @param containsMeat
   *          - boolean whether its vegetarian
   * @param calories
   *          - int
   * @param allergy
   *          - allergy info in string
   * @param expiryDate
   *          - int
   * @param cost
   *          - cost of 1 stock of ingredient.
   */
  public Ingredient(int id, String name, boolean containsMeat, int calories, String allergy, int expiryDate,
      double cost) {
    this.id = id;
    this.name = name;
    this.containsMeat = containsMeat;
    this.calories = calories;
    this.allergy = allergy;
    this.expiryDate = expiryDate;
    this.price = cost;
  }

  /**
   * gets id of ingredient
   * 
   * @return int
   */
  public int getId() {
    return this.id;
  }

  /**
   * returns allergy info
   * 
   * @return String
   */
  public String getAllergy() {
    return this.allergy;
  }

  /**
   * returns expiry date
   * 
   * @return int
   */
  public int getExpiryDate() {
    return this.expiryDate;
  }

  /**
   * returns name of ingredient
   * 
   * @return string
   */
  public String getName() {
    return this.name;
  }

  /**
   * returns quantity of ingredient
   * 
   * @return int
   */
  public int getQuantity() {
    return this.quantity;
  }

  /**
   * returns cost of ingredient
   * 
   * @return double
   */
  public double getPrice() {
    return this.price;
  }

  /**
   * return true if its vegetarian
   * 
   * @return boolean
   */
  public boolean isVegetarian() {
    return this.containsMeat;
  }

  /**
   * sets cost
   * 
   * @param cost
   */
  public void setPrice(double cost) {
    this.price = cost;
  }

  /**
   * add to the quantity
   * 
   * @param quantity
   */
  public void addQuantity(int quantity) {
    this.quantity += quantity;
  }

  /**
   * removes from the quantity
   * 
   * @param quantity
   */
  public void removeQuantity(int quantity) {
    this.quantity -= quantity;
  }

  /**
   * gets calories
   * 
   * @return calories
   */
  public int getCalories() {
    return this.calories;
  }

  /**
   * gets whether if it has meat
   * 
   * @return boolean
   */
  public boolean isContainsMeat() {
    return containsMeat;
  }

  /**
   * set to vegetarian or not
   * 
   * @param containsMeat
   */
  public void setContainsMeat(boolean containsMeat) {
    this.containsMeat = containsMeat;
  }

  /**
   * sets id
   * 
   * @param id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * sets allergy information
   * 
   * @param allergy
   */
  public void setAllergy(String allergy) {
    this.allergy = allergy;
  }

  /**
   * set expiry date
   * 
   * @param expiryDate
   */
  public void setExpiryDate(int expiryDate) {
    this.expiryDate = expiryDate;
  }

  /**
   * set name of ingredient
   * 
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * set the quantity
   * 
   * @param quantity
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * set calories.
   * 
   * @param calories
   */
  public void setCalories(int calories) {
    this.calories = calories;
  }

  /**
   * get XML for putting on database
   * 
   * @return stringbuffer contain xml.
   */
  public StringBuffer getXML() {
    StringBuffer answer = new StringBuffer();
    answer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> \n");
    answer.append("<ingredient>\n");
    answer.append("<id>" + this.getId() + "</id> \n");
    answer.append("<name>" + this.getName() + "</name> \n");
    answer.append("<expiryDate>" + this.getExpiryDate() + "</expiryDate> \n");
    answer.append("<quantity>" + this.getQuantity() + "</quantity> \n");
    answer.append("<allergy>" + this.getAllergy() + "</allergy> \n");
    answer.append("</ingredient> \n");

    return answer;
  }

  /**
   * gets all stock from server
   * 
   * @return list of ingredients
   */
  public static List<Ingredient> getAllStock() {
    Ingredients multiStock = new Ingredients();
    StringBuffer fromServer = new StringBuffer();

    fromServer = scli.httpGet("http://shouganai.net:8080/restaurant/rest/ingredients");
    multiStock = (Ingredients) scli.convertXML(fromServer, Ingredients.class);

    return multiStock.getIngredients();
  }

  /**
   * gets stock from database
   * 
   * @param item
   *          you want
   * @return Ingredient
   */
  public Ingredient getStock(int item) {
    StringBuffer fromServer = new StringBuffer();
    fromServer = scli.httpGet("http://shouganai.net:8080/restaurant/rest/ingredients/" + item);

    return (Ingredient) scli.convertXML(fromServer, Ingredient.class);
  }

  /**
   * adds to stock on db
   */
  public void addToStock() {
    int answer = scli.httpPost("http://shouganai.net:8080/restaurant/rest/ingredients", this.getXML());
    if (answer != 200) {
      System.out.println("ERROR " + answer + ": item was not added");
    } else {
      System.out.println("SUCCESS");
    }
  }

  /**
   * update the stock
   */
  public void updateStock() {
    int answer = scli.httpPut("http://shouganai.net:8080/restaurant/rest/ingredients", this.getXML());
    if (answer != 200) {
      System.out.println("ERROR " + answer + ": item was not added");
    } else {
      System.out.println("SUCCESS");
    }
  }

  /**
   * convert a string of ingredient IDs into an arraylist of the actual objects.
   * This is used to convert the string that is stored in the database into
   * actual java Ingredient objects so they can be edited.
   * 
   * @param ingrStr
   * @return
   */
  public List<Ingredient> stringToList(String ingrString) {
    String[] ingrids = new String[50];
    List<Ingredient> ingrList = new ArrayList<>();
    ingrids = ingrString.split(",");
    int arraylength = ingrids.length;
    StringBuffer ingrResponse = new StringBuffer();

    for (int i = 0; i < arraylength; i++) {
      if (ingrids[i].equals("")) {
        return ingrList;
      }
      ingrResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/ingredients/" + ingrids[i].trim());

      ingrList.add((Ingredient) scli.convertXML(ingrResponse, Ingredient.class));

    }

    return ingrList;
  }

}