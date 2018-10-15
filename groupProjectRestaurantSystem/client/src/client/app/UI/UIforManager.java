package client.app.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.mail.internet.AddressException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import client.app.logic.Account;
import client.app.logic.Email;
import client.app.logic.Ingredient;
import client.app.logic.Menu;
import client.app.logic.MenuItem;
import client.app.logic.Order;
import client.app.logic.Table;
import client.app.logic.TableList;
import client.app.swing.objects.NoEditDefaultTable;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;


/*
 * @Author Johannes Herforth
 * @Author James Johnson
 */

public class UIforManager extends JFrame {
	final static String DATAPANEL = "Real-Time Data";
	final static String MENUPANEL = "Menu";
	final static String ACCOUNTSPANEL = "Accounts";
	final static String EMAILPANEL = "Email";
	String email;
	String password1;
	String header;
	String content;

	
	private final String[] accountCols = {"ID","Name","Position","Email"};
	private final String[] menuCols = {"ID","Name","Price","onMenu", "Available", "Special Offer"};
	private final String[] tableCols = {"ID","# of people","availability"};
	private final String[] stockCols = {"ID","Name","quantity", "exp. date", "allergy"};
	private final String[] orderCols = {"ID","TableID", "WaiterID" , "Time of Order","Order Stage"};
	private final String[] osCols = {"TableID","WaiterID","Time of Request"};
	private Object[][] accountData = {};
	private Object[][] menuData = {};
	private Object[][] tableData = {};
    private Object[][] stockData = {};
    private Object[][] orderData = {};
    private Object[][] osData = {};
    private List<MenuItem> menu = new ArrayList<>();
    private List<Table> tables = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private List<Ingredient> stocks = new ArrayList<>();
    private List<Account> accs = new ArrayList<>();
    private List<SwingWorker<Void, Void>> workers = new ArrayList<>();
	private long currItem;
	private JPanel contentPane;

	private JPasswordField passwordField;
	private final JScrollPane accountsscrollpane = new JScrollPane();
	private final JButton btnAddAccount = new JButton("");
	private JTable accountTable = new JTable(accountData,accountCols);
	private JTable menuTable = new JTable(menuData, menuCols);
	private JTable orderTable = new JTable(orderData, orderCols);
	private JTable stockTable = new JTable(stockData, stockCols);
	private JTable requestTable = new JTable(osData, osCols);
	private NoEditDefaultTable accountModel = new NoEditDefaultTable(accountData, accountCols);
	private NoEditDefaultTable menuModel = new NoEditDefaultTable(menuData, menuCols);
	//private NoEditDefaultTable reserveModel = new NoEditDefaultTable(tableData, tableCols);
	private NoEditDefaultTable stockModel = new NoEditDefaultTable(stockData, stockCols);
	private NoEditDefaultTable orderModel = new NoEditDefaultTable(orderData, orderCols);
	private NoEditDefaultTable osModel = new NoEditDefaultTable(osData, osCols);
	private NoEditDefaultTable tableModel = new NoEditDefaultTable(tableData, tableCols);
	private final JScrollPane menuscrollpane = new JScrollPane();
	private final JTable table = new JTable();
	private final JButton btnEdit = new JButton("");
	private final JButton emailButton = new JButton("");
	private final JButton btnNewButton = new JButton("");
	private final JButton btnNewButton_1 = new JButton("");
	private final JButton btnNewButton_2 = new JButton("");
	private final JButton btnNewButton_3 = new JButton("");
	private final JScrollPane realtimescrollpane = new JScrollPane();
	private JTable realtimedatatable = new JTable(tableData, tableCols);
	private final JLabel emailAd = new JLabel("Email Address: ");
	private final JLabel password = new JLabel("Password: ");
	private JTextField usernameField = new JTextField(53);
	private JPasswordField emailPassField = new JPasswordField(55);
	private JLabel eText = new JLabel("Text: ");
	private JTextField emailText = new JTextField(58);
	private JLabel confirmation = new JLabel(Email.confirmEmail());
	private JLabel emailHeader = new JLabel("Header: ");
	private JTextField headerInput = new JTextField(57);
	private final JLabel label = new JLabel("");
	private final JLabel label_1 = new JLabel("");
	private final JLabel label_2 = new JLabel("");
	private final JLabel label_3 = new JLabel("");
	private final JLabel label_4 = new JLabel("");
	private final JLabel label_5 = new JLabel("");
	
