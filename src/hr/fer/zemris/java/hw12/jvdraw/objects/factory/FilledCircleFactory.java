package hr.fer.zemris.java.hw12.jvdraw.objects.factory;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;

import hr.fer.zemris.java.hw12.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;

public class FilledCircleFactory extends CircleFactory {

	public FilledCircleFactory() {
		this.name = "FCIRCLE";
	}

	@Override
	public void draw(Graphics2D g, Point start, Point end, Color foreground, Color background) {
		createObject(start, end, foreground, background).drawObject(g);
	}

	@Override
	public GeometricalObject createObject(Point start, Point end, Color foreground, Color background) {
		return new FilledCircle(start, calculateRadius(start, end), foreground, background);
	}

	@Override
	protected GeometricalObject createFromSpecs(List<Integer> args) {
		return new FilledCircle
				(new Point(args.get(0), args.get(1)),
				args.get(2),
				new Color(args.get(6), args.get(7), args.get(8)),
				new Color(args.get(3), args.get(4), args.get(5))
		);
	}

}