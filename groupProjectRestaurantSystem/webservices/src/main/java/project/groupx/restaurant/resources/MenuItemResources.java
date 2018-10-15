/**
 * The Class MenuItemResource is dedicated towards linking the paths given in the URL (towards Menu Items) with 
 * actual commands that they should each respectively do. This is done by calling methods in the class 
 * MenuItemService correctly.
 * 
 */
package project.groupx.restaurant.resources;

/**
 * @author Johannes Herforth.
 */

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import project.groupx.restaurant.model.MenuItem;
import project.groupx.restaurant.services.MenuItemService;

//path will be {ip}/restaurant/rest/...
@Path("/menuitems")
public class MenuItemResources {
  
  MenuItemService ingrser = new MenuItemService();
  
  /**
   * the method getMenuItems takes a GET request for all menu items and executes
   * a method in the service to return the requested command.
   * @return List of Menu Items.
   */
  @GET
  @Produces(MediaType.APPLICATION_XML)
  public List<MenuItem> getMenuItems(){
    return ingrser.getMenuItems();
  }
  
  /**
   * The method addMenuItem takes a POST request including an Menu Item of XML format to then
   * with the help of the MenuItemService class add it to the Database.
   * @param mItem MenuItem to append to database.
   * @return returns the added MenuItem if successful.
   */
  @POST
  @Consumes(MediaType.APPLICATION_XML)
  @Produces(MediaType.APPLICATION_XML)
  public MenuItem addMenuItem(MenuItem mItem) {
    return ingrser.addMenuItem(mItem);
  }  
  
  /**
   * alters the values of the menuitem to new ones
   * @param item new values for menuitem with the ID
   * @return new menuitem if successful
   */
  @Path("/alter")
  @PUT
  @Consumes(MediaType.APPLICATION_XML)
  @Produces(MediaType.APPLICATION_XML)
  public MenuItem alterItem(MenuItem item) {
    return ingrser.alterItem(item);
  }
  
  /**
   * the method getMenuItem takes a GET request including a specific id to return the specified
   * MenuItem with help of the MenuItemService class.
   * @param id
   * @return specific Menu Item
   */
  @Path("/{menuItemId}")
  @GET
  @Produces(MediaType.APPLICATION_XML)
  public MenuItem getMenuItem(@PathParam("menuItemId") long id) {
    return ingrser.getMenuItem(id);
  }
  
  /**
   * the method removeMenuItem takes a DELETE request including a specific id to then with
   * help of the MenuItemService class, delete the Menu Item from the database.
   * @param id id to be deleted.
   */
  @Path("/{menuItemId}")
  @DELETE
  public void removeMenuItem(@PathParam("menuItemId") long id) {
    ingrser.removeMenuItem(id);
  }
  
  
  /**
   * the method alterToMenu takes a PUT request including the menuitem to which the MenuItemService
   * will alter whether the menuitem should be put on the menu or not.
   * @param id id to be changed
   * @param onMenu
   * @return
   */
  @Path("/onMenu/{menuItemId}")
  @PUT
  @Consumes(MediaType.APPLICATION_XML)
  @Produces(MediaType.APPLICATION_XML)
  public MenuItem alterToMenu(@PathParam("menuItemId") long id) {
    return ingrser.alterToMenu(id);
  }
  
  /**
   * the method alterSpecialOffer takes a PUT request including the menuitem to which the MenuItemService
   * will alter whether the menuitem should be on sale or not.
   * @param id id to be changed
   * @param specialoffer
   * @return
   */
  @Path("/specialoffer/{menuItemId}/{newdiscount}")
  @PUT
  @Consumes(MediaType.APPLICATION_XML)
  @Produces(MediaType.APPLICATION_XML)
  public MenuItem alterSpecialOffer(@PathParam("menuItemId") long id, @PathParam("newdiscount") double discount) {
    return ingrser.alterSpecialOffer(id, discount);
  }
  
  /**
   * the method getSorted takes a GET request including a specific sorting String. With
   * help of the MenuItemService, will return a sorted MenuItem list based on the String.
   * @param sorting Attribute the list should sort by.
   * @return sorted list of MenuItems. 
   */
  @Path("/sort/{sorttype}")
  @GET
  @Produces(MediaType.APPLICATION_XML)
  public List<MenuItem> getSorted(@PathParam("sorttype") String sorting) {
    return ingrser.sortBy(sorting);
  }
  
  /**
   * the method getType takes a GET request including a specific type String. With help
   * from the MenuItemService, will return a MenuItem list that contains only the specific type.
   * @param type Attribute the list MUST contain
   * @return MenuItem list of the type
   */
  @Path("/type/{type}")
  @GET
  @Produces(MediaType.APPLICATION_XML)
  public List<MenuItem> getType(@PathParam("type") String type) {
    return ingrser.getItemType(type);
  }
  
  
}
