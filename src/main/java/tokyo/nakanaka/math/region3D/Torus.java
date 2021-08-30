package tokyo.nakanaka.math.region3D;

import tokyo.nakanaka.math.PolarVector2D;
import tokyo.nakanaka.math.Vector3D;

/**
 * Represents a torus shape region, which center is the origin, and which axis is z.
 */
public class Torus implements Region3D{
	private double radiusMain;
	private double radiusSub;
	
	/**
	 * @throws IllegalArgumentException
	 */
	public Torus(double radiusMain, double radiusSub) {
		if(radiusMain < 0 || radiusSub < 0) {
			throw new IllegalArgumentException();
		}
		this.radiusMain = radiusMain;
		this.radiusSub = radiusSub;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		PolarVector2D polar = PolarVector2D.valueOf(x, y);
		double angle = polar.getArgument();
		Vector3D q = new Vector3D(this.radiusMain * Math.cos(angle), this.radiusMain * Math.sin(angle), 0);
		return new Vector3D(x, y, z).getDistance(q) <= this.radiusSub;
	}
	
}
