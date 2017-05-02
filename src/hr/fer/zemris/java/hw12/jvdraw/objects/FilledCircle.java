package hr.fer.zemris.java.hw12.jvdraw.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import hr.fer.zemris.java.hw12.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw12.jvdraw.util.Utility;

public class FilledCircle extends Circle {

	private Color foreground;
	private Color background;

	private JLabel fgColorLabel = new JLabel("FgColor:");
	private JLabel bgColorLabel = new JLabel("BgColor:");

	private JColorArea fgColor;
	private JColorArea bgColor;

	public FilledCircle(Point center, int radius, Color foreground, Color background) {
		super(center, radius, foreground);
		this.foreground = foreground;
		this.background = background;
		this.fgColor = new JColorArea(foreground);
		this.bgColor = new JColorArea(background);
	}

	@Override
	public void drawObject(Graphics2D g) {
		g.setColor(foreground);
		g.fillOval(center.x - radius , center.y - radius, 2 * radius, 2 * radius);
		g.setColor(background);
		g.drawOval(center.x - radius , center.y - radius, 2 * radius, 2 * radius);
	}

	@Override
	public void drawShifted(Graphics2D g, int x, int y) {
		new FilledCircle(new Point(center.x - x, center.y - y), radius, foreground, background).drawObject(g);
	}

	@Override
	public JPanel createEditPanel() {
		JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

	    panel.add(centerXLabel);
	    panel.add(centerXText);

	    panel.add(centerYLabel);
	    panel.add(centerYText);

	    panel.add(radiusLabel);
	    panel.add(radiusText);

	    panel.add(fgColorLabel);
	    panel.add(fgColor);

	    panel.add(bgColorLabel);
	    panel.add(bgColor);

	    return panel;
	}

	@Override
	public String toString() {
		return "FCIRCLE " + center.x + " " + center.y + " " + radius + " " + Utility.getRGB(background) + " " + Utility.getRGB(foreground);
	}

	@Override
	public void updateObject() {
		center.x = Utility.getInt(centerXText.getText()).orElse(center.x);
		center.y = Utility.getInt(centerYText.getText()).orElse(center.y);
		radius = Utility.getInt(radiusText.getText()).orElse(radius);
		foreground = fgColor.getCurrentColor();
		background = bgColor.getCurrentColor();
	}

}
