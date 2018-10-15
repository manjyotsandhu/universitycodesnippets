/**
 * The Class CustomerResource is dedicated towards linking the paths given in the URL (towards Customers) with 
 * actual commands that they should each respectively do. This is done by calling methods in the class 
 * CustomerService correctly.
 * 
 */
package project.groupx.restaurant.resources;

/**
 * @author Johannes Herforth.
 * @author Manjyot Sandhu
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
import project.groupx.restaurant.model.Customer;
import project.groupx.restaurant.services.CustomerService;

//path will be {ip}/restaurant/rest/...
@Path("/customers")
public class CustomerResources {

  CustomerService cusser = new CustomerService();

  /**
   * the method getCustomers takes a GET request for all menu items and executes
   * a method in the service to return the requested command.
   * 
   * @return List of Customers
   */
  @GET
  @Produces(MediaType.APPLICATION_XML)
  public List<Customer> getCustomers() {
    return cusser.getCustomers();
  }

  /**
   * The method addCustomer takes a POST request including an Customer of XML
   * format to then with the help of the CustomerService class add it to the
   * Database.
   * 
   * @param ingre
   *          Order to append to database.
   * @return Customer the added Customer if successful.
   * @throws DBEntryNotFound
   */
  @POST
  @Consumes(MediaType.APPLICATION_XML)
  @Produces(MediaType.APPLICATION_XML)
  public Customer addCustomer(Customer customerinput) throws DBEntryNotFound {
    return cusser.addCustomer(customerinput);
  }

  /**
   * the method getCustomer takes a GET request including a specific id to
   * return the specified Customer with help of the CustomerService class.
   * 
   * @param id
   *          The ID of the customer to get
   * @return Customer specific Customer with given ID.
   */
  @Path("/{id}")
  @GET
  @Produces(MediaType.APPLICATION_XML)
  public Customer getCustomer(@PathParam("id") long id) {
    return cusser.getCustomer(id);
  }

  /**
   * the method removeCustomer takes a DELETE request including a specific id
   * and deletes the Customer from the database.
   * 
   * @param id
   *          id to be deleted.
   */
  @Path("/{id}")
  @DELETE
  @Produces(MediaType.APPLICATION_XML)
  public void removeCustomer(@PathParam("id") long id) {
    cusser.removeCustomer(id);
  }

  /**
   * The method updateCustomer will update a customer in the database
   * 
   * @param uCustomer
   *          The customer to be updated
   * @return Customer The newly editted customer
   */
  @Path("/alter")
  @PUT
  @Consumes(MediaType.APPLICATION_XML)
  @Produces(MediaType.APPLICATION_XML)
  public Customer updateCustomer(Customer uCustomer) {
    try {
      return cusser.updateCustomer(uCustomer);
    } catch (DBEntryNotFound e) {
      e.printStackTrace();
    }

    return new Customer();
  }
}
