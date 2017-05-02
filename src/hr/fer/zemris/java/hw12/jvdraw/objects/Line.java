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

public class Line extends GeometricalObject {

	private Point start;
	private Point end;
	private Color color;

	private JLabel startXLabel = new JLabel("Start x:");
	private JLabel startYLabel = new JLabel("Start y:");

	private JLabel endXLabel = new JLabel("End x:");
	private JLabel endYLabel = new JLabel("End y:");

	private JLabel colorLabel = new JLabel("Color:");

	private JTextField startXText = new JTextField();
	private JTextField startYText = new JTextField();

	private JTextField endXText = new JTextField();
	private JTextField endYText = new JTextField();

	private JColorArea colorArea;

	public Line(Point start, Point end, Color color) {
		this.start = start;
		this.end = end;
		this.color = color;
		this.colorArea = new JColorArea(color);
	}

	@Override
	public void drawObject(Graphics2D g) {
		g.setColor(color);
		g.drawLine(start.x, start.y, end.x, end.y);
	}

	@Override
	public void drawShifted(Graphics2D g, int x, int y) {
		new Line(new Point(start.x - x, start.y - y), new Point(end.x - x, end.y - y), color).drawObject(g);
	}

	@Override
	public Rectangle getBounds() {
		Point p = new Point((start.x < end.x) ? start.x : end.x, (start.y < end.y) ? start.y : end.y);
		int width = Math.abs(start.x - end.x);
		int height = Math.abs(start.y - end.y);
		return new Rectangle(p.x, p.y, width, height);
	}

	@Override
	public JPanel createEditPanel() {
		JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

	    panel.add(startXLabel);
	    panel.add(startXText);

	    panel.add(startYLabel);
	    panel.add(startYText);

	    panel.add(endXLabel);
	    panel.add(endXText);

	    panel.add(endYLabel);
	    panel.add(endYText);

	    panel.add(colorLabel);
	    panel.add(colorArea);

	    return panel;
	}

	@Override
	public String toString() {
		return "LINE " + start.x + " " + start.y + " " + end.x + " " + end.y + " " + Utility.getRGB(color);
	}

	@Override
	public void updateObject() {
		start.x = Utility.getInt(startXText.getText()).orElse(start.x);
		start.y = Utility.getInt(startYText.getText()).orElse(start.y);
		end.x = Utility.getInt(endXText.getText()).orElse(end.x);
		end.y = Utility.getInt(endYText.getText()).orElse(end.y);
		color = colorArea.getCurrentColor();
	}

}
