package dsai.group24.force.model.object;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;


public class Cube extends MainObject {
	/**
	 * Holds the size property of this Cube.
	 */
	private DoubleProperty size = new SimpleDoubleProperty(MAX_SIZE * 0.3);
	/**
	 * Holds the max size of class Cube
	 */
	public static final double MAX_SIZE = 1.0;
	/**
	 * Holds the min size of class Cube
	 */
	public static final double MIN_SIZE = 0.1;

	/**
	 * Default class constructor
	 */
	public Cube() {
		super();
	}

	/**
	 * Class constructor specifying mass
	 */
	public Cube(double mass) throws Exception {
		super(mass);
	}

	/**
	 * Class constructor specifying mass and size
	 */
	public Cube(double mass, double size) throws Exception {
		this(mass);
		setSize(size);
	}

	/**
	 * Gets size property of this Cube
	 */
	public DoubleProperty sizeProperty() {
		return size;
	}

	/**
	 * Gets the size of this Cube
	 */
	public double getSize() {
		return size.get();
	}

	/**
	 * Changes the size of this Cube
	 */
	public void setSize(double size) throws Exception {
		if (size < MIN_SIZE || size > MAX_SIZE) {
			setSize(MAX_SIZE * 0.3);
			throw new Exception("Cube's size must >= " + MIN_SIZE + " and <= " + MAX_SIZE);
		} else {
			this.size.setValue(size);
		}
	}

}