	private UIforManager thisUI = this;
	private final JButton btnLogout = new JButton("");
	private final JButton btnAddItem = new JButton("");
	

	
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIforManager frame = new UIforManager();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void stopWorkers() {
	    for (int i = 0; i < workers.size(); i++) {
	      workers.get(i).cancel(true);
	    }
	  }
	    
	  /**
	   * Worker thread Which updates table 1 every 5 seconds by getting a single order
	   */
	  private void updateTables(int tableid) {

	    this.stopWorkers();
	    //System.out.println(tableid);
	    if (tableid == 0) {//table worker
	      SwingWorker<Void, Void> tableWorker = new SwingWorker<Void, Void>() {

	        @Override
	        protected Void doInBackground() throws Exception {
	          while (true) {
	            List<Table> newTables = new ArrayList<>();
	            newTables = TableList.INSTANCE.getTablesFromDataBase();

	            if (tableModel.getRowCount() == 0) {
	              for (Table tab : newTables) {
	                tableModel.addRow(new Object[] {tab.getTableID(), tab.getSize(), tab.getStatus()}); // {"ID","#
	                                                                                                    // of
	                                                                                                    // people","availability"}
	              }
	            } else {
	              int tablesize = tableModel.getRowCount() - 1;
	              tables = newTables;
	              for (int i = tablesize; i >= 0; i--) {
	                tableModel.removeRow(i);
	              }
	              for (Table tab : tables) {
	                tableModel.addRow(new Object[] {tab.getTableID(), tab.getSize(), tab.getStatus()}); // {"ID","#
	                                                                                                    // of
	                                                                                                    // people","availability"}
	              }
	            }

	            realtimedatatable.setModel(tableModel);
	            tableModel.fireTableDataChanged();
	            //System.out.println("UPDATED 0");
	            Thread.sleep(5000);
	          }
	        }
	      };

	      workers.add(tableWorker);
	      tableWorker.execute();
	    } else if (tableid == 1) { //order worker
	      SwingWorker<Void, Void> orderWorker = new SwingWorker<Void, Void>() {

	        @Override
	        protected Void doInBackground() throws Exception {
	          while (true) {
	            List<Order> newOrders = new ArrayList<>();
	            // Order newOrder = new Order();
	            newOrders = Order.getAllOrders();

	            if (orderModel.getRowCount() == 0) {
	              for (Order order : newOrders) {
	                orderModel.addRow(new Object[] {order.getId(), order.getTableId(),
	                    order.getWaiterId(), order.getTOA(), order.getOrderStage()}); // {"ID","TableID",
	                                                                                  // "WaiterID" ,
	                                                                                  // "Time of
	                                                                                  // Order","Order
	                                                                                  // Stage"};
	              }
	            } else {
	              int ordersize = orderModel.getRowCount() - 1;
	              orders = newOrders;
	              for (int i = ordersize; i >= 0; i--) {
	                orderModel.removeRow(i);
	              }
	              for (Order order : orders) {
	                orderModel.addRow(new Object[] {order.getId(), order.getTableId(),
	                    order.getWaiterId(), order.getTOA(), order.getOrderStage()}); // {"ID","TableID",
	                                                                                  // "WaiterID" ,
	                                                                                  // "Time of
	                                                                                  // Order","Order
	                                                                                  // Stage"};
	              }
	            }

	            //orderTable.setModel(orderModel);
	            orderModel.fireTableDataChanged();
	            //System.out.println("UPDATED 1");
	            Thread.sleep(5000);
	          }
	        }
	      };
	      workers.add(orderWorker);
	      orderWorker.execute();
	    } else if (tableid == 2) {
	      SwingWorker<Void, Void> stockWorker = new SwingWorker<Void, Void>() {

	        @Override
	        protected Void doInBackground() throws Exception {
	          while (true) {
	            List<Ingredient> newStock = new ArrayList<>();
	            newStock = Ingredient.getAllStock();
	            Date date = new Date();
	            String modifiedDate = new SimpleDateFormat("yyyyMMdd").format(date);

	            if (stockModel.getRowCount() == 0) {
	              for (Ingredient stock : newStock) {
	                if (Integer.parseInt(modifiedDate) > stock.getExpiryDate()) {
	                  stock.setName("(EXPIRED) " + stock.getName());
	                }
	                stockModel.addRow(new Object[] {stock.getId(), stock.getName(), stock.getQuantity(),
	                    stock.getExpiryDate(), stock.getAllergy()}); // {"ID","Name","quantity", "exp.
	                                                                 // date", "allergy"};
	              }
	            } else {
	              int stocksize = stockModel.getRowCount() - 1;
	              stocks = newStock;
	              for (int i = stocksize; i >= 0; i--) {
	                stockModel.removeRow(i);
	              }

	              for (Ingredient stock : stocks) {
	                if (Integer.parseInt(modifiedDate) > stock.getExpiryDate()) {
	                  stock.setName("(EXPIRED) " + stock.getName());
	                }
	                stockModel.addRow(new Object[] {stock.getId(), stock.getName(), stock.getQuantity(),
	                    stock.getExpiryDate(), stock.getAllergy()}); // {"ID","Name","quantity", "exp.
	                                                                 // date", "allergy"};

	              }
	            }

	            stockTable.setModel(stockModel);
	            stockModel.fireTableDataChanged();
	            Thread.sleep(5000);
	          }
	        }
	      };
	      workers.add(stockWorker);
	      stockWorker.execute();
	    } else if (tableid == 3) {
	      // SwingWorker<Void, Void> worker3 = new SwingWorker<Void, Void>() {
	      //
	      // @Override
	      // protected Void doInBackground() throws Exception {
	      // while (true) {
	      // Thread.sleep(5000);
	      // }
	      // }
	      // };
	    } //else if (tableid == 4) {
	      SwingWorker<Void, Void> menuWorker = new SwingWorker<Void, Void>() {

	        @Override
	        protected Void doInBackground() throws Exception {
	          while (true) {
	            List<MenuItem> newMenu = new ArrayList<>();
	            Menu menuClass = new Menu();
	            newMenu = menuClass.getMenu();
	            if (!menu.equals(newMenu)) {
	              int menusize = menu.size() - 1;
	              menu = newMenu;

	              for (int i = menusize; i >= 0; i--) {
	                menuModel.removeRow(i);
	              }
	              for (MenuItem mitem : menu) {
	                menuModel.addRow(new Object[] {mitem.getId(), mitem.getName(), mitem.getPrice(),
	                    mitem.isOnMenu(), mitem.isAvailable(), mitem.getSpecialOffer()});
	              }

	            }
	            menuTable.setModel(menuModel);
	            menuModel.fireTableDataChanged();
	            Thread.sleep(10000);
	          }
	        }
	      };
	      workers.add(menuWorker);
	      menuWorker.execute();
	    //} else if (tableid == 5) {
	      SwingWorker<Void, Void> accountWorker = new SwingWorker<Void, Void>() {

	        @Override
	        protected Void doInBackground() throws Exception {
	          while (true) {
	            List<Account> newAccounts = new ArrayList<>();
	            Account accountClass = new Account();
	            newAccounts = accountClass.getAllAccounts();
	            if (!accs.equals(newAccounts)) {
	              int accsize = accs.size() - 1;
	              accs = newAccounts;

	              for (int i = accsize; i >= 0; i--) {
	                accountModel.removeRow(i);
	              }
	              for (Account acc : accs) {
	                accountModel.addRow(new Object[] {acc.getAccountID(), acc.getName(),
	                    acc.getAccountType(), acc.getEmail()});
	              }

	            }
	            accountTable.setModel(accountModel);
	            accountModel.fireTableDataChanged();
	            Thread.sleep(10000);
	          }
	        }
	      };
	      workers.add(accountWorker);
	      accountWorker.execute();

	    //}
	  }

