package dsai.group24.force.model.surface;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * This class is used to represent surface which contains attributes and methods
 * for adjusting static friction coefficient and kinetic friction coefficient
 *
 */
public class Surface {
	/**
	 * Holds the max static coefficient of class Surface
	 */
	public static final double MAX_STA_COEF = 1.0;
	/**
	 * Holds the static friction property of this Surface
	 */
	private DoubleProperty staCoef = new SimpleDoubleProperty(MAX_STA_COEF / 2);
	/**
	 * Holds the kinetic friction coefficient of this Surface
	 */
	private DoubleProperty kiCoef = new SimpleDoubleProperty(MAX_STA_COEF / 4);
	
	/**
	 * Holds the step of static/kinetic coefficient of class Surface
	 */
	public static final double STEP_COEF = 0.001;

	/**
	 * Default class constructor
	 */
	public Surface() {
	}

	/**
	 * Class constructor specifying static friction coefficient and set kinetic
	 */
	public Surface(double staCoef) throws Exception {
		setStaCoef(staCoef);
		setKiCoef(staCoef / 2);
	}

	/**
	 * Class constructor specifying static friction coefficient and kinetic friction
	 */
	public Surface(double staCoef, double kiCoef) throws Exception {
		setStaCoef(staCoef);
		setKiCoef(kiCoef);
	}

	/**
	 * Gets the static friction coefficient property of this Surface
	 */
	public DoubleProperty staCoefProperty() {
		return staCoef;
	}

	/**
	 * Gets the static friction coefficient of this Surface
	 */
	public double getStaCoef() {
		return staCoef.get();
	}

	/**
	 * Gets the kinetic friction coefficient property of this Surface
	 */
	public DoubleProperty kiCoefProperty() {
		return kiCoef;
	}

	/**
	 * Gets the kinetic friction coefficient of this Surface
	 */
	public double getKiCoef() {
		return kiCoef.get();
	}

	/**
	 * Changes the static friction coefficient of this Surface
	 */
	public void setStaCoef(double staCoef) throws Exception {
		if (staCoef == 0) {
			// Sets both staCoef and kiCoef to 0 if staCoef = 0
			kiCoef.setValue(0);
			this.staCoef.setValue(0);
		} else if (staCoef <= getKiCoef()) {
			this.staCoef.setValue(getKiCoef() + STEP_COEF);
			throw new Exception("Static friction coefficient must be > kinetic friction coefficient: "
					+ String.format("%.3f", getKiCoef()));
		} else {
			this.staCoef.setValue(staCoef);
		}
	}

	/**
	 * Changes the kinetic friction coefficient of this Surface
	 */
	public void setKiCoef(double kiCoef) throws Exception {
		if (getStaCoef() <= kiCoef) {
			// Handles case when staCoef has already = 0
			this.kiCoef.setValue(Math.max(0, getStaCoef() - STEP_COEF));
			throw new Exception("Kinetic friction coefficient must be < static friction coefficient: "
					+ String.format("%.3f", getStaCoef()));
		} else {
			this.kiCoef.setValue(kiCoef);
		}
	}

}
