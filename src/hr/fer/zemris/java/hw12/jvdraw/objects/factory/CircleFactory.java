package hr.fer.zemris.java.hw12.jvdraw.objects.factory;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;

import hr.fer.zemris.java.hw12.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;

public class CircleFactory extends AbstractGeometricalObjectFactory {

	public CircleFactory() {
		super("CIRCLE");
	}

	@Override
	public void draw(Graphics2D g, Point start, Point end, Color foreground, Color background) {
		createObject(start, end, foreground, background).drawObject(g);
	}

	@Override
	public GeometricalObject createObject(Point start, Point end, Color foreground, Color background) {
		return new Circle(start, calculateRadius(start, end), foreground);
	}

	protected static int calculateRadius(Point start, Point end) {
		int width = Math.abs(start.x - end.x);
    	int height = Math.abs(start.y - end.y);
    	return (int) Math.sqrt(width * width + height * height);
	}

	@Override
	protected GeometricalObject createFromSpecs(List<Integer> args) {
		return new Circle
				(new Point(args.get(0), args.get(1)),
				args.get(2),
				new Color(args.get(3), args.get(4), args.get(5))
		);
	}

}