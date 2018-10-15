/**
 * The Class AccountResource is dedicated towards linking the paths given in the URL (towards Accounts) with 
 * actual commands that they should each respectively do. This is done by calling methods in the class 
 * AccountService correctly.
 */
package project.groupx.restaurant.resources;

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
import project.groupx.restaurant.model.Account;
import project.groupx.restaurant.services.AccountService;


/**
 * @author Johannes Herforth
 *
 */
//path will be {ip}/restaurant/rest/...
@Path("/accounts")
public class AccountResources {

  AccountService accser = new AccountService();

  /**
   * the method getAccounts takes a GET request for all accounts and executes a method in the
   * service to return the requested command.
   * 
   * @return List of Accounts.
   */
  @GET
  @Produces(MediaType.APPLICATION_XML)
  public List<Account> getAccounts() {
    return accser.getAccounts();
  }
  
  /**
   * the method getAccount takes a GET request including a specific id to return the specified
   * Account with help of the AccountService class.
   * @param id
   * @return specific Account with given ID.
   */
  @Path("/{accountId}")
  @GET
  @Produces(MediaType.APPLICATION_XML)
  public Account getAccount(@PathParam("accountId") long id) {
    return accser.getAccount(id);
  }
  
  /**
   * The method authenticateAccount takes a POST request including an object of Account to
   * authenticate the account in question. 
   * @param authAcc Account to authenticate
   * @return Account if successful
   */
  @Path("/login")
  @POST
  @Consumes(MediaType.APPLICATION_XML)
  @Produces(MediaType.APPLICATION_XML)
  public Account authenticateAccount(Account authAcc) {
    return accser.authenticateAccount(authAcc.getEmail(), authAcc.getPassword());
  }
  /**
   * The method logoutAccount takes in a POST request to log out of the given account.
   * @param loAcc Account to log out of
   * @return default account
   */
  @Path("/logout")
  @POST
  @Consumes(MediaType.APPLICATION_XML)
  @Produces(MediaType.APPLICATION_XML)
  public Account logoutAccount(Account loAcc) {
    return accser.logoutAccount(loAcc);
  }
  /**
   * The method createAccount takes a POST request to create a new Guest Account to the database
   * based on the XML given.
   * @param addAccount Account details to be added
   * @return the new account if successful
   */
  @Path("/create")
  @POST
  @Consumes(MediaType.APPLICATION_XML)
  @Produces(MediaType.APPLICATION_XML)
  public Account createAccount(Account addAccount) {
    return accser.createGuestAccount(addAccount);
  }
  /**
   * The method createAccount takes a POST request to create a new Account of ANY type to the database
   * based on the XML given.
   * @param addAccount Account details to be added
   * @return the new account if successful
   */
  @Path("/sudo/create")
  @POST
  @Consumes(MediaType.APPLICATION_XML)
  @Produces(MediaType.APPLICATION_XML)
  public Account createAnyAccount(Account addAccount) {
    return accser.createAnyAccount(addAccount);
  }
  
  /**
   * allows the accounts of users to be changed. On fail, returns a default Guest Account.
   * @param editAccount Account information to be edited.
   * @return the confirmed new account information. On fail, returns default Guest Account.
   */
  @Path("/sudo/edit")
  @PUT
  @Consumes(MediaType.APPLICATION_XML)
  @Produces(MediaType.APPLICATION_XML)
  public Account alterAccount(Account editAccount) {
    
    try {
      return accser.alterAccount(editAccount);
    } catch (DBEntryNotFound e) {

      e.printStackTrace();
    }
    
    return new Account();
  }
  
  /**
   * The method deleteAccount takes in an accountID to be deleted off the database.
   * @param id
   */
  @Path("/{accountId}")
  @DELETE
  public void deleteAccount(@PathParam("accountId") long id) {
    accser.deleteAccount(id);
  }
}
