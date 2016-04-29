package hr.fer.zemris.java.hw12.jvdraw.color;

import java.awt.Color;

public class ColorObserver implements ColorChangeListener {

	private Color color;
	private IColorUpdater updater;

	public ColorObserver(Color color, IColorUpdater updater) {
		super();
		this.color = color;
		this.updater = updater;
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		this.color = newColor;
		updater.updateColor();
	}

	public Color getColor() {
		return color;
	}

}