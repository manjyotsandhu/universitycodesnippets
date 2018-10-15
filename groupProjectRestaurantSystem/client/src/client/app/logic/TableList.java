package client.app.logic;

import java.util.ArrayList;

import client.server.connection.ServerClient;
/**
 * 
 * @author amrit
 * TableList singleton.
 *
 */

public enum TableList{
  INSTANCE;
  
  private ServerClient sercli = new ServerClient();
  private StringBuffer currResponse;  
  private Tables allDbTables = new Tables();
  ArrayList<Table> tables = new ArrayList<>();
  
  /**
   * method to add table to table list.
   * @param table.
   */
  public void addTable(Table table) {
    tables.add(table);
  }
  
  /**
   * method to add table to database.
   * @param table.
   */
  public void addDbTable(Table table) {
    addTable(table);
    sercli.httpPut("http://shouganai.net:8080/restaurant/rest/tables" + table.getTableID(), table.getXML());
  }

  /**
   * method to remove table from table list.
   * @param table.
   */
  public void removeTable(Table table) {
    tables.remove(tables.indexOf(table));
  }
  
  /**
   * method to remove table from database.
   * @param table.
   */
  public void removeDbTable(Table table) {
    removeTable(table);
    sercli.httpDelete("http://shouganai.net:8080/restaurant/rest/tables" + table.getTableID());

  }
  
  
  /**
   * method to get a array list of all the tables.
   * @return An arraylist of type table.
   */
  public ArrayList<Table> getTableList() {
    return tables;
  }
  
  /**
   * method to get Tables from Database.
   * @return ArrayList of Table.
   */
  public ArrayList<Table> getTablesFromDataBase() {
    currResponse = sercli.httpGet("http://shouganai.net:8080/restaurant/rest/tables");
    allDbTables= (Tables) sercli.convertXML(currResponse, Tables.class);    
    return allDbTables.getTables();
  }
  // have to add a database table for tables to be able to compare

  /**
   * gets the number of tables.
   * @return an int of the number of tables.
   */
  public int getSize() {
    return tables.size();
  }

}