	/**
	 * Create the frame.
	 */
	public UIforManager() {
		setForeground(Color.WHITE);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		realtimescrollpane.setOpaque(false);
        realtimescrollpane.getViewport().setOpaque(false);
        realtimedatatable.setOpaque(false);
		((DefaultTableCellRenderer)realtimedatatable.getDefaultRenderer(Object.class)).setOpaque(false);
		realtimedatatable.setShowGrid(false);
		
		orderTable.setOpaque(false);
		((DefaultTableCellRenderer)orderTable.getDefaultRenderer(Object.class)).setOpaque(false);
		orderTable.setShowGrid(false);
		
		stockTable.setOpaque(false);
		((DefaultTableCellRenderer)orderTable.getDefaultRenderer(Object.class)).setOpaque(false);
		stockTable.setShowGrid(false);
		
		requestTable.setOpaque(false);
		((DefaultTableCellRenderer)orderTable.getDefaultRenderer(Object.class)).setOpaque(false);
		requestTable.setShowGrid(false);
		
		menuscrollpane.setOpaque(false);
		menuscrollpane.getViewport().setOpaque(false);
        menuTable.setForeground(Color.WHITE);
        menuTable.setFont(new Font("Euphemia UCAS", Font.PLAIN, 12));
        menuTable.setOpaque(false);
		((DefaultTableCellRenderer)menuTable.getDefaultRenderer(Object.class)).setOpaque(false);
		menuTable.setShowGrid(false);
		
		
		accountsscrollpane.setOpaque(false);
        accountsscrollpane.getViewport().setOpaque(false);
        accountTable.setFont(new Font("Euphemia UCAS", Font.PLAIN, 12));
        accountTable.setForeground(Color.WHITE);
        accountTable.setOpaque(false);
		((DefaultTableCellRenderer)accountTable.getDefaultRenderer(Object.class)).setOpaque(false);
		accountTable.setShowGrid(false);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBackground(new Color(255, 69, 0));
		tabbedPane.setBounds(0, 0, 720, 400);
		tabbedPane.setForeground(new Color(128, 0, 0));
		tabbedPane.setFont(new Font("Euphemia UCAS", Font.PLAIN, 13));
		contentPane.add(tabbedPane);
		this.updateTables(0);

        //Create the "cards".
        JPanel realtimecard = new JPanel();
        realtimecard.setLayout(null);

        JPanel menucard = new JPanel();
        
        
        //Card 3 variables.
        JPanel accountscard = new JPanel();
        accountTable.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            if(e.getClickCount() == 2) {
              //JOptionPane.showMessageDialog(null, accountTable.getValueAt(accountTable.getSelectedRow(), 0));
              currItem = (long)accountTable.getValueAt(accountTable.getSelectedRow(), 0);
              UImanagerAccountInfo accountEdit = new UImanagerAccountInfo(currItem);
              accountEdit.setVisible(true);
            }
          }
        });
        
        accountTable.setModel(accountModel);

        
        //CARD 1 THINGS
        tabbedPane.addTab(DATAPANEL, realtimecard);
        realtimecard.setLayout(null);

        realtimedatatable.setFont(new Font("Euphemia UCAS", Font.PLAIN, 12));
        realtimedatatable.setForeground(new Color(255, 255, 255));
        orderTable.setForeground(new Color(255, 255, 255));
        
        requestTable.setForeground(new Color(255, 255, 255));
        

        
        realtimedatatable.setModel(tableModel);
        btnNewButton.setIcon(new ImageIcon(UIforManager.class.getResource("/client/app/btnTables.png")));
        btnNewButton.setBorder(null);
        btnNewButton.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            List<Table> newTables = new ArrayList<>();
            newTables = TableList.INSTANCE.getTablesFromDataBase();

            if(tableModel.getRowCount() == 0) {
              for(Table tab : newTables) {
                tableModel.addRow(new Object[] {tab.getTableID(), tab.getSize(), tab.getStatus()}); //{"ID","# of people","availability"}
              }
            }else {
              int tablesize = tableModel.getRowCount() - 1;
              tables = newTables;
              for(int i = tablesize; i >= 0; i--) {
                tableModel.removeRow(i);
              }
              for (Table tab : tables) {
                tableModel.addRow(new Object[] {tab.getTableID(), tab.getSize(), tab.getStatus()}); //{"ID","# of people","availability"}
              }
            }
            
            realtimedatatable.setModel(tableModel);
            realtimescrollpane.setViewportView(realtimedatatable);
            
            realtimedatatable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            realtimedatatable.setAutoCreateRowSorter(true);
            
            thisUI.updateTables(0);
            
          }
        });
        btnNewButton.setBounds(556, 6, 143, 29);
        
        realtimecard.add(btnNewButton);
        btnNewButton_1.setIcon(new ImageIcon(UIforManager.class.getResource("/client/app/btnOrders.png")));
        btnNewButton_1.setBorder(null);
        btnNewButton_1.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            List<Order> newOrders = new ArrayList<>();
            //Order newOrder = new Order();
            newOrders = Order.getAllOrders();
            
            if(orderModel.getRowCount() == 0) {
              for(Order order : newOrders) {
                orderModel.addRow(new Object[] {order.getId(), order.getTableId(), order.getWaiterId(), order.getTOA(), order.getOrderStage()}); //{"ID","TableID", "WaiterID" , "Time of Order","Order Stage"};
              }
            }else {
              int ordersize = orderModel.getRowCount() - 1;
              orders = newOrders;
              for(int i = ordersize; i >= 0; i--) {
                orderModel.removeRow(i);
              }
              for (Order order : orders) {
                orderModel.addRow(new Object[] {order.getId(), order.getTableId(), order.getWaiterId(), order.getTOA(), order.getOrderStage()}); //{"ID","TableID", "WaiterID" , "Time of Order","Order Stage"};
              }
            }
            
            orderTable.setModel(orderModel);
            realtimescrollpane.setViewportView(orderTable);
            orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            orderTable.setAutoCreateRowSorter(true);
            thisUI.updateTables(1);
          }
        });
        btnNewButton_1.setBounds(556, 46, 143, 29);
        
        realtimecard.add(btnNewButton_1);
        btnNewButton_2.setIcon(new ImageIcon(UIforManager.class.getResource("/client/app/btnStocks.png")));
        btnNewButton_2.setBorder(null);
        btnNewButton_2.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            List<Ingredient> newStock = new ArrayList<>();
            //Order newOrder = new Order();
            newStock = Ingredient.getAllStock();
            Date date = new Date();
            String modifiedDate= new SimpleDateFormat("yyyyMMdd").format(date);
            
            if(stockModel.getRowCount() == 0) {
              for(Ingredient stock : newStock) {
                if(Integer.parseInt(modifiedDate) > stock.getExpiryDate()){
                  stock.setName("(EXPIRED) " + stock.getName());
                }
                stockModel.addRow(new Object[] {stock.getId(), stock.getName(), stock.getQuantity(), stock.getExpiryDate(), stock.getAllergy()}); //{"ID","Name","quantity", "exp. date", "allergy"};
              }
            }else {
              int stocksize = stockModel.getRowCount() - 1;
              stocks = newStock;
              for(int i = stocksize; i >= 0; i--) {
                stockModel.removeRow(i);
              }

              for (Ingredient stock : stocks) {
                if(Integer.parseInt(modifiedDate) > stock.getExpiryDate()){
                  stock.setName("(EXPIRED) " + stock.getName());
                }
                stockModel.addRow(new Object[] {stock.getId(), stock.getName(), stock.getQuantity(), stock.getExpiryDate(), stock.getAllergy()}); //{"ID","Name","quantity", "exp. date", "allergy"};
                
              }
            }
            
            stockTable.setModel(stockModel);
            realtimescrollpane.setViewportView(stockTable);
            stockTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            stockTable.setAutoCreateRowSorter(true);
            thisUI.updateTables(2);
          }
        });
        btnNewButton_2.setBounds(556, 87, 143, 29);
        
        realtimecard.add(btnNewButton_2);
        btnNewButton_3.setIcon(new ImageIcon(UIforManager.class.getResource("/client/app/btnRequests.png")));
        btnNewButton_3.setBorder(null);
        btnNewButton_3.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            requestTable.setModel(osModel);
            realtimescrollpane.setViewportView(requestTable);
            requestTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            requestTable.setAutoCreateRowSorter(true);
          }
        });
        btnNewButton_3.setBounds(556, 128, 143, 29);
        
        
        //END OF CARD 1
        
        realtimecard.add(btnNewButton_3);
        realtimescrollpane.setBounds(6, 6, 552, 342);
        
        realtimecard.add(realtimescrollpane);
        
        realtimescrollpane.setViewportView(realtimedatatable);
        label_1.setIcon(new ImageIcon(UIforManager.class.getResource("/client/app/redbackground.png")));
        label_1.setBounds(0, 0, 699, 364);
        
        realtimecard.add(label_1);
        tabbedPane.addTab(MENUPANEL, menucard);
        menucard.setLayout(null);
        btnEdit.setIcon(new ImageIcon(UIforManager.class.getResource("/client/app/EditItem.png")));
        btnEdit.setBorder(null);
        btnEdit.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            currItem = (long)menuTable.getValueAt(menuTable.getSelectedRow(), 0);
            UImanagerMenuItem mitemedit = new UImanagerMenuItem(currItem);
            mitemedit.setVisible(true);
            //JOptionPane.showMessageDialog(null, menuTable.getValueAt(menuTable.getSelectedRow(), 0));
          }
        });
        btnAddItem.setIcon(new ImageIcon(UIforManager.class.getResource("/client/app/btnAddItem.png")));
        btnAddItem.setBorder(null);
        btnAddItem.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            UImanagerAddItem uimai = new UImanagerAddItem();
            uimai.setVisible(true);
            //ADDING ITEM
          }
        });
        btnAddItem.setBounds(540, 32, 159, 29);
        
        menucard.add(btnAddItem);
        btnEdit.setBounds(556, 73, 137, 29);
        
        menucard.add(btnEdit);
        menuTable.setModel(menuModel);
        menuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menuTable.setAutoCreateRowSorter(true);
        menuscrollpane.setBounds(6, 6, 538, 342);
        
        menucard.add(menuscrollpane);
        
        menuscrollpane.setViewportView(menuTable);
        label_2.setIcon(new ImageIcon(UIforManager.class.getResource("/client/app/redbackground.png")));
        label_2.setBounds(0, 0, 699, 364);
        
        menucard.add(label_2);
        tabbedPane.addTab(ACCOUNTSPANEL, accountscard);
        accountscard.setLayout(null);
        accountsscrollpane.setBounds(6, 6, 538, 342);
        
        accountscard.add(accountsscrollpane);
        accountTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        accountTable.getColumnModel().getColumn(0).setPreferredWidth(-100);
        accountTable.getColumnModel().getColumn(2).setPreferredWidth(-100);
        accountTable.setAutoCreateRowSorter(true);
        accountsscrollpane.setViewportView(accountTable);
        btnAddAccount.setIcon(new ImageIcon(UIforManager.class.getResource("/client/app/Add Account.png")));
        btnAddAccount.setBorder(null);
        btnAddAccount.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            dispose();
            UIRegister register = new UIRegister();

        	register.radioButton.setVisible(true);
        	register.radioButton_1.setVisible(true);
        	register.radioButton_2.setVisible(true);
        	register.radioButton_3.setVisible(true);

            register.setVisible(true);
            
          }
        });
        
        btnAddAccount.setBounds(549, 6, 150, 39);
        
        accountscard.add(btnAddAccount);
        label_3.setIcon(new ImageIcon(UIforManager.class.getResource("/client/app/redbackground.png")));
        label_3.setBounds(0, 0, 699, 364);
        
        accountscard.add(label_3);
        
 
        /**
         * @author Martin
         */
        contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPane);
		contentPane.setLayout(null);
        JPanel emailcard = new JPanel();
        emailcard.setBackground(new Color(128, 0, 0));
        tabbedPane.addTab(EMAILPANEL, emailcard); //new panel
        emailcard.setLayout(null);
        emailAd.setFont(new Font("Euphemia UCAS", Font.PLAIN, 15));
        emailAd.setBounds(298, 5, 113, 21);
		emailcard.add(emailAd); //add email label
		usernameField.setBackground(new Color(0, 0, 0));
		usernameField.setForeground(new Color(255, 69, 0));
		usernameField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		usernameField.setBounds(24, 31, 650, 28);
		emailcard.add(usernameField); //add email input
        password.setFont(new Font("Euphemia UCAS", Font.PLAIN, 15));
        password.setBounds(311, 64, 77, 21);
        emailcard.add(password); //add password label
        emailPassField.setBackground(new Color(0, 0, 0));
        emailPassField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        emailPassField.setForeground(new Color(255, 69, 0));
        emailPassField.setBounds(24, 90, 650, 28);
        emailcard.add(emailPassField); //add password input
        emailHeader.setFont(new Font("Euphemia UCAS", Font.PLAIN, 15));
        emailHeader.setBounds(318, 123, 62, 21);
        emailcard.add(emailHeader); //add header label
        headerInput.setBackground(new Color(0, 0, 0));
        headerInput.setForeground(new Color(255, 69, 0));
        headerInput.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        headerInput.setBounds(24, 149, 650, 28);
        emailcard.add(headerInput); //add header input
        eText.setFont(new Font("Euphemia UCAS", Font.PLAIN, 15));
        eText.setBounds(329, 182, 41, 21);
        emailcard.add(eText); //add content label
        emailText.setForeground(new Color(255, 69, 0));
        emailText.setBackground(new Color(0, 0, 0));
        emailText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        emailText.setBounds(24, 208, 650, 73);
        emailcard.add(emailText); //add content input
        emailButton.setIcon(new ImageIcon(UIforManager.class.getResource("/client/app/Send Email.png")));
        emailButton.setBorder(null);
        emailButton.setBounds(284, 293, 136, 29); //email button dimensions
        emailcard.add(emailButton); //add email button to card 2
        confirmation.setForeground(new Color(192, 192, 192));
        confirmation.setBounds(421, 298, 73, 16);
        emailcard.add(confirmation);
        label_4.setIcon(new ImageIcon(UIforManager.class.getResource("/client/app/redbackground.png")));
        label_4.setBounds(293, 30, -208, -55);
        
        emailcard.add(label_4);
        label_5.setIcon(new ImageIcon(UIforManager.class.getResource("/client/app/redbackground.png")));
        label_5.setBounds(0, -13, 711, 379);
        
        emailcard.add(label_5);
        btnLogout.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		UIUserLogin frame = new UIUserLogin();
                frame.setVisible(true);
                dispose();
                UIUserLogin.loggedInId = 0;
        	}
        });
        btnLogout.setIcon(new ImageIcon(UIforManager.class.getResource("/client/app/btnLogoutsmall.png")));
        btnLogout.setBorder(null);
        
        tabbedPane.addTab("Logout", null, btnLogout, null);
        label.setBounds(-13, 0, 757, 410);
        contentPane.add(label);
        label.setFont(new Font("Euphemia UCAS", Font.PLAIN, 13));
        label.setForeground(new Color(255, 69, 0));
        label.setIcon(new ImageIcon(UIforManager.class.getResource("/client/app/background.png")));
        confirmation.setVisible(false);

        /**
         * When text is inputted into the text fields
         */
        emailButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		email = usernameField.getText();
        		char[] input = emailPassField.getPassword();
        		password1 = new String(input);
        		header = headerInput.getText();
        		content = emailText.getText();
        		
        	}
        });
        
        /**
         * When mouse is clicked on the send email button
         */
        emailButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		try {
        			Email emails = new Email(email, password1, header, content);
        			emails.sendEmail();
        			confirmation.setVisible(true);
				} catch (AddressException e1) {
					System.out.println("Caught address exception.");
					e1.printStackTrace();
				}
        	}
        });
	}
}
