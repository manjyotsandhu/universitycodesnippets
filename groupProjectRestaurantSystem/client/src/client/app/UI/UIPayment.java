package client.app.UI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.app.logic.Order;
import client.app.logic.Payment;
import client.server.connection.ServerClient;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;
import java.awt.Color;
import javax.swing.ImageIcon;

/**
 * @author James Johnson
 */
public class UIPayment extends JFrame implements KeyListener, MouseListener {

	private JPanel contentPane;
	private JTextField nameTextField;
	private JTextField cardNumTextField;
	private JTextField expiryTextField;
	private JTextField CVCTextField;
	private ServerClient sercli = new ServerClient();
	private static Order iniorder;
	private static double iniamount;
	private double amount = 0;
	private Order order;
	private JButton btnPayment;
	private Payment newPayment;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIPayment frame = new UIPayment(iniamount, iniorder);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Takes an order and updates the total amount paid in the webserver
	 * @author Youcefa
	 * @param o An order that needs paid
	 */
	public void postPayment(Order o) {
		order = o;
		o.setTotalPaid(amount);
		o.setTotalCost(amount);
		sercli.httpPut("http://shouganai.net:8080/restaurant/rest/orders/" + o.getId(), order.getXML());
	}

	public UIPayment(double amount, Order order) {
		this.amount = amount;
		this.order = order;
		setForeground(Color.WHITE);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 487, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPayment = new JLabel("Payment");
		JLabel lblName = new JLabel("Name on card");
		JLabel lblCardNum = new JLabel("Card Number");
		JLabel lblExpiry = new JLabel("Expiry (MMYY)");
		JLabel lblAmount = new JLabel("Amount");
		JLabel lblOrderNum = new JLabel("Order number");
		JLabel lblCVC = new JLabel("CVC");
		JLabel lblShowAmount;
		JLabel lblShowOrderNum;

		btnPayment = new JButton("");
		btnPayment.setIcon(new ImageIcon(UIPayment.class.getResource("/client/app/processpayment.png")));
		btnPayment.setBorder(null);
		btnPayment.setFont(new Font("Euphemia UCAS", Font.PLAIN, 20));
		btnPayment.setBounds(88, 330, 335, 70);
		contentPane.add(btnPayment);
		btnPayment.setVisible(false);

		/**
		 * @author James
		 * Makes payment for this order
		 */
		btnPayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				postPayment(order);
				JOptionPane.showMessageDialog(contentPane, "Payment Confirmation");
				UIGuestMenu newmenu = new UIGuestMenu();
				newmenu.setVisible(true);
				dispose();

			}
		});

		lblPayment.setForeground(new Color(255, 69, 0));
		lblPayment.setFont(new Font("Euphemia UCAS", Font.PLAIN, 24));
		lblPayment.setBounds(202, 17, 101, 38);
		contentPane.add(lblPayment);

		lblName.setForeground(new Color(255, 69, 0));
		lblName.setFont(new Font("Euphemia UCAS", Font.PLAIN, 14));
		lblName.setBounds(30, 80, 190, 38);
		contentPane.add(lblName);

		lblCardNum.setForeground(new Color(255, 69, 0));
		lblCardNum.setFont(new Font("Euphemia UCAS", Font.PLAIN, 14));
		lblCardNum.setBounds(30, 120, 150, 38);
		contentPane.add(lblCardNum);

		lblExpiry.setForeground(new Color(255, 69, 0));
		lblExpiry.setFont(new Font("Euphemia UCAS", Font.PLAIN, 14));
		lblExpiry.setBounds(30, 160, 150, 38);
		contentPane.add(lblExpiry);

		lblCVC.setForeground(new Color(255, 69, 0));
		lblCVC.setFont(new Font("Euphemia UCAS", Font.PLAIN, 14));
		lblCVC.setBounds(30, 200, 150, 38);
		contentPane.add(lblCVC);

		lblAmount.setForeground(new Color(255, 69, 0));
		lblAmount.setFont(new Font("Euphemia UCAS", Font.PLAIN, 14));
		lblAmount.setBounds(30, 240, 150, 38);
		contentPane.add(lblAmount);

		lblOrderNum.setForeground(new Color(255, 69, 0));
		lblOrderNum.setFont(new Font("Euphemia UCAS", Font.PLAIN, 14));
		lblOrderNum.setBounds(30, 280, 150, 38);
		contentPane.add(lblOrderNum);

		nameTextField = new JTextField();
		nameTextField.setBackground(new Color(0, 0, 0));
		nameTextField.setForeground(new Color(230, 230, 250));
		nameTextField.setFont(new Font("Euphemia UCAS", Font.PLAIN, 13));
		nameTextField.setBounds(170, 88, 150, 22);
		nameTextField.setBorder(null);
		contentPane.add(nameTextField);
		nameTextField.setColumns(10);
		nameTextField.addKeyListener(this);

		cardNumTextField = new JTextField();
		cardNumTextField.setBackground(new Color(0, 0, 0));
		cardNumTextField.setForeground(new Color(230, 230, 250));
		cardNumTextField.setFont(new Font("Euphemia UCAS", Font.PLAIN, 13));
		cardNumTextField.setBounds(170, 128, 150, 22);
		cardNumTextField.setBorder(null);
		contentPane.add(cardNumTextField);
		cardNumTextField.setColumns(10);
		cardNumTextField.addKeyListener(this);

		expiryTextField = new JTextField();
		expiryTextField.setBackground(new Color(0, 0, 0));
		expiryTextField.setForeground(new Color(230, 230, 250));
		expiryTextField.setFont(new Font("Euphemia UCAS", Font.PLAIN, 13));
		expiryTextField.setBounds(170, 168, 50, 22);
		contentPane.add(expiryTextField);
		expiryTextField.setColumns(10);
		expiryTextField.setBorder(null);
		expiryTextField.addKeyListener(this);

		CVCTextField = new JTextField();
		CVCTextField.setBackground(new Color(0, 0, 0));
		CVCTextField.setBorder(null);
		CVCTextField.setForeground(new Color(230, 230, 250));
		CVCTextField.setFont(new Font("Euphemia UCAS", Font.PLAIN, 13));
		CVCTextField.setBounds(170, 208, 40, 22);
		contentPane.add(CVCTextField);
		CVCTextField.setColumns(10);
		CVCTextField.addKeyListener(this);

		lblShowAmount = new JLabel("£" + Double.toString(amount));
		lblShowAmount.setForeground(new Color(233, 150, 122));
		lblShowAmount.setFont(new Font("Euphemia UCAS", Font.PLAIN, 20));
		lblShowAmount.setBounds(175, 248, 150, 22);
		contentPane.add(lblShowAmount);

		lblShowOrderNum = new JLabel(Integer.toString((int) order.getId()));
		lblShowOrderNum.setForeground(new Color(233, 150, 122));
		lblShowOrderNum.setFont(new Font("Euphemia UCAS", Font.PLAIN, 20));
		lblShowOrderNum.setBounds(175, 288, 150, 22);
		contentPane.add(lblShowOrderNum);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(UIPayment.class.getResource("/client/app/background.png")));
		label.setBounds(-53, -296, 738, 766);
		contentPane.add(label);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * @author Mark
	 * Checks if the user has added sufficient input 
	 * before the pay button is made visible. 
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (CVCTextField.getText().length() == 3 && expiryTextField.getText().length() == 4
				&& nameTextField.getText().length() > 0 && cardNumTextField.getText().length() == 16) {

			btnPayment.setVisible(true);
			newPayment = new Payment(this.amount, (int) this.order.getId(), cardNumTextField.getText(),
					Integer.parseInt(CVCTextField.getText()), nameTextField.getText(),
					Integer.parseInt(expiryTextField.getText()));
		} else {

			btnPayment.setVisible(false);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

  @Override
  public void mouseExited(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }
}
