package tokyo.nakanaka.math;

/**
 * Represents a vector in 2D space
 */
public class Vector2D {
	private double x;
	private double y;
	
	/**
	 * Construct a vector which coordinates are (x, y)
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the x coordinate
	 * @return the x coordinate
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the y coordinate
	 * @return the y coordinate
	 */
	public double getY() {
		return y;
	}

	/**
	 * Returns the inner product of this vector and another one
	 * @param another another vector
	 * @return the inner product of this vector and another one
	 */
	public double innerProduct(Vector2D another) {
		return x * another.getX() + y * another.getY();
	}
	
	/**
	 * Returns the length of this vector
	 * @return the length of this vector
	 */
	public double getAbsolute() {
		return Math.sqrt(this.innerProduct(this));
	}
	
}
