package client.app.logic;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Johannes Herforth.
 *
 */

@XmlRootElement(name = "menuItems")
public class MenuItems {

  @XmlElement(name = "menuItem", type = MenuItem.class)
  private List<MenuItem> setofitems = new ArrayList<>();
  
  public MenuItems() {};
  
  public MenuItems(List<MenuItem> mItems) {
    this.setofitems = mItems;
  }
  
  public List<MenuItem> getItems() {
    return setofitems;
  }
  
  public void setItems(List<MenuItem> items) {
    this.setofitems = items;
  }
  
}
