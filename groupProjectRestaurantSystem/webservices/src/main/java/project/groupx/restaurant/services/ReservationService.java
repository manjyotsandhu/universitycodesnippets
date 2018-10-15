/**
 * ReservationService shows all the business logic behind the communication between the webservice
 * and database.
 */
package project.groupx.restaurant.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import project.groupx.restaurant.database.DatabaseClient;
import project.groupx.restaurant.model.Reservation;

/**
 * @author Johannes Herforth.
 *
 */
public class ReservationService {

  private Reservation currRes;
  private List<Reservation> resList;
  private ResultSet rs;
  private Connection conn;


  /**
   * gets a list of all the reservations that are on the database.
   * 
   * @return all the reservations.
   */
  public List<Reservation> getReservations() {
    resList = new ArrayList<>();
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "SELECT * FROM reservations;");

    try {
      while (rs.next()) {
        currRes = new Reservation();
        currRes.setId(rs.getInt(1));
        currRes.setTableid(rs.getInt(2));
        currRes.setCustomerName(rs.getString(3));
        currRes.setPhoneNumber(rs.getString(4));
        currRes.setNumberOfPeople(rs.getInt(5));
        currRes.setTimeOfReservation(rs.getLong(6));

        resList.add(currRes);
      }
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return resList;
  }

  /**
   * gets the specific reservation that has the ID.
   * 
   * @param id of the reservation.
   * @return Reservation of the certain ID.
   */
  public Reservation getReservation(long id) {
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "SELECT * FROM reservations WHERE id = " + id + ";");

    try {
      while (rs.next()) {
        currRes = new Reservation();
        currRes.setId(rs.getInt(1));
        currRes.setTableid(rs.getInt(2));
        currRes.setCustomerName(rs.getString(3));
        currRes.setPhoneNumber(rs.getString(4));
        currRes.setNumberOfPeople(rs.getInt(5));
        currRes.setTimeOfReservation(rs.getLong(6));

      }
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return currRes;
  }

  /**
   * deletes the reservation with the specific id.
   * 
   * @param id of reservation to be deleted.
   */
  public void deleteReservation(long id) {
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "DELETE FROM reservations WHERE id=" + id + ";");
    try {
      conn.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  /**
   * adds a new reservation onto the database
   * @param reserve new reservation to add
   * @return new reservation added.
   */
  public Reservation addReservation(Reservation reserve) {
    reserve.setId(this.getNextID());
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    DatabaseClient.executeQuery(conn,
        "INSERT INTO reservations (id,tableid,customername,phonenumber,numberofpeople,timeofreservation) "
            + "VALUES (" + reserve.getId() + ", " + reserve.getTableid() + ", '"
            + reserve.getCustomerName() + "', '" + reserve.getPhoneNumber() + "', "
            + reserve.getNumberOfPeople() + ", " + reserve.getTimeOfReservation() + ")");

    try {
      conn.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return reserve;
  }

  /**
   * gets the highest ID of Reservation from database to safely add a new Reservation with a new ID.
   * 
   * @return next open id to be used.
   */
  public long getNextID() {
    long nextid = 0L;
    conn = DatabaseClient.getInstance().getConnection();
    System.out.println("CONNECTED!");
    rs = DatabaseClient.executeQuery(conn, "SELECT id FROM reservations ORDER BY id DESC LIMIT 1;");

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
