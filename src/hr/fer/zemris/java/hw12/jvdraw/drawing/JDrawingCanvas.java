package hr.fer.zemris.java.hw12.jvdraw.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw12.jvdraw.color.ColorObserver;
import hr.fer.zemris.java.hw12.jvdraw.color.IColorUpdater;
import hr.fer.zemris.java.hw12.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw12.jvdraw.models.DrawingModel;
import hr.fer.zemris.java.hw12.jvdraw.models.DrawingModelListener;
import hr.fer.zemris.java.hw12.jvdraw.objects.factory.IGeometricalObjectFactory;
import hr.fer.zemris.java.hw12.jvdraw.pointer.MousePointer;

public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	private static final long serialVersionUID = 1L;

	private DrawingModel model;
	private JDrawer drawer;
	private MousePointer mousePointer = new MousePointer();
	private boolean changed;

	public JDrawingCanvas(DrawingModel model, JColorArea foreground, JColorArea background) {
		super();
		this.model = model;
		model.addDrawingModelListener(this);
		this.drawer = new JDrawer(foreground, background);
		setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(2));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		for(int i = 0, n = model.getSize(); i < n; i++) {
			model.getObject(i).drawObject(g2d);
		}
		if(drawer.firstClick) {
			drawer.object.draw(g2d, drawer.start, drawer.end, drawer.foreground, drawer.background);
		}
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
		changed = true;
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
		changed = true;
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
		changed = true;
	}

	/**
	 * @return the changed
	 */
	public boolean isChanged() {
		return changed;
	}

	/**
	 * @param changed the changed to set
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	/**
	 * @return the object
	 */
	public void setObjectDrawer(IGeometricalObjectFactory object) {
		drawer.object = object;
	}

	/**
	 * @return the mousePointer
	 */
	public MousePointer getMousePointer() {
		return mousePointer;
	}

	public class JDrawer implements IColorUpdater {

		private Point start;
		private Point end;

		private Color foreground;
		private Color background;

		private ColorObserver foregroundListener;
		private ColorObserver backgroundListener;

		private boolean firstClick = false;

		private IGeometricalObjectFactory object;

		public JDrawer(JColorArea foreground, JColorArea background) {
			super();
			addMouseListener(mouseClick);
			addMouseMotionListener(mouseMotion);
			this.foregroundListener = new ColorObserver(foreground.getCurrentColor(), this);
			foreground.addColorChangeListener(foregroundListener);
			this.backgroundListener = new ColorObserver(background.getCurrentColor(), this);
			background.addColorChangeListener(backgroundListener);
			updateColor();
		}

		private MouseListener mouseClick = new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					firstClick = false;
					repaint();
					return;
				}
				if (!firstClick) {
					firstClick = true;
					start = e.getPoint();
				} else {
					firstClick = false;
					addObject();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mousePointer.changePosition(null);
			};

		};

		private MouseMotionListener mouseMotion = new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				end = e.getPoint();
				mousePointer.changePosition(end);
				if (firstClick) {
					repaint();
				}
			}

		};

		private void addObject() {
			model.add(object.createObject(start, end, foreground, background));
		};

		@Override
		public void updateColor() {
			foreground = foregroundListener.getColor();
			background = backgroundListener.getColor();
		}

	}

}
