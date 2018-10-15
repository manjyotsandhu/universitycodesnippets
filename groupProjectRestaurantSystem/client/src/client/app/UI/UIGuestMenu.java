package client.app.UI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import client.app.logic.Customer;
import client.app.logic.Menu;
import client.app.logic.MenuItem;
import client.app.logic.MenuItems;
import client.app.logic.Order;
import client.app.swing.objects.NoEditDefaultTable;
import client.server.connection.ServerClient;

/**
 * @author James Johnson
 */
public class UIGuestMenu extends JFrame {
	
	private JButton btnRequestWaiter = new JButton("");
	private double amount;
	private int setUpTable = UISetUp.getTableChosen();
	private StringBuffer currResponse = new StringBuffer();
	private MenuItems mitems = new MenuItems();
	private JButton btnViewMenu = new JButton("");
	private JLabel lblfilter = new JLabel("All Selected");
	private JButton Choosebtn = new JButton("");
	private ServerClient scli = new ServerClient();
	private JButton btnVegetarian = new JButton("");
	private JLabel lblYourOrderIs = new JLabel("Your order is on the way...");
	private JPanel contentPane;
	private JButton btnAll = new JButton("");
	private List<MenuItem> dbmenu = new ArrayList<>();
	private JLabel lblEnjoyYourMeal = new JLabel("Enjoy your meal, at the Smart Cafe.");
	private JLabel lblOrderStatus = new JLabel("Order Status");
	private Menu newmenu = new Menu();
	private JLabel cover = new JLabel("");
	private JLabel lblfiltering = new JLabel("");
	private JButton Removebtn = new JButton("");
	private JButton btnKosher = new JButton("");
	private JLabel lblCurrentStatus = new JLabel("Current status");
	private List<MenuItem> menuArray = new ArrayList<>();
	private JButton btnRefresh = new JButton("");
	private List<String> menuNameList = new ArrayList<>();
	JButton btnLoginregister = new JButton("");
	JButton btnLogout = new JButton("");
	private JButton btnPay;
	private JTable table;
	private JButton btnOrder = new JButton("");
	private Order newOrder = new Order();
	private MenuItem selection = new MenuItem();
	private JTable mainstable;
	private JTable dessertstable;
	private JTable drinkstable;
	private String[] menuCols = { "Name", "Price" };
	private Object[][] menuData = {};
	private JTable StartersTable = new JTable(menuData, menuCols);
	private String[] orderCols = { "Name", "Price" };
	private Object[][] orderData = {};
	private JButton btnHalal = new JButton("");
	private NoEditDefaultTable orderModel = new NoEditDefaultTable(orderData, orderCols);
	private JScrollPane Order_scrollPane = new JScrollPane();
	private NoEditDefaultTable starterModel = new NoEditDefaultTable(menuData, menuCols);
	private NoEditDefaultTable mainModel = new NoEditDefaultTable(menuData, menuCols);
	private NoEditDefaultTable dessertModel = new NoEditDefaultTable(menuData, menuCols);
	private NoEditDefaultTable drinkModel = new NoEditDefaultTable(menuData, menuCols);
	private Menu menuClass = new Menu();
	private JTable orderstable = new JTable(orderData, orderCols);
	private Customer loggedInCust = new Customer();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIGuestMenu frame = new UIGuestMenu();
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
	public UIGuestMenu() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1440, 900);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Setting up the menu items to their respective tables
		menuArray = menuClass.getMenu();
		for (MenuItem mitem : menuArray) {
			if (mitem.getType() == 0) {
				dessertModel.addRow(new Object[] { mitem.getName(), mitem.getPrice(), });
			}
			if (mitem.getType() == 1) {
				starterModel.addRow(new Object[] { mitem.getName(), mitem.getPrice(), });
			}
			if (mitem.getType() == 2) {
				mainModel.addRow(new Object[] { mitem.getName(), mitem.getPrice(), });
			}
			if (mitem.getType() == 3) {
				drinkModel.addRow(new Object[] { mitem.getName(), mitem.getPrice(), });
			}

		}
		dbmenu = newmenu.getMenu();
		for (MenuItem item : dbmenu) {
			menuNameList.add(item.getName());
		}

		// Setting the customer details
		loggedInCust.setCust_id(UIUserLogin.loggedInId);
		loggedInCust.setCallingWaiter(0);
		loggedInCust.setTableID(1);
		System.out.println("Logged in customer on menu is: " + loggedInCust.getCust_id());

		lblCurrentStatus.setFont(new Font("Euphemia UCAS", Font.PLAIN, 16));
		lblCurrentStatus.setVisible(false);

		btnPay = new JButton("");
		btnPay.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/Paybtn.png")));
		btnPay.setBorder(null);
		btnPay.setVisible(false);
		btnPay.setForeground(Color.GRAY);
		btnPay.setFont(new Font("Euphemia UCAS", Font.PLAIN, 13));

		/**
		 * @author James 
		 * Send the payment through to the payment UI
		 */
		btnPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UIPayment pay = new UIPayment(amount, newOrder);
				pay.setVisible(true);
				dispose();
			}
		});

		/**
		 * @author James 
		 * Makes an order for the current user by taking the
		 * values of the order table and calling a new order from the
		 * Order class. Also reorganises menu UI to postorder.
		 */
		btnOrder.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/Orderbtn.png")));
		btnOrder.setBorder(null);
		
		btnOrder.addActionListener(new ActionListener() {
			List<String> itemList = new ArrayList<>();
			public void actionPerformed(ActionEvent e) {
				lblEnjoyYourMeal.setVisible(true);
				btnPay.setVisible(true);
				btnRefresh.setVisible(true);
				lblOrderStatus.setVisible(true);
				lblCurrentStatus.setVisible(true);
				cover.setVisible(true);
				lblYourOrderIs.setVisible(true);
				btnOrder.setVisible(false);
				btnViewMenu.setVisible(true);
				btnAll.setVisible(false);
				btnKosher.setVisible(false);
				btnVegetarian.setVisible(false);
				btnHalal.setVisible(false);
				lblfiltering.setVisible(false);
				lblfilter.setVisible(false);

				//Cycles through order table
				int nRow = orderModel.getRowCount();
				Object[] tableData = new Object[nRow];
				for (int i = 0; i < nRow; i++) {
					tableData[i] = orderModel.getValueAt(i, 0);
					itemList.add((String) tableData[i]);

				}

				newOrder.setTableId(setUpTable);
				for (String s : itemList) {
					for (MenuItem item : dbmenu) {
						if (s.equals(item.getName())) {
							newOrder.addMenuItem(item.getId(), item.getPrice());
							amount = amount + item.getPrice();
							break;
						}
					}
				}

				newOrder = (Order) scli.convertXML(newOrder.makeOrder(), Order.class);		//Sends the order
				
				if (newOrder.getId() > 0) {
					JOptionPane.showMessageDialog(null, "Order Placed!");
				}
				else {
					JOptionPane.showMessageDialog(null, "There was an error placing your order! call for help!");
				}
				if (itemList.size() == 0) {
					JOptionPane.showMessageDialog(null, "You haven't chosen anything to order!");
				}

			}
		});

		lblYourOrderIs.setForeground(new Color(230, 230, 250));
		lblYourOrderIs.setFont(new Font("Euphemia UCAS", Font.PLAIN, 33));
		lblYourOrderIs.setVisible(false);
		
		/**
		 * @author James 
		 * Sets elements of UI that view the 
		 * menu visible but no order functionality
		 */
		btnViewMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAll.setVisible(true);
				btnKosher.setVisible(true);
				btnVegetarian.setVisible(true);
				btnHalal.setVisible(true);
				lblfiltering.setVisible(true);
				lblfilter.setVisible(true);
				cover.setVisible(false);
				lblYourOrderIs.setVisible(false);
				btnOrder.setVisible(false);
				btnViewMenu.setVisible(false);
				Choosebtn.setVisible(false);
				Removebtn.setVisible(false);
				lblEnjoyYourMeal.setVisible(false);
			}
		});

		btnViewMenu.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/btnViewMenu.png")));
		btnViewMenu.setBorder(null);
		btnViewMenu.setVisible(false);

		lblEnjoyYourMeal.setForeground(new Color(230, 230, 250));
		lblEnjoyYourMeal.setFont(new Font("Euphemia UCAS", Font.PLAIN, 34));
		lblEnjoyYourMeal.setVisible(false);
		lblEnjoyYourMeal.setBounds(370, 136, 609, 69);
		
		contentPane.add(lblEnjoyYourMeal);
		btnViewMenu.setBounds(918, 470, 170, 66);
		contentPane.add(btnViewMenu);
		lblYourOrderIs.setBounds(422, 450, 484, 86);
		contentPane.add(lblYourOrderIs);

		btnOrder.setForeground(SystemColor.inactiveCaptionText);
		btnOrder.setFont(new Font("Euphemia UCAS", Font.PLAIN, 13));
		btnOrder.setBounds(1038, 688, 369, 101);
		contentPane.add(btnOrder);

		btnRequestWaiter.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/requestwaiterbtn.png")));
		btnRequestWaiter.setBorder(null);
		btnRequestWaiter.setBounds(542, 688, 484, 101);
		contentPane.add(btnRequestWaiter);
		
		/**
		 * @author Manjyot 
		 * Sends a request to a waiter for this table
		 */
		btnRequestWaiter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				loggedInCust.setCallingWaiter(1);
				loggedInCust.setCust_id(UIUserLogin.loggedInId);
				loggedInCust.setTableID(1);
				int responseCode = loggedInCust.updateCustomer();

				if (responseCode == 200) {
					System.out.println("Call made");
				} else {
					System.out.println("Error making call");
				}
				JOptionPane.showMessageDialog(contentPane, "Call made to your waiter, please wait.");

			}
		});
		
		btnPay.setBounds(305, 688, 232, 101);
		contentPane.add(btnPay);
		cover.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/largeredbg.png")));
		cover.setVisible(false);
		cover.setBounds(-6, 265, 1462, 574);
		contentPane.add(cover);
		lblCurrentStatus.setForeground(new Color(230, 230, 250));
		lblCurrentStatus.setBounds(1201, 204, 140, 16);
		contentPane.add(lblCurrentStatus);

		/**
		 * @author James 
		 * Refreshes and checks for the order stage, which is then
		 * converted into meaninful information for the customer.
		 */
		btnRefresh.setVisible(false);
		btnRefresh.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/refreshbutton.png")));
		btnRefresh.setBorder(null);
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newOrder = newOrder.refreshOrder();
				if (newOrder.getOrderStage() == 0) {
					lblCurrentStatus.setText("N/A");
				} else if (newOrder.getOrderStage() == 1) {
					lblCurrentStatus.setText("Processing");
				} else if (newOrder.getOrderStage() == 2) {
					lblCurrentStatus.setText("Waiter Confirmed");
				} else if (newOrder.getOrderStage() == 3) {
					lblCurrentStatus.setText("Cooking");
				} else if (newOrder.getOrderStage() == 4) {
					lblCurrentStatus.setText("Delivering");
				} else if (newOrder.getOrderStage() == 5) {
					lblCurrentStatus.setText("Delivered");
				} 
			}
		});
		
		btnRefresh.setBounds(1181, 136, 198, 66);
		contentPane.add(btnRefresh);

		lblOrderStatus.setVisible(false);
		lblOrderStatus.setFont(new Font("Euphemia UCAS", Font.PLAIN, 23));
		lblOrderStatus.setForeground(new Color(255, 255, 255));
		lblOrderStatus.setBounds(1201, 107, 153, 27);
		contentPane.add(lblOrderStatus);

		lblfilter.setForeground(new Color(230, 230, 250));
		lblfilter.setFont(new Font("Euphemia UCAS", Font.PLAIN, 16));
		lblfilter.setBounds(308, 199, 354, 27);
		contentPane.add(lblfilter);

		lblfiltering.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/Filtering.png")));
		lblfiltering.setBounds(311, 107, 124, 69);
		contentPane.add(lblfiltering);

		/**
		 * @author Martin 
		 * button displays all menu items by deleting all items
		 * displayed, then all selecting menu items
		 */
		btnAll.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/all.png")));
		btnAll.setBorder(null);
		btnAll.setBounds(311, 136, 162, 69);
		contentPane.add(btnAll);
		btnAll.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { // when button is clicked
				lblfilter.setText("All Selected");
				resetTables(); // clears table
				currResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/menuitems");
				mitems = (MenuItems) scli.convertXML(currResponse, MenuItems.class); // gets
																						// menu
																						// items
				for (MenuItem mitem : mitems.getItems()) { // iterates through
															// all menu
															// items

					if (mitem.getType() == 0) { // if item is a dessert, add to
												// dessert
												// table
						dessertModel.addRow(new Object[] { mitem.getName(), mitem.getPrice(), });
					}
					if (mitem.getType() == 1) { // if item is a starter, add to
												// starter
												// table
						starterModel.addRow(new Object[] { mitem.getName(), mitem.getPrice(), });
					}
					if (mitem.getType() == 2) { // if item is a main, add to
												// main table
						mainModel.addRow(new Object[] { mitem.getName(), mitem.getPrice(), });
					}
					if (mitem.getType() == 3) { // if item is a drink, add to
												// drink table
						drinkModel.addRow(new Object[] { mitem.getName(), mitem.getPrice(), });

					}
				}
			}
		});

		/**
		 * @author Martin 
		 * button displays only kosher items by deleting all
		 * items displayed, then selecting menu items that have true set
		 * for isKosher
		 */
		btnKosher.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/kosher.png")));
		btnKosher.setBorder(null);
		btnKosher.setBounds(636, 136, 162, 69);
		contentPane.add(btnKosher);
		btnKosher.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { // when button is clicked
				lblfilter.setText("Kosher Selected");

				resetTables(); // clears table
				currResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/menuitems");
				mitems = (MenuItems) scli.convertXML(currResponse, MenuItems.class); // gets
																						// all
																						// menu
																						// items
				for (MenuItem mitem : mitems.getItems()) { // iterates through
															// all items
					if (mitem.isKosher() == true) { // if item has true for
													// isKosher

						if (mitem.getType() == 0) { // if item is a dessert, add
													// to dessert
													// table
							dessertModel.addRow(new Object[] { mitem.getName(), mitem.getPrice(), });
						}
						if (mitem.getType() == 1) { // if item is a starter, add
													// to starter
													// table
							starterModel.addRow(new Object[] { mitem.getName(), mitem.getPrice(), });
						}
						if (mitem.getType() == 2) { // if item is a main, add to
													// main table
							mainModel.addRow(new Object[] { mitem.getName(), mitem.getPrice(), });
						}
						if (mitem.getType() == 3) { // if item is a drink, add
													// to drink
													// table
							drinkModel.addRow(new Object[] { mitem.getName(), mitem.getPrice(), });

						}
					}
				}
			}
		});

		/**
		 * @author Martin 
		 * button displays only vegetarian menu items by deleting
		 * all displayed items and adding only menu items with true for
		 * isVege.
		 */
		btnVegetarian.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/vegetarian.png")));
		// button
		btnVegetarian.setBorder(null);
		btnVegetarian.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { // when button is clicked
				lblfilter.setText("Vegetarian Selected");

				resetTables(); // clears table
				currResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/menuitems");
				mitems = (MenuItems) scli.convertXML(currResponse, MenuItems.class); // gets
																						// all
																						// menu
																						// items
				for (MenuItem mitem : mitems.getItems()) { // iterates through
															// menu
															// items
					if (mitem.isVegeDish() == true) { // if item has true for
														// isVege

						if (mitem.getType() == 0) { // if item is a dessert, add
													// to dessert
													// table
							dessertModel.addRow(new Object[] { mitem.getName(), mitem.getPrice(), });
						}
						if (mitem.getType() == 1) { // if item is a starter, add
													// to starter
													// table
							starterModel.addRow(new Object[] { mitem.getName(), mitem.getPrice(), });
						}
						if (mitem.getType() == 2) { // if item is a main, add to
													// main table
							mainModel.addRow(new Object[] { mitem.getName(), mitem.getPrice(), });
						}
						if (mitem.getType() == 3) { // if item is a drink, add
													// to drink
													// table
							drinkModel.addRow(new Object[] { mitem.getName(), mitem.getPrice(), });

						}
					}
				}
			}
		});
		btnVegetarian.setBounds(474, 136, 162, 69);
		contentPane.add(btnVegetarian);

		/**
		 * @author Martin 
		 * button displays halal food on menu when clicked by
		 * deleting all menu items, then adding only menu items with
		 * true for isHalal.
		 */
		btnHalal.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/halal.png")));
		btnHalal.setBorder(null);
		btnHalal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { // when button is clicked
				lblfilter.setText("Halal Selected");

				resetTables(); // clears table
				currResponse = scli.httpGet("http://shouganai.net:8080/restaurant/rest/menuitems");
				mitems = (MenuItems) scli.convertXML(currResponse, MenuItems.class); // gets
																						// all
																						// menu
																						// items
				for (MenuItem mitem : mitems.getItems()) { // iterates through
															// menu
															// items
					if (mitem.isHalal() == true) { // if item has true for
													// isHalal

						if (mitem.getType() == 0) { // if item is a dessert, add
													// to dessert
													// table
							dessertModel.addRow(new Object[] { mitem.getName(), mitem.getPrice(), });
						}
						if (mitem.getType() == 1) { // if item is a starter, add
													// to starter
													// table
							starterModel.addRow(new Object[] { mitem.getName(), mitem.getPrice(), });
						}
						if (mitem.getType() == 2) { // if item is a main, add to
													// main table
							mainModel.addRow(new Object[] { mitem.getName(), mitem.getPrice(), });
						}
						if (mitem.getType() == 3) { // if item is a drink, add
													// to drink
													// table
							drinkModel.addRow(new Object[] { mitem.getName(), mitem.getPrice(), });

						}
					}
				}
			}
		});

		btnHalal.setBounds(794, 136, 170, 69);
		contentPane.add(btnHalal);

		Order_scrollPane.setBounds(1078, 319, 308, 320);
		contentPane.add(Order_scrollPane);
		orderstable.setForeground(new Color(230, 230, 250));
		orderstable.setFont(new Font("Euphemia UCAS", Font.PLAIN, 17));

		orderstable.setModel(orderModel);
		orderstable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Order_scrollPane.setViewportView(orderstable);
		orderstable.setAutoCreateRowSorter(true);

		Order_scrollPane.setOpaque(false);
		Order_scrollPane.getViewport().setOpaque(false);
		orderstable.setOpaque(false);
		((DefaultTableCellRenderer) orderstable.getDefaultRenderer(Object.class)).setOpaque(false);
		orderstable.setShowGrid(false);

		JScrollPane Starters_scrollpane = new JScrollPane();
		StartersTable.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		StartersTable.setForeground(new Color(230, 230, 250));
		StartersTable.setBorder(null);
		Starters_scrollpane.setBounds(36, 323, 374, 116);
		contentPane.add(Starters_scrollpane);
		Starters_scrollpane.setViewportView(StartersTable);
		StartersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		StartersTable.setModel(starterModel);
		StartersTable.setAutoCreateRowSorter(true);

		Starters_scrollpane.setOpaque(false);
		Starters_scrollpane.getViewport().setOpaque(false);
		StartersTable.setOpaque(false);
		((DefaultTableCellRenderer) StartersTable.getDefaultRenderer(Object.class)).setOpaque(false);
		StartersTable.setShowGrid(false);

		/**
		 * @author James
		 * When an item on the starter menu is double clicked, information
		 * for that particular item comes up in a pop up UI. 
		 */
		StartersTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && !e.isConsumed()) {
					e.consume();

					String chosen = (String) starterModel.getValueAt(StartersTable.getSelectedRow(), 0);

					for (MenuItem item : dbmenu) {
						if (chosen.equals(item.getName())) {
							UIItemInfo iteminfo = new UIItemInfo(item.getId());
							iteminfo.setVisible(true);
							StartersTable.clearSelection();
							break;
						}
					}
				}
			}
		});

		JScrollPane Mains_scrollPane = new JScrollPane();
		Mains_scrollPane.setBounds(36, 523, 374, 116);
		contentPane.add(Mains_scrollPane);

		mainstable = new JTable(menuData, menuCols);
		mainstable.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		mainstable.setForeground(new Color(230, 230, 250));
		mainstable.setModel(mainModel);
		mainstable.setAutoCreateRowSorter(true);
		Mains_scrollPane.setViewportView(mainstable);

		Mains_scrollPane.setOpaque(false);
		Mains_scrollPane.getViewport().setOpaque(false);
		mainstable.setOpaque(false);
		((DefaultTableCellRenderer) mainstable.getDefaultRenderer(Object.class)).setOpaque(false);
		mainstable.setShowGrid(false);
		
		/**
		 * @author James
		 * When an item on the mains menu is double clicked, information
		 * for that particular item comes up in a pop up UI. 
		 */
		mainstable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && !e.isConsumed()) {
					e.consume();
					String chosen = (String) mainModel.getValueAt(mainstable.getSelectedRow(), 0);

					for (MenuItem item : dbmenu) {
						if (chosen.equals(item.getName())) {
							UIItemInfo iteminfo = new UIItemInfo(item.getId());
							iteminfo.setVisible(true);
							mainstable.clearSelection();
							break;
						}
					}
				}
			}
		});
		JScrollPane Drinks_scrollPane = new JScrollPane();
		Drinks_scrollPane.setBounds(446, 523, 374, 116);
		contentPane.add(Drinks_scrollPane);

		drinkstable = new JTable(menuData, menuCols);
		drinkstable.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		drinkstable.setForeground(new Color(230, 230, 250));
		drinkstable.setModel(drinkModel);
		drinkstable.setAutoCreateRowSorter(true);
		Drinks_scrollPane.setViewportView(drinkstable);

		Drinks_scrollPane.setOpaque(false);
		Drinks_scrollPane.getViewport().setOpaque(false);
		drinkstable.setOpaque(false);
		((DefaultTableCellRenderer) drinkstable.getDefaultRenderer(Object.class)).setOpaque(false);
		drinkstable.setShowGrid(false);

		/**
		 * @author James
		 * When an item on the drinks menu is double clicked, information
		 * for that particular item comes up in a pop up UI. 
		 */
		drinkstable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && !e.isConsumed()) {
					e.consume();
					String chosen = (String) drinkModel.getValueAt(drinkstable.getSelectedRow(), 0);

					for (MenuItem item : dbmenu) {
						if (chosen.equals(item.getName())) {
							UIItemInfo iteminfo = new UIItemInfo(item.getId());
							iteminfo.setVisible(true);
							drinkstable.clearSelection();
							break;
						}
					}
				}
			}
		});

		JScrollPane Desserts_scrollPane = new JScrollPane();
		Desserts_scrollPane.setBounds(446, 323, 374, 116);
		contentPane.add(Desserts_scrollPane);

		dessertstable = new JTable(menuData, menuCols);
		dessertstable.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		dessertstable.setForeground(new Color(230, 230, 250));
		dessertstable.setModel(dessertModel);
		dessertstable.setAutoCreateRowSorter(true);
		Desserts_scrollPane.setViewportView(dessertstable);

		Desserts_scrollPane.setOpaque(false);
		Desserts_scrollPane.getViewport().setOpaque(false);
		dessertstable.setOpaque(false);
		((DefaultTableCellRenderer) dessertstable.getDefaultRenderer(Object.class)).setOpaque(false);
		dessertstable.setShowGrid(false);

		/**
		 * @author James
		 * When an item on the dessert menu is double clicked, information
		 * for that particular item comes up in a pop up UI. 
		 */
		dessertstable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && !e.isConsumed()) {
					e.consume();
					String chosen = (String) dessertModel.getValueAt(dessertstable.getSelectedRow(), 0);

					for (MenuItem item : dbmenu) {
						if (chosen.equals(item.getName())) {
							UIItemInfo iteminfo = new UIItemInfo(item.getId());
							iteminfo.setVisible(true);
							dessertstable.clearSelection();
							break;
						}
					}

				}
			}
		});

		JLabel DrinksLabel = new JLabel("");
		DrinksLabel.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/Drinks.png")));
		DrinksLabel.setBounds(449, 497, 97, 29);
		contentPane.add(DrinksLabel);

		JLabel DessertsLabel = new JLabel("");
		DessertsLabel.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/Desserts.png")));
		DessertsLabel.setBounds(446, 289, 108, 33);
		contentPane.add(DessertsLabel);

		JLabel MainsLabel = new JLabel("");
		MainsLabel.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/Main Courses.png")));
		MainsLabel.setBounds(38, 494, 135, 32);
		contentPane.add(MainsLabel);

		JLabel StartersLabel = new JLabel("");
		StartersLabel.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/Starters.png")));
		StartersLabel.setBounds(36, 289, 91, 33);
		contentPane.add(StartersLabel);

		JLabel logo = new JLabel("");
		logo.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/smalllogo.png")));
		logo.setBounds(36, 25, 212, 211);
		contentPane.add(logo);
		btnLoginregister.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/login.png")));
		btnLoginregister.setBorder(null);
		
		/**
		 * @author James
		 * Goes to the login screen if user is not logged in. 
		 */
		btnLoginregister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				UIUserLogin loginbox = new UIUserLogin();
				loginbox.setVisible(true);
			}
		});
		btnLoginregister.setForeground(SystemColor.inactiveCaptionText);
		btnLoginregister.setBackground(new Color(245, 245, 220));
		btnLoginregister.setFont(new Font("Euphemia UCAS", Font.PLAIN, 13));
		btnLoginregister.setBounds(1254, 24, 170, 42);
		contentPane.add(btnLoginregister);

		JLabel yourorderslbl = new JLabel("");
		yourorderslbl.setBounds(1074, 291, 153, 31);
		yourorderslbl.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/Your Orders.png")));
		yourorderslbl.setForeground(new Color(255, 69, 0));
		yourorderslbl.setFont(new Font("Euphemia UCAS", Font.PLAIN, 13));
		contentPane.add(yourorderslbl);

		Choosebtn.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/btnChoose.png")));
		Choosebtn.setForeground(new Color(255, 69, 0));
		
		/**
		 * @author James
		 * User is able to select an item from any menu
		 * and select "choose" to add it to the orders table. 
		 */
		Choosebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (drinkstable.getSelectedRow() != -1) {
					String chosen = (String) drinkModel.getValueAt(drinkstable.getSelectedRow(), 0);

					for (MenuItem item : dbmenu) {
						if (chosen.equals(item.getName())) {
							long id = item.getId();
							selection = newmenu.getMenuItem(id);
							if (selection.getPrice() == 0) {
								JOptionPane.showMessageDialog(null, "No Order Selected!");
							} else {
								orderModel.addRow(new Object[] { selection.getName(), selection.getPrice(), });
							}
							drinkstable.clearSelection();
							break;
						}
					}
				}

				if (StartersTable.getSelectedRow() != -1) {
					String chosen = (String) starterModel.getValueAt(StartersTable.getSelectedRow(), 0);

					for (MenuItem item : dbmenu) {
						if (chosen.equals(item.getName())) {
							long id = item.getId();
							selection = newmenu.getMenuItem(id);
							if (selection.getPrice() == 0) {
								JOptionPane.showMessageDialog(null, "No Order Selected!");
							} else {
								orderModel.addRow(new Object[] { selection.getName(), selection.getPrice(), });
							}
							StartersTable.clearSelection();
							break;
						}
					}
				}

				if (dessertstable.getSelectedRow() != -1) {
					String chosen = (String) dessertModel.getValueAt(dessertstable.getSelectedRow(), 0);

					for (MenuItem item : dbmenu) {
						if (chosen.equals(item.getName())) {
							long id = item.getId();
							selection = newmenu.getMenuItem(id);
							if (selection.getPrice() == 0) {
								JOptionPane.showMessageDialog(null, "No Order Selected!");
							} else {
								orderModel.addRow(new Object[] { selection.getName(), selection.getPrice(), });
							}
							dessertstable.clearSelection();
							break;
						}
					}
				}

				if (mainstable.getSelectedRow() != -1) {
					String chosen = (String) mainModel.getValueAt(mainstable.getSelectedRow(), 0);

					for (MenuItem item : dbmenu) {
						if (chosen.equals(item.getName())) {
							long id = item.getId();
							selection = newmenu.getMenuItem(id);
							if (selection.getPrice() == 0) {
								JOptionPane.showMessageDialog(null, "No Order Selected!");
							} else {
								orderModel.addRow(new Object[] { selection.getName(), selection.getPrice(), });
							}
							mainstable.clearSelection();
							break;
						}
					}
				}

			}
		});

		Removebtn.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/btnMenuRemove.png")));
		Removebtn.setForeground(new Color(255, 69, 0));
		Removebtn.setBorder(null);
		Removebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = orderstable.getSelectedRow();
				if (index != -1) {
					orderModel.removeRow(index);
				}

			}
		});

		Choosebtn.setBounds(832, 390, 232, 69);
		contentPane.add(Choosebtn);
		Choosebtn.setBorder(null);
		Removebtn.setBounds(835, 491, 217, 86);
		contentPane.add(Removebtn);
		btnLogout.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/logout.png")));
		btnLogout.setBorder(null);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UIUserLogin frame = new UIUserLogin();
				frame.setVisible(true);
				dispose();
				UIUserLogin.loggedInId = 0;
			}
		});
		btnLogout.setForeground(Color.GRAY);
		btnLogout.setFont(new Font("Euphemia UCAS", Font.PLAIN, 13));
		btnLogout.setBounds(1096, 25, 135, 41);
		contentPane.add(btnLogout);

		JLabel startertablebg = new JLabel("]");
		startertablebg.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/tablebg.png")));
		startertablebg.setBounds(36, 329, 374, 110);
		contentPane.add(startertablebg);

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/tablebg.png")));
		label_1.setBounds(446, 323, 374, 116);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/tablebg.png")));
		label_2.setBounds(36, 523, 374, 116);
		contentPane.add(label_2);

		JLabel label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/tablebg.png")));
		label_3.setBounds(446, 523, 375, 116);
		contentPane.add(label_3);

		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/ordertablebg.png")));
		label_4.setBounds(1078, 329, 308, 310);
		contentPane.add(label_4);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/largeredbg.png")));
		label.setBounds(-16, 265, 1473, 562);
		contentPane.add(label);

		JLabel Background = new JLabel("");
		Background.setIcon(new ImageIcon(UIGuestMenu.class.getResource("/client/app/background.png")));
		Background.setBounds(0, -14, 1457, 841);
		contentPane.add(Background);

	}

	/**
	 * @author Martin gets the row count for each table, uses this to clear each
	 *         table of each row so that it is ready for new rows to be added
	 *         to, once a button is clicked.
	 */
	public void resetTables() {
		int starterRow = starterModel.getRowCount(); // gets row count for
														// starter
														// table
		int mainRow = mainModel.getRowCount(); // gets row count for main table
		int dessertRow = dessertModel.getRowCount(); // gets row count for
														// dessert
														// table
		int drinkRow = drinkModel.getRowCount(); // gets row count for drink
													// table

		for (int i = starterRow - 1; i >= 0; i--) {
			starterModel.removeRow(i);
		}

		for (int i = mainRow - 1; i >= 0; i--) {
			mainModel.removeRow(i);
		}

		for (int i = dessertRow - 1; i >= 0; i--) {
			dessertModel.removeRow(i);
		}

		for (int i = drinkRow - 1; i >= 0; i--) {
			drinkModel.removeRow(i);
		}
	}
}
