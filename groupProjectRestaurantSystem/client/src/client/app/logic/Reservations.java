package client.app.logic;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Reservations class, to be used with the server.
 * @author amrit.
 *
 */
@XmlRootElement(name = "reservations")
public class Reservations {

  @XmlElement(name = "Reservation", type = Reservation.class)
  private List<Reservation> reservationset = new ArrayList<>();
  /**
   * empty constructor
   */
  public Reservations(){};
  
  /**
   * Constructor with reservations
   * @param reservations
   */
  public Reservations(List<Reservation> reservations) {
    this.reservationset = reservations;
  }
  
  /**
   * returns all reservation.
   * @return
   */
  public List<Reservation> getReservations() {
    return reservationset;
  }
  
  /**
   * sets the reservations.
   * @param reservations
   */
  public void setReservations(List<Reservation> reservations) {
    this.reservationset = reservations;
  }
}
