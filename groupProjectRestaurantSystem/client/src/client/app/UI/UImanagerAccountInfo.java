package client.app.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import client.app.logic.Account;
import client.app.logic.Menu;
import client.app.logic.MenuItem;
import client.app.logic.Order;
import client.app.logic.Orders;
import client.app.swing.objects.NoEditDefaultTable;
import client.server.connection.ServerClient;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPasswordField;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


/*
 * @author Johannes Herforth.
 */

public class UImanagerAccountInfo extends JFrame {

  ;
  private JPanel contentPane;

  private JPasswordField passwordField;
  private final JTable table = new JTable();
  private final JLabel lblName = new JLabel("Name:");
  private final JLabel lblPrice = new JLabel("Position:");
  private final JLabel lblNewLabel = new JLabel("Email:");
  private final JButton btnCancel = new JButton("Cancel");
  private final JLabel lblId = new JLabel("ID:");
  private final JLabel label = new JLabel("?");
  private MenuItem selection = new MenuItem();
  private Menu menu = new Menu();
  private final JLabel label_1 = new JLabel("?");
  private final JLabel label_2 = new JLabel("?");
  private final JLabel label_3 = new JLabel("?");
  private final JLabel lblSales = new JLabel("Total Sales:");
  private final JLabel label_4 = new JLabel("?");
  private ServerClient scli = new ServerClient();
  private int acctype = 0;


  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          UImanagerAccountInfo frame = new UImanagerAccountInfo();
          frame.setVisible(true);

        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the frame.
   */
  public UImanagerAccountInfo() {



    initGUI();
  }

  private void initGUI() {
    setTitle("Account View");
    setResizable(false);
    setForeground(Color.WHITE);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setBounds(100, 100, 720, 420);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);
    lblName.setBounds(227, 53, 61, 16);

    contentPane.add(lblName);
    lblPrice.setBounds(422, 53, 61, 16);

    contentPane.add(lblPrice);
    
    if(acctype == 1) {
      lblSales.setBounds(418, 223, 95, 16);
    
      contentPane.add(lblSales);
    }
    
    lblNewLabel.setBounds(227, 139, 61, 16);

    contentPane.add(lblNewLabel);
    btnCancel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        dispose();
      }
    });
    btnCancel.setBounds(220, 320, 117, 29);

    contentPane.add(btnCancel);
    lblId.setBounds(228, 226, 18, 16);

    contentPane.add(lblId);
    label.setBounds(258, 226, 61, 16);

    contentPane.add(label);
    label_1.setBounds(227, 81, 183, 16);

    contentPane.add(label_1);
    label_2.setBounds(422, 81, 144, 16);

    contentPane.add(label_2);
    label_3.setBounds(227, 167, 256, 16);

    contentPane.add(label_3);
    
    if(acctype == 1) {
      label_4.setBounds(499, 223, 183, 16);
    
      contentPane.add(label_4);
    }

  }

  public UImanagerAccountInfo(long id) {
    Account currAccount = new Account();
    currAccount = currAccount.getAccount(id);
    acctype = currAccount.getAccountType();
    int orderamount = 0;
    Orders placeholder = new Orders();
    placeholder = (Orders) scli.convertXML(scli.httpGet("http://shouganai.net:8080/restaurant/rest/orders"), Orders.class);
    
    if(acctype == 1) {
      for(Order ord : placeholder.getOrders()) {
        if(ord.getWaiterId() == currAccount.getAccountID()) {
          orderamount = orderamount + 1;
        }
      }
      label_4.setText("" + orderamount);
    }
    
    
    
    label.setText("" + currAccount.getAccountID());
    label_1.setText(currAccount.getName());
    
    if(acctype == 0) {
      label_2.setText("Customer");
    }else if(acctype == 1) {
      label_2.setText("Waiter");
    }else if(acctype == 2) {
      label_2.setText("Manager");
    }else if(acctype == 3) {
      label_2.setText("Kitchen Staff");
    }
    label_3.setText(currAccount.getEmail());



    initGUI();

  }
}
