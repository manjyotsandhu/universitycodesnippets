package client.app.logic;

import java.util.*;

//import client.server.connection.ServerClient;
import client.server.connection.*;

/**
 * 
 * @author Martin
 *
 *The Menu class is essentially methods constructed for 
 *adding and removing menu items to the menu, listing the 
 *menu, sorting items, list ingredients, returning allergies 
 *and calories, and other methods to do with the actual menu. 
 *Each one of these methods can be called from other classes 
 *in order to restructure the menu and return information from 
 *the menu.
 */
public class Menu {
	/*public static void main(String[] args) {
		
	}*/
  private ServerClient scli = new ServerClient();
  private List<MenuItem> menu = new ArrayList<>();
  private StringBuffer currResponse = new StringBuffer();
  private MenuItems mitems = new MenuItems();
  private MenuItem selection = new MenuItem();
  private Ingredients ingred = new Ingredients();
  private List<Ingredient> ingredient1 = new ArrayList<>();
  
  public Menu() {
    
  }
	
	public List<MenuItem> getMenu() { //gets menu
		//MenuItemService.getMenuItems();
	    menu = new ArrayList<>();
		currResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/menuitems");
		mitems = (MenuItems) scli.convertXML(currResponse, MenuItems.class);

		return mitems.getItems();
	}
	
	public MenuItem getMenuItem(long id) {
	  currResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/menuitems/" + id);
	  selection = (MenuItem) scli.convertXML(currResponse, MenuItem.class);
	  return selection;
	}
	
	/**
	 * adds item to menu after checks
	 */
	public void addToMenu(MenuItem menuItem) { 
		if(menuItem.isAvailable() && !menuItem.isOnMenu()) {
		  scli.httpPut("http://shouganai.net:8080/restaurant/rest/menuitems/onmenu/" + menuItem.getId(), menuItem.getXML());
			System.out.println(menuItem + " was added to the menu!");
		} else {
			System.out.println("Item is either not available or already on the menu.");
			return;
		}
	}
	
	/**
	 * removes item from menu after checks
	 */
	public void removeFromMenu(MenuItem menuItem) { 
		if(menuItem.isOnMenu()) {
          scli.httpPut("http://shouganai.net:8080/restaurant/rest/menuitems/onmenu/" + menuItem.getId(), menuItem.getXML());
			//MenuItemService.removeMenuItem(menuItem.getId()); //or change to removeFromMenu(long id) & use .removeMenuItem(id)
			System.out.println(menuItem + " was removed from the menu!");
		}
	}
	
	/**
	 * returns menu in a sorted order chosen by user *only for id, name and price*
	 */
	public List<MenuItem> sortBy(String vari) { 
		if(vari.equals("id")||vari.equals("name")||vari.equals("price")) {
		  menu = new ArrayList<>();
		  currResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/menuitems/sort/" + vari);
		  mitems = (MenuItems) scli.convertXML(currResponse, MenuItems.class);
		  for(MenuItem item : mitems.getItems()) {
		    menu.add(item);
		  }
		} else {
			System.out.println("Variable entered is not valid.");
		}
		return menu;
	}
	
	/**
	 * returns vegetarian, halal or kosher menu items only
	 */
	public List<MenuItem> returnAll(String type) { 
		if(type.equals("vegedish")||type.equals("ishalal")||type.equals("iskosher")) {
		     menu = new ArrayList<>();
		     currResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/menuitems/type/" + type);
		     mitems = (MenuItems) scli.convertXML(currResponse, MenuItems.class);
		     for(MenuItem item : mitems.getItems()) {
		       menu.add(item);
		     }
		} else {
			System.out.println("Variable entered is not valid.");
		}
		
		return menu;
	}
	
	/**
	 * list of ingredients for the menuItem selected.
	 */
	public List<Ingredient> listIngredients(MenuItem type) {
		 ingredient1 = new ArrayList<>();
		 currResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/ingredients/type/" + type);
		 ingred = (Ingredients) scli.convertXML(currResponse, Ingredients.class);
		 for(Ingredient ingredient : ingred.getIngredients()) {
		  	ingredient1.add(ingredient);
		 }
		 return ingredient1;
		 	    
	}
	
	
	/**
	 * updates menu and includes items that are available and on menu.
	 */
	public List<MenuItem> updateMenu() {
		menu = new ArrayList<>();
		currResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/menuitems");
		mitems = (MenuItems) scli.convertXML(currResponse, MenuItems.class);
		for(MenuItem item : mitems.getItems()) {
			if(item.isAvailable() && item.isOnMenu()) {
				menu.add(item);
			}
		}
		return menu;
	}
	
	
	/**
	 * returns calories for the item chosen by referencing the MenuItem method getItemCalories()
	 */
	public int returnCalories(MenuItem item) {
		int calories = item.getItemCalories(item);
		if(item.isAvailable() && item.isOnMenu()) {
			return calories;
		} else {
			System.out.println("Item is either no available or not on menu.");
			return 0;
		}
	}
	
	/**
	 * returns a list of allergies when a menu item is selected.
	 */
	public String listAllergies(MenuItem item) {
		ingredient1 = new ArrayList<>();
		String message = item + " contains no allergy information.";
		String message1 = item + " contains the following allergy information: ";
		
		if(item.getAllergy().equals(null)) {
			return message;
		} else {
			return message1 + item.getAllergy();
		}
	}
	
	/**
	 * returns a list of items when an ingredient is selected.
	 */
	public List<MenuItem> selectSpecificIngredient(Ingredient ingr) {
		menu = new ArrayList<>();
		currResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/menuitems");
		mitems = (MenuItems) scli.convertXML(currResponse, MenuItems.class);
		for(MenuItem item: mitems.getItems()) {
			if(item.getIngredientList().contains(ingr)) {
				menu.add(item);
			}
		}
		return menu;
	}


	
	/**
	 * returns the cheapest item on the menu by comparing items to the cheapest item saved
	 */
	public List<MenuItem> listCheapestItem() {
		menu = new ArrayList<>();
		currResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/menuitems");
		mitems = (MenuItems) scli.convertXML(currResponse, MenuItems.class);
		for(MenuItem item: mitems.getItems()) {
			if(menu.size() == 0) {
				menu.add(item);
			} else if(item.getPrice() < menu.get(0).getPrice()) {
				menu.remove(0);
				menu.add(item);
			}
		}
		return menu;
	}
	
	/**
	 * adds a menuitem to the MenuItem list on the database
	 * @param mitem item to add to the database
	 * @return menuitem function just added
	 */
	public MenuItem addItem(MenuItem mitem) {
	  currResponse = scli.httpPostSB("http://shouganai.net:8080/restaurant/rest/menuitems", mitem.getXML());
	  
	  return (MenuItem) scli.convertXML(currResponse, MenuItem.class);
	}

	
}