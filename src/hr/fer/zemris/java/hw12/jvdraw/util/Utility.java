package hr.fer.zemris.java.hw12.jvdraw.util;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw12.jvdraw.models.DrawingModel;

public final class Utility {

	public static String getRGB(Color c) {
		return c.getRed() + " " + c.getGreen() + " " + c.getBlue();
	}

	public static Optional<Color> getColorFromString(String c) {
		if(c.isEmpty()) {
			return Optional.empty();
		}
		List<Integer> a = Arrays.asList(c.split(", ")).stream()
				.mapToInt(Integer::parseInt)
				.boxed()
				.collect(Collectors.toList());
		return Optional.of(new Color(a.get(0), a.get(1), a.get(2)));
	}

	public static String createFileConfig(DrawingModel model) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0, n = model.getSize(); i < n; i++) {
			sb.append(model.getObject(i).toString() + "\n");
		}
		return sb.toString().trim();
	}

	public static Optional<Integer> getInt(String n) {
		return n.isEmpty() ? Optional.empty() : Optional.of(Integer.parseInt(n));
	}

}
