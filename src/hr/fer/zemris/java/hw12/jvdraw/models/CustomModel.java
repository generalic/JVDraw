package hr.fer.zemris.java.hw12.jvdraw.models;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;

public class CustomModel implements DrawingModel {

	private List<GeometricalObject> objects = new ArrayList<>();
	private List<DrawingModelListener> listeners = new ArrayList<>();

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(object);
		int index = objects.size() - 1;
		listeners.forEach(l -> l.objectsAdded(this, index, index));
	}

	@Override
	public void clear() {
		if(!objects.isEmpty()) {
			int size = objects.size();
			objects.clear();
			listeners.forEach(l -> l.objectsRemoved(this, 0, size - 1));
		}
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		if(!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	@Override
	public Rectangle getBoundingBox() {
		int left = Integer.MAX_VALUE;
		int top = Integer.MAX_VALUE;
		int right = Integer.MIN_VALUE;
		int bottom = Integer.MIN_VALUE;
		for(GeometricalObject o : objects) {
			Rectangle r = o.getBounds();
			left = (left > r.x) ? r.x : left;
			right = (right < r.x + r.width) ? r.x + r.width : right;
			top = (top > r.y) ? r.y : top;
			bottom = (bottom < r.y + r.height) ? r.y + r.height : bottom;
		}
		return new Rectangle(left, top, right - left, bottom - top);
	}

}
