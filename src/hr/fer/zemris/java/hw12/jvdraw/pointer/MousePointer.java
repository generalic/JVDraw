package hr.fer.zemris.java.hw12.jvdraw.pointer;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class MousePointer implements IPositionChanger<Point> {

	private List<IPositionChangeListener<Point>> listeners = new ArrayList<>();

	@Override
	public void changePosition(Point position) {
		listeners.forEach(l -> l.positionChanged(position));
	}

	public void addPositionChangeListener(IPositionChangeListener<Point> l) {
		if(!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	public void removePositionChangeListener(IPositionChangeListener<Point> l) {
		listeners.remove(l);
	}

}
