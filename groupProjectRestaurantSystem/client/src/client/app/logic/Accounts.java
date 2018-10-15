/**
 * 
 */
package client.app.logic;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Johannes Herforth.
 *
 */

@XmlRootElement(name = "accounts")
public class Accounts {

  @XmlElement(name = "account", type = Account.class)
  private List<Account> setofitems = new ArrayList<>();
  
  public Accounts() {};
  
  public Accounts(List<Account> listOfAccs) {
    this.setofitems = listOfAccs;
  }
  
  public List<Account> getItems() {
    return setofitems;
  }
  
  public void setItems(List<Account> listOfAccs) {
    this.setofitems = listOfAccs;
  }
  
}
