package client.app.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import client.server.connection.ServerClient;

/**
 * 
 * @author amrit ReservationManager class.
 *
 */
public class ReservationManager {

  ArrayList<Table> tables;
  private ArrayList<Reservation> reservations;
    
  //Database Connection
  private ServerClient sercli = new ServerClient();
  private StringBuffer currResponse;
  private Reservations allDbReservations = new Reservations();
  
  /**
   * constructor for reservationManager.
   */
  public ReservationManager() {
    tables = TableList.INSTANCE.getTablesFromDataBase();
    reservations = new ArrayList<Reservation>();
  }

  /**
   * gets reservations
   * @return ArrayList of reservations.
   */
  public ArrayList<Reservation> getReservations() {
    return reservations;
  }

  /**
   * Adds reservation to the reservation manager, only if the table is free.
   * @param reservation that you want to add.
   */
  public void addReservation(Reservation reservation) {
    
    if (isTableFree(reservation)) {
      reservations.add(reservation);
    } else {
      System.out.println("No free table");
    }
  }
  
  /**
   * iterates through reservationslist and removes, the 
   * reservation based on id number
   * @param le (id)
   */
  public void removeReservation(long le) {
    Iterator<Reservation> iter = reservations.iterator();
    while (iter.hasNext()) {
      
      Reservation reservation = iter.next();
      if (reservation.getId() == le) {
        iter.remove();
      }
    }
  }

  /**
   * checks if the table is free, first iterates through size, 
   * to see if theres a big enough table, then iterates through its
   * reservations to check for conflicts
   * @param reservation that you want to add
   * @return a boolean based on whether the table is free.
   */
  public boolean isTableFree(Reservation reservation) {
    for (int i = 0; i < tables.size(); i++) {
      if (tables.get(i).getSize() >= reservation.getNumberOfPeople()) {

        // if there is a table big enough for the reservation
        // the reservations at this table dont conflict
        if (tables.get(i).isReservationNotConflicted(reservation.getTimeOfReservation())) {
          // add the reservation to the specific tables list.
          tables.get(i).addTableReservation(reservation);
          System.out.print("Reservation made for " +  reservation.getTimeOfReservation() + " for "
              + reservation.getNumberOfPeople());
          reservation.setTableid(tables.get(i).getTableID());//sets reservation to the tables id.
          System.out.println(" on table" + tables.get(i).getTableID());
          return true;
        }
      }
    }
    return false;
  }
  
  /**
   * gets reservations from database.
   * @return list of reservations
   */
  public List<Reservation> getReservationsFromDatabase() {
    
    currResponse = sercli.httpGet("http://shouganai.net:8080/restaurant/rest/reservation");
    allDbReservations = (Reservations) sercli.convertXML(currResponse, Reservations.class);    
    return allDbReservations.getReservations();
  }
  
  
  /**
   * Adds reservations to the db, using the id and the getXML.
   * @param reservation you want to add.
   */
  public void addReservationDb(Reservation reservation) {
    sercli.httpPut("http://shouganai.net:8080/restaurant/rest/reservation" + reservation.getId(), reservation.getXML());
  }
  
  /**
   * removes reservation from database based on id.
   * @param id
   */
  public void removeReservationDaB(long id) {
    sercli.httpDelete("http://shouganai.net:8080/restaurant/rest/reservation/"+id);
  }  
  
};