package hr.fer.zemris.java.hw12.jvdraw.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw12.jvdraw.objects.factory.IGeometricalObjectFactory;

public class FileUtil {

	public static Map<String, FileNameExtensionFilter> imageExtensions = new HashMap<>();
	public static Map<String, IGeometricalObjectFactory> factories = new HashMap<>();
	public static final String EXTENSION = ".jvd";

	static {
		imageExtensions.put(".png", new FileNameExtensionFilter("PNG (*.png)", "png"));
		imageExtensions.put(".jpg", new FileNameExtensionFilter("JPG (*.jpg)", "jpg"));
		imageExtensions.put(".gif", new FileNameExtensionFilter("GIF (*.gif)", "gif"));
	}

	public static JFileChooser getExportFileChooser() {
		JFileChooser fc = new JFileChooser();
		imageExtensions.values().forEach(fc::addChoosableFileFilter);
		fc.setFileFilter(imageExtensions.get(".png"));
		return fc;
	}

	public static Path setFileExtension(String file, String extension) {
		return Paths.get(file.endsWith(extension) ? file : file + extension);
	}

	/**
	 * Returns the selected file from a JFileChooser, including the extension from
	 * the file filter.
	 */
	public static Path getSelectedFilePathWithExtension(JFileChooser c) {
	    File file = c.getSelectedFile();
	    if (c.getFileFilter() instanceof FileNameExtensionFilter) {
	        String[] exts = ((FileNameExtensionFilter)c.getFileFilter()).getExtensions();
	        String nameLower = file.getName().toLowerCase();
	        for (String ext : exts) { // check if it already has a valid extension
	            if (nameLower.endsWith('.' + ext.toLowerCase())) {
	                return file.toPath(); // if yes, return as-is
	            }
	        }
	        // if not, append the first extension from the selected filter
	        file = new File(file.toString() + '.' + exts[0]);
	    }
	    return file.toPath();
	}

	public static String getFileExtension(File file) {
		String fileName = file.getName();
		int dotIndex = fileName.lastIndexOf('.');
		return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
	}

	public static List<GeometricalObject> readFile(Path file) throws IOException {
		List<GeometricalObject> objects = new ArrayList<>();
		List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
		lines.forEach(l -> {
			l = l.trim();
			int index = l.indexOf(' ');
			String name = l.substring(0, index);
			String line = l.substring(index + 1, l.length()).trim();
			objects.add(factories.get(name).createFromString(line));
		});
		return objects;
	}

	public static void saveFile(Path file, String config) throws IOException {
		Files.write(file, config.getBytes(StandardCharsets.UTF_8));
	}

}
