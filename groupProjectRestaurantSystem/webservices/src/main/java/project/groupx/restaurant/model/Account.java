/**
 * the Account class is a general class in which all the other account classes will extend from. It contains trivial information
 * that will be found in all of the different account extended classes.
 */
package project.groupx.restaurant.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author Johannes Herforth
 *
 */
@XmlRootElement(name = "account")
public class Account {
  
  //ACCOUNT values needed.
  private long accountID;
  private String name;
  private int accountType;
  private String password;
  private String email;
  
  //empty constructor.
  public Account(){
    this.accountID = 0;
    this.accountType = 0;
    this.name = "Guest";
  }
  
  /**
   * get accountID of account
   * @return account ID
   */
  public long getAccountID() {
    return accountID;
  }

  /**
   * set accountID of account
   * @param accountID new accountID
   */
  public void setAccountID(long accountID) {
    this.accountID = accountID;
  }
  /**
   * get name of account
   * @return account name
   */
  public String getName() {
    return name;
  }
  /**
   * set name of account
   * @param name new name
   */
  public void setName(String name) {
    this.name = name;
  }
  /**
   * get accountType of account
   * @return account accountType
   */
  public int getAccountType() {
    return accountType;
  }
  /**
   * set accountType of account
   * @param accountType new accountType 
   */
  public void setAccountType(int accountType) {
    this.accountType = accountType;
  }
  /**
   * get password of account
   * @return account password
   */
  public String getPassword() {
    return password;
  }
  /**
   * set password of account
   * @param password new password
   */
  public void setPassword(String password) {
    this.password = password;
  }
  /**
   * get email of account
   * @return account email
   */
  public String getEmail() {
    return email;
  }
  
  /**
   * set email of account
   * @param email new email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * converts plain text to SHA-256 encrypted hash to store on the database
   * @param password plaintext password
   * @return SHA-256 hashed password
   */
  public String toHash(String password) {
    try {
      MessageDigest shaIns = MessageDigest.getInstance("SHA-256");
      byte[] hash = shaIns.digest(password.getBytes("UTF-8"));
      return DatatypeConverter.printHexBinary(hash);
    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "invalid";
  }
  
}