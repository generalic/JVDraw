package hr.fer.zemris.java.hw12.jvdraw.objects.factory;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;

import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw12.jvdraw.objects.Line;

public class LineFactory extends AbstractGeometricalObjectFactory {

	public LineFactory() {
		super("LINE");
	}

	@Override
	public void draw(Graphics2D g, Point start, Point end, Color foreground, Color background) {
		createObject(start, end, foreground, background).drawObject(g);
	}

	@Override
	public GeometricalObject createObject(Point start, Point end, Color foreground, Color background) {
		return new Line(start, end, foreground);
	}

	@Override
	protected GeometricalObject createFromSpecs(List<Integer> args) {
		return new Line
				(new Point(args.get(0), args.get(1)),
				new Point(args.get(2), args.get(3)),
				new Color(args.get(4), args.get(5), args.get(6))
		);
	}

}
