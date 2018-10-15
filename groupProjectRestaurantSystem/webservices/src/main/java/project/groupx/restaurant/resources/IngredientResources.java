/**
 * The Class IngredientResource is dedicated towards linking the paths given in the URL (towards Ingredients) with 
 * actual commands that they should each respectively do. This is done by calling methods in the class 
 * IngredientService correctly.
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
import project.groupx.restaurant.model.Ingredient;
import project.groupx.restaurant.services.IngredientService;

//path will be {ip}/restaurant/rest/...
@Path("/ingredients")
public class IngredientResources {
  
  IngredientService ingrser = new IngredientService();
  
  /**
   * the method getIngredients takes a GET request for all ingredients and executes
   * a method in the service to return the requested command.
   * @return List of Ingredients.
   */
  @GET
  @Produces(MediaType.APPLICATION_XML)
  public List<Ingredient> getIngredients(){
    return ingrser.getIngredients();
  }
  
  /**
   * The method addIngredient takes a POST request including an Ingredient of XML format to then
   * with the help of the IngredientService class add it to the Database.
   * @param ingre Ingredient to append to database.
   * @return returns the added Ingredient if successful.
   */
  @POST
  @Consumes(MediaType.APPLICATION_XML)
  @Produces(MediaType.APPLICATION_XML)
  public Ingredient addIngredient(Ingredient ingre) {
    return ingrser.addIngredient(ingre);
  }
  
  /**
   * The method updateIngredient takes a PUT request including an Ingredient of XML format to then
   * with the help of the IngredientService class update it the Database.
   * @param ingre Ingredient to append to database.
   * @return returns the added Ingredient if successful.
   * @throws DBEntryNotFound 
   */
  @PUT
  @Consumes(MediaType.APPLICATION_XML)
  @Produces(MediaType.APPLICATION_XML)
  public Ingredient updateIngredient(Ingredient ingre) throws DBEntryNotFound {
    return ingrser.updateIngredient(ingre);
  }
  
  /**
   * the method getIngredient takes a GET request including a specific id to return the specified
   * Ingredient with help of the IngredientService class.
   * @param id id to get
   * @return Ingredient if successful
   */
  @Path("/{ingredientId}")
  @GET
  @Produces(MediaType.APPLICATION_XML)
  public Ingredient getIngredient(@PathParam("ingredientId") long id) {
    return ingrser.getIngredient(id);
  }
  
  /**
   * the method removeIngredient takes a DELETE request including a specific id to then with
   * help of the IngredientService class, delete the ingredient from the database.
   * @param id id to be deleted.
   */
  @Path("/{ingredientId}")
  @DELETE
  @Produces(MediaType.APPLICATION_XML)
  public void removeIngredient(@PathParam("ingredientId") long id) {
    ingrser.removeIngredient(id);
  }
  
  /**
   * The method orderIngredient takes a PUT request including an id of the ingredient to then
   * with the help of the IngredientService class remove one of the ingredient from the database.
   * @param id id to be subtracted
   * @throws DBEntryNotFound 
   */
  @Path("/{ingredientId}")
  @PUT
  @Consumes(MediaType.APPLICATION_XML)
  @Produces(MediaType.APPLICATION_XML)
  public void orderIngredient(@PathParam("ingredientId") long id) throws DBEntryNotFound {
    ingrser.orderIngredient(id);
  }
}
