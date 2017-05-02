package hr.fer.zemris.java.hw12.jvdraw.objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

public abstract class GeometricalObject {

	public abstract void drawObject(Graphics2D g);

	public abstract void drawShifted(Graphics2D g, int x, int y);

	public abstract Rectangle getBounds();

	public abstract JPanel createEditPanel();

	public abstract void updateObject();

}
