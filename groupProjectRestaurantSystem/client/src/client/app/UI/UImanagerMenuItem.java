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

import client.app.logic.Menu;
import client.app.logic.MenuItem;
import client.app.swing.objects.NoEditDefaultTable;

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

public class UImanagerMenuItem extends JFrame {

;
	private JPanel contentPane;

	private JPasswordField passwordField;
	private final JTable table = new JTable();
	private final JLabel lblPicture = new JLabel("Picture:");
	private final JTextField txtName = new JTextField();
	private final JLabel lblName = new JLabel("Name:");
	private final JTextField txtPrice = new JTextField();
	private final JLabel lblPrice = new JLabel("Price:");
	private final JComboBox comboBox = new JComboBox();
	private final JLabel lblNewLabel = new JLabel("On Menu:");
	private final JTextField txtIngredients = new JTextField();
	private final JLabel lblIngredients = new JLabel("Ingredients:");
	private final JButton btnCancel = new JButton("Cancel");
	private final JButton btnUpdate = new JButton("Update");
	private final JLabel lblId = new JLabel("ID:");
	private final JLabel label = new JLabel("?");
	private MenuItem selection = new MenuItem();
	private Menu menu = new Menu();
	private final JLabel lblDiscount = new JLabel("Discount:");
	private final JTextField txtDiscount = new JTextField();
	private final JTextField txtUrl = new JTextField();
	
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UImanagerMenuItem frame = new UImanagerMenuItem();
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
	public UImanagerMenuItem() {
	  txtDiscount.setText("Discount");
	  txtDiscount.setBounds(340, 228, 130, 26);
	  txtDiscount.setColumns(10);
	  txtIngredients.setToolTipText("ingredient");
	  txtIngredients.setText("ingredients");
	  txtIngredients.setBounds(344, 137, 130, 26);
	  txtIngredients.setColumns(10);
	  txtPrice.setToolTipText("price");
	  txtPrice.setText("price");
	  txtPrice.setBounds(340, 51, 130, 26);
	  txtPrice.setColumns(10);
	  txtName.setText("name");
	  txtName.setToolTipText("name");
	  txtName.setBounds(198, 51, 130, 26);
	  txtName.setColumns(10);
        
 
		
		
	  initGUI();
	}
	private void initGUI() {
	  setTitle("Update Item");
	  setResizable(false);
	  setForeground(Color.WHITE);
	  setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	  setBounds(100, 100, 480, 360);
	  contentPane = new JPanel();
	  contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	  setContentPane(contentPane);
	  contentPane.setLayout(null);

	  lblPicture.setIcon(null);
	  lblPicture.setBounds(6, 30, 61, 26);
	  
	  contentPane.add(lblPicture);
	  txtUrl.setToolTipText("URL");
	  //txtUrl.setText("URL");
	  txtUrl.setColumns(10);
	  txtUrl.setBounds(6, 51, 180, 26);
	  
	  contentPane.add(txtUrl);
	  
	  contentPane.add(txtName);
	  lblName.setBounds(201, 35, 61, 16);
	  
	  contentPane.add(lblName);
	  
	  contentPane.add(txtPrice);
	  lblPrice.setBounds(344, 34, 61, 16);
	  
	  contentPane.add(lblPrice);
	  comboBox.setModel(new DefaultComboBoxModel(new String[] {"true", "false"}));
	  comboBox.setSelectedIndex(1);
	  comboBox.setMaximumRowCount(2);
	  comboBox.setBounds(198, 136, 130, 27);
	  
	  contentPane.add(comboBox);
	  lblNewLabel.setBounds(201, 121, 61, 16);
	  
	  contentPane.add(lblNewLabel);
	  
	  contentPane.add(txtIngredients);
	  lblIngredients.setBounds(347, 122, 75, 16);
	  
	  contentPane.add(lblIngredients);
	  btnCancel.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
	      dispose();
	    }
	  });
	  btnCancel.setBounds(198, 303, 117, 29);
	  
	  contentPane.add(btnCancel);
	  btnUpdate.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
	      //JOptionPane.showMessageDialog(null, "nice one!");
	      selection.setName(txtName.getText().trim());
	      selection.setPrice(Double.parseDouble(txtPrice.getText().trim()));
	      selection.setOnMenu(Boolean.parseBoolean((String)comboBox.getSelectedItem()));
	      selection.setIngredientsList(txtIngredients.getText().trim());
	      selection.setSpecialOffer(Double.parseDouble(txtDiscount.getText().trim()));
	      selection.setPictureUrl(txtUrl.getText().trim());
	      
	      System.out.println(selection.getPictureUrl() + "1");
	      //System.out.println(selection.getXML());
	      StringBuffer responseCode = selection.updateMenuItem();
	      MenuItem returned = selection.convertToObject(responseCode);
	      
	      System.out.println(returned.getPictureUrl() + "2");

	      
	      if(returned.getId() != 0) {
	        JOptionPane.showMessageDialog(null, "updated.");
	      }else {
	        JOptionPane.showMessageDialog(null, "opps! there was an error: Check profit Margin of MenuItem");
	      }
	      dispose();
	    }
	  });
	  btnUpdate.setBounds(340, 303, 117, 29);
	  
	  contentPane.add(btnUpdate);
	  lblId.setBounds(202, 208, 18, 16);
	  
	  contentPane.add(lblId);
	  label.setBounds(232, 208, 61, 16);
	  
	  contentPane.add(label);
	  lblDiscount.setBounds(340, 208, 61, 16);
	  
	  contentPane.add(lblDiscount);
	  
	  contentPane.add(txtDiscount);
	}
	
	public UImanagerMenuItem(long id) {
	   txtIngredients.setToolTipText("ingredient");
	      txtIngredients.setText("ingredients");
	      txtIngredients.setBounds(344, 137, 130, 26);
	      txtIngredients.setColumns(10);
	      txtDiscount.setText("Discount");
	      txtDiscount.setBounds(340, 228, 130, 26);
	      txtDiscount.setColumns(10);
	      txtPrice.setToolTipText("price");
	      txtPrice.setText("price");
	      txtPrice.setBounds(340, 51, 130, 26);
	      txtPrice.setColumns(10);
	      txtName.setText("name");
	      txtName.setToolTipText("name");
	      txtName.setBounds(198, 51, 130, 26);
	      txtName.setColumns(10);
	      label.setText("" + id);
	      selection = menu.getMenuItem(id);
	      
	      txtName.setText(selection.getName().trim());
	      txtPrice.setText("" + selection.getPrice());
	      txtIngredients.setText(selection.getIngredientsList().trim());
	      txtDiscount.setText("" + selection.getSpecialOffer());
	      
	      txtUrl.setText(selection.getPictureUrl());
	        
	      initGUI();
	      if(selection.isOnMenu()) {
	        comboBox.setSelectedIndex(0);
	      }else {
	        comboBox.setSelectedIndex(1);
	      }
	      
	}
}
