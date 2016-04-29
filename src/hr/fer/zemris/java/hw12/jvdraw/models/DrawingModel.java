package hr.fer.zemris.java.hw12.jvdraw.models;

import java.awt.Rectangle;

import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;

public interface DrawingModel {

	int getSize();

	GeometricalObject getObject(int index);

	void add(GeometricalObject object);

	void clear();

	void addDrawingModelListener(DrawingModelListener l);

	void removeDrawingModelListener(DrawingModelListener l);

	Rectangle getBoundingBox();

}
