package hr.fer.zemris.java.hw12.jvdraw.objects.factory;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;

public interface IGeometricalObjectFactory {

	void draw(Graphics2D g, Point start, Point end, Color foreground, Color background);

	GeometricalObject createObject(Point start, Point end, Color foreground, Color background);

	GeometricalObject createFromString(String line);

	String getName();

}

