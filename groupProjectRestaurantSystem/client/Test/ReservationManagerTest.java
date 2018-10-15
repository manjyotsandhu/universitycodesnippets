

//package client.Test;

//package client.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import client.app.logic.Reservation;
import client.app.logic.ReservationManager;
import client.app.logic.Table;
import client.app.logic.TableList;
import client.server.connection.ServerClient;

/**
 * tests the reservationmanager class and most of the table and reservations itself.
 * @author amrit
 *
 */
public class ReservationManagerTest {
 
  ReservationManager resManager = new ReservationManager();
  Table tbl1 = new Table(10); 
  Table tbl2 = new Table(20);
  Reservation rsrv1 = new Reservation(1, "test", "101239233", 4, 1530885600 );
  Reservation rsrv2 = new Reservation(2, "test", "101239233", 4, 1530885600 );
  Reservation rsrv3 = new Reservation(3, "test", "101239233", 4, 1530885600 );
  boolean madeReservation;
  private StringBuffer currResponse = new StringBuffer();
  private ServerClient sercli = new ServerClient();
  Reservation newReservation = new Reservation(2, "Amrit", "0893219321321", 4, 1532523600);
  
  @Test
  public void addTabletest() {
    TableList.INSTANCE.addTable(tbl1);
    System.out.println(TableList.INSTANCE.getSize());
    assertEquals(TableList.INSTANCE.getSize(),2);
  }
  
  @Test
  public void addMultipleTables() {
    TableList.INSTANCE.addTable(tbl2);
    assertEquals(5,TableList.INSTANCE.getSize());
  }
  
  @Test
  //Checks whether if you try to create a reservation at the same time on the same table it is conflicted
  public void checkIsTableConflicted() {
    TableList.INSTANCE.addTable(tbl1);
    tbl1.addTableReservation(rsrv1);
    madeReservation = tbl1.isReservationNotConflicted(1530885600);
    assertFalse(madeReservation);
  }
  
  @Test
  public void checkTableisNotConflicted() {
    TableList.INSTANCE.addTable(tbl1);
    tbl1.addTableReservation(rsrv1);
    madeReservation = tbl1.isReservationNotConflicted(153235600);
    assertTrue(madeReservation);
  }
  
  @Test
  public void getTableReservations() {
    TableList.INSTANCE.addTable(tbl1);
    tbl1.addTableReservation(rsrv1);
    tbl1.addTableReservation(rsrv2);
    System.out.println(tbl1.getTableReservations());
    assertEquals(tbl1.getTableReservations().size(),1);
    
  }
  
  @Test
  //checks whether reservations only added through the reservation manager.
  public void checkIsTableFree() {
    TableList.INSTANCE.addTable(tbl1);
    resManager.addReservation(rsrv2);
    resManager.addReservation(rsrv1);
    resManager.addReservation(rsrv1);
    assertEquals(resManager.getReservations().size(), 2);
  }
  
  @Test
  public void doesgettingtablefromdbwork() {
    StringBuffer newXML = newReservation.getXML();
    Reservation new_reservation = new Reservation();
    currResponse = sercli.httpPostSB("http://shouganai.net:8080/restaurant/rest/reservation", newXML);
    System.out.println(newXML);
    Date date = new Date();
    new_reservation = (Reservation) sercli.convertXML(currResponse, Reservation.class);
    System.out.println(sercli.convertXML(currResponse, Reservation.class));
    
    TableList.INSTANCE.getTablesFromDataBase();
    System.out.println(TableList.INSTANCE.getTablesFromDataBase());
    for(Table tbl :TableList.INSTANCE.getTablesFromDataBase()) {
    System.out.println(tbl.toString());
  }
    ArrayList<Reservation> reservations = new ArrayList<>();
    reservations = (ArrayList<Reservation>) resManager.getReservationsFromDatabase();
    for(Reservation res :reservations) {
      System.out.println(res.getId());
    }
    System.out.println(reservations);
    
  }
  
  @Test
  public void conflictingReservations() {
    ReservationManager resmanage = new ReservationManager();
    Reservation test1 = new Reservation(1, "test", "101239233", 4, 1530885600 );
    Reservation test2 = new Reservation(2, "test", "101239233", 4, 1530885600 );
    Reservation test3 = new Reservation(3, "test", "101239233", 4, 1530885600 );
    if(resmanage.isTableFree(test1)) {
        resmanage.addReservation(test1);
      }
    
    if(resmanage.isTableFree(test2)) {
      resmanage.addReservation(test2);
    }
    if(resmanage.isTableFree(test3)) {
      resmanage.addReservation(test3);
    }/* else {
      System.out.println("No free ");
    }*/
}

  
  
  
  
}
