package hr.fer.zemris.java.hw12.jvdraw.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw12.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw12.jvdraw.util.Utility;

public class Circle extends GeometricalObject {

	protected Point center;
	protected int radius;

	private Color color;

	protected JLabel centerXLabel = new JLabel("Center x:");
	protected JLabel centerYLabel = new JLabel("Center y:");
	protected JLabel radiusLabel = new JLabel("Radius:");

	private JLabel colorLabel = new JLabel("Color:");

	protected JTextField centerXText = new JTextField();
	protected JTextField centerYText = new JTextField();
	protected JTextField radiusText = new JTextField();

	private JColorArea colorArea;

	public Circle(Point center, int radius, Color color) {
		this.center = center;
		this.radius = radius;
		this.color = color;
		this.colorArea = new JColorArea(color);
	}

	@Override
	public void drawObject(Graphics2D g) {
		g.setColor(color);
		g.drawOval(center.x - radius , center.y - radius, 2 * radius, 2 * radius);
	}

	@Override
	public void drawShifted(Graphics2D g, int x, int y) {
		new Circle(new Point(center.x - x, center.y - y), radius, color).drawObject(g);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
	}

	@Override
	public JPanel createEditPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 3, 5, 5));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		panel.add(centerXLabel);
		panel.add(centerXText);

		panel.add(centerYLabel);
		panel.add(centerYText);

		panel.add(radiusLabel);
		panel.add(radiusText);

		panel.add(colorLabel);
		panel.add(colorArea);

	    return panel;
	}

	@Override
	public void updateObject() {
		center.x = Utility.getInt(centerXText.getText()).orElse(center.x);
		center.y = Utility.getInt(centerYText.getText()).orElse(center.y);
		radius = Utility.getInt(radiusText.getText()).orElse(radius);
		color = colorArea.getCurrentColor();
	}

	@Override
	public String toString() {
		return "CIRCLE " + center.x + " " + center.y + " " + radius + " " + Utility.getRGB(color);
	}

}
