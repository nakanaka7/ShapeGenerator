package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.math.Vector2D;

/**
 * Represents a cylinder which base is on x-y plane, which base center is the space origin,
 * and which axis is z-axis.
 */
@PublicAPI
public class Cylinder implements Region3D {
	private double radius;
	private double height;
	
	/**
	 * @param radius the radius of the base disc
	 * @param height the height of the cylinder
	 * @throws if height is smaller than zero
	 */
	public Cylinder(double radius, double height) {
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
		return pos.getAbsolute() <= this.radius;
	}
	
}
