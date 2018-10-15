package client.app.UI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import client.app.logic.Reservation;
import client.app.logic.ReservationManager;
import client.app.swing.objects.NoEditDefaultTable;
import javafx.event.ActionEvent;
import javax.swing.ImageIcon;

/**
 * Reservations UI, displays all the reservations using jtable,
 * also allows showing todays reservations and adding and removing.
 * @author amrit
 *
 */
public class ReservationsUI extends JFrame{

  JFrame frame;
  
  private final String[] reservationsCols = {"ID","Name", "No. People", "Time", "Phone No.", "Table"};
  private Object[][] reservationData = {};
  private NoEditDefaultTable reservationModel = new NoEditDefaultTable(reservationData, reservationsCols);
  private JTable Reservation_table = new JTable(reservationData,reservationsCols);
  
  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          ReservationsUI window = new ReservationsUI();
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
  public ReservationsUI() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();

    frame.getContentPane().setBackground(new Color(224, 255, 255));
    frame.setBounds(100, 100, 683, 429);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);
    Reservation_table.setFont(new Font("Euphemia UCAS", Font.PLAIN, 13));
    Reservation_table.setForeground(new Color(255, 69, 0));
    
    
   

    Reservation_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    Reservation_table.setAutoCreateRowSorter(true);
    ReservationManager resman = new ReservationManager();
    List<Reservation> reservations = new ArrayList<>();
    reservations = resman.getReservationsFromDatabase();
    
    
    for(Reservation resitem : reservations ) {
      long unixSeconds = resitem.getTimeOfReservation();
      Date date = new Date(unixSeconds * 1000L);
      SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy hh z");
      sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
      String formattedDate = sdf.format(date);
      
     
      reservationModel.addRow(new Object[] {resitem.getId(), resitem.getCustomerName(), resitem.getNumberOfPeople(),formattedDate, resitem.getPhoneNumber(), resitem.getTableid()});
    }
    
    
    
    Reservation_table.setModel(reservationModel);
    
    JButton btnNewButton = new JButton("");
    btnNewButton.setIcon(new ImageIcon(ReservationsUI.class.getResource("/client/app/btnAdd.png")));
    btnNewButton.setBorder(null);
    btnNewButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        
        frame.dispose();
        AddReservationUI addRes = new AddReservationUI();
        addRes.frame.setVisible(true);
      }

      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {

        frame.dispose();
        AddReservationUI addRes = new AddReservationUI();
        addRes.frame.setVisible(true);
        
      }

    });
    btnNewButton.setBounds(552, 135, 131, 33);
    frame.getContentPane().add(btnNewButton);
    
    JButton btnRemove = new JButton("");
    btnRemove.setIcon(new ImageIcon(ReservationsUI.class.getResource("/client/app/btnRemove.png")));
    btnRemove.setBorder(null);
    btnRemove.addActionListener(new ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent arg0) {
        if (Reservation_table.getSelectedRow() != -1) {
          long id = (long)Reservation_table.getValueAt(Reservation_table.getSelectedRow(), 0);
          //System.out.println(id);
          resman.removeReservationDaB(id);
          resman.removeReservation(id);
          reservationModel.removeRow(Reservation_table.getSelectedRow());
          
        }
      }
    });
    btnRemove.setBounds(552, 169, 131, 42);
    frame.getContentPane().add(btnRemove);
    
    JLabel lblReservations = new JLabel("Reservations");
    lblReservations.setForeground(new Color(255, 69, 0));
    lblReservations.setFont(new Font("Euphemia UCAS", Font.PLAIN, 20));
    lblReservations.setBounds(248, 14, 136, 24);
    frame.getContentPane().add(lblReservations);
    
    JScrollPane Reservationsscrollpane = new JScrollPane();
    Reservationsscrollpane.setBounds(12, 50, 542, 300);
    frame.getContentPane().add(Reservationsscrollpane);
    
    Reservationsscrollpane.setOpaque(false);
    Reservationsscrollpane.getViewport().setOpaque(false);
    Reservation_table.setOpaque(false);
    ((DefaultTableCellRenderer) Reservation_table.getDefaultRenderer(Object.class))
        .setOpaque(false);
    Reservation_table.setShowGrid(false);
    
    Reservationsscrollpane.setViewportView(Reservation_table);
    
    JButton btnTodays = new JButton("");
    btnTodays.setIcon(new ImageIcon(ReservationsUI.class.getResource("/client/app/btnToday.png")));
    btnTodays.setBorder(null);
    btnTodays.addActionListener(new ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent arg0) {

       reservationModel.setRowCount(0);
       ReservationManager resman = new ReservationManager();
       List<Reservation> reservations = new ArrayList<>();
       reservations = resman.getReservationsFromDatabase();
       
       for(Reservation resitem : reservations ) {
         long unixSeconds = resitem.getTimeOfReservation();
         Date date = new Date(unixSeconds * 1000L);
         SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy z");
         sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
         String formattedDate = sdf.format(date);
         Date now = new Date();
         String strDate = sdf.format(now);
                  
         if(formattedDate.equals(strDate)) {               
           reservationModel.addRow(new Object[] {resitem.getId(), resitem.getCustomerName(), resitem.getNumberOfPeople(),formattedDate, resitem.getPhoneNumber(), resitem.getTableid()});
         }
       }
      }
    });
    btnTodays.setBounds(552, 97, 131, 33);
    frame.getContentPane().add(btnTodays);
    
    JButton btnAll = new JButton("");
    btnAll.setIcon(new ImageIcon(ReservationsUI.class.getResource("/client/app/btnAll.png")));
    btnAll.setBackground(new Color(255, 255, 255));
    btnAll.setBorder(null);
    btnAll.addActionListener(new ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        
        reservationModel.setRowCount(0);
        ReservationManager resman = new ReservationManager();
        List<Reservation> reservations = new ArrayList<>();
        reservations = resman.getReservationsFromDatabase();
        
        for(Reservation resitem : reservations ) {
          
          long unixSeconds = resitem.getTimeOfReservation();
          Date date = new Date(unixSeconds * 1000L);
          SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy hh z");
          sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
          String formattedDate = sdf.format(date);
          
          reservationModel.addRow(new Object[] {resitem.getId(), resitem.getCustomerName(), resitem.getNumberOfPeople(),formattedDate, resitem.getPhoneNumber(), resitem.getTableid()});
        }
        
      }
    });
    btnAll.setBounds(552, 50, 131, 42);
    frame.getContentPane().add(btnAll);
    
    JLabel lblNewLabel = new JLabel("");
    lblNewLabel.setIcon(new ImageIcon(ReservationsUI.class.getResource("/client/app/background.png")));
    lblNewLabel.setBounds(-14, -13, 714, 431);
    frame.getContentPane().add(lblNewLabel);
  }
}