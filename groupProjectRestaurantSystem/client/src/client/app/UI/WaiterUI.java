package client.app.UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.app.logic.Account;
import client.app.logic.Customer;
import client.app.logic.CustomerManager;
import client.app.logic.Order;
import client.app.logic.Waiter;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.awt.Color;
import java.awt.Component;

import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.ComponentOrientation;
import java.util.*;
import javax.swing.ListSelectionModel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import java.awt.TextArea;

/**************************************************************************************************
 * Class which creates a GUI interface for the waiter to use. Includes: - Tables
 * panel to confirm and cancel orders - Orders panel to display all
 * orders/assigned orders in order they were placed - Calls panel to display all
 * calls/assigned calls and allows to resolve calls
 * 
 * @author Youcef,Manjyot
 * 
 *         *************************************************************************************************
 */
@SuppressWarnings("serial")
public class WaiterUI extends JFrame {

  // Real life entities
  private static Account waiterAccount = new Account();
  private static Waiter w = new Waiter(1, "Name");
  private static CustomerManager custManager = new CustomerManager();

  // Amount of tables
  private static final int AMOUNT_OF_TABLES = 6;

  // Lists of orders
  static List<Order> orderListFromDb;
  static List<Order> assignedOrdersFromDb;
  static List<Order> sortedByTimeOrders;
  private static Order[] tableOrder = new Order[AMOUNT_OF_TABLES + 1]; // The
                                                                       // orders

  // flags watched by listener thread
  private static boolean confirmFlag;
  private static boolean cancelFlag;
  private static boolean deliveredFlag;
  private static int tableView;
  private static boolean[] tablePress = new boolean[AMOUNT_OF_TABLES + 1]; // array
                                                                           // to
                                                                           // check
                                                                           // which
                                                                           // table
                                                                           // is
                                                                           // clicked

  // J Components
  private JPanel infoPanel;
  private JPanel infoPanel_1;
  private JPanel contentPane;
  private JLabel loginLbl;
  private static JLabel timeLbl;
  private JButton logoutBtn;
  private JButton tablesBtn;
  private JButton feedBtn;
  private JPanel tablesPanel;
  private static JButton table1Btn;
  private static JButton table2Btn;
  private static JButton table3Btn;
  private static JButton table4Btn;
  private static JButton table5Btn;
  private static JButton table6Btn;
  private static TextArea orderArea;
  private static JPanel feedPanel;
  private JPanel seat1Panel;
  private static JPanel ordersByTimePanel;
  private JLabel tableTxt;
  private JLabel orderTxt;
  private JLabel notesTxt;
  private JLabel priceTxt;
  private JButton InstructionBtn;
  private static JLabel tableLbl;
  private static JLabel priceLbl;
  private static JButton confirmBtn;
  private static JButton cancelBtn;
  private static JButton deliverBtn;
  private JButton ordersBtn;
  private JLabel payedTxt;
  private static JLabel assignedLbl;

  // for orders panel
  private JLabel lblOrderpanelid;
  private JLabel lblOrderpaneltableid;
  private JLabel lblOrderpanelcost;
  private JLabel lblOrderpaneltimeplaced;
  private JLabel lblOrderpanelstage;
  private static JList<Order> orderlist;
  private JLabel stageTxt;
  private JLabel lblProgressBarLabel;
  private static DefaultListModel<Order> ordersModel;

  private static JLabel stageLbl;
  private static JLabel orderTimeLbl;
  private static JLabel payedLbl;
  private static DefaultListModel<Order> waitersOrdersModel;
  private static JList<Order> waiterOrderList;
  private JLabel totalOrderNum;
  private static Order selectedOrder;

  // for feed panel
  private static List<Customer> customersCalling;
  private static DefaultListModel<Customer> custCallingModel;
  private static JList<Customer> feedList;
  private static Customer selectedCustomer;
  private static Border border;
  private static boolean selectedCustomerSetUp = false;
  private static ArrayList<Customer> assignedCustomersCalling;
  private static DefaultListModel<Customer> waitersCallingModel;
  private static JList<Customer> waiterFeedList;
  private static ArrayList<Order> waiterAssignedOrders;
  private static List<Order> ordersFromDbTemp;
  private JLabel lblNewLabel_2;
  private JLabel label;
  private JButton ReservationButton;
  
  //For workers/threads
  private List<SwingWorker<Void, Void>> waiterWorkers = new ArrayList<>();

