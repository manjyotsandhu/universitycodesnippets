package client.app.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import client.app.logic.Menu;
import client.app.logic.MenuItem;
import client.app.swing.objects.NoEditDefaultTable;

public class GuestReadOnlyUI extends JFrame {

  private JPanel contentPane;
  private Menu menu = new Menu();
  private MenuItem selection = new MenuItem();
  GridLayout experimentLayout = new GridLayout(0, 2);
  BufferedImage image = null;
  private static JFrame mainFrame = new JFrame();

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          GuestReadOnlyUI frame = new GuestReadOnlyUI();

          // Dimension screenSize =
          // java.awt.Toolkit.getDefaultToolkit().getScreenSize();
          // frame.setBounds(0, 0, screenSize.width, screenSize.height);

          // Display the window.
          mainFrame.pack();

          mainFrame.setVisible(true);
          // frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the frame.
   */
  public GuestReadOnlyUI() {

    // mainFrame.setSize(1000, 600);
    mainFrame.setLayout(new GridLayout(1, 2));

    mainFrame.setResizable(false);

    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setTitle("Read-Only Menu");
    mainFrame.setBounds(200, 100, 600, 400);
    contentPane = new JPanel();
    contentPane.setBackground(new Color(245, 245, 245));
    setContentPane(contentPane);
    contentPane.setLayout(experimentLayout);
    JButton btnChange = new JButton("Return to Login Screen");
    btnChange.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UIUserLogin frame2 = new UIUserLogin();
        frame2.setVisible(true);
        mainFrame.dispose();

      }
    });

    JPanel panel = new JPanel();
    panel.add(btnChange);

    JPanel a = getItems(1);
    JPanel b = getItems(2);
    JPanel c = getItems(3);
    JPanel d = getItems(4);
    JPanel e = getItems(5);
    contentPane.add(a);
    contentPane.add(b);
    contentPane.add(c);
    contentPane.add(d);
    contentPane.add(e);
    contentPane.add(panel);
    contentPane.setSize(600, 400);
    mainFrame.add(contentPane);

  }

  private BufferedImage ScaledImage(java.awt.Image img, int w, int h) {
    BufferedImage resizedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = resizedImage.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(img, 0, 0, w, h, null);
    g2.dispose();
    return resizedImage;
  }

  public JPanel getItems(int num) {
    selection = menu.getMenuItem(num);
    try {
      URL url = new URL("http://hprcc.unl.edu/images/No-Image-Available.jpg");
      if (selection.getPictureUrl() != null) {
        // System.out.println(selection.getPictureUrl());
        url = new URL(selection.getPictureUrl());
      }
      image = ImageIO.read(url);
      // image = (BufferedImage) image.getScaledInstance(30, 30, 0);
      image = ScaledImage(image, 120, 110);
    } catch (IOException e) {
    }
    JPanel panel = new JPanel();
    panel.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(panel);

    JPanel Photo = new JPanel();
    Photo.setBounds(28, 19, 131, 112);
    panel.add(Photo);
    JLabel lblimage = new JLabel(new ImageIcon(image));
    Photo.add(lblimage);

    JLabel Name = new JLabel("<Title>");
    Name.setFont(new Font("Futura", Font.PLAIN, 13));
    Name.setText("<Name>");
    Name.setBounds(209, 19, 176, 16);
    panel.add(Name);

    JLabel lblPrice = new JLabel("Price");
    lblPrice.setFont(new Font("Futura", Font.PLAIN, 13));
    lblPrice.setBounds(324, 228, 61, 16);
    panel.add(lblPrice);

    JLabel priceTextbox = new JLabel("<price>");
    priceTextbox.setFont(new Font("Futura", Font.PLAIN, 13));
    priceTextbox.setBounds(372, 228, 61, 16);
    panel.add(priceTextbox);

    Name.setText(selection.getName());
    String price = Double.toString(selection.getPrice());
    priceTextbox.setText(price);

    return panel;
  }

}
