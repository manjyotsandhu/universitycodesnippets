package client.app.UI;

import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import client.app.logic.KitchenStaff;
import client.app.logic.Order;
import client.app.swing.objects.NoEditDefaultTable;

import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class newKitchenUI extends JFrame {

  private JPanel contentPane;
  private JTable table;
  private KitchenStaff kstaff = new KitchenStaff();
  WaiterUI waiterUi = new WaiterUI();


  // Database variable
  private List<Order> allOrders = kstaff.getOrdersFromDatabase();
  private List<Order> currentOrders;

  // Table variables
  private String[] columnNames =
      {"Order Number", "Table Number", "Order Status", "Order items", "Time Placed"};
  private Object[][] rowData = {};
  private NoEditDefaultTable tabModel = new NoEditDefaultTable(rowData, columnNames);
  private static SwingWorker<Void, Void> worker;


  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          newKitchenUI frame = new newKitchenUI();
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
  public newKitchenUI() {
    setTitle("Kitchen Staff UI");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 1700, 900);
    contentPane = new JPanel();
    contentPane.setBackground(Color.DARK_GRAY);
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    // Surrounding table with scrollPane
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBounds(24, 193, 1635, 515);
    contentPane.add(scrollPane);

    // Initialise table layout
    table = new JTable(rowData, columnNames);
    scrollPane.setViewportView(table);
    table.setRowHeight(50);
    table.setRowMargin(2);
    JTableHeader header = table.getTableHeader();
    header.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 16));
    table.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
    table.setAutoCreateRowSorter(true);
    table.setModel(tabModel);

   
   
    /*    allOrders = kstaff.getOrdersFromDatabase();
    if (table.getModel().getRowCount() == 0) {
      // if table empty , fill it with data

      for (Order ord : allOrders) {
        if (ord.getOrderStage() == 2 || ord.getOrderStage() == 3 || ord.getOrderStage() == 4) {
          setLayoutx();
          tabModel.addRow(new Object[] {ord.getId(), ord.getTableId(), displayOrderStage(ord),
              displayMenuItems(ord), calculateTimeForDisplay(ord.getTimeOfOrder())});
        }
      }
    } else {

      int ordersize = tabModel.getRowCount() - 1;
      // orders = allOrders;
      for (int i = ordersize; i >= 0; i--) {
        tabModel.removeRow(i);
        setLayoutx();
      }
      for (Order ord : allOrders) {
        if (ord.getOrderStage() == 2 || ord.getOrderStage() == 3 || ord.getOrderStage() == 4) {
          tabModel.addRow(new Object[] {ord.getId(), ord.getTableId(), displayOrderStage(ord),
              displayMenuItems(ord), calculateTimeForDisplay(ord.getTimeOfOrder())});
        }
      }
      setLayoutx();
    }
    tabModel.fireTableDataChanged();
    updateData();
*/

    // create Start button that changes order stage to Cooking
    JButton startButton = new JButton("");
    startButton.setBackground(Color.DARK_GRAY);
    startButton.setForeground(Color.DARK_GRAY);
    startButton.setIcon(new ImageIcon(newKitchenUI.class.getResource("/client/app/ksStart.png")));
    startButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) throws IndexOutOfBoundsException{
        try{
        int row = table.getSelectedRow();
        getCurrentOrders(allOrders);
        if (row < currentOrders.size() && row >= 0) {
          kstaff.getOrder(currentOrders.get(row).getId());
          kstaff.startOrder(currentOrders.get(row).getId());
          dispose();
          newKitchenUI ui = new newKitchenUI();
          ui.setVisible(true);
         
        //  stopWorker();
        //  updateData();
        }}
        catch(Exception exp){
          System.out.println("start inndex out of bounds");
          
        }

      }
    });
    startButton.setBounds(24, 732, 366, 100);
    contentPane.add(startButton);
    
    
    // create NotifyWaiter button which changes order stage to WaiterNotified
    JButton notifyWaiterButton = new JButton("");
    notifyWaiterButton.setBackground(Color.DARK_GRAY);
    notifyWaiterButton.setForeground(Color.DARK_GRAY);
    notifyWaiterButton
        .setIcon(new ImageIcon(newKitchenUI.class.getResource("/client/app/ksnotifywaiter.png")));
    notifyWaiterButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) throws IndexOutOfBoundsException{
       try{
        int row = table.getSelectedRow();
        getCurrentOrders(allOrders);
        if (row < currentOrders.size() && row >= 0) {
          kstaff.getOrder(currentOrders.get(row).getId());
          kstaff.MarkAsCooked(currentOrders.get(row).getId());
          dispose();
          newKitchenUI ui = new newKitchenUI();
          ui.setVisible(true);
          //stopWorker();
         // updateData();
        }}
       catch(Exception exp){
         System.out.println("waiter index out of bounds");
         
       }
      }
    });
    notifyWaiterButton.setBounds(667, 730, 362, 102);
    contentPane.add(notifyWaiterButton);
    
    
    // create log out button that displays the User Login UI
    JButton logoutButton = new JButton("");
    logoutButton.setBackground(Color.DARK_GRAY);
    logoutButton.setForeground(Color.DARK_GRAY);
    logoutButton
        .setIcon(new ImageIcon(newKitchenUI.class.getResource("/client/app/kslogout.png")));
    logoutButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        contentPane.setVisible(false);
        UIUserLogin login = new UIUserLogin();
        login.setVisible(true);
        dispose();
      }
    });
    logoutButton.setBounds(1308, 732, 362, 108);
    contentPane.add(logoutButton);

    JLabel logolabel = new JLabel(" ");
    logolabel.setIcon(new ImageIcon(newKitchenUI.class.getResource("/client/app/smalllogo.png")));
    logolabel.setBounds(1460, 13, 199, 168);
    contentPane.add(logolabel);
    
    updateData();
    setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] {contentPane, scrollPane,
        table, startButton, notifyWaiterButton, logoutButton, logolabel}));
  }



  /**
   * Creates an ArrayList that contains orders with stages 2,3 and 4.
   * 
   * @param allOrders an ArrayList of Database Orders.
   */
  public void getCurrentOrders(List<Order> allOrders) {
    currentOrders = new ArrayList<Order>();
    for (Order o : allOrders) {
      if (o.getOrderStage() == 2 || o.getOrderStage() == 3 || o.getOrderStage() == 4) {
        currentOrders.add(o);
      }
    }
  }

  /**
   * Calculates the correct time, to display on the UI. Returns in string format.
   * 
   * @return String of correct time for order, formatted
   */
  public String calculateTimeForDisplay(long orderTime) {
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
   * Displays MenuItems in each Order as a String after removing all white spaces.
   * 
   * @param Order.
   * @return String represents MenuItems in each order.
   */
  public String displayMenuItems(Order ord) {
    String value = "";
    CharSequence charsq = "                    ";
    CharSequence comma = ",";

    String items = ord.getMenuItemList() + ", ";
    String val = ord.getMenuItemsString(items);
    value = val.replace(charsq, comma);


    return value;

  }

  /**
   * Displays text that represents each Order Stage.
   * 
   * @param Order to get stage from.
   * @return String contains that textual meaning of order stage.
   */

  public String displayOrderStage(Order ord) {

    if (ord.getOrderStage() == 2) {
      return "Waiter Confirmed";

    }
    if (ord.getOrderStage() == 3) {
      return "Cooking";
    }
    if (ord.getOrderStage() == 4) {

      return "Waiter Notified";
    }
    return null;

  }

  /**
   * Worker thread Which updates jtable every 10 seconds.
   */
  private void updateData() {

    stopWorker();
    worker = new SwingWorker<Void, Void>() {

      @Override
      protected Void doInBackground() throws Exception {
        while (true) {
          allOrders = kstaff.getOrdersFromDatabase();
          if (table.getModel().getRowCount() == 0) {
            // if table empty , fill it with data

            for (Order ord : allOrders) {
              if (ord.getOrderStage() == 2 || ord.getOrderStage() == 3
                  || ord.getOrderStage() == 4) {
                setLayoutx();
                tabModel.addRow(new Object[] {ord.getId(), ord.getTableId(), displayOrderStage(ord),
                    displayMenuItems(ord), calculateTimeForDisplay(ord.getTimeOfOrder())});
              }
            }
          } else {

            int ordersize = tabModel.getRowCount() -1 ;
             List<Order> orders = allOrders;
            for (int i = ordersize; i >= 0; i--) {
              tabModel.removeRow(i);
              setLayoutx();
            }
            for (Order ord : orders) {
              if (ord.getOrderStage() == 2 || ord.getOrderStage() == 3
                  || ord.getOrderStage() == 4) {
                tabModel.addRow(new Object[] {ord.getId(), ord.getTableId(), displayOrderStage(ord),
                    displayMenuItems(ord), calculateTimeForDisplay(ord.getTimeOfOrder())});
              }
            }
            setLayoutx();
          }
          table.setModel(tabModel);
          tabModel.fireTableDataChanged();
          Thread.sleep(10000);
        }
      }
    };
    worker.execute();
  }

  private void stopWorker() {
    if(worker != null)
      worker.cancel(true);
  }

  /**
   * Used to apply changes to table layout, by setting columns width and cells alignment.
   */
  public void setLayoutx() {
    // set size for all table columns
    TableColumn items = table.getColumnModel().getColumn(0);
    items.setPreferredWidth(20);
    TableColumn items1 = table.getColumnModel().getColumn(1);
    items1.setPreferredWidth(20);
    TableColumn items2 = table.getColumnModel().getColumn(2);
    items2.setPreferredWidth(20);
    TableColumn items3 = table.getColumnModel().getColumn(3);
    items3.setPreferredWidth(200);
    TableColumn items4 = table.getColumnModel().getColumn(4);
    items4.setPreferredWidth(20);


    // use Centre Alignment for all table cells
    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
    rightRenderer.setHorizontalAlignment(0);

    for (int columnIndex = 0; columnIndex < table.getModel().getColumnCount(); columnIndex++) {
      table.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
      rightRenderer.setForeground(Color.BLACK);
    }
  }
}
