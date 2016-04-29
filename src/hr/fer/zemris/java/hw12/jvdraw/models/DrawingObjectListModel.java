package hr.fer.zemris.java.hw12.jvdraw.models;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;

public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private DrawingModel model;

	public DrawingObjectListModel(DrawingModel model) {
		super();
		this.model = model;
		model.addDrawingModelListener(this);
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(source, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(source, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
	}

}
