package client.app.UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import client.app.logic.Reservation;
import client.app.logic.ReservationManager;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Window;
import java.awt.Color;
import javax.swing.ImageIcon;

/**
 * 
 * @author amrit, ui for adding a new reservation, which checks whether 
 * the new reservation will conflict, and to parse all the data to the database.
 *
 */
public class AddReservationUI extends JFrame{

  JFrame frame;
  
  private JTextField Name;
  private JTextField numberpeople;
  
  ReservationsUI reservationsbox = new ReservationsUI();
  ReservationManager resmanage = new ReservationManager();
  private JTextField phonenumber;
  private JTextField Time;
  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          AddReservationUI window = new AddReservationUI();
          window.frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public AddReservationUI() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    
    frame = new JFrame();
    frame.getContentPane().setBackground(new Color(224, 255, 255));
    frame.setBounds(100, 100, 609, 454);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);
    
    JLabel lblName = new JLabel("Name");
    lblName.setForeground(new Color(255, 69, 0));
    lblName.setFont(new Font("Euphemia UCAS", Font.PLAIN, 15));
    lblName.setBounds(27, 96, 84, 39);
    frame.getContentPane().add(lblName);
    
    Name = new JTextField();
    Name.setBackground(new Color(0, 0, 0));
    Name.setBorder(null);
    Name.setForeground(new Color(255, 69, 0));
    Name.setBounds(148, 105, 358, 31);
    frame.getContentPane().add(Name);
    Name.setColumns(10);
    
    JLabel lblNopeople = new JLabel("No.People");
    lblNopeople.setForeground(new Color(255, 69, 0));
    lblNopeople.setFont(new Font("Euphemia UCAS", Font.PLAIN, 15));
    lblNopeople.setBounds(27, 163, 109, 39);
    frame.getContentPane().add(lblNopeople);
    
    numberpeople = new JTextField();
    numberpeople.setBorder(null);
    numberpeople.setBackground(new Color(0, 0, 0));
    numberpeople.setForeground(new Color(255, 69, 0));
    numberpeople.setColumns(10);
    numberpeople.setBounds(148, 172, 358, 31);
    frame.getContentPane().add(numberpeople);
    
    JLabel lblPhoneNo = new JLabel("Phone No.");
    lblPhoneNo.setForeground(new Color(255, 69, 0));
    lblPhoneNo.setFont(new Font("Euphemia UCAS", Font.PLAIN, 15));
    lblPhoneNo.setBounds(27, 225, 109, 39);
    frame.getContentPane().add(lblPhoneNo);
    
    JLabel lblTime = new JLabel("Time");
    lblTime.setForeground(new Color(255, 69, 0));
    lblTime.setFont(new Font("Euphemia UCAS", Font.PLAIN, 15));
    lblTime.setBounds(27, 294, 64, 39);
    frame.getContentPane().add(lblTime);
    
    JButton btnConfirmReservation = new JButton("");
    btnConfirmReservation.setIcon(new ImageIcon(AddReservationUI.class.getResource("/client/app/Group 40.png")));
    btnConfirmReservation.setFont(new Font("Tahoma", Font.PLAIN, 20));
    btnConfirmReservation.setBorder(null);
    btnConfirmReservation.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        
        String data2 = Name.getText();
        String data3 = numberpeople.getText();
        String data4 = phonenumber.getText();
        String data5 = Time.getText();
        
        //Wed, 5 Jul 2018 3 GMT IS THE FORMAT IT NEEDS TO BE.
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy hh z");
        Date date = null;
        try {
          date = dateFormat.parse(data5);
        } catch (ParseException e) {
          e.printStackTrace();
        }
        
        long unixTime = ((long) date.getTime()/1000);
        
        //creates new reservation
        Reservation new_reservation = new Reservation();
        new_reservation.setCustomerName(data2);
        new_reservation.setNumberOfPeople(Integer.parseInt(data3));
        new_reservation.setPhoneNumber(data4);
        new_reservation.setTimeOfReservation(unixTime);
        new_reservation.setTableid(0);
        //creates new reservation manager and adds all the reservations off the database.        
        ReservationManager newresman = new ReservationManager();
        ArrayList<Reservation> reservations = (ArrayList<Reservation>) newresman.getReservationsFromDatabase();
        for(Reservation res: reservations) {
          newresman.addReservation(res);
        }
        //if the new reservation is conflicting dont add it.
        if (newresman.isTableFree(new_reservation)) {
          //add reservation to database
          Reservation new_reservation2 = new_reservation.confirmReservation(new_reservation.getXML());
         
          if (new_reservation.getTableid()!= 0) {
            JOptionPane.showMessageDialog(frame, "confirmed reservation");
            frame.dispose();
            ReservationsUI reservationsboxnew = new ReservationsUI();
            reservationsboxnew.frame.setVisible(true);
          } else {
            JOptionPane.showMessageDialog(frame, "Error");
          }
          
        } else {
          JOptionPane.showMessageDialog(frame, "Table not free");
        }
      }
    });
    

    btnConfirmReservation.setBounds(27, 358, 322, 55);
    frame.getContentPane().add(btnConfirmReservation);
    
    JButton btnCancel = new JButton("");
    
    btnCancel.setIcon(new ImageIcon(AddReservationUI.class.getResource("/client/app/Group 42.png")));
    btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 20));
    btnCancel.setBorder(null);
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.dispose();        
        reservationsbox.frame.setVisible(true);
        
      }
    });
    btnCancel.setBounds(361, 358, 181, 55);
    frame.getContentPane().add(btnCancel);
    
    JLabel lblReservation = new JLabel("Reservation");
    lblReservation.setForeground(new Color(255, 69, 0));
    lblReservation.setFont(new Font("Euphemia UCAS", Font.PLAIN, 23));
    lblReservation.setBounds(233, 31, 140, 39);
    frame.getContentPane().add(lblReservation);
    
    phonenumber = new JTextField();
    phonenumber.setBorder(null);
    phonenumber.setBackground(new Color(0, 0, 0));
    phonenumber.setForeground(new Color(255, 69, 0));
    phonenumber.setBounds(148, 234, 358, 31);
    frame.getContentPane().add(phonenumber);
    phonenumber.setColumns(10);
    
    Time = new JTextField();
    Time.setBorder(null);
    Time.setBackground(new Color(0, 0, 0));
    Time.setForeground(new Color(255, 69, 0));
    Time.setBounds(148, 303, 358, 31);
    frame.getContentPane().add(Time);
    Time.setColumns(10);
    
    JLabel lblEnterTimeIn = new JLabel("Enter time in format: Wed, 5 Jul 2018 13 GMT");
    lblEnterTimeIn.setForeground(new Color(255, 69, 0));
    lblEnterTimeIn.setFont(new Font("Euphemia UCAS", Font.PLAIN, 15));
    lblEnterTimeIn.setBounds(126, 270, 397, 31);
    frame.getContentPane().add(lblEnterTimeIn);
    
    JLabel label = new JLabel("");
    label.setIcon(new ImageIcon(AddReservationUI.class.getResource("/client/app/background.png")));
    label.setBounds(-19, -140, 647, 748);
    frame.getContentPane().add(label);
  }
}
