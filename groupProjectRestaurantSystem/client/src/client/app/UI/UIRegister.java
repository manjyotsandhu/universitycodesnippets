package client.app.UI;
import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import client.app.logic.Account;
import client.app.logic.Customer;
import client.server.connection.ServerClient;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

/**
 * @author James Johnson
 *
 */

public class UIRegister extends JFrame {

	private JPanel contentPane;
	private JTextField NameTextField;
	private JTextField EmailTextField;
	private ServerClient scli = new ServerClient();
	private JPasswordField PasswordTextField;
	private ButtonGroup accountype = new ButtonGroup();

	JRadioButton radioButton = new JRadioButton("Customer");
	JRadioButton radioButton_1 = new JRadioButton("Kitchen Staff");
	JRadioButton radioButton_2 = new JRadioButton("Waiter");
	JRadioButton radioButton_3 = new JRadioButton("Manager");
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIRegister frame = new UIRegister();
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
	public UIRegister() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1440, 900);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		NameTextField = new JTextField();
		NameTextField.setBackground(new Color(0, 0, 0));
		NameTextField.setForeground(new Color(255, 69, 0));
		NameTextField.setFont(new Font("Euphemia UCAS", Font.PLAIN, 13));
		NameTextField.setBounds(568, 386, 393, 28);
		contentPane.add(NameTextField);
		NameTextField.setColumns(10);
		NameTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
		JLabel NameLabel = new JLabel("Name");
		NameLabel.setForeground(new Color(255, 69, 0));
		NameLabel.setFont(new Font("Euphemia UCAS", Font.PLAIN, 18));
		NameLabel.setBounds(403, 381, 80, 32);
		contentPane.add(NameLabel);

		JLabel EmailLabel = new JLabel("Email");
		EmailLabel.setForeground(new Color(255, 69, 0));
		EmailLabel.setFont(new Font("Euphemia UCAS", Font.PLAIN, 18));
		EmailLabel.setBounds(403, 456, 91, 28);
		contentPane.add(EmailLabel);

		EmailTextField = new JTextField();
		EmailTextField.setBackground(new Color(0, 0, 0));
		EmailTextField.setForeground(new Color(255, 69, 0));
		EmailTextField.setFont(new Font("Euphemia UCAS", Font.PLAIN, 13));
		EmailTextField.setBounds(568, 459, 393, 28);
		contentPane.add(EmailTextField);
		EmailTextField.setColumns(10);
		EmailTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		JLabel PasswordLabel = new JLabel("Password");
		PasswordLabel.setForeground(new Color(255, 69, 0));
		PasswordLabel.setFont(new Font("Euphemia UCAS", Font.PLAIN, 18));
		PasswordLabel.setBounds(403, 524, 91, 33);
		contentPane.add(PasswordLabel);

		JButton btnRegistered = new JButton("");
		btnRegistered.setIcon(new ImageIcon(UIRegister.class.getResource("/client/app/Registerbtn.png")));

		/**
		 * @author James
		 * Registers user based on input. Also contains functionality 
		 * for registering a user through the manager UI, where the
		 * manager can select the account type manually. Otherwise,
		 * account type is set by default to customer. 
		 */
		btnRegistered.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!NameTextField.getText().isEmpty() || !EmailTextField.getText().isEmpty()) {
					if (!EmailTextField.getText().contains("@")) {
						JOptionPane.showMessageDialog(contentPane, "Invalid Password or Email!");
					} else {
					char[] input = PasswordTextField.getPassword();
					String b = new String(input);

					Account newacc = new Account();
					String type = accountype.getSelection().getActionCommand();

					newacc.setName(NameTextField.getText());
					newacc.setEmail(EmailTextField.getText());
					newacc.setPassword(b);

					System.out.println(type);
					if (type.equals("customer")) {
						newacc.setAccountType(0);
					} else if (type.equals("waiter")) {
						newacc.setAccountType(1);
					} else if (type.equals("kitchen")) {
						newacc.setAccountType(3);
					} else if (type.equals("manager")) {
						newacc.setAccountType(2);
					}

					newacc = newacc.registerAnyAccount(newacc.getXML());

					if (newacc.getAccountType() == 0) {
	                    Customer newCust = new Customer();
	                    newCust.setCust_id(newacc.getAccountID());
	                    newCust.setCallingWaiter(0);
	                    newCust.setTableID(1);
	                    System.out.println("Adding to customers " + newCust.getCust_id() + " calling waiter " + newCust.getCallingWaiter() + " of table " + newCust.getTableID());
	                    int responseCode = newCust.makeCustomer();
	                    
	                    if (responseCode == 200) {
	                      System.out.println("Added to customer database");
	                    } else {
	                      System.out.println("Error adding to customer database");
	                    }
					}
					
					if (newacc.getAccountID() != 0) {
						JOptionPane.showMessageDialog(contentPane, "Successfully Registered!");
						dispose();
						UIUserLogin login = new UIUserLogin();
						login.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(contentPane, "Error in Registering!");
					}
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "Error in Registering!");
				}
			}
		});
		
		btnRegistered.setFont(new Font("Euphemia UCAS", Font.PLAIN, 25));
		btnRegistered.setForeground(Color.GRAY);
		btnRegistered.setBounds(482, 603, 460, 107);
		btnRegistered.setBorder(null);
		contentPane.add(btnRegistered);
		
		JButton btnBack = new JButton("");
		btnBack.setIcon(new ImageIcon(UIRegister.class.getResource("/client/app/Backbtn.png")));
		
		/**
		 * @author James
		 * Navigates back to user login UI
		 */
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				UIUserLogin loginbox = new UIUserLogin();
				loginbox.setVisible(true);
			}
		});
		btnBack.setBounds(32, 29, 134, 38);
		btnBack.setBorder(null);
		contentPane.add(btnBack);
		
		PasswordTextField = new JPasswordField();
		PasswordTextField.setForeground(new Color(255, 69, 0));
		PasswordTextField.setBackground(new Color(0, 0, 0));
		PasswordTextField.setBounds(568, 529, 393, 28);
		PasswordTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(PasswordTextField);
		radioButton.setBackground(new Color(128, 0, 0));
		radioButton.setForeground(new Color(255, 69, 0));
		
		radioButton.setSelected(true);
		radioButton.setVisible(false);
		radioButton.setActionCommand("customer");
		radioButton.setBounds(627, 303, 93, 23);
		contentPane.add(radioButton);
		radioButton_1.setBackground(new Color(128, 0, 0));
		radioButton_1.setForeground(new Color(255, 69, 0));
		
		radioButton_1.setVisible(false);
		radioButton_1.setActionCommand("kitchen");
		radioButton_1.setBounds(627, 332, 112, 23);
		contentPane.add(radioButton_1);
		radioButton_2.setBackground(new Color(128, 0, 0));
		radioButton_2.setForeground(new Color(255, 69, 0));
		
		radioButton_2.setVisible(false);
		radioButton_2.setActionCommand("waiter");
		radioButton_2.setBounds(756, 303, 71, 23);
		contentPane.add(radioButton_2);
		radioButton_3.setBackground(new Color(128, 0, 0));
		radioButton_3.setForeground(new Color(255, 69, 0));

		radioButton_3.setVisible(false);
		radioButton_3.setActionCommand("manager");
		radioButton_3.setBounds(755, 332, 93, 23);
		contentPane.add(radioButton_3);

		accountype.add(radioButton_3);
		accountype.add(radioButton_1);
		accountype.add(radioButton_2);
		accountype.add(radioButton);

		JLabel Icon = new JLabel("");
		Icon.setIcon(new ImageIcon(UIRegister.class.getResource("/client/app/smalllogo.png")));
		Icon.setBounds(617, 108, 239, 183);
		contentPane.add(Icon);

		JLabel Background = new JLabel("New label");
		Background.setIcon(new ImageIcon(UIRegister.class.getResource("/client/app/background.png")));
		Background.setBounds(0, 0, 1468, 872);
		contentPane.add(Background);
	}
}