  /**
   * On tables view: All of youcefs threads (4)
   * 
   */

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          WaiterUI frame = new WaiterUI();
          frame.setVisible(true);
          frame.updateTables(0);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

  }

  /**
   * Method to stop all of the workers in the waiterWorkers arrayList
   */
  private void stopWorkers() {
    for (int i = 0; i < waiterWorkers.size(); i++) {
      waiterWorkers.get(i).cancel(true);
    }
  }

  /**
   * Worker thread Which updates the tables orders using getTableOrder and
   * getOrder method in waiter class
   */
  private void updateTables(int threadid) {
    this.stopWorkers();

    if (threadid == 0) {
      SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

        @Override
        protected Void doInBackground() throws Exception {
          for (int i = 1; i <= AMOUNT_OF_TABLES; i++) {// search all orders for
                                                       // the one which matches
                                                       // the table i, and store
                                                       // in tableOrder [i]
            tableOrder[i] = w.getTableOrder(i);
          }
          while (true) {
            System.out.println("update tables");
            for (int i = 1; i <= AMOUNT_OF_TABLES; i++) {// search all orders for
              // the one which matches
              // the table i, and store
              // in tableOrder [i]
              tableOrder[i] = w.getTableOrder(i);
              Thread.sleep(1000);
              if (tableOrder[i].getId() == 0) {// if an orders id is 0 it means
                                               // no order with table i is found
                tableOrder[i] = w.getTableOrder(i); // so check again through
                                                    // all orders
                Thread.sleep(1000);
              }
            }
            

            // w.resetOrder(tableOrder[2]);//used for manual resetting leave
            // commented out
          }
        }
      };
      waiterWorkers.add(worker);
      worker.execute();

      this.setBackgroundColours();
      this.clock();
      this.tableBtnPressListener();
      this.waiterActionListener();

    } else if (threadid == 1) {
      this.updateFeedAndOrders();
      this.clock();
    }

  }

  /**
   * Thread to update the feed and orders panel every 5 seconds
   */
  private void updateFeedAndOrders() {
    SwingWorker<Void, Void> feedOrdersWorker = new SwingWorker<Void, Void>() {

      @Override
      protected Void doInBackground() throws Exception {
        while (true) {
          System.out.println("feed & orders");
          initialiseOrdersPanel();
          initialiseFeedPanel();
          Thread.sleep(500);
        }

      }
    };

    waiterWorkers.add(feedOrdersWorker);
    feedOrdersWorker.execute();
  }

  /**
   * Thread to update the background colours on the tables panel
   */
  public void setBackgroundColours() {

    SwingWorker<Void, Void> bgColoursWorker = new SwingWorker<Void, Void>() {
      
      @Override
      protected Void doInBackground() throws Exception {
        while (true) {
          System.out.println("COLOURS");
          Thread.sleep(6000);
          for (int i = 1; i <= AMOUNT_OF_TABLES; i++) {
            setBtnColours(tableOrder[i]);
          }
        }

      }
    };

    waiterWorkers.add(bgColoursWorker);
    bgColoursWorker.execute();

  }

  /**
   * sets the button colour of a table based on int returned by checkUnpaid
   * method **see checkUnpaid** in Waiter class
   * 
   * @param o
   */
  public void setBtnColours(Order o) { // For later iterations this code can be
                                       // made very robust by using an arraylist
                                       // of JButtons
    int table = o.getTableId();
    if (table == 0) {
      return;
    }
    if (table == 1) {
      switch (w.checkUnpaid(o)) {
      case 1:
        table1Btn.setBackground(Color.WHITE);
        break;
      case 2:
        table1Btn.setBackground(Color.ORANGE);
        break;
      case 3:
        table1Btn.setBackground(Color.BLUE);
        break;
      case 4:
        table1Btn.setBackground(Color.GREEN);
        break;
      case 5:
        table1Btn.setBackground(Color.RED);
        break;
      }
    } else if (table == 2) {
      switch (w.checkUnpaid(o)) {
      case 1:
        table2Btn.setBackground(Color.WHITE);
        break;
      case 2:
        table2Btn.setBackground(Color.ORANGE);
        break;
      case 3:
        table2Btn.setBackground(Color.BLUE);
        break;
      case 4:
        table2Btn.setBackground(Color.GREEN);
        break;
      case 5:
        table2Btn.setBackground(Color.RED);
        break;
      }
    } else if (table == 3) {
      switch (w.checkUnpaid(o)) {
      case 1:
        table3Btn.setBackground(Color.WHITE);
        break;
      case 2:
        table3Btn.setBackground(Color.ORANGE);
        break;
      case 3:
        table3Btn.setBackground(Color.BLUE);
        break;
      case 4:
        table3Btn.setBackground(Color.GREEN);
        break;
      case 5:
        table3Btn.setBackground(Color.RED);
        break;
      }
    } else if (table == 4) {
      switch (w.checkUnpaid(o)) {
      case 1:
        table4Btn.setBackground(Color.WHITE);
        break;
      case 2:
        table4Btn.setBackground(Color.ORANGE);
        break;
      case 3:
        table4Btn.setBackground(Color.BLUE);
        break;
      case 4:
        table4Btn.setBackground(Color.GREEN);
        break;
      case 5:
        table4Btn.setBackground(Color.RED);
        break;
      }
    } else if (table == 5) {
      switch (w.checkUnpaid(o)) {
      case 1:
        table5Btn.setBackground(Color.WHITE);
        break;
      case 2:
        table5Btn.setBackground(Color.ORANGE);
        break;
      case 3:
        table5Btn.setBackground(Color.BLUE);
        break;
      case 4:
        table5Btn.setBackground(Color.GREEN);
        break;
      case 5:
        table5Btn.setBackground(Color.RED);
        break;
      }
    } else if (table == 6) {
      switch (w.checkUnpaid(o)) {
      case 1:
        table6Btn.setBackground(Color.WHITE);
        break;
      case 2:
        table6Btn.setBackground(Color.ORANGE);
        break;
      case 3:
        table6Btn.setBackground(Color.BLUE);
        break;
      case 4:
        table6Btn.setBackground(Color.GREEN);
        break;
      case 5:
        table6Btn.setBackground(Color.RED);
        break;
      }
    }
  }

  /**
   * Thread Acts as a Listener. Listens for a confirm,cancel,delivered or
   * complete click on orders in the tables panel
   */
  public void waiterActionListener() {
    SwingWorker<Void, Void> waitersActionWorker = new SwingWorker<Void, Void>() {

      @Override
      protected Void doInBackground() throws Exception {
        while (true) {
          System.out.println("waiter action");
          checkConfirmClick(tableView);
          Thread.sleep(500);
          checkCancelClick(tableView);
          Thread.sleep(500);
          checkdeliveredClick(tableView);
        }

      }
    };

    waiterWorkers.add(waitersActionWorker);
    waitersActionWorker.execute();
  }

  /**
   * Checks if the confirm flag is set to true when confirm is pressed and
   * confirms the order viewed if it is.
   * 
   * @param tableview
   *          The current tables order that is being viewed set automatically.
   */
  public void checkConfirmClick(int tableview) {
    if (tableview == 0) { // if no order is being viewed e.g. main screen
      return;
    }

    Order o = new Order();
    o = tableOrder[tableview]; // order is the order being viewed

    if (confirmFlag == true) { // if confirm has been pressed
      w.confirmOrder(o); // Confirm the order being viewed
      confirmFlag = false;
    }
  }

  /**
   * Checks if the deliver flag is set to true when deliver is pressed and marks
   * the order viewed as delivered if it is.
   * 
   * @param tableview
   *          The current tables order that is being viewed set automatically.
   */
  public void checkdeliveredClick(int tableview) {
    if (tableview == 0) {
      return;
    }

    Order o = new Order();
    o = tableOrder[tableview];

    if (deliveredFlag == true) {
      w.deliverOrder(o);
      deliveredFlag = false;
    }
  }

  /**
   * Checks if the cancel flag is set to true when cancel is pressed and marks
   * the order viewed as cancelled if it is.
   * 
   * @param tableview
   *          The current tables order that is being viewed set automatically.
   */
  public void checkCancelClick(int tableview) {
    if (tableview == 0) {
      return;
    }

    Order o = new Order();
    o = tableOrder[tableview];

    if (cancelFlag == true) {
      w.cancelOrder(o);
      cancelFlag = false;
    }
  }

  /**
   * Sets the text in the seat panel and listens for which table is selected
   */
  public void tableBtnPressListener() {

    SwingWorker<Void, Void> tablePressWorker = new SwingWorker<Void, Void>() {

      @Override
      protected Void doInBackground() throws Exception {
        while (true) {
          System.out.println("table button press");
          checkBtnPress();
          setLabels(tableView);
          displayButtons(tableView);
          Thread.sleep(2000);
          
        }

      }
    };

    waiterWorkers.add(tablePressWorker);
    tablePressWorker.execute();
  }

  /**
   * checks the corresponding tablePress boolean to see if its been selected and
   * set the table view variable accordingly
   */
  private void checkBtnPress() {
    for (int i = 1; i <= AMOUNT_OF_TABLES; i++) {
      if (tablePress[i]) {
        tablePress[i] = false;
        tableView = i;
        return;
      }
    }
  }

  /**
   * Sets the labels with an order depending on which table is given, sets the
   * labels to empty string if order is cancelled
   * 
   * @param table
   *          Is the Table which order details to fill the labels with
   */
  public void setLabels(int table) {
    if (table == 0) {
      return;
    }
    Order o = new Order();
    o = tableOrder[table];
    if (o.getTotalCost() != 0.0) {
      tableLbl.setText(String.valueOf(o.getTableId()));
      priceLbl.setText(String.valueOf(o.getTotalCost()));
      stageLbl.setText(String.valueOf(o.getOrderStage()));
      orderArea.setText(o.getMenuItemsString(o.getMenuItemList()));
      orderTimeLbl.setText(calculateTimeForDisplay(o.getTimeOfOrder()));
      payedLbl.setText(String.valueOf(o.getTotalPaid()));
      if (o.getOrderStage() > 1) {
        assignedLbl.setText("Waiter ID : " + String.valueOf(o.getWaiterId()));
      } else
        assignedLbl.setText("Unassigned Confirm to Commit to this table");
    } else if (o.getOrderStage() == 0 || o.getTotalCost() == 0.0) {
      confirmBtn.setVisible(false);
      cancelBtn.setVisible(false);
      tableLbl.setText("");
      priceLbl.setText("");
      stageLbl.setText("TABLE EMPTY OR ORDER CANCELLED");
      orderArea.setText("");
      orderTimeLbl.setText("");
      payedLbl.setText("");
    }
  }

  /**
   * Sets the buttons seen by the waiter depending on the order stage and if
   * they have confirmed the order
   * 
   * @param tableview
   *          The table which order is being viewed
   */
  private void displayButtons(int tableview) {
    Order o = new Order();
    if (tableview == 0) {
      return;
    }
    o = tableOrder[tableview];

    if (o.getOrderStage() == 0) {
      confirmBtn.setVisible(false);
      cancelBtn.setVisible(false);
      deliverBtn.setVisible(false);
    }

    if (o.getOrderStage() == 1) { // Order pending but not confirmed
      confirmBtn.setVisible(true);
      cancelBtn.setVisible(true);
      deliverBtn.setVisible(false);
    }
    if (o.getOrderStage() == 2 || o.getOrderStage() == 3) { // Order Confirmed
                                                            // or cooking
      confirmBtn.setVisible(false); // cannot confirm it again
      deliverBtn.setVisible(false);
      if (o.getWaiterId() != w.getid()) {
        cancelBtn.setVisible(false);// cannot cancel if you didnt assign
                                    // yourself to it
      }
    }

    if (o.getOrderStage() == 4) { // if order stage is less than or equal to
                                  // needs delivered,
      confirmBtn.setVisible(false);
      cancelBtn.setVisible(false);
      deliverBtn.setVisible(false);
      if (o.getWaiterId() == w.getid()) {
        cancelBtn.setVisible(true);
        deliverBtn.setVisible(true);
      }
    }
    if (o.getOrderStage() == 5) {
      confirmBtn.setVisible(false);
      cancelBtn.setVisible(false);
      deliverBtn.setVisible(false);
    }

  }

  /**
   * Thread which updates the clock
   */
  public void clock() {

    SwingWorker<Void, Void> clockWorker = new SwingWorker<Void, Void>() {

      @Override
      protected Void doInBackground() throws Exception {
        while (true) {

          Calendar cal = new GregorianCalendar();
          int day = cal.get(Calendar.DAY_OF_MONTH);
          int month = cal.get(Calendar.MONTH);
          int year = cal.get(Calendar.YEAR);

          int second = cal.get(Calendar.SECOND);
          int minute = cal.get(Calendar.MINUTE);
          int hour = cal.get(Calendar.HOUR);

          timeLbl.setText("Time: " + hour + ":" + minute + ":" + second
              + "\n Date: " + day + "/" + month + "/" + year);
          Thread.sleep(1000);
        }

      }
    };

    waiterWorkers.add(clockWorker);
    clockWorker.execute();

  }

  /**
   * Creates and initialises the orders panel
   */
  public static void initialiseOrdersPanel() {

    // retrieving the orders from database, into arraylist
    orderListFromDb = new ArrayList<Order>();
    orderListFromDb = w.getOrdersFromDatabase();
    assignedOrdersFromDb = new ArrayList<Order>();

    sortOrdersByTime();

    ordersModel = new DefaultListModel<Order>();
    for (Order o : sortedByTimeOrders) {
      ordersModel.addElement(o);
    }

    waitersOrdersModel = new DefaultListModel<Order>();
    for (Order o : sortedByTimeOrders) {
      if (w.getid() == o.getWaiterId()) {
        assignedOrdersFromDb.add(o);
        waitersOrdersModel.addElement(o);
      }
    }

    orderlist = new JList<Order>(ordersModel);
    orderlist.setBounds(21, 64, 350, 293);
    ordersByTimePanel.add(orderlist);
    orderlist.setVisible(true);
    orderlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    orderlist.setSelectedIndex(0);
    orderlist.setVisibleRowCount(3);
    border = BorderFactory.createLineBorder(Color.black);
    orderlist.setBorder(border);

    // custom renderer to display correct order (object) info on side panel
    orderlist.setCellRenderer(new DefaultListCellRenderer() {
      /**
       * 
       */
      private static final long serialVersionUID = 1L;

      @Override
      public Component getListCellRendererComponent(JList<?> list, Object value,
          int index, boolean isSelected, boolean cellHasFocus) {
        Component renderer = super.getListCellRendererComponent(list, value,
            index, isSelected, cellHasFocus);
        if (renderer instanceof JLabel && value instanceof Order) {

          ((JLabel) renderer).setText("Order "
              + String.valueOf(((Order) value).getId()) + " on table "
              + String.valueOf(((Order) value).getTableId()) + " [STAGE "
              + String.valueOf(((Order) value).getOrderStage()) + "] placed "
              + calculateTimeForDisplay(((Order) value).getTimeOfOrder()));

        }
        return renderer;
      }
    });

    waiterOrderList = new JList<Order>(waitersOrdersModel);
    waiterOrderList.setBounds(400, 64, 350, 293);
    ordersByTimePanel.add(waiterOrderList);
    waiterOrderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    waiterOrderList.setSelectedIndex(0);
    waiterOrderList.setVisibleRowCount(3);
    waiterOrderList.setBorder(border);

    // custom renderer to display correct order (object) info on side panel
    waiterOrderList.setCellRenderer(new DefaultListCellRenderer() {
      @Override
      public Component getListCellRendererComponent(JList<?> list, Object value,
          int index, boolean isSelected, boolean cellHasFocus) {
        Component renderer = super.getListCellRendererComponent(list, value,
            index, isSelected, cellHasFocus);
        if (renderer instanceof JLabel && value instanceof Order) {

          ((JLabel) renderer).setText("Order "
              + String.valueOf(((Order) value).getId()) + " on table "
              + String.valueOf(((Order) value).getTableId()) + " [STAGE "
              + String.valueOf(((Order) value).getOrderStage()) + "] placed "
              + calculateTimeForDisplay(((Order) value).getTimeOfOrder()));
        }
        return renderer;
      }
    });

    if (orderlist.getModel().getSize() > 0) {
      selectedOrder = (Order) orderlist.getModel().getElementAt(0);
    }
  }

  /**
   * Calculates the correct time, to display on the UI. Returns in string
   * format.
   * 
   * @return String of correct time for order, formatted
   */
  public static String calculateTimeForDisplay(long orderTime) {
    String format = "";
    Date date = new Date();
    long differenceInTime = date.getTime() - orderTime;
    long seconds = differenceInTime / 1000;
    if (seconds <= 60) { // if less that minute, show in seconds
      format = seconds + " seconds ago";
    } else { // if over 60 seconds, show in minute format
      format = (differenceInTime / 1000) / 60 + " minutes ago";
    }

    return format;
  }

  /**
   * Method to sort list of orders by time placed (most recent orders first)
   */
  public static void sortOrdersByTime() {
    sortedByTimeOrders = new ArrayList<Order>();
    List<Order> temp = new ArrayList<Order>();

    for (Order o : orderListFromDb) {
      temp.add(o);
    }

    // while all elements have not been transferred
    while (sortedByTimeOrders.size() != orderListFromDb.size()) {

      Order earliestOrder = temp.get(0);
      for (Order o : temp) {

        // if order has an earlier (higher) order time than earliest order,
        // reassign
        if (o.getTimeOfOrder() > earliestOrder.getTimeOfOrder()) {
          earliestOrder = o;
        }
      }

      temp.remove(earliestOrder);
      sortedByTimeOrders.add(earliestOrder);
    }

  }

  /**
   * Creates and initialises the feed panel
   */
  public static void initialiseFeedPanel() {
    customersCalling = new ArrayList<Customer>();
    customersCalling = custManager.getDbCustomersCalling();
    assignedCustomersCalling = new ArrayList<Customer>();
    ordersFromDbTemp = new ArrayList<Order>();
    ordersFromDbTemp = w.getOrdersFromDatabase();
    List<Order> waiterAssignedOrders = new ArrayList<Order>();

    for (Order o : waiterAssignedOrders) {
      if (w.getid() == o.getWaiterId()) {
        waiterAssignedOrders.add(o);
      }
    }

    custCallingModel = new DefaultListModel<Customer>();
    for (Customer c : customersCalling) {
      custCallingModel.addElement(c);
    }

    for (Order o : waiterAssignedOrders) {
      for (Customer c : customersCalling) {
        if (o.getTableId() == c.getTableID()) {
          assignedCustomersCalling.add(c);
        }
      }
    }

    waitersCallingModel = new DefaultListModel<Customer>();
    for (Customer c : customersCalling) {
      waitersCallingModel.addElement(c);
    }

    feedList = new JList<Customer>(custCallingModel);
    feedList.setBounds(21, 64, 300, 300);
    feedPanel.add(feedList);
    feedPanel.setBackground(Color.WHITE);

    feedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    feedList.setSelectedIndex(0);
    feedList.setVisibleRowCount(3);
    feedList.setVisible(true);
    feedList.setBorder(border);

    feedList.setCellRenderer(new DefaultListCellRenderer() {
      private static final long serialVersionUID = 1L;

      @Override
      public Component getListCellRendererComponent(JList<?> list, Object value,
          int index, boolean isSelected, boolean cellHasFocus) {
        Component renderer = super.getListCellRendererComponent(list, value,
            index, isSelected, cellHasFocus);
        if (renderer instanceof JLabel && value instanceof Customer) {

          ((JLabel) renderer).setText(
              "Customer " + String.valueOf(((Customer) value).getCust_id())
                  + " needs assistance ");

        }
        return renderer;
      }
    });

    waiterFeedList = new JList<Customer>(waitersCallingModel);
    waiterFeedList.setBounds(350, 64, 300, 300);
    feedPanel.add(waiterFeedList);
    waiterFeedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    waiterFeedList.setSelectedIndex(0);
    waiterFeedList.setVisibleRowCount(3);
    waiterFeedList.setBorder(border);

    waiterFeedList.setCellRenderer(new DefaultListCellRenderer() {
      private static final long serialVersionUID = 1L;

      @Override
      public Component getListCellRendererComponent(JList<?> list, Object value,
          int index, boolean isSelected, boolean cellHasFocus) {
        Component renderer = super.getListCellRendererComponent(list, value,
            index, isSelected, cellHasFocus);
        if (renderer instanceof JLabel && value instanceof Customer) {

          ((JLabel) renderer).setText(
              "Customer " + String.valueOf(((Customer) value).getCust_id())
                  + " needs assistance ");

        }
        return renderer;
      }
    });

    if (customersCalling.size() > 0 && selectedCustomerSetUp == false) {
      selectedCustomer = (Customer) feedList.getModel().getElementAt(0);
      selectedCustomerSetUp = true;
    }
  }

  /**
   * Create the frame.
   */
  public WaiterUI() {

    // Setting waiter details

    if (UIUserLogin.loggedInId == 0) {
      waiterAccount = waiterAccount.getAccount(7);
      w = new Waiter(waiterAccount.getAccountID(), waiterAccount.getName());
    } else {
      waiterAccount = waiterAccount.getAccount(UIUserLogin.loggedInId);
      w = new Waiter(waiterAccount.getAccountID(), waiterAccount.getName());
    }

    loginLbl = new JLabel("Welcome " + w.getName());
    loginLbl.setBounds(61, 608, 256, 39);

    setBackground(Color.DARK_GRAY);

    // ############### Main panel components ###################

    contentPane = new JPanel();
    contentPane.setBackground(Color.DARK_GRAY);
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);

    setTitle("Waiter");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 1500, 800);
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    // setUndecorated(true);
    timeLbl = new JLabel("Time here");
    timeLbl.setBounds(600, 608, 300, 39);
    feedBtn = new JButton("");
    feedBtn.setBounds(1201, 442, 147, 105);
    feedBtn.setIcon(
        new ImageIcon(WaiterUI.class.getResource("/client/app/feed.png")));
    tablesBtn = new JButton("");
    tablesBtn.setBounds(1201, 326, 147, 105);
    tablesBtn.setIcon(
        new ImageIcon(WaiterUI.class.getResource("/client/app/tables.png")));

    ordersBtn = new JButton("");
    ordersBtn.setBounds(1035, 326, 147, 105);
    ordersBtn.setIcon(new ImageIcon(
        WaiterUI.class.getResource("/client/app/ordersicon.png")));
    ordersBtn.setBackground(Color.DARK_GRAY);
    feedBtn.setVerticalAlignment(SwingConstants.BOTTOM);
    tablesBtn.setVerticalAlignment(SwingConstants.BOTTOM);
    ordersBtn.setVerticalAlignment(SwingConstants.BOTTOM);
    feedBtn.setHorizontalTextPosition(SwingConstants.LEFT);
    tablesBtn.setHorizontalTextPosition(SwingConstants.LEFT);
    ordersBtn.setHorizontalTextPosition(SwingConstants.LEFT);
    feedBtn.setForeground(Color.BLACK);
    tablesBtn.setForeground(Color.BLACK);
    ordersBtn.setForeground(Color.BLACK);
    ordersBtn.setBorderPainted(false);
    loginLbl.setForeground(new Color(220, 220, 220));
    feedBtn.setBackground(Color.DARK_GRAY);
    timeLbl.setForeground(new Color(220, 220, 220));
    tablesBtn.setBackground(Color.DARK_GRAY);
    tablesBtn.setBorderPainted(false);

    timeLbl.setFont(new Font("SimSun-ExtB", Font.PLAIN, 20));
    loginLbl.setFont(new Font("SimSun-ExtB", Font.PLAIN, 20));

    // feed button functionality on click
    feedBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        feedPanel.setVisible(true);
        tablesPanel.setVisible(false);
        seat1Panel.setVisible(false);
        ordersByTimePanel.setVisible(false);
        updateTables(1);

      }
    });

    // View tables button functionality on click
    tablesBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        tablesPanel.setVisible(true);
        feedPanel.setVisible(false);
        seat1Panel.setVisible(false);
        ordersByTimePanel.setVisible(false);
        updateTables(0);
      }
    });

    // View orders in order of when places button functionality on click
    ordersBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        tablesPanel.setVisible(false);
        feedPanel.setVisible(false);
        seat1Panel.setVisible(false);
        ordersByTimePanel.setVisible(true);
        updateTables(1);
      }
    });

    // ################# Orders by time panel ###############
    ordersByTimePanel = new JPanel();
    ordersByTimePanel.setBounds(61, 192, 900, 405);
    ordersByTimePanel.setBorder(new LineBorder(new Color(128, 128, 128), 20));
    ordersByTimePanel.setBackground(Color.WHITE);
    ordersByTimePanel.setVisible(false);

    // ################ Table panel and components ##############
    tablesPanel = new JPanel();
    tablesPanel.setBounds(61, 192, 900, 405);
    tablesPanel.setBorder(new LineBorder(new Color(220, 220, 220), 20));

    table1Btn = new JButton("Table 1");
    table1Btn.setFont(new Font("Arial Black", Font.PLAIN, 11));

    tablesPanel.setBackground(Color.BLACK);
    tablesPanel.setLayout(null);
    table1Btn.setForeground(Color.BLACK);
    table1Btn.setBorder(null);
    table1Btn.setBackground(Color.WHITE);
    table1Btn.setBounds(228, 149, 100, 68);

    // Table1 button functionality on click
    table1Btn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tablesPanel.setVisible(false);
        tablePress[1] = true;
        seat1Panel.setVisible(true);
      }
    });
    contentPane.setLayout(null);

    // ################ Seat1 panel and components #############
    seat1Panel = new JPanel();
    seat1Panel.setBounds(61, 192, 900, 405);
    seat1Panel.setBorder(new LineBorder(new Color(128, 128, 128), 20));
    seat1Panel.setBackground(Color.WHITE);
    seat1Panel.setLayout(null);
    seat1Panel.setVisible(false);
    tableTxt = new JLabel("Table:");
    orderTxt = new JLabel("Order:");
    stageTxt = new JLabel("Order Status:");
    notesTxt = new JLabel("Time:");
    priceTxt = new JLabel("Price:");
    tableLbl = new JLabel("getTable");
    stageLbl = new JLabel("getStage");
    assignedLbl = new JLabel("Unassigned");
    orderTimeLbl = new JLabel("getOrderTime");
    priceLbl = new JLabel("getPrice");
    payedLbl = new JLabel("getAmountPayed");
    payedTxt = new JLabel("Amount Payed:");
    tableTxt.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    orderTxt.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    stageTxt.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    notesTxt.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    priceTxt.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    tableLbl.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    stageLbl.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    orderTimeLbl.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    priceLbl.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    payedLbl.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    payedTxt.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    tableTxt.setBounds(107, 35, 46, 14);
    orderTxt.setBounds(107, 60, 121, 14);
    stageTxt.setBounds(77, 109, 76, 14);
    notesTxt.setBounds(121, 134, 46, 14);
    priceTxt.setBounds(121, 159, 46, 14);
    tableLbl.setBounds(243, 35, 121, 14);

    stageLbl.setBounds(248, 108, 281, 14);
    orderTimeLbl.setBounds(248, 133, 266, 14);
    priceLbl.setBounds(243, 158, 121, 14);
    tableLbl.setFont(new Font("Arial", Font.BOLD, 14));
    stageLbl.setFont(new Font("Arial", Font.BOLD, 14));
    orderTimeLbl.setFont(new Font("Arial", Font.BOLD, 14));
    priceLbl.setFont(new Font("Arial", Font.BOLD, 14));
    payedLbl.setFont(new Font("Arial", Font.BOLD, 14));
    payedLbl.setBounds(243, 183, 121, 14);
    payedTxt.setBounds(77, 184, 93, 14);

    deliverBtn = new JButton("Food Delivered");
    deliverBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        deliveredFlag = true;

      }
    });
    deliverBtn.setVisible(false);
    deliverBtn.setHorizontalTextPosition(SwingConstants.LEFT);
    deliverBtn.setHorizontalAlignment(SwingConstants.LEFT);
    deliverBtn.setForeground(Color.BLACK);
    deliverBtn.setBackground(SystemColor.activeCaption);
    deliverBtn.setAlignmentY(0.0f);
    deliverBtn.setBounds(153, 303, 143, 54);
    seat1Panel.add(deliverBtn);
    cancelBtn = new JButton("Cancel");
    cancelBtn.setBounds(317, 304, 150, 53);
    seat1Panel.add(cancelBtn);
    cancelBtn.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent arg0) {
        cancelFlag = true;

      }

    });
    cancelBtn.setHorizontalTextPosition(SwingConstants.LEFT);
    cancelBtn.setHorizontalAlignment(SwingConstants.LEFT);
    cancelBtn.setForeground(Color.BLACK);
    cancelBtn.setBackground(SystemColor.activeCaption);
    cancelBtn.setAlignmentY(0.0f);
    confirmBtn = new JButton("Confirm");
    confirmBtn.setBounds(153, 303, 143, 54);
    seat1Panel.add(confirmBtn);

    confirmBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        confirmFlag = true;
        w.setWaitersTable(1);
      }
    });

    confirmBtn.setHorizontalTextPosition(SwingConstants.LEFT);
    confirmBtn.setHorizontalAlignment(SwingConstants.LEFT);

    confirmBtn.setForeground(Color.BLACK);

    confirmBtn.setBackground(SystemColor.activeCaption);

    confirmBtn.setAlignmentY(0.0f);
    seat1Panel.add(tableTxt);
    seat1Panel.add(orderTxt);
    seat1Panel.add(stageTxt);
    seat1Panel.add(notesTxt);
    seat1Panel.add(priceTxt);
    seat1Panel.add(tableLbl);
    seat1Panel.add(stageLbl);
    seat1Panel.add(orderTimeLbl);
    seat1Panel.add(priceLbl);
    seat1Panel.add(payedLbl);

    seat1Panel.add(payedTxt);

    contentPane.add(seat1Panel);

    JLabel lblAssignedTo = new JLabel("Assigned to:");
    lblAssignedTo.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    lblAssignedTo.setBounds(89, 226, 93, 14);
    seat1Panel.add(lblAssignedTo);

    assignedLbl.setFont(new Font("Arial", Font.BOLD, 14));
    assignedLbl.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    assignedLbl.setBounds(243, 225, 317, 14);
    seat1Panel.add(assignedLbl);

    orderArea = new TextArea();
    orderArea.setFont(new Font("Arial", Font.BOLD, 14));
    orderArea.setBounds(234, 52, 380, 50);
    seat1Panel.add(orderArea);

    /////

    tablesPanel.add(table1Btn);
    contentPane.add(tablesPanel);

    table2Btn = new JButton("Table 2");
    table2Btn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        tablesPanel.setVisible(false);
        tablePress[2] = true;
        seat1Panel.setVisible(true);
      }
    });
    table2Btn.setForeground(Color.BLACK);
    table2Btn.setFont(new Font("Arial Black", Font.PLAIN, 11));
    table2Btn.setBorder(null);
    table2Btn.setBackground(Color.WHITE);
    table2Btn.setBounds(382, 149, 100, 68);
    tablesPanel.add(table2Btn);

    table3Btn = new JButton("Table 3");
    table3Btn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tablesPanel.setVisible(false);
        tablePress[3] = true;
        seat1Panel.setVisible(true);
      }
    });
    table3Btn.setForeground(Color.BLACK);
    table3Btn.setFont(new Font("Arial Black", Font.PLAIN, 11));
    table3Btn.setBorder(null);
    table3Btn.setBackground(Color.WHITE);
    table3Btn.setBounds(540, 149, 100, 68);
    tablesPanel.add(table3Btn);

    table5Btn = new JButton("Table 5");
    table5Btn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tablesPanel.setVisible(false);
        tablePress[5] = true;
        seat1Panel.setVisible(true);
      }
    });
    table5Btn.setForeground(Color.BLACK);
    table5Btn.setFont(new Font("Arial Black", Font.PLAIN, 11));
    table5Btn.setBorder(null);
    table5Btn.setBackground(Color.WHITE);
    table5Btn.setBounds(382, 237, 100, 68);
    tablesPanel.add(table5Btn);

    table4Btn = new JButton("Table 4");
    table4Btn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tablesPanel.setVisible(false);
        tablePress[4] = true;
        seat1Panel.setVisible(true);
      }
    });
    table4Btn.setForeground(Color.BLACK);
    table4Btn.setFont(new Font("Arial Black", Font.PLAIN, 11));
    table4Btn.setBorder(null);
    table4Btn.setBackground(Color.WHITE);
    table4Btn.setBounds(228, 237, 100, 68);
    tablesPanel.add(table4Btn);

    table6Btn = new JButton("Table 6");
    table6Btn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tablesPanel.setVisible(false);
        tablePress[6] = true;
        seat1Panel.setVisible(true);
      }
    });
    table6Btn.setForeground(Color.BLACK);
    table6Btn.setFont(new Font("Arial Black", Font.PLAIN, 11));
    table6Btn.setBorder(null);
    table6Btn.setBackground(Color.WHITE);
    table6Btn.setBounds(540, 237, 100, 68);
    tablesPanel.add(table6Btn);

    lblNewLabel_2 = new JLabel("");
    lblNewLabel_2.setIcon(new ImageIcon(
        WaiterUI.class.getResource("/client/app/birdseyev2.png")));
    lblNewLabel_2.setBounds(22, 11, 857, 383);
    tablesPanel.add(lblNewLabel_2);

    // ############### Feed panel and components ###############
    feedPanel = new JPanel();
    feedPanel.setBounds(61, 192, 900, 405);
    feedPanel.setBorder(new LineBorder(new Color(128, 128, 128), 20));
    feedPanel.setBackground(Color.BLACK);
    feedPanel.setVisible(false);
    feedPanel.setLayout(null);

    contentPane.add(feedPanel);

    initialiseFeedPanel();

    JLabel feedAllLbl = new JLabel("ALL");
    feedAllLbl.setBounds(168, 21, 159, 35);
    feedPanel.add(feedAllLbl);

    JLabel lblCallsAssigned = new JLabel("ASSIGNED");
    lblCallsAssigned.setBackground(Color.LIGHT_GRAY);
    lblCallsAssigned.setBounds(400, 21, 297, 36);
    feedPanel.add(lblCallsAssigned);

    JButton feedResolveBtn = new JButton("Resolve");
    feedResolveBtn.setBounds(670, 250, 194, 100);
    feedPanel.add(feedResolveBtn);

    feedList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent arg0) {
        if (!arg0.getValueIsAdjusting()) {
          selectedCustomer = custCallingModel
              .getElementAt(feedList.getSelectedIndex());
        }
      }
    });

    feedResolveBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        System.out.println(
            "Resolving call from customer: " + selectedCustomer.getAccountID());
        selectedCustomer.setCallingWaiter(0);
        selectedCustomer.updateCustomer();
      }
    });

    ///////////////////////////////////////////// waiter orders panel
    contentPane.add(ordersByTimePanel);
    ordersByTimePanel.setLayout(null);

    initialiseOrdersPanel();

    JLabel lblOrdersAssigned = new JLabel("ASSIGNED ORDERS");
    lblOrdersAssigned.setBackground(Color.LIGHT_GRAY);
    lblOrdersAssigned.setBounds(400, 21, 297, 36);
    ordersByTimePanel.add(lblOrdersAssigned);

    JLabel lblOrdersAll = new JLabel("ALL ORDERS");
    lblOrdersAll.setBackground(Color.LIGHT_GRAY);
    lblOrdersAll.setBounds(100, 21, 156, 43);
    ordersByTimePanel.add(lblOrdersAll);

    totalOrderNum = new JLabel("");
    totalOrderNum.setForeground(Color.BLACK);
    totalOrderNum.setBackground(Color.LIGHT_GRAY);
    totalOrderNum.setBounds(21, 21, 151, 43);
    ordersByTimePanel.add(totalOrderNum);

    orderlist.addListSelectionListener(new ListSelectionListener() {

      @Override
      public void valueChanged(ListSelectionEvent arg0) {
        if (!arg0.getValueIsAdjusting()) {
          selectedOrder = ordersModel
              .getElementAt(orderlist.getSelectedIndex());

        }
      }

    });

    waiterOrderList.addListSelectionListener(new ListSelectionListener() {

      @Override
      public void valueChanged(ListSelectionEvent arg0) {
        if (!arg0.getValueIsAdjusting()) {
          selectedOrder = waitersOrdersModel
              .getElementAt(waiterOrderList.getSelectedIndex());

        }
      }

    });

    contentPane.add(timeLbl);
    contentPane.add(feedBtn);
    contentPane.add(tablesBtn);
    contentPane.add(ordersBtn);
    InstructionBtn = new JButton("");
    InstructionBtn.setBounds(1035, 442, 147, 105);
    InstructionBtn.setIcon(
        new ImageIcon(WaiterUI.class.getResource("/client/app/info.png")));
    InstructionBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        tablesPanel.setVisible(false);
        ordersByTimePanel.setVisible(false);
        feedPanel.setVisible(false);
        infoPanel_1.setVisible(true);
        updateTables(1);
      }
    });
    InstructionBtn.setVerticalAlignment(SwingConstants.BOTTOM);
    InstructionBtn.setHorizontalTextPosition(SwingConstants.LEFT);
    InstructionBtn.setForeground(Color.BLACK);
    InstructionBtn.setBackground(Color.DARK_GRAY);
    InstructionBtn.setBorderPainted(false);
    feedBtn.setBorderPainted(false);
    contentPane.add(InstructionBtn);

    ReservationButton = new JButton("");
    ReservationButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ReservationsUI reser = new ReservationsUI();
        dispose();
        reser.frame.setVisible(true);
      }
    });
    ReservationButton.setBackground(Color.DARK_GRAY);
    ReservationButton.setIcon(new ImageIcon(
        WaiterUI.class.getResource("/client/app/Reservations.png")));
    ReservationButton.setBounds(50, 50, 213, 46);
    ReservationButton.setBorderPainted(false);
    ReservationButton.setForeground(Color.BLACK);

    contentPane.add(ReservationButton);

    contentPane.add(loginLbl);

    logoutBtn = new JButton("");
    logoutBtn.setBounds(1116, 570, 147, 108);
    contentPane.add(logoutBtn);
    logoutBtn.setIcon(new ImageIcon(
        WaiterUI.class.getResource("/client/app/power-icon.png")));

    logoutBtn.setVerticalAlignment(SwingConstants.BOTTOM);
    logoutBtn.setBorderPainted(false);
    logoutBtn.setFocusPainted(false);

    logoutBtn.setHorizontalTextPosition(SwingConstants.LEFT);

    logoutBtn.setForeground(Color.WHITE);
    logoutBtn.setBackground(Color.DARK_GRAY);

    infoPanel_1 = new JPanel();
    infoPanel_1.setBounds(121, 192, 840, 405);
    infoPanel_1.setBorder(new LineBorder(new Color(128, 128, 128), 20));
    infoPanel_1.setVisible(false);
    infoPanel_1.setBackground(Color.WHITE);
    contentPane.add(infoPanel_1);
    infoPanel_1.setLayout(null);

    JLabel lblNewLabel1 = new JLabel("Legend");
    lblNewLabel1.setFont(new Font("Tahoma", Font.BOLD, 18));
    lblNewLabel1.setVerticalAlignment(SwingConstants.TOP);
    lblNewLabel1.setBounds(110, 29, 98, 22);
    infoPanel_1.add(lblNewLabel1);

    JLabel lblStageCancelled1 = new JLabel("Stage 0: Cancelled Or Empty");
    lblStageCancelled1.setVerticalAlignment(SwingConstants.TOP);
    lblStageCancelled1.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblStageCancelled1.setBounds(72, 62, 214, 22);
    infoPanel_1.add(lblStageCancelled1);

    JLabel lblStage1 = new JLabel("Stage 1: Waiter requested");
    lblStage1.setForeground(Color.ORANGE);
    lblStage1.setBackground(Color.ORANGE);
    lblStage1.setVerticalAlignment(SwingConstants.TOP);
    lblStage1.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblStage1.setBounds(72, 93, 175, 22);
    infoPanel_1.add(lblStage1);

    JLabel lblStageWaiter1 = new JLabel("Stage 2: Waiter Confirmed");
    lblStageWaiter1.setVerticalAlignment(SwingConstants.TOP);
    lblStageWaiter1.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblStageWaiter1.setBounds(72, 126, 175, 22);
    infoPanel_1.add(lblStageWaiter1);

    JLabel lblStageWaiter_11 = new JLabel("Stage 3: Cooking");
    lblStageWaiter_11.setVerticalAlignment(SwingConstants.TOP);
    lblStageWaiter_11.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblStageWaiter_11.setBounds(72, 159, 175, 22);
    infoPanel_1.add(lblStageWaiter_11);

    JLabel lblStageWaiter_21 = new JLabel("Stage 5: Delivered unpaid");
    lblStageWaiter_21.setForeground(Color.RED);
    lblStageWaiter_21.setBackground(Color.RED);
    lblStageWaiter_21.setVerticalAlignment(SwingConstants.TOP);
    lblStageWaiter_21.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblStageWaiter_21.setBounds(72, 225, 175, 22);
    infoPanel_1.add(lblStageWaiter_21);

    JLabel lblStageWaiter_31 = new JLabel("Stage 5: Paid");
    lblStageWaiter_31.setForeground(new Color(0, 128, 0));
    lblStageWaiter_31.setBackground(Color.WHITE);
    lblStageWaiter_31.setVerticalAlignment(SwingConstants.TOP);
    lblStageWaiter_31.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblStageWaiter_31.setBounds(72, 258, 175, 22);
    infoPanel_1.add(lblStageWaiter_31);

    JLabel lblStageCooked = new JLabel("Stage 4: Cooked Undelivered");
    lblStageCooked.setForeground(new Color(30, 144, 255));
    lblStageCooked.setVerticalAlignment(SwingConstants.TOP);
    lblStageCooked.setFont(new Font("Tahoma", Font.PLAIN, 14));
    lblStageCooked.setBounds(72, 192, 214, 22);
    infoPanel_1.add(lblStageCooked);

    JLabel lblNewLabel_1 = new JLabel("");
    lblNewLabel_1.setBounds(1035, -40, 313, 358);
    contentPane.add(lblNewLabel_1);
    lblNewLabel_1.setIcon(
        new ImageIcon(WaiterUI.class.getResource("/client/app/Logo.png")));

    label = new JLabel("");
    label.setIcon(new ImageIcon(
        WaiterUI.class.getResource("/client/app/background.png")));
    label.setBounds(0, 0, 1407, 843);
    contentPane.add(label);

    // Logout button functionality on click
    logoutBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UIUserLogin LUI = new UIUserLogin();
        w.logout();
        LUI.setVisible(true);
        dispose();

      }
    });

  }
}
