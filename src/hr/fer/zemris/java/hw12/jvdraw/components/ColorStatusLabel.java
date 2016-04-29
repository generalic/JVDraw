package hr.fer.zemris.java.hw12.jvdraw.components;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.hw12.jvdraw.color.ColorObserver;
import hr.fer.zemris.java.hw12.jvdraw.color.IColorUpdater;
import hr.fer.zemris.java.hw12.jvdraw.color.JColorArea;

public class ColorStatusLabel extends JLabel implements IColorUpdater {

	private static final long serialVersionUID = 1L;

	private ColorObserver foregroundListener;
	private ColorObserver backgroundListener;

	public ColorStatusLabel(JColorArea foreground, JColorArea background) {
		super();
		setHorizontalAlignment(SwingConstants.CENTER);
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.foregroundListener = new ColorObserver(foreground.getCurrentColor(), this);
		foreground.addColorChangeListener(foregroundListener);
		this.backgroundListener = new ColorObserver(background.getCurrentColor(), this);
		background.addColorChangeListener(backgroundListener);
		updateColor();
	}

	@Override
	public void updateColor() {
		setText(getColorStatus(foregroundListener.getColor(), backgroundListener.getColor()));
	}

	private static String getColorStatus(Color foreground, Color background) {
		return  "Foreground color: (" + getColor(foreground) + "), " +
				"Background color: (" + getColor(background) + ").";
	}

	private static String getColor(Color c) {
		return c.getRed() + ", " + c.getGreen() + ", " + c.getBlue();
	}

}
