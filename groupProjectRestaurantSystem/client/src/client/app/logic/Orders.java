package client.app.logic;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Manjyot
 *
 */

@XmlRootElement(name = "orders")
public class Orders {

  @XmlElement(name = "order", type = Order.class)
  
  private List<Order> setoforders = new ArrayList<>();
  
  public Orders() {};
  
  public Orders(List<Order> orders) {
    this.setoforders = orders;
  }
  
  public List<Order> getOrders() {
    return setoforders;
  }
  
  public void setOrders(List<Order> orders) {
    this.setoforders = orders;
  }
  
}