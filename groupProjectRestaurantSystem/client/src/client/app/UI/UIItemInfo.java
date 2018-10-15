package client.app.UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.app.logic.Ingredient;
import client.app.logic.Menu;
import client.app.logic.MenuItem;
import javafx.scene.image.Image;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.awt.Color;

/**
 * @author James Johnson
 */
public class UIItemInfo extends JFrame {

	private JPanel contentPane;
	private static int num =0;
	private MenuItem selection = new MenuItem();
	private Menu menu = new Menu();
	private Ingredient ingrHolder = new Ingredient();
	private List<Ingredient> ingrlist = new ArrayList<>();
  BufferedImage image = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIItemInfo frame = new UIItemInfo(num);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * @author Amrit
	 */
	private BufferedImage ScaledImage(java.awt.Image img, int w, int h) {
		BufferedImage resizedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = resizedImage.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(img, 0, 0, w, h, null);
		g2.dispose();
		return resizedImage;
	}

	public UIItemInfo(long currItem) {
		selection = menu.getMenuItem(currItem);
		initGUI();
	}

	private void initGUI() {
		try {
			URL url = new URL("http://hprcc.unl.edu/images/No-Image-Available.jpg");
			if (selection.getPictureUrl() != null) {
				url = new URL(selection.getPictureUrl());
			}
			image = ImageIO.read(url);
			image = ScaledImage(image, 120, 110);
		} catch (IOException e) {
		}

		setBackground(new Color(128, 0, 0));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 379, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel Name = new JLabel("<Title>");
		Name.setForeground(new Color(222, 184, 135));
		Name.setFont(new Font("Euphemia UCAS", Font.PLAIN, 18));
		Name.setText("<Name>");
		Name.setBounds(110, 174, 344, 28);
		contentPane.add(Name);

		Name.setText(selection.getName());

		JLabel ingredientsTextbox = new JLabel("<Ingredients>");
		ingredientsTextbox.setForeground(new Color(222, 184, 135));
		ingredientsTextbox.setFont(new Font("Euphemia UCAS", Font.PLAIN, 15));
		ingredientsTextbox.setVerticalAlignment(SwingConstants.TOP);
		ingredientsTextbox.setBounds(23, 260, 328, 28);
		contentPane.add(ingredientsTextbox);

		JLabel lblAllergies = new JLabel("Allergy Concerns");
		lblAllergies.setForeground(new Color(222, 184, 135));
		lblAllergies.setFont(new Font("Euphemia UCAS", Font.PLAIN, 19));
		lblAllergies.setBounds(23, 290, 163, 28);
		contentPane.add(lblAllergies);

		JLabel lblallergylist = new JLabel("<allergies>");
		lblallergylist.setVerticalAlignment(SwingConstants.TOP);
		lblallergylist.setForeground(new Color(222, 184, 135));
		lblallergylist.setFont(new Font("Euphemia UCAS", Font.PLAIN, 15));
		lblallergylist.setBounds(23, 323, 272, 28);
		contentPane.add(lblallergylist);

		JPanel Photo = new JPanel();
		Photo.setBackground(new Color(165, 42, 42));
		Photo.setBounds(28, 19, 321, 143);
		contentPane.add(Photo);
		Photo.setLayout(null);

		JLabel lblimage = new JLabel(new ImageIcon(image));
		lblimage.setBounds(101, 16, 120, 110);
		Photo.add(lblimage);

		JLabel lblIngredients = new JLabel("Ingredients");
		lblIngredients.setForeground(new Color(222, 184, 135));
		lblIngredients.setFont(new Font("Euphemia UCAS", Font.PLAIN, 19));
		lblIngredients.setBounds(23, 219, 113, 28);
		contentPane.add(lblIngredients);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setForeground(new Color(222, 184, 135));
		lblPrice.setFont(new Font("Euphemia UCAS", Font.PLAIN, 25));
		lblPrice.setBounds(300, 287, 62, 28);
		contentPane.add(lblPrice);

		JLabel priceTextbox = new JLabel("<price>");
		priceTextbox.setForeground(new Color(222, 184, 135));
		priceTextbox.setFont(new Font("Futura", Font.PLAIN, 13));
		priceTextbox.setBounds(333, 327, 52, 16);
		contentPane.add(priceTextbox);

		JLabel lblcount = new JLabel("<#>");
		lblcount.setForeground(new Color(210, 180, 140));
		lblcount.setFont(new Font("Euphemia UCAS", Font.PLAIN, 17));
		lblcount.setBounds(278, 220, 37, 28);
		contentPane.add(lblcount);

		JLabel label_1 = new JLabel("£");
		label_1.setForeground(new Color(210, 180, 140));
		label_1.setFont(new Font("Euphemia UCAS", Font.PLAIN, 17));
		label_1.setBounds(309, 319, 19, 28);
		contentPane.add(label_1);

		JLabel lblCalories = new JLabel("kcal");
		lblCalories.setForeground(new Color(222, 184, 135));
		lblCalories.setFont(new Font("Euphemia UCAS", Font.PLAIN, 19));
		lblCalories.setBounds(319, 220, 43, 28);
		contentPane.add(lblCalories);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(UIItemInfo.class.getResource("/client/app/redbackground.png")));
		label.setBounds(0, 0, 385, 365);
		contentPane.add(label);
		StringBuffer ingrString = new StringBuffer();
		ingrlist = selection.ingrStringtoList();

		for (Ingredient ing : ingrlist) {
			ingrString.append(ing.getName() + ", ");
		}

		ingredientsTextbox.setText(ingrString.toString().substring(0, ingrString.length() - 2));
		String price = Double.toString(selection.getPrice());
		priceTextbox.setText(price);
		lblcount.setText("" + selection.getCalories());
		lblallergylist.setText(selection.getAllergy());
	}
}
