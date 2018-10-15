package client.app.logic;

import java.util.*;

import client.server.connection.ServerClient;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.time.*;

/**
 * 
 * @author Martin
 *
 *Special class is made for creating, editing and deleting
 *special offers and events. This changes the price of menu
 *items and is linked with the Email class. This class uses
 *the server to reach the database and give the specialOffer
 *variable, a value.
 */

@XmlRootElement(name = "menuitems")
public class Special {
	
	static int dueTime;
	
	@XmlElement(name = "menuitems", type = MenuItem.class)
	 private static List<MenuItem> setOfItems = new ArrayList<>();
	
	public Special() {
		
	}
	
	public Special(List<MenuItem> listOfItems) {
		this.setOfItems = listOfItems;
	}
	
	public List<MenuItem> getItems() {
	    return setOfItems;
	  }
	  
	public void setItems(List<MenuItem> listOfItems) {
		this.setOfItems = listOfItems;
	}
	
	/*public static void main(String[] args) {
		measureTime();
	}*/
	/**
	 * Updates the percentage taken off original price.
	 */
	public void createSpecialOffer(MenuItem item, double newDiscount) {
		ServerClient scli = new ServerClient();
		if(item.isOnMenu() && item.isAvailable() && (0 < newDiscount) && (newDiscount < 1)) {
			scli.httpPut("http://shouganai.net:8080/restaurant/rest/specialoffer/" + item.getId() + "/" + newDiscount, item.getXML());
			item.setPrice((item.getPrice()*(1-newDiscount)));
			System.out.println("Discount of " + (newDiscount*100) + " taken off " + item);
		} else {
			System.out.println(item + " is either not currently available or not on the menu. Or newDiscount is not between 0 and 1.");
			return;
		}
	}
	
	/**
	 * @param item
	 * @param newPrice
	 * deletes special off on specific item and sets a new price.
	 */
	public static void deleteSpecialOffer(MenuItem item, double newPrice) {
		ServerClient scli = new ServerClient();
		if(item.isOnMenu() && item.isAvailable() && (0 < item.getSpecialOffer()) && (item.getSpecialOffer() < 1)) {
			scli.httpDelete("http://shouganai.net:8080/restaurant/rest/specialoffer/" + item.getId());
			item.setPrice(newPrice);
			System.out.println("Special offer for " + item + " has been deleted. New price is: " + newPrice);
		} else {
			System.out.println(item + " is either not currently available or not on the menu.");
			return;
		}
	}
	
	/**
	 * @return list of menu items which have special offers.
	 */
	public static List<MenuItem> listSpecialOffers() {
		setOfItems = new ArrayList<>();
		ServerClient scli = new ServerClient();
		StringBuffer currResponse = new StringBuffer();
		MenuItems mitems = new MenuItems();
		currResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/menuitems");
		mitems = (MenuItems) scli.convertXML(currResponse, MenuItems.class);
		for(MenuItem item: mitems.getItems()) {
			if(item.isAvailable() && item.isOnMenu() && item.getSpecialOffer() > 0 && item.getSpecialOffer() < 1) {
				setOfItems.add(item);
			}
		}
		return setOfItems;
	}
	
	/**
	 * @param creates a special offer on all items.
	 */
	public static void createEvent(double newDiscount) {
		ServerClient scli = new ServerClient();
		StringBuffer currResponse = new StringBuffer();
		MenuItems mitems = new MenuItems();
		currResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/menuitems");
		mitems = (MenuItems) scli.convertXML(currResponse, MenuItems.class);
		
		for(MenuItem item : mitems.getItems()) {
			if(item.isAvailable() && item.isOnMenu() && (0 < newDiscount) && (newDiscount < 1)) {
				scli.httpPut("http://shouganai.net:8080/restaurant/rest/specialoffer/" + item.getId() + "/" + newDiscount, item.getXML());
				item.setPrice(item.getPrice()*(1-newDiscount));
			} else {
				System.out.println(item + " is either not available or not on the menu.");
			}
		}
	}
	
	/**
	 * deletes a special offer from every item on the menu.
	 */
	public static void deleteEvent() {
		ServerClient scli = new ServerClient();
		StringBuffer currResponse = new StringBuffer();
		MenuItems mitems = new MenuItems();
		currResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/menuitems");
		mitems = (MenuItems) scli.convertXML(currResponse, MenuItems.class);
		
		for(MenuItem item : mitems.getItems()) {
			if(item.isAvailable() && item.isOnMenu() && (0 < item.getSpecialOffer()) && (item.getSpecialOffer() < 1)) {
				scli.httpDelete("http://shouganai.net:8080/restaurant/rest/specialoffer/" + item.getId());
				item.setPrice(item.getPrice()); //need to correct this, possibly implement new column "originalPrice" in database
			} else {
				System.out.println(item + " is either not available or not on the menu.");
			}
		}
	}
}
