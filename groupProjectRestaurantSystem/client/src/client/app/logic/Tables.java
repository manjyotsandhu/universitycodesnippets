package client.app.logic;


import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Tables class to use for xml, db
 * @author amrit.
 *
 */
@XmlRootElement(name = "tables")
public class Tables {
  @XmlElement(name = "Table", type = Table.class)
 
  private ArrayList<Table> tableset = new ArrayList<>();
  
  /**
   * empty constructor
   */
  public Tables(){};
  
  /**
   * creates tableset using arraylist
   * @param tables
   */
  public Tables(ArrayList<Table> tables) {
    this.tableset = tables;
  }
  
  /**
   * returns tableset, (Arraylist)
   * @return arraylist
   */
  public ArrayList<Table> getTables() {
    return tableset;
  }
    
  /**
   * set tables
   * @param tables
   */
  public void setTables(ArrayList<Table> tables) {
    this.tableset = tables;
  }
}


