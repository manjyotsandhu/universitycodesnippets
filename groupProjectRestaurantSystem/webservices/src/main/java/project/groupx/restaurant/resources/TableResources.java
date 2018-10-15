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

import project.groupx.restaurant.model.Table;
import project.groupx.restaurant.services.TableService;


/**
 * 
 * @author amrit
 *
 */

@Path("/tables")
public class TableResources {
  TableService tabser = new TableService();
  
  @GET
  @Produces(MediaType.APPLICATION_XML)
  public List<Table> getReservations() {
    return tabser.getTables();
  }
  
  @Path("/{tableId}")
  @GET
  @Produces(MediaType.APPLICATION_XML)
  public Table getTable(@PathParam("tableId") long id) {
    return tabser.getTable(id);
  }
  
  @Path("/{tableId}")
  @DELETE
  public void deleteTable(@PathParam("tableId") long id) {
    tabser.deleteTable(id);
  }
  
  @POST
  @Consumes(MediaType.APPLICATION_XML)
  @Produces(MediaType.APPLICATION_XML)
  public Table addTable(Table table) {
    return tabser.addTable(table);
  } 
  
}
