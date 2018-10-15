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

public class UImanagerAddItem extends JFrame {

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
	private final JButton btnUpdate = new JButton("Create");
	private MenuItem selection = new MenuItem();
	private Menu menu = new Menu();
	private final JTextField txtUrl = new JTextField();
	private final JComboBox comboBox_1 = new JComboBox();
	private final JComboBox comboBox_2 = new JComboBox();
	private final JComboBox comboBox_3 = new JComboBox();
	private final JLabel lblIsHalal = new JLabel("Halal:");
	private final JLabel lblVegetarian = new JLabel("Vegetarian:");
	private final JLabel lblKosher = new JLabel("Kosher:");
	
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UImanagerAddItem frame = new UImanagerAddItem();
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
	public UImanagerAddItem() {
	  txtIngredients.setToolTipText("ingredient");
	  txtIngredients.setBounds(344, 137, 130, 26);
	  txtIngredients.setColumns(10);
	  txtPrice.setToolTipText("price");
	  txtPrice.setBounds(340, 51, 130, 26);
	  txtPrice.setColumns(10);
	  txtName.setToolTipText("name");
	  txtName.setBounds(198, 51, 130, 26);
	  txtName.setColumns(10);
        
 
		
		
	  initGUI();
	}
	private void initGUI() {
	  setTitle("Add Item");
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
	  comboBox.setSelectedIndex(0);
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
	      boolean iferror = false;
	      MenuItem returned = new MenuItem();
	      selection.setName(txtName.getText().trim());
	      selection.setPrice(Double.parseDouble(txtPrice.getText().trim()));
	      selection.setOnMenu(Boolean.parseBoolean((String)comboBox.getSelectedItem()));
	      selection.setIngredientsList(txtIngredients.getText().trim());
	      selection.setPictureUrl(txtUrl.getText().trim());
	      selection.setSpecialOffer(1.0);
	      selection.setAvailable(true);
	      selection.setHalal(Boolean.parseBoolean((String)comboBox_1.getSelectedItem()));
	      selection.setVegeDish(Boolean.parseBoolean((String)comboBox_2.getSelectedItem()));
	      selection.setKosher(Boolean.parseBoolean((String)comboBox_3.getSelectedItem()));
	      
        if (selection.getName().equals("") | selection.getPrice() == 0
            | selection.getIngredientsList().equals("") | selection.getPictureUrl().equals("")) {
          JOptionPane.showMessageDialog(null, "ERROR: Please fill in all Values.");
          iferror = true;
        }else {
          returned = menu.addItem(selection);
        }
	      //System.out.println(selection.getXML());
	      

	      
	      if(returned.getId() != 0 && !iferror) {
	        JOptionPane.showMessageDialog(null, "Item added.");
	      }else {
	        if(!iferror) {
	          JOptionPane.showMessageDialog(null, "opps! there was an error with making a new MenuItem!");
	        } 
	      }
	      if(!iferror) {
	        dispose();
	      }
	    }
	  });
	  btnUpdate.setBounds(340, 303, 117, 29);
	  
	  contentPane.add(btnUpdate);
	  comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"true", "false"}));
	  comboBox_1.setSelectedIndex(0);
	  comboBox_1.setMaximumRowCount(2);
	  comboBox_1.setBounds(56, 136, 130, 27);
	  
	  contentPane.add(comboBox_1);
	  comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"true", "false"}));
	  comboBox_2.setSelectedIndex(0);
	  comboBox_2.setMaximumRowCount(2);
	  comboBox_2.setBounds(198, 227, 130, 27);
	  
	  
	  contentPane.add(comboBox_2);
	  comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"true", "false"}));
	  comboBox_3.setSelectedIndex(0);
	  comboBox_3.setMaximumRowCount(2);
	  comboBox_3.setBounds(340, 227, 130, 27);
	  
	  
	  contentPane.add(comboBox_3);
	  lblIsHalal.setBounds(56, 121, 61, 16);
	  
	  contentPane.add(lblIsHalal);
	  lblVegetarian.setBounds(198, 209, 75, 16);
	  
	  contentPane.add(lblVegetarian);
	  lblKosher.setBounds(343, 209, 61, 16);
	  
	  contentPane.add(lblKosher);
	}
}
