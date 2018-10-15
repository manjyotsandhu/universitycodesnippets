/**
 * The Class OrderResource is dedicated towards linking the paths given in the URL (towards Orders) with 
 * actual commands that they should each respectively do. This is done by calling methods in the class 
 * OrderService correctly.
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

import project.groupx.restaurant.exceptions.DBEntryNotFound;
import project.groupx.restaurant.model.Order;
import project.groupx.restaurant.services.OrderService;

//path will be {ip}/restaurant/rest/...
@Path("/orders")
public class OrderResources {
  
  OrderService ingrser = new OrderService();
  
  /**
   * the method getOrders takes a GET request for all menu items and executes
   * a method in the service to return the requested command.
   * @return List of Orders.
   */
  @GET
  @Produces(MediaType.APPLICATION_XML)
  public List<Order> getOrders(){
    return ingrser.getOrders();
  }
  
  /**
   * The method addOrder takes a POST request including an Order of XML format to then
   * with the help of the OrderService class add it to the Database.
   * @param ingre Order to append to database.
   * @return returns the added Order if successful.
   * @throws DBEntryNotFound 
   */
  @POST
  @Consumes(MediaType.APPLICATION_XML)
  @Produces(MediaType.APPLICATION_XML)
  public Order addOrder(Order orderinput) throws DBEntryNotFound {
    return ingrser.addOrder(orderinput);
  }  
  
  /**
   * the method getOrder takes a GET request including a specific id to return the specified
   * Order with help of the OrderService class.
   * @param id
   * @return specific Order with given ID.
   */
  @Path("/{orderId}")
  @GET
  @Produces(MediaType.APPLICATION_XML)
  public Order getOrder(@PathParam("orderId") long id) {
    return ingrser.getOrder(id);
  }
  
  /**
   * the method removeOrder takes a DELETE request including a specific id to then with
   * help of the MenuOrder class, delete the Order from the database.
   * @param id id to be deleted.
   */
  @Path("/{orderId}")
  @DELETE
  @Produces(MediaType.APPLICATION_XML)
  public void removeMenuItem(@PathParam("orderId") long id) {
    ingrser.removeOrder(id);
  }
  
  /**
   * update the current order to change items
   * @param uOrder new and updated order
   * @return new and updated order when successful
   */
  @Path("/{orderId}")
  @PUT
  @Consumes(MediaType.APPLICATION_XML)
  @Produces(MediaType.APPLICATION_XML)
  public Order updateOrder(Order uOrder) {
    try {
      return ingrser.updateOrder(uOrder);
    } catch (DBEntryNotFound e) {
      e.printStackTrace();
    }
    
    return new Order();
  }
}
