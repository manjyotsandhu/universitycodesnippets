package client.app.UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.app.logic.Account;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;


@SuppressWarnings("serial")
public class UIUserLogin extends JFrame {

	private JPanel contentPane;
	private JTextField Email;
	private JPasswordField Password;
	private JButton guestbtn;
	private JButton registerbtn;
	private JLabel lblEmail = new JLabel("");
	private JLabel lblPassword = new JLabel("");
	private JButton loginbtn = new JButton("");
	private JLabel logolbl = new JLabel("");
	public static long loggedInId = 0;
	
	public boolean type = true;
	private JLabel background;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIUserLogin frame = new UIUserLogin();
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
	public UIUserLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1400, 900);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		logolbl.setIcon(new ImageIcon(UIUserLogin.class.getResource("/client/app/Logo.png")));
		logolbl.setBounds(542, 49, 293, 261);
		contentPane.add(logolbl);
		
		Email = new JTextField();
		Email.setForeground(new Color(255, 69, 0));
		Email.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		Email.setBackground(Color.BLACK);
		Email.setBounds(444, 348, 500, 28);
		contentPane.add(Email);
		Email.setColumns(10);
		
		Password = new JPasswordField();
		Password.setForeground(new Color(255, 69, 0));
		Password.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		Password.setBackground(Color.BLACK);
		Password.setBounds(444, 440, 500, 28);
		contentPane.add(Password);
		lblEmail.setIcon(new ImageIcon(UIUserLogin.class.getResource("/client/app/emaillbl.png")));
		
		lblEmail.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		lblEmail.setForeground(new Color(255, 165, 0));
		lblEmail.setBounds(444, 307, 63, 28);
		contentPane.add(lblEmail);
		lblPassword.setIcon(new ImageIcon(UIUserLogin.class.getResource("/client/app/passwordlbl.png")));
		
		lblPassword.setForeground(new Color(255, 165, 0));
		lblPassword.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		lblPassword.setBounds(444, 402, 95, 31);
		contentPane.add(lblPassword);
		
		loginbtn.setIcon(new ImageIcon(UIUserLogin.class.getResource("/client/app/loginbtn.png")));
        loginbtn.setBorder(null);
		loginbtn.setBounds(215, 513, 935, 107);
		contentPane.add(loginbtn);
	
		guestbtn = new JButton("");
		guestbtn.setIcon(new ImageIcon(UIUserLogin.class.getResource("/client/app/LoginasGuestBtn.png")));
		guestbtn.setBorder(null);
		guestbtn.setBounds(509, 619, 360, 36);
		contentPane.add(guestbtn);
		
		registerbtn = new JButton("");
		registerbtn.setIcon(new ImageIcon(UIUserLogin.class.getResource("/client/app/registerherebtn.png")));
		registerbtn.setBorder(null);
		registerbtn.setBounds(640, 651, 95, 16);
		contentPane.add(registerbtn);

		background = new JLabel("");
		background.setIcon(new ImageIcon(UIUserLogin.class.getResource("/client/app/background.png")));
		background.setBounds(0, 0, 1462, 849);
		contentPane.add(background);

		/**
		 * @author James
		 * Opens registration UI.
		 */
		registerbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				UIRegister registerbox = new UIRegister();
				registerbox.setVisible(true);
			}
		});
		
		/**
		 * @author James
		 * Opens a menu with no specified account.
		 */
		guestbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			dispose();
			UIGuestMenu guestMenuBox = new UIGuestMenu();
			guestMenuBox.btnLoginregister.setVisible(true);
			guestMenuBox.btnLogout.setVisible(false);
			guestMenuBox.setVisible(true);
			}
		});
		
		/**
		 * @author James
		 * Opens a menu based on log in input by the user.
		 */
		loginbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String uname = Email.getText();
				
					char[] inputlogin = Password.getPassword();
					String pword = new String(inputlogin);
					Account login = new Account();
					login.setEmail(uname);
					login.setPassword(pword);
					login = login.verifyLogin(login.getAuthXML());
					loggedInId = login.getIdFromUsername(uname);

					if (login.getAccountType() == 0) {
						dispose();
						UIGuestMenu guestMenuBox = new UIGuestMenu();
						guestMenuBox.btnLoginregister.setVisible(false);
						guestMenuBox.btnLogout.setVisible(true);
						guestMenuBox.setVisible(true);
						JOptionPane.showMessageDialog(contentPane, "Welcome, " + login.getName());
					}

					// waiter
					else if (login.getAccountType() == 1) {
						dispose();
						WaiterUI waiterView = new WaiterUI();
						waiterView.setVisible(true);
					}

					// manager
					else if (login.getAccountType() == 2) {
						dispose();
						UIforManager managerView = new UIforManager();
						managerView.setVisible(true);
					}

					// kitchen staff
					else if (login.getAccountType() == 3) {
						dispose();
						//UIforKitchenStaff kitchenView = new UIforKitchenStaff();
						newKitchenUI kitchenView = new newKitchenUI();
						kitchenView.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(contentPane, "Invalid Password or Email!");
					}
				}
			

		});
	}
}
