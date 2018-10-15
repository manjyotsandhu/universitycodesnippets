package client.app.UI;

import client.app.logic.KitchenStaff;
import client.app.logic.Order;
import client.app.swing.objects.NoEditDefaultTable;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;


/**
 * @author Ghadah.
 *
 */

@SuppressWarnings("serial")
public class UIforKitchenStaff extends JFrame {

  private JPanel contentPane;
  private static KitchenStaff kstaff = new KitchenStaff();
  WaiterUI waiterUi = new WaiterUI();

  // Database variable
  private static List<Order> allOrders = kstaff.getOrdersFromDatabase();
  private static List<Order> currentOrders;

  // Table variables
  private static String[] columnNames =
      {"Order Number", "Table Number", "Order Status", "Order items", "Time Placed"};
  private static Object[][] rowData = {};
  private static JTable table = new JTable(rowData, columnNames);
  private static NoEditDefaultTable tabModel = new NoEditDefaultTable(rowData, columnNames);


  private JPanel orderPanel;
  private JScrollPane scrollPane;
  private JTableHeader header;
  private JLabel bkgroundLabel;
  private JLabel background3;
  private JLabel background4;
  private JLabel logolabel;
  private JLabel label;
  private JButton waiterNButton;
  private JButton btnStart;
  private JButton btnLogOut;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          UIforKitchenStaff window = new UIforKitchenStaff();
          window.setVisible(true);

        } catch (Exception exc) {
          exc.printStackTrace();
        }
      }
    });
  }

  /**
   * Worker thread Which updates jtable every 10 seconds.
   */
  private static void updateData() {
    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

      @Override
      protected Void doInBackground() throws Exception {
        setLayoutx();
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

            int ordersize = tabModel.getRowCount() - 1;
            // orders = allOrders;
            for (int i = ordersize; i >= 0; i--) {
              tabModel.removeRow(i);
              setLayoutx();
            }
            for (Order ord : allOrders) {
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

  /**
   * Initialise the contents of the frame.
   */
  public UIforKitchenStaff() {

    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("UIforKitchenStaff");
    setBackground(Color.DARK_GRAY);
    // setBounds(100, 100, 1500, 950);
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setContentPane(contentPane);
    contentPane.setLayout(null);

    orderPanel = new JPanel();
    scrollPane = new JScrollPane();
    scrollPane.setBounds(0, 0, 1872, 636);
    orderPanel.add(scrollPane);
    orderPanel.setLayout(null);
    orderPanel.setBounds(19, 194, 1872, 636);
    orderPanel.setBackground(Color.DARK_GRAY);
    orderPanel.setForeground(Color.WHITE);
    contentPane.add(orderPanel);
    contentPane.setLayout(null);


    // set table preferences
    table.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
    table.setRowHeight(50);
    table.setRowMargin(2);
    table.setAutoCreateRowSorter(true);
    scrollPane.add(table);
    header = table.getTableHeader();
    header.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 18));
    scrollPane.setViewportView(table);


    allOrders = kstaff.getOrdersFromDatabase();
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
    table.setModel(tabModel);
    tabModel.fireTableDataChanged();
    updateData();


    // display message on waiter UI
    waiterNButton = new JButton("");
    waiterNButton.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 20));
    waiterNButton.setBounds(747, 843, 360, 99);
    waiterNButton.setBackground(Color.DARK_GRAY);
    waiterNButton.setIcon(
        new ImageIcon(UIforKitchenStaff.class.getResource("/client/app/ksnotifywaiter.png")));

    waiterNButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        int row = table.getSelectedRow();
        getCurrentOrders(allOrders);
        if (row < currentOrders.size() & row >= 0) {
          kstaff.getOrder(currentOrders.get(row).getId());
          kstaff.MarkAsCooked(currentOrders.get(row).getId());

        }
      }
    });
    contentPane.add(waiterNButton);

    // when pressed User login screen gets displayed
    btnLogOut = new JButton("");
    btnLogOut.setBackground(Color.DARK_GRAY);
    btnLogOut.setBounds(1531, 843, 360, 99);
    btnLogOut.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 20));
    btnLogOut
        .setIcon(new ImageIcon(UIforKitchenStaff.class.getResource("/client/app/kslogout.png")));

    btnLogOut.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {

        contentPane.setVisible(false);
        UIUserLogin login = new UIUserLogin();
        login.setVisible(true);
        dispose();
      }
    });
    contentPane.add(btnLogOut);


    // when pressed User changes order Stage to Cooking
    btnStart = new JButton("");
    btnStart.setBounds(19, 843, 360, 99);
    btnStart.setBackground(Color.DARK_GRAY);
    btnStart.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 20));
    btnStart.setIcon(new ImageIcon(UIforKitchenStaff.class.getResource("/client/app/ksStart.png")));

    btnStart.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        int row = table.getSelectedRow();
        getCurrentOrders(allOrders);
        if (row < currentOrders.size() && row >= 0) {
          kstaff.getOrder(currentOrders.get(row).getId());
          kstaff.startOrder(currentOrders.get(row).getId());

        }
      }
    });
    contentPane.add(btnStart);

    logolabel = new JLabel(" ");
    logolabel.setBounds(1698, 13, 204, 168);
    logolabel
        .setIcon(new ImageIcon(UIforKitchenStaff.class.getResource("/client/app/smalllogo.png")));
    contentPane.add(logolabel);


    bkgroundLabel = new JLabel("");
    bkgroundLabel.setBounds(476, -54, 1452, 984);
    bkgroundLabel
        .setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/background.png")));
    contentPane.add(bkgroundLabel);


    label = new JLabel("");
    label.setIcon(new ImageIcon(UIforKitchenStaff.class.getResource("/client/app/background.png")));
    label.setBounds(0, -12, 1365, 908);
    contentPane.add(label);

    background3 = new JLabel("");
    background3
        .setIcon(new ImageIcon(UIforKitchenStaff.class.getResource("/client/app/background.png")));
    background3.setBounds(-237, 104, 1365, 908);
    contentPane.add(background3);

    background4 = new JLabel("");
    background4
        .setIcon(new ImageIcon(UIforKitchenStaff.class.getResource("/client/app/background.png")));
    background4.setBounds(563, 104, 1365, 908);
    contentPane.add(background4);

  }


  /**
   * Creates an ArrayList that contains orders with stages 2 or 3.
   * 
   * @param allOrders an ArrayList of Database Orders.
   */
  public static void getCurrentOrders(List<Order> allOrders) {
    currentOrders = new ArrayList<Order>();
    for (Order o : allOrders) {
      if (o.getOrderStage() == 2 || o.getOrderStage() == 3) {
        currentOrders.add(o);
      }
    }
  }

  /**
   * Calculates the correct time, to display on the UI. Returns in string format.
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
   * Displays MenuItems in each Order as a String after removing all white spaces.
   * 
   * @param Order.
   * @return String represents MenuItems in each order.
   */
  public static String displayMenuItems(Order ord) {
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

  public static String displayOrderStage(Order ord) {

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
   * Used to apply changes to table layout, by setting columns width and cells alignment.
   */
  public static void setLayoutx() {
    // set size for all table columns
    TableColumn items = table.getColumnModel().getColumn(0);
    items.setPreferredWidth(20);
    TableColumn items1 = table.getColumnModel().getColumn(1);
    items1.setPreferredWidth(20);
    TableColumn items2 = table.getColumnModel().getColumn(2);
    items2.setPreferredWidth(20);
    TableColumn items3 = table.getColumnModel().getColumn(3);
    items3.setPreferredWidth(600);
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
