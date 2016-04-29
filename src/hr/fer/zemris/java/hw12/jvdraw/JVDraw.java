package hr.fer.zemris.java.hw12.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw12.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw12.jvdraw.components.ColorStatusLabel;
import hr.fer.zemris.java.hw12.jvdraw.components.MousePositionLabel;
import hr.fer.zemris.java.hw12.jvdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw12.jvdraw.models.CustomModel;
import hr.fer.zemris.java.hw12.jvdraw.models.DrawingModel;
import hr.fer.zemris.java.hw12.jvdraw.models.DrawingObjectListModel;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw12.jvdraw.objects.factory.CircleFactory;
import hr.fer.zemris.java.hw12.jvdraw.objects.factory.FilledCircleFactory;
import hr.fer.zemris.java.hw12.jvdraw.objects.factory.IGeometricalObjectFactory;
import hr.fer.zemris.java.hw12.jvdraw.objects.factory.LineFactory;
import hr.fer.zemris.java.hw12.jvdraw.util.FileUtil;
import hr.fer.zemris.java.hw12.jvdraw.util.Utility;

public class JVDraw extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static final String TITLE = "JVDraw v1.0";

	private static final String FILE_MENU = "File";

	private static final String OPEN = "Open";

	private static final String SAVE = "Save";

	private static final String SAVE_AS = "Save As";

	private static final String EXPORT = "Export";

	private static final String EXIT = "Exit";

	private static final String LINE = "Line";

	private static final String CIRCLE = "Circle";

	private static final String FILLED_CIRCLE = "Filled Circle";

	private static final String EXTENSION = ".jvd";

	private JPanel panel;
	private JColorArea foregroundButton = new JColorArea(Color.RED);
	private JColorArea backgroundButton = new JColorArea(Color.BLUE);
	private JLabel colorsStatus = new ColorStatusLabel(foregroundButton, backgroundButton);

	private List<JToggleButton> objectButtons = new ArrayList<>();

	private DrawingModel model = new CustomModel();
	private JDrawingCanvas canvas = new JDrawingCanvas(model, foregroundButton, backgroundButton);
	private JLabel mousePositionStatus = new MousePositionLabel(canvas.getMousePointer());

	private Path file;

	public JVDraw() {
		setTitle(TITLE);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocation(100, 100);
		setSize(900, 600);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException ignorable) {}

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(null);
			}
		});
		initGui();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JVDraw().setVisible(true));
	}

	private void initGui() {
		getContentPane().setLayout(new BorderLayout());

		panel = new JPanel(new BorderLayout());
		getContentPane().add(panel, BorderLayout.CENTER);

		createIcon();
		createActions();
		createMenus();
		createToolBar();
		createStatusBar();
		createObjectList();
		createCanvas();
	}

	private void createIcon() {
		try {
			setIconImage(ImageIO.read(getClass().getResource("pes.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
//		try {
//		    setIconImage(ImageIO.read(new File(".res/pes.jpg")));
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	private void createActions() {
		openAction.putValue(Action.NAME, "Open");
		openAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control O"));
		openAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openAction.putValue(Action.SHORT_DESCRIPTION, "Opens a new file");

		saveAction.putValue(Action.NAME, "Save");
		saveAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control S"));
		saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveAction.putValue(Action.SHORT_DESCRIPTION,
				"Saves the current file");

		saveAsAction.putValue(Action.NAME, "Save as");
		saveAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsAction.putValue(Action.SHORT_DESCRIPTION,
				"Saves the current file to given path");

		exportAction.putValue(Action.NAME, "Export");
		exportAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control E"));
		exportAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exportAction.putValue(Action.SHORT_DESCRIPTION,
				"Exports the created file as image");

		exitAction.putValue(Action.NAME, "Exit");
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Closes the program");
	}

	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu(FILE_MENU);
		menuBar.add(fileMenu);

		fileMenu.add(openAction);
		fileMenu.addSeparator();
		fileMenu.add(saveAction);
		fileMenu.add(saveAsAction);
		fileMenu.addSeparator();
		fileMenu.add(exportAction);
		fileMenu.addSeparator();
		fileMenu.add(exitAction);
	}

	private void createToolBar() {
		JToolBar toolBar = new JToolBar();

		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));

		buttons.add(foregroundButton);
		buttons.add(backgroundButton);

		ButtonGroup group = new ButtonGroup();

		objectButtons.add(new JToggleButton(lineDrawAction));
		objectButtons.add(new JToggleButton(circleDrawAction));
		objectButtons.add(new JToggleButton(filledCircleDrawAction));

		objectButtons.stream()
			.peek(group::add)
			.peek(JToggleButton::doClick)
			.forEach(buttons::add);

		toolBar.add(buttons);
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(e -> model.clear());
		toolBar.add(clearButton);

		getContentPane().add(toolBar, BorderLayout.PAGE_START);

	}

	private void createStatusBar() {
		JPanel statusBar = new JPanel(new GridLayout(1, 0, 5, 5));
		statusBar.add(colorsStatus);
		statusBar.add(mousePositionStatus);
		getContentPane().add(statusBar, BorderLayout.PAGE_END);
	}

	private void createObjectList() {
		JList<GeometricalObject> list = new JList<>(new DrawingObjectListModel(model));
		list.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {
					GeometricalObject obj = list.getSelectedValue();
					final int r = JOptionPane.showConfirmDialog(
							JVDraw.this,
							obj.createEditPanel(),
							"Edit",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.PLAIN_MESSAGE);
					if(r != JOptionPane.YES_OPTION) {
						return;
					}
					try {
						obj.updateObject();
					} catch(Exception e1) {
						JOptionPane.showMessageDialog(
								JVDraw.this,
								e1.getMessage(),
								"Error",
								JOptionPane.ERROR_MESSAGE
						);
						return;
					}
					repaint();
			     }

			}

		});
		JScrollPane listView = new JScrollPane(list);
		listView.setPreferredSize(list.getPreferredScrollableViewportSize());
		getContentPane().add(listView, BorderLayout.LINE_END);
	}

	private void createCanvas() {
		getContentPane().add(canvas, BorderLayout.CENTER);
	}

	@SuppressWarnings("serial")
	private Action openAction = new AbstractAction(OPEN) {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(canvas.isChanged()) {
				int r = JOptionPane.showConfirmDialog(
						JVDraw.this,
						"Do you want to save changes before opening a new file?",
						"File changed",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if(r == JOptionPane.YES_OPTION) {
					saveAction.actionPerformed(e);
				} else if(r == JOptionPane.CANCEL_OPTION) {
					return;
				}
			}
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JVDraw files (*" + EXTENSION + ")", "jvd");
			fc.setFileFilter(filter);
			if(fc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			file = fc.getSelectedFile().toPath();
			if(!file.getFileName().toString().endsWith(EXTENSION)) {
				JOptionPane.showMessageDialog(
						JVDraw.this,
						"Only " + EXTENSION + " files are supported.",
						"Invalid file",
						JOptionPane.WARNING_MESSAGE
				);
				return;
			}
			try {
				model.clear();
				FileUtil.readFile(file).forEach(model::add);
				canvas.setChanged(false);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(
						JVDraw.this,
						e1.getMessage(),
						"Invalid file configuration",
						JOptionPane.WARNING_MESSAGE
				);
				return;
			}
			repaint();
		}

	};

	@SuppressWarnings("serial")
	private Action saveAction = new AbstractAction(SAVE) {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!canvas.isChanged()) {
				return;
			}
			if(Objects.isNull(file)) {
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JVDraw files (*" + EXTENSION + ")", "jvd");
				fc.setFileFilter(filter);
				if(fc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				file = FileUtil.getSelectedFilePathWithExtension(fc);
			}
			try {
				FileUtil.saveFile(file, Utility.createFileConfig(model));
				canvas.setChanged(false);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(
						JVDraw.this,
						e1.getMessage(),
						"Error",
						JOptionPane.ERROR_MESSAGE
				);
				return;
			}
		}

	};

	@SuppressWarnings("serial")
	private Action saveAsAction = new AbstractAction(SAVE_AS) {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JVDraw files (*.jvd)", "jvd");
			fc.setFileFilter(filter);
			if(fc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			file = FileUtil.getSelectedFilePathWithExtension(fc);
			try {
				FileUtil.saveFile(file, Utility.createFileConfig(model));
				canvas.setChanged(false);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(
						JVDraw.this,
						e1.getMessage(),
						"Error",
						JOptionPane.ERROR_MESSAGE
				);
				return;
			}
		}

	};

	@SuppressWarnings("serial")
	private Action exportAction = new AbstractAction(EXPORT) {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = FileUtil.getExportFileChooser();
			if(fc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File file = FileUtil.getSelectedFilePathWithExtension(fc).toFile();
			Rectangle r = model.getBoundingBox();
			BufferedImage image = new BufferedImage(r.width, r.height, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g = image.createGraphics();
			for(int i = 0, n = model.getSize(); i < n; i++) {
				model.getObject(i).drawShifted(g, r.x, r.y);
			}
			g.dispose();
			try {
				ImageIO.write(image, FileUtil.getFileExtension(file), file);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(
						JVDraw.this,
						e1.getMessage(),
						"Error",
						JOptionPane.ERROR_MESSAGE
				);
				return;
			}
		}

	};

	@SuppressWarnings("serial")
	private Action exitAction = new AbstractAction(EXIT) {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(canvas.isChanged()) {
				int r = JOptionPane.showConfirmDialog(
						JVDraw.this,
						"Do you want to save changes before exiting?",
						"File changed",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if(r == JOptionPane.YES_OPTION) {
					saveAction.actionPerformed(e);
				} else if(r == JOptionPane.CANCEL_OPTION) {
					return;
				}
			}
			JVDraw.this.dispose();
		}

	};

	@SuppressWarnings("serial")
	private Action lineDrawAction = new AbstractAction(LINE) {

		@Override
		public void actionPerformed(ActionEvent e) {
			setObjectFactory(new LineFactory());
		}

	};

	@SuppressWarnings("serial")
	private Action circleDrawAction = new AbstractAction(CIRCLE) {

		@Override
		public void actionPerformed(ActionEvent e) {
			setObjectFactory(new CircleFactory());
		}

	};

	@SuppressWarnings("serial")
	private Action filledCircleDrawAction = new AbstractAction(FILLED_CIRCLE) {

		@Override
		public void actionPerformed(ActionEvent e) {
			setObjectFactory(new FilledCircleFactory());
		}

	};

	private void setObjectFactory(IGeometricalObjectFactory factory) {
		FileUtil.factories.put(factory.getName(), factory);
		canvas.setObjectDrawer(factory);
	}

}
