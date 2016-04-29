package hr.fer.zemris.java.hw12.jvdraw.objects;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SpringSample {
  public static void main(String args[]) {
    JFrame frame = new JFrame("SpringLayout");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
    panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    JLabel center = new JLabel("Center:", SwingConstants.CENTER);
    JTextField centerText = new JTextField();

    JLabel radius = new JLabel("Radius:", SwingConstants.CENTER);
    JTextField radiusText = new JTextField();
    radiusText.setPreferredSize(new Dimension(70, radius.getHeight()));

    JLabel color = new JLabel("Color:", SwingConstants.CENTER);
    JTextField colorText = new JTextField();

    panel.add(center);
    panel.add(centerText);

    panel.add(radius);
    panel.add(radiusText);

    panel.add(color);
    panel.add(colorText);

//    SpringUtilities.makeGrid(panel, 3, 2, 0, 5, 5, 5);

    frame.add(panel);
    frame.pack();
    frame.setResizable(false);
    frame.setVisible(true);
  }
}