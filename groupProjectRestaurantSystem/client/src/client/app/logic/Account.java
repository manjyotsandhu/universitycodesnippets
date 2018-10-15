/**
 * the Account class is a general class in which all the other account classes will extend from. It contains trivial information
 * that will be found in all of the different account extended classes.
 */
package client.app.logic;



import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import client.server.connection.ServerClient;

/**
 * @author Johannes Herforth
 *
 */
@XmlRootElement(name = "account")
public class Account {
  //RESTFUL values needed.
  private ServerClient scli = new ServerClient();
  private StringBuffer currResponse = new StringBuffer();
  private Accounts multiAccs = new Accounts();
  
  //ACCOUNT values needed.
  private long accountID;
  private String name;
  private int accountType;
  private String password;
  private String email;
  
  //empty constructor.
  public Account(){
    this.accountID = 0;
    this.accountType = 5;
    this.name = "Guest";
  };
  
  //Constructor used when given a lot of information.
  public Account(long id, String name, int type, String password, String email) {
    this.accountID = id;
    this.name = name;
    this.accountType = type;
    this.password = password;
    this.email = email;
    // not sure if this is needed as the database will be handling all of this.
  }
  
  
  public long getAccountID() {
    return accountID;
  }
  public void setAccountID(long accountID) {
    this.accountID = accountID;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public int getAccountType() {
    return accountType;
  }
  
  //0 = invalid
  //1 = waiter
  //2 = manager
  //3 = kitchen staff
  //5 = guest
  public void setAccountType(int accountType) {
    this.accountType = accountType;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  
  /**
   * authentication XML for logging in.
   * @return StringBuffer of an account XML
   */
  public StringBuffer getAuthXML() {
    StringBuffer answer = new StringBuffer();
    answer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> \n");
    answer.append("<account>\n");
    answer.append("<email>" + this.getEmail() + "</email> \n");
    answer.append("<password>" + this.getPassword() + "</password> \n");
    answer.append("</account> \n");

    return answer;
  }
  
  /**
   * full XML of the account instance
   * @return StringBuffer of account XML
   */
  public StringBuffer getXML() {
    StringBuffer answer = new StringBuffer();
    answer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> \n");
    answer.append("<account>\n");
    answer.append("<name>" + this.getName() + "</name> \n");
    answer.append("<email>" + this.getEmail() + "</email> \n");
    answer.append("<password>" + this.getPassword() + "</password> \n");
    answer.append("<accountType>" + this.getAccountType() + "</accountType> \n");
    answer.append("<id>" + this.getAccountID() + "</id> \n");
    answer.append("</account> \n");

    return answer;
  }
  
  /**
   * registers a new guest account
   * @param newXML xml of the account to register
   * @return the newly registered account
   */
  public Account registerAccount(StringBuffer newXML) {
    Account newacc = new Account();
    currResponse = scli.httpPostSB("http://shouganai.net:8080/restaurant/rest/accounts/create", newXML);
    newacc = (Account) scli.convertXML(currResponse, Account.class);
    
    return newacc;
  }
  
  /**
   * register an account of ANY type
   * @param newXML xml of the account to register
   * @return the newly registered account
   */
  public Account registerAnyAccount(StringBuffer newXML) {
    Account newacc = new Account();
    currResponse = scli.httpPostSB("http://shouganai.net:8080/restaurant/rest/accounts/sudo/create", newXML);
    newacc = (Account) scli.convertXML(currResponse, Account.class);
    
    return newacc;
  }
  
  /**
   * 
   * @param postXML
   * @return
   */
  public Account verifyLogin(StringBuffer postXML) {
    Account returnacc = new Account();
    currResponse = scli.httpPostSB("http://shouganai.net:8080/restaurant/rest/accounts/login", postXML);
    returnacc = (Account) scli.convertXML(currResponse, Account.class);
    
    if (returnacc.accountID != 0) {
      return returnacc;
    }
    return new Account(); 
  }
  
  /**
   * get a list of all available accounts
   * @return List of all accounts
   */
  public List<Account> getAllAccounts() {
    currResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/accounts");
    multiAccs = (Accounts) scli.convertXML(currResponse, Accounts.class);
 
    return multiAccs.getItems();
  }
  
  /**
   * gets a single account by ID from the server/database
   * @param id id of the account wanted
   * @return Account object of the account requested
   */
  public Account getAccount(long id) {
    currResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/accounts/" + id);
    return (Account) scli.convertXML(currResponse, Account.class);
  }
  
  public void logout(){
    this.accountID = 0;
    this.password = "";
    this.accountType = 0;
    this.name = "Guest";
  }
  
  /**
   * Method returns the correct account id, based on an email string
   * @param uname The email belonging to the account to search
   * @return long Id of the correct account to return
   */
  public long getIdFromUsername(String uname) {
    long returnId = 0;
    List<Account> dbAccountList = new ArrayList<Account>();
    dbAccountList = getAllAccounts();
    
    for (Account a: dbAccountList) {
      if (uname.equals(a.getEmail())) {
        returnId = a.getAccountID();
      }
    }
    
    return returnId;
    
  }
  
     
}
