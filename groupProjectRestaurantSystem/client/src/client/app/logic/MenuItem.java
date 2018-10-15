package client.app.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import client.server.connection.ServerClient;

/**
 * 
 * Class to represent a menu item
 * 
 * @author manjyot zbvc854
 *
 */
@XmlRootElement
public class MenuItem {

  private long id;
  private String name;
  private double price;
  private int calories;
  private boolean available;
  private ArrayList<Ingredient> ingredientList;
  private String ingredientsList;
  private boolean VegeDish;
  private boolean isOnMenu;
  private String Allergy;
  private boolean isHalal;
  private boolean isKosher;
  private ServerClient scli = new ServerClient();
  private double specialOffer;
  private int specialofferdays;
  private String pictureUrl;
  private int type;

  /**
   * Empty constructor
   */
  public MenuItem() {
  };

  /**
   * Method to get all of the ingredients in a menu item
   * @return String List of ingredients
   */
  public String getIngredientsList() {
    return ingredientsList;
  }

  /**
   * Sets new ingredients for the menu item
   * @param ingredientsList String of new ingredients
   */
  public void setIngredientsList(String ingredientsList) {
    this.ingredientsList = ingredientsList;
  }

  /**
   * Constructor which assigns the relevant variables
   * @param id ID of the menu item
   * @param name Name of the menu item
   * @param price Price of the menu item
   * @param list List of ingredients in the menu item
   * @param type Type of the menu item
   */
  public MenuItem(int id, String name, double price, ArrayList<Ingredient> list,
      int type) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.calories = 0;
    this.available = true;
    this.ingredientList = list;
    this.VegeDish = true;
    this.isOnMenu = true;
    this.Allergy = "";
    this.isHalal = false;
    this.isKosher = false;
    this.specialOffer = 0;
    this.specialofferdays = 0;
    this.pictureUrl = "";
    this.type = type;
    updateDishStats();
  }
  
  /**
   * Iterates through the ingredients list and assigns the relevant allergies for the menu item
   */
  public void assignAllergy() {
    HashSet<String> allAllergy = new HashSet<String>();
    String returnAllergyInfo = " ";

    for (int i = 0; i < this.ingredientList.size(); i++) {
      allAllergy.add(this.ingredientList.get(i).getAllergy());
    }

    Iterator<String> itr = allAllergy.iterator();
    while (itr.hasNext()) {
      String input = itr.next();
      if (!itr.hasNext()) {
        returnAllergyInfo = returnAllergyInfo.concat(input);
      } else {
        returnAllergyInfo = returnAllergyInfo.concat(input + ", ");
      }
    }

    /**
     * for (String temp : allAllergy) { System.out.println(temp);
     * returnAllergyInfo = returnAllergyInfo.concat(temp + " / "); }
     **/

    setAllergy(returnAllergyInfo);
  }

  /**
   * Gets the allergy information for the menu item
   * @return String The allergy information
   */
  public String getAllergy() {
    return Allergy;
  }

  /**
   * Sets new allergy information for the menu item
   * @param Allergy The new allergy information
   */
  public void setAllergy(String Allergy) {
    this.Allergy = Allergy;
  }

  /**
   * @return boolean Returns if the menu item is on menu or not
   */
  public boolean isOnMenu() {
    return isOnMenu;
  }

  /**
   * @param isOnMenu
   *          Will be set as the new state for whether it is on menu or not
   */
  public void setOnMenu(boolean isOnMenu) {
    this.isOnMenu = isOnMenu;
  }

  /**
   * @return long Returns ID of menu item
   */
  public long getId() {
    return id;
  }

  /**
   * @param id
   *          Will be set as the new ID for the menu item
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * @return String Returns name of menu item
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   *          Will be set as the new name for menu item
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return double Returns price of the menu item
   */
  public double getPrice() {
    return price;
  }

  /**
   * @param price
   *          Will be set as the new price for menu item
   */
  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * @return int Returns calories of menu item
   */
  public int getCalories() {
    return calories;
  }

  /**
   * @param calories
   *          Will be set as the new calories value for menu item
   */
  public void setCalories(int calories) {
    this.calories = calories;
  }

  /**
   * @return boolean Returns boolean to represent if menu item is available or
   *         not
   */
  public boolean isAvailable() {
    return available;
  }

  /**
   * @param available
   *          Will represent the new availability of the dish
   */
  public void setAvailable(boolean available) {
    this.available = available;
  }

  /**
   * @param specialOffer
   *          true means there is an offer and price has changed.
   */
  public void setSpecialOffer(double specialOffer) {
    this.specialOffer = specialOffer;
  }

  /**
   * @return whether or not a dish has a special offer.
   */
  public double getSpecialOffer() {
    return specialOffer;
  }

  /**
   * @param number
   *          of days a special offer is valid for.
   */
  public void setSpecialOfferDays(int specialofferdays) {
    this.specialofferdays = specialofferdays;
  }

  /**
   * @return how many days the current special offer is valid for.
   */
  public int getSpecialOfferDays() {
    return specialofferdays;
  }

  /**
   * @return ArrayList<Ingredient> Returns list of ingredients
   */
  public ArrayList<Ingredient> getIngredientList() {
    return ingredientList;
  }

  /**
   * @param ingredientList
   *          Sets a new list, as the ingredients for menu item
   */
  public void setIngredientList(ArrayList<Ingredient> ingredientList) {
    this.ingredientList = ingredientList;
  }

  /**
   * @return boolean Returns if menu item is vegetarian or not
   */
  public boolean isVegeDish() {
    return VegeDish;
  }

  /**
   * @param vegeDish
   *          Will set if the menu item is vegetarian or not
   */
  public void setVegeDish(boolean vegeDish) {
    VegeDish = vegeDish;
  }

  public int getType() {
    return type;
  }

  /**
   * Sets the type of menu item. Types are: 0 = dessert, 1 = starter, 2 = main, 3 = drinks
   * @param type The new type for the menu item
   */
  public void setType(int type) {
    this.type = type;
  }

  /**
   * Returns if the dish is halal or not
   * @return boolean Returns halal status of menu item
   */
  public boolean isHalal() {
    return isHalal;
  }

  /**
   * Sets if the dish if halal or not
   * @param isHalal The new halal status of the menu item
   */
  public void setHalal(boolean isHalal) {
    this.isHalal = isHalal;
  }

  /**
   * Returns if the dish is kosher or not
   * @return boolean Returns kosher status of menu item
   */
  public boolean isKosher() {
    return isKosher;
  }

  /**
   * Sets if the dish is kosher or not
   * @param isKosher The new kosher status of the menu item
   */
  public void setKosher(boolean isKosher) {
    this.isKosher = isKosher;
  }
  
  

  /**
   * Will iterate over ingredients list to check the stock levels and vegetarian
   * status. According to these results, the relevant fields will be modified.
   */
  public void updateDishStats() {
    for (int i = 0; i < this.ingredientList.size(); i++) {
      if (this.ingredientList.get(i).getQuantity() == 0) {
        setAvailable(false);
      }
      if (this.ingredientList.get(i).getQuantity() > 0) {
        setAvailable(true);
      }
      if (this.ingredientList.get(i).isVegetarian() == false) {
        setVegeDish(false);
      }
      if (this.ingredientList.get(i).isVegetarian() == true) {
        setVegeDish(true);
      }
    }

    updateCalories();
    assignAllergy();
  }

  /**
   * Will iterate over ingredients list and total up the calories, from each
   * ingredient. Will then set the correct calories value to the variable
   * calories.
   */
  public void updateCalories() {
    int caloriesCount = 0;
    for (int i = 0; i < this.ingredientList.size(); i++) {
      caloriesCount += this.ingredientList.get(i).getCalories();
    }
    setCalories(caloriesCount);
  }

  /**
   * Will iterate over ingredients list and total up the calories, from each
   * ingredient. Will then return the correct calories value for the menuItem
   * selected. - martin zbvc377
   */
  public int getItemCalories(MenuItem item) {
    updateCalories();
    int caloriesCount = 0;
    for (int i = 0; i < item.ingredientList.size(); i++) {
      caloriesCount += item.ingredientList.get(i).getCalories();
    }
    return caloriesCount;
  }

  /**
   * returns XML format of the selected menuitem
   * @return StringBuffer of a menuitem XML
   */
  public StringBuffer getXML() {
    StringBuffer answer = new StringBuffer();
    answer.append(
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> \n");
    answer.append("<menuItem>\n");
    answer.append("<id>" + this.getId() + "</id> \n");
    answer.append("<name>" + this.getName() + "</name> \n");
    answer.append("<price>" + this.getPrice() + "</price> \n");
    answer.append("<calories>" + this.getCalories() + "</calories> \n");
    answer.append("<ingredientsList>" + this.getIngredientsList()
        + "</ingredientsList> \n");
    answer.append("<vegeDish>" + this.isVegeDish() + "</vegeDish> \n");
    answer.append("<halal>" + this.isHalal() + "</halal> \n");
    answer.append("<kosher>" + this.isKosher() + "</kosher> \n");
    answer.append("<type>" + this.getType() + "</type> \n");
    // answer.append("<allergy>" + this.getAllergyInfo() + "</allergy> \n");
    answer.append(
        "<specialOffer>" + this.getSpecialOffer() + "</specialOffer> \n");
    answer.append("<onMenu>" + this.isOnMenu() + "</onMenu> \n");
    answer.append("<available>" + this.isAvailable() + "</available> \n");
    answer.append("<pictureUrl>" + this.getPictureUrl() + "</pictureUrl> \n");
    answer.append("</menuItem> \n");

    return answer;
  }

  /**
   * ingrListtoString is used to convert the list of ingredients to a string
   * that is stored by the database.
   * 
   * @return String of ingredient IDs.
   */

  public String ingrListtoString() {
    StringBuffer ingrStr = new StringBuffer();
    for (Ingredient ingr : ingredientList) {
      ingrStr.append(ingr.getId() + ",");
    }

    return ingrStr.toString().substring(0, ingrStr.length() - 2);
  }

  /**
   * convert a string of ingredient IDs into an arraylist of the actual objects.
   * This is used to convert the string that is stored in the database into
   * actual java Ingredient objects so they can be edited.
   * 
   * @param ingrStr
   * @return
   */
  public List<Ingredient> ingrStringtoList() {
    String[] ingrids = new String[50];
    List<Ingredient> ingrList = new ArrayList<>();
    ingrids = this.getIngredientsList().split(",");
    int arraylength = ingrids.length;
    StringBuffer ingrResponse = new StringBuffer();

    for (int i = 0; i < arraylength; i++) {
      if (ingrids[i].equals("")) {
        return ingrList;
      }

      ingrResponse = scli
          .httpGet("http://shouganai.net:8080/restaurant/rest/ingredients/"
              + ingrids[i].trim());
      Ingredient toAdd = (Ingredient) scli.convertXML(ingrResponse,
          Ingredient.class);
      ingrList.add(toAdd);

    }

    return ingrList;
  }

  /**
   * Updates the menu item, in accordance to the current instance
   * @return StringBuffer StringBuffer containing the XML format of the menu item
   */
  public StringBuffer updateMenuItem() {
    // System.out.println(this.getXML());
    return scli.httpPutSB(
        "http://shouganai.net:8080/restaurant/rest/menuitems/alter",
        this.getXML());
  }

  /**
   * Returns the the picture URL for the menu item
   * @return String The picture URL
   */
  public String getPictureUrl() {
    return pictureUrl;
  }

  /**
   * sets the picture URL.
   * 
   * @param pictureUrl
   */
  public void setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
  }

  /**
   * Converts a string buffer to a menu item object
   * @param sb StringBuffer which contains the XML code 
   * @return MenuItem The new menu item object, created from the XML
   */
  public MenuItem convertToObject(StringBuffer sb) {
    return (MenuItem) scli.convertXML(sb, MenuItem.class);
  }

}
