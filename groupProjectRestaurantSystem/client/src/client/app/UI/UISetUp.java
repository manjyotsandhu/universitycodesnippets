package client.app.UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class UISetUp extends JFrame {

	private JPanel contentPane;
	private static int tableChosen;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UISetUp frame = new UISetUp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
    public static int getTableChosen(){
    	return tableChosen;
    }
	
	/**
	 * Create the frame.
	 */
	public UISetUp() {
		//tableclicked= new Boolean[7];
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton t1 = new JButton("");
		t1.setIcon(new ImageIcon(UISetUp.class.getResource("/client/app/Table1.png")));
		t1.setBorder(null);
		t1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				tableChosen=1;
				UIUserLogin login = new UIUserLogin();
				login.setVisible(true);
				dispose();
			}
		});
		
		t1.setBounds(52, 111, 100, 36);
		contentPane.add(t1);
		
		JLabel lblNewLabel = new JLabel("Place the tablet on the table and Confirm table");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Euphemia UCAS", Font.PLAIN, 16));
		lblNewLabel.setBounds(38, 52, 424, 47);
		contentPane.add(lblNewLabel);
		
		JButton t2 = new JButton("");
		t2.setIcon(new ImageIcon(UISetUp.class.getResource("/client/app/Table2.png")));
		t2.setBorder(null);
		t2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableChosen=2;
				UIUserLogin login = new UIUserLogin();
				login.setVisible(true);
				dispose();
			}
		});
		t2.setBounds(174, 111, 100, 36);
		contentPane.add(t2);
		
		JButton t3 = new JButton("");
		t3.setIcon(new ImageIcon(UISetUp.class.getResource("/client/app/Table3.png")));
		t3.setBorder(null);
		t3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableChosen=3;
				UIUserLogin login = new UIUserLogin();
				login.setVisible(true);
				dispose();
			}
		});
		t3.setBounds(295, 111, 100, 36);
		contentPane.add(t3);
		
		JButton t4 = new JButton("");
		t4.setIcon(new ImageIcon(UISetUp.class.getResource("/client/app/Table4.png")));
		t4.setBorder(null);
		t4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableChosen=4;
				UIUserLogin login = new UIUserLogin();
				login.setVisible(true);
				dispose();
			}
		});
		t4.setBounds(52, 185, 100, 36);
		contentPane.add(t4);
		
		JButton t5 = new JButton("");
		t5.setIcon(new ImageIcon(UISetUp.class.getResource("/client/app/Table5.png")));
		t5.setBorder(null);
		t5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableChosen=5;
				UIUserLogin login = new UIUserLogin();
				login.setVisible(true);
				dispose();
			}
		});
		t5.setBounds(174, 185, 100, 36);
		contentPane.add(t5);
		
		JButton t6 = new JButton("");
		t6.setIcon(new ImageIcon(UISetUp.class.getResource("/client/app/Table6.png")));
		t6.setBorder(null);
		t6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableChosen=6;
				UIUserLogin login = new UIUserLogin();
				login.setVisible(true);
				dispose();}
		});
		t6.setBounds(295, 185, 100, 36);
		contentPane.add(t6);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(UISetUp.class.getResource("/client/app/largeredbg.png")));
		label.setBounds(-13, 0, 475, 290);
		contentPane.add(label);
	}
}

