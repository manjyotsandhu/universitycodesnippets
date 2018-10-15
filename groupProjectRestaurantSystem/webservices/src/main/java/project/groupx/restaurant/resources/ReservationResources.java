/**
 * ReservationResources shows all of the links that are available to the user to use to get information on the database. It
 * uses the ReservationService to then do all of the work between the Server and database.
 */
package project.groupx.restaurant.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//import project.groupx.restaurant.exceptions.DBEntryNotFound;
import project.groupx.restaurant.model.Reservation;
import project.groupx.restaurant.services.ReservationService;


/**
 * @author Johannes Herforth
 *
 */
//path will be {ip}/restaurant/rest/...
@Path("/reservation")
public class ReservationResources {

  ReservationService resser = new ReservationService();

  /**
   * the method getReservations takes a GET request for all accounts and executes a method in the
   * service to return the requested command.
   * 
   * @return List of Reservations.
   */
  @GET
  @Produces(MediaType.APPLICATION_XML)
  public List<Reservation> getReservations() {
    return resser.getReservations();
  }
  
  /**
   * the method getReservation takes a GET request including a specific id to return the specified
   * Reservation with help of the ReservationService class.
   * @param id
   * @return specific Reservation with given ID.
   */
  @Path("/{reservationId}")
  @GET
  @Produces(MediaType.APPLICATION_XML)
  public Reservation getReservation(@PathParam("reservationId") long id) {
    return resser.getReservation(id);
  }
  
  /**
   * the method deleteReservation takes a DELETE request including a specific id to then delete
   * the specified Reservation from the list.
   * @param id Reservation to delete.
   */
  @Path("/{reservationId}")
  @DELETE
  public void deleteReservation(@PathParam("reservationId") long id) {
    resser.deleteReservation(id);
  }
  
  /**
   * The method addReservation takes a POST request including an Reservation of XML format to then
   * with the help of the ReservationService class add it to the Database.
   * @param reserve Reservation to append to database.
   * @return returns the added Reservation if successful.
   */
  @POST
  @Consumes(MediaType.APPLICATION_XML)
  @Produces(MediaType.APPLICATION_XML)
  public Reservation addMenuItem(Reservation reserve) {
    return resser.addReservation(reserve);
  } 
  

}
