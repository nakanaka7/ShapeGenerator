package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.math.Vector2D;

/**
 * Represents a cone which base is on x-y plane, which base center is the space origin,
 * and which extends to positive z.
 */
@PublicAPI
public class Cone implements Region3D {
	private double radius;
	private double height;
	
	/**
	 * @param radius a radius of the base disc
	 * @param height a height of the cone
	 */
	public Cone(double radius, double height) {
		this.radius = radius;
		if(height < 0) {
			throw new IllegalArgumentException();
		}
		this.height = height;
	}
	
	@Override
	public boolean contains(double x, double y, double z) {
		if(z < 0 || this.height < z) {
			return false;
		}
		Vector2D pos = new Vector2D(x, y);
		return pos.getAbsolute() <= this.radius * (1 - z / this.height);
	}

}
