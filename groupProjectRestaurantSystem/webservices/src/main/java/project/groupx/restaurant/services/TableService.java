package project.groupx.restaurant.services;

import project.groupx.restaurant.database.DatabaseClient;
import project.groupx.restaurant.model.Table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * logic behind webservice communication
 * @author amrit
 *
 */
public class TableService {
  private Table currTable;
  private List<Table> tableList;
  private ResultSet rs;
  private Connection conn;
  
  /**
   * gets a list of all the tables on the database.
   * @return list of all the tables.
   */
  public List<Table> getTables() {
    tableList = new ArrayList<>();
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "SELECT * FROM tables;");
  
    try {
      while (rs.next()) {
        currTable = new Table();
        currTable.setTableID(rs.getLong(1));
        currTable.setSize(rs.getInt(2));
        currTable.setStatus(rs.getInt(3));

        tableList.add(currTable);
      }
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return tableList;
  }  
  
  /**
   * gets specific table based on ID.
   * @param tableId of the table.
   * @return Table with that Id.
   */
  public Table getTable(long tableId) {
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "SELECT * FROM tables WHERE tableid = " + tableId + ";");
  
    try {
      while (rs.next()) {
        currTable = new Table();
        currTable.setTableID(rs.getLong(1));
        currTable.setSize(rs.getInt(2));
        currTable.setStatus(rs.getInt(3));

      }
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return currTable;
  } 
  
  /**
   * method to delete table with id.
   * @param id of table to delete.
   */
  public void deleteTable(long id) {
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "DELETE FROM tables WHERE tableid=" + id + ";");
    try {
      conn.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  /**
   * method to add table and insert into table.
   * @param table your adding
   * @return the table you added.
   */
  public Table addTable(Table table) {
    table.setTableID(this.getNextID());
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    DatabaseClient.executeQuery(conn,
        "INSERT INTO tables (tableId,size,status) "
            + "VALUES (" + table.getTableID() + ", " + table.getSize() + ", "
            + table.getStatus() + ")");

    try {
      conn.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return table;
  }
  
  /**
   * gets the highest ID of Table from database to safely add a new Table with a new ID.
   * 
   * @return next open id to be used.
   */
  public long getNextID() {
    long nextid = 0L;
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "SELECT tableId FROM tables ORDER BY id DESC LIMIT 1;");

    try {

      rs.next();
      nextid = rs.getLong(1);
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return nextid + 1;
  }

  
  
}

