package client.app.logic;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author manjyot
 *
 */

@XmlRootElement(name = "customers")
public class Customers {
  
  @XmlElement(name = "customer", type = Customer.class)
  
  private List<Customer> setofcustomers = new ArrayList<>();
  
  public Customers() {};
  
  public Customers(List<Customer> customers) {
    this.setofcustomers = customers;
  }
  
  public List<Customer> getCustomers() {
    return setofcustomers;
  }
  
  public void setCustomers(List<Customer> customers) {
    this.setofcustomers = customers;
  }

}
