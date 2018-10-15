/**
 * The class MenuItem is built as a model of how the MenuItem class looks like. It is almost identical to
 * the MenuItem class in the client.
 */

package project.groupx.restaurant.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class MenuItem {
  private long id;
  private String name;
  private double price;
  private int calories;
  private boolean available;
  private String ingredientsList;
  private boolean VegeDish;
  private boolean onMenu;
  private String allergy;
  private boolean isHalal;
  private boolean isKosher;
  private double specialOffer;
  private String picture;
  private int type;
  
  public MenuItem() {
    this.id = 0;
    this.type = 1;
    this.specialOffer = 1.0;
  };
  
  public MenuItem(long id, String name, double price, String list) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.calories = 0;
    this.available = true;
    this.ingredientsList = list;
    this.VegeDish = true;
    this.specialOffer = 1.000;
    this.picture= "";
  }
  /**
   * get the current ID of the menuitem
   * @return current menuitem ID
   */
  public long getId() {
    return id;
  }
  /**
   * set new ID for the menuitem
   * @param id new ID
   */
  public void setId(long id) {
    this.id = id;
  }
  /**
   * get the current menuitem name
   * @return current name of menuitem
   */
  public String getName() {
    return name;
  }
  /**
   * sets a new name for the menuitem
   * @param name new name
   */
  public void setName(String name) {
    this.name = name;
  }
  /**
   * get the current price of the menuitem
   * @return current price of menuitem
   */
  public double getPrice() {
    return price;
  }
  /**
   * set the new price for the menuitem
   * @param price new price
   */
  public void setPrice(double price) {
    this.price = price;
  }
  /**
   * get amount of calories in the menuitem
   * @return calories in kcal
   */
  public int getCalories() {
    return calories;
  }
  /**
   * set new calorie count for the menuitem
   * @param calories number of calories in kcal
   */
  public void setCalories(int calories) {
    this.calories = calories;
  }
  /**
   * get if the menuitem is available (enough ingredients)
   * @return true/false
   */
  public boolean isAvailable() {
    return available;
  }
  /**
   * set if the menuitem is available (enough ingredients)
   * @param available true/false
   */
  public void setAvailable(boolean available) {
    this.available = available;
  }
  /**
   * get the list of ingredient IDs in the menuitem
   * @return String of Ingredient IDs
   */
  public String getIngredientsList() {

    return ingredientsList;
  }
  /**
   * set a new ingredients list for the menuitem
   * @param ingredientsList new list of ingredients in the menuitem
   */
  public void setIngredientsList(String ingredientsList) {
    this.ingredientsList = ingredientsList;
  }
  /**
   * get if the menuitem is vegetarian
   * @return true/false
   */
  public boolean isVegeDish() {
    return VegeDish;
  }
  /**
   * set if the menuitem is vegetarian
   * @param vegeDish true/false
   */
  public void setVegeDish(boolean vegeDish) {
    VegeDish = vegeDish;
  }
  /**
   * get if the menuitem is on the menu
   * @return true/false
   */
  public boolean isOnMenu() {
    return onMenu;
  }
  /**
   * set if the menuitem is listed on the menu
   * @param onMenu true/false
   */
  public void setOnMenu(boolean onMenu) {
    this.onMenu = onMenu;
  }
  /**
   * get if menuitem is Halal
   * @return true/false
   */
  public boolean isHalal() {
    return isHalal;
  }
  /**
   * set if the menuitem is Halal
   * @param isHalal true/false
   */
  public void setHalal(boolean isHalal) {
    this.isHalal = isHalal;
  }
  /**
   * get if the menuitem is kosher
   * @return true/false
   */
  public boolean isKosher() {
    return isKosher;
  }
  /**
   * set if the menuitem is Kosher
   * @param isKosher true/false
   */
  public void setKosher(boolean isKosher) {
    this.isKosher = isKosher;
  }
  /**
   * get the current Allergy StringList
   * @return current allergies for the menuitem
   */
  public String getAllergy() {
    return allergy;
  }
  /**
   * set new allergies for the menuitem
   * @param allergy new allergy Stringlist for the menuitem
   */
  public void setAllergy(String allergy) {
    this.allergy = allergy;
  }
  /**
   * get the current special offer price for the menuitem
   * @return current menuitem special price multiplier
   */
  public double getSpecialOffer() {
    return specialOffer;
  }
  /**
   * set a new special offer price for the menuitem
   * @param specialOffer new price multiplier 
   */
  public void setSpecialOffer(double specialOffer) {
    this.specialOffer = specialOffer;
  }
  /**
   * get the current pictureUrl for the menuitem
   * @return pictureUrl for selected menuitem
   */
  public String getPictureUrl() {
    return picture;
  }
  /**
   * set a new pictureUrl for the menuitem
   * @param pictureUrl new menuitem pictureUrl
   */
  public void setPictureUrl(String pictureUrl) {
    this.picture = pictureUrl;
  }
  /**
   * get the current menuitem type
   * @return current menuitem type
   */
  public int getType() {
    return type;
  }
  /**
   * set the type of the menuitem
   * @param type new menuitem type
   */
  public void setType(int type) {
    this.type = type;
  }
  
  /**
   * converts the string of ingredient IDs to an ArrayList for easy access.
   * @return List of Ingredient IDs.
   */
  public List<Long> stringToList() {
    String[] ingrids = new String[50];
    List<Long> ingr = new ArrayList<>();
    ingrids = this.getIngredientsList().split(",");
    int arraylength = ingrids.length;

    for (int i = 0; i < arraylength; i++) {
      if (ingrids[i].equals("")) {
      }
      ingr.add(Long.parseLong(ingrids[i].trim()));
    }
    
    return ingr;
  }
  
}
