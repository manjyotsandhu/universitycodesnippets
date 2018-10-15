/**
 * The class AccountService is all the business value for the Account data structure. The class
 * contains all the methods that are called from the resource. The methods all work with updating or 
 * receiving information about Accounts from the database.
 */
package project.groupx.restaurant.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import project.groupx.restaurant.database.DatabaseClient;
import project.groupx.restaurant.exceptions.DBEntryNotFound;
import project.groupx.restaurant.model.Account;

/**
 * @author Johannes Herforth.
 *
 */

public class AccountService {

  private List<Account> accounts;
  private Account currAcc;
  private ResultSet rs;
  private Connection conn;

  /**
   * gets a list of all the Accounts that are stored on the database.
   * 
   * @return list of Accounts on the database.
   */
  public List<Account> getAccounts() {
    accounts = new ArrayList<>();
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "SELECT * FROM accounts;");

    try {
      while (rs.next()) {
        currAcc = new Account();
        currAcc.setAccountID(rs.getLong(1));
        currAcc.setName(rs.getString(2));
        currAcc.setPassword(rs.getString(4));
        currAcc.setAccountType(rs.getInt(3));
        currAcc.setEmail(rs.getString(5));

        accounts.add(currAcc);
      }
      // rs.close();
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return accounts;
  }


  /**
   * Takes an id to then retrieve the specific Account from the database.
   * 
   * @param id specific id of Account.
   * @return specific Account from database.
   */
  public Account getAccount(long id) {
    currAcc = new Account();
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "SELECT * FROM accounts WHERE id=" + id + ";");

    try {
      while (rs.next()) {
        currAcc.setAccountID(rs.getLong(1));
        currAcc.setName(rs.getString(2));
        currAcc.setPassword(rs.getString(4));
        currAcc.setAccountType(rs.getInt(3));
        currAcc.setEmail(rs.getString(5));
      }
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return currAcc;
  }
  /**
   * takes an email and a password and tried to authenticate it with the database.
   * @param email email of account
   * @param password password of account
   * @return
   */
  public Account authenticateAccount(String email, String password) {
    Account toVerify = new Account();

    String hashPass = toVerify.toHash(password);
    // System.out.println(hashPass + " HASH");
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn,
        "SELECT * FROM accounts WHERE email='" + email + "' AND password = '" + hashPass + "';");

    toVerify.setAccountType(5); //if the database does not contain the account, ERROR account type 5

    try {
      while (rs.next()) {
        // System.out.println(rs.getLong(1) + rs.getString(2) + rs.getInt(3));
        toVerify.setAccountID(rs.getLong(1));
        toVerify.setName(rs.getString(2));
        toVerify.setAccountType(rs.getInt(3));
        toVerify.setEmail(rs.getString(5));
      }
      if (conn != null) {
        conn.close();
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    

    return toVerify;
  }
  /**
   * takes in an account to then insert the data into the database as a GUEST.
   * @param addAcc account to add to the database
   * @return successful new account
   */
  public Account createGuestAccount(Account addAcc) {

    addAcc.setAccountID(this.getNextID());
    addAcc.setPassword(addAcc.toHash(addAcc.getPassword()));
    addAcc.setAccountType(0);
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    DatabaseClient.executeQuery(conn,
        "INSERT INTO accounts (id,name,accountype,password,email) " + "VALUES ("
            + addAcc.getAccountID() + ", '" + addAcc.getName() + "', " + addAcc.getAccountType()
            + ", '" + addAcc.getPassword() + "', '" + addAcc.getEmail() + "');");

    if (addAcc.getAccountID() < 1) {
      return new Account();
    }
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return getAccount(addAcc.getAccountID());
  }
  /**
   * returns new default account
   * @param loAcc old account to log out of
   * @return new default "not logged in" account
   */
  public Account logoutAccount(Account loAcc) {

    return new Account();

  }


  /**
   * gets the highest ID of Account from database to safely add a new Account with a new ID.
   * 
   * @return next open id to be used.
   */
  public long getNextID() {
    long longID = 0L;
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "SELECT id FROM accounts ORDER BY id DESC LIMIT 1;");

    try {
      rs.next();
      longID = rs.getLong(1);
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return longID + 1;
  }

  /**
   * deletes the account with the given ID.
   * @param id account id to delete.
   */
  public void deleteAccount(long id) {
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    DatabaseClient.executeQuery(conn, "DELETE FROM accounts WHERE id=" + id + ";");

    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  /**
   * creates a new account with any AccountType.
   * @param addAcc information for the new account
   * @return the newly created account
   */
  public Account createAnyAccount(Account addAcc) {
    addAcc.setAccountID(this.getNextID());
    addAcc.setPassword(addAcc.toHash(addAcc.getPassword()));

    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    DatabaseClient.executeQuery(conn,
        "INSERT INTO accounts (id,name,accountype,password,email) " + "VALUES ("
            + addAcc.getAccountID() + ", '" + addAcc.getName() + "', " + addAcc.getAccountType()
            + ", '" + addAcc.getPassword() + "', '" + addAcc.getEmail() + "')");

    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }


    // need to add more databases for each different type of staff
    // so that I can add more information depending on the account type
    return addAcc;
  }

  /**
   * update an existing account with new information
   * @param editAcc new information for the given ID
   * @return account with the new details
   * @throws DBEntryNotFound if the account ID does not exist, throw an error.
   */
  public Account alterAccount(Account editAcc) throws DBEntryNotFound {
    Account dbAcc = getAccount(editAcc.getAccountID());

    if (dbAcc.getName().equals("Guest")) {
      throw new DBEntryNotFound("ERROR: Account ID not found");
    }

    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn,
        "UPDATE accounts set name='" + editAcc.getName() + "', email = '" + editAcc.getEmail()
            + "', accountype = '" + editAcc.getAccountType() + "' WHERE id=" + dbAcc.getAccountID() + ";");


    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return editAcc;
  }

}
