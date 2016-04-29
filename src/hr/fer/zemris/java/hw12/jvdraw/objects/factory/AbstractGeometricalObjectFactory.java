package hr.fer.zemris.java.hw12.jvdraw.objects.factory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;

public abstract class AbstractGeometricalObjectFactory implements IGeometricalObjectFactory {

	protected String name;

	public AbstractGeometricalObjectFactory(String name) {
		super();
		this.name = name;
	}

	@Override
	public final GeometricalObject createFromString(String line) {
		List<Integer> args = Arrays.asList(line.split("\\s")).stream()
				.mapToInt(Integer::parseInt)
				.boxed()
				.collect(Collectors.toList());
		return createFromSpecs(args);
	}

	protected abstract GeometricalObject createFromSpecs(List<Integer> args);

	/**
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractGeometricalObjectFactory other = (AbstractGeometricalObjectFactory) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
