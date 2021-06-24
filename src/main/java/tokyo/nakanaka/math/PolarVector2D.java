package tokyo.nakanaka.math;

import org.apache.commons.math3.complex.Complex;

public class PolarVector2D {
	private double radius;
	private double arg;
	
	public PolarVector2D(double radius, double arg) {
		this.radius = radius;
		this.arg = arg;
	}
	
	public static PolarVector2D valueOf(double x, double y) {
		Complex c = new Complex(x, y);
		return new PolarVector2D(c.abs(), c.getArgument());
	}
	
	public static PolarVector2D valueOf(Vector2D v) {
		Complex c = new Complex(v.getX(), v.getY());
		return new PolarVector2D(c.abs(), c.getArgument());
	}
	
	public double getRadius() {
		return this.radius;
	}
	
	/**Get the argument of this vector by radian. The value is between -PI (not inclusive) and PI (inclusive). 
	 * @return the argument of this vector by radian. The value is between -PI (not inclusive) and PI (inclusive)
	 */
	public double getArgument() {
		return this.arg;
	}
}
