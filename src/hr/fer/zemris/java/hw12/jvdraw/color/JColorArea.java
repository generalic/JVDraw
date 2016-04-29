package hr.fer.zemris.java.hw12.jvdraw.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

public class JColorArea extends JComponent implements IColorProvider {

	private static final long serialVersionUID = 1L;

	private static final Dimension DIMENSION = new Dimension(20, 20);
	private Color selectedColor;
	private List<ColorChangeListener> listeners = new ArrayList<>();

	public JColorArea(final Color selectedColor) {
		super();
		this.selectedColor = selectedColor;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				final Color c = JColorChooser.showDialog(
						JColorArea.this,
						"Choose a color",
						JColorArea.this.selectedColor
				);
				if(Objects.nonNull(c)) {
					setNewColor(c);
				}
			}
		});
	}

	@Override
	public Dimension getPreferredSize() {
		return DIMENSION;
	}

	@Override
	public Dimension getMinimumSize() {
		return DIMENSION;
	}

	@Override
	public Dimension getMaximumSize() {
		return DIMENSION;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(selectedColor);
		final Insets ins = getInsets();
		final Dimension size = getSize();
		g2d.fillRect(ins.left + (size.width - (DIMENSION.width)) / 2, ins.top
				+ (size.height - (DIMENSION.height)) / 2, DIMENSION.width,
				DIMENSION.height);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	public void setNewColor(Color color) {
		listeners.forEach(l -> l.newColorSelected(this, selectedColor, color));
		this.selectedColor = color;
		repaint();
	}

	public void addColorChangeListener(ColorChangeListener l) {
		if(!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}

}
