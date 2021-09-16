package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.math.PolarVector2D;
import tokyo.nakanaka.math.Vector3D;

/**
 * Represents a torus shape region, which center is the origin, and which axis is z.
 */
@PublicAPI
public class Torus implements Region3D{
	private double majorRadius;
	private double minorRadius;
	
	/**
	 * @param majorRadius the main radius
	 * @param minorRadius the sub radius
	 * @throws IllegalArgumentException if radiusMain or radiusSub is less than 0 (not inclusive)
	 */
	public Torus(double majorRadius, double minorRadius) {
		if(majorRadius < 0 || minorRadius < 0) {
			throw new IllegalArgumentException();
		}
		this.majorRadius = majorRadius;
		this.minorRadius = minorRadius;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		PolarVector2D polar = PolarVector2D.valueOf(x, y);
		double angle = polar.getArgument();
		Vector3D q = new Vector3D(this.majorRadius * Math.cos(angle), this.majorRadius * Math.sin(angle), 0);
		return new Vector3D(x, y, z).getDistance(q) <= this.minorRadius;
	}
	
}
