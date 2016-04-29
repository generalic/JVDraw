package hr.fer.zemris.java.hw12.jvdraw.components;

import java.awt.Point;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.hw12.jvdraw.pointer.IPositionChangeListener;
import hr.fer.zemris.java.hw12.jvdraw.pointer.MousePointer;

public class MousePositionLabel extends JLabel implements IPositionChangeListener<Point> {

	private static final long serialVersionUID = 1L;

	public MousePositionLabel(MousePointer provider) {
		provider.addPositionChangeListener(this);
		setHorizontalAlignment(SwingConstants.CENTER);
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}

	@Override
	public void positionChanged(Point position) {
		setText(Objects.isNull(position) ? "" : formatText(position));
	}

	private static String formatText(Point p) {
		return "Mouse position: (" + p.x + ", " + p.y + ")";
	}

}
