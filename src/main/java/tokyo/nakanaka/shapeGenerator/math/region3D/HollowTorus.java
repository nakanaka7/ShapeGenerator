package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.math.PolarVector2D;
import tokyo.nakanaka.math.Vector3D;

/**
 * Represents a torus shape region, which center is the origin, and which axis is z.
 */
@PublicAPI
public class HollowTorus implements Region3D {
	private double majorRadius;
	private double outerMinorRadius;
	private double innerMinorRadius;
	
	/**
	 * @param majorRadius the main radius
	 * @param outerMinorRadius the outer minor radius
	 * @param innerMinorRadius the inner minor radius
	 * @throws IllegalArgumentException if radiusMain or radiusSub is less than 0 (not inclusive)
	 */
	public HollowTorus(double majorRadius, double outerMinorRadius, double innerMinorRadius) {
		if(majorRadius < 0 || outerMinorRadius < 0 || innerMinorRadius < 0) {
			throw new IllegalArgumentException();
		}
		if(innerMinorRadius > outerMinorRadius) {
			throw new IllegalArgumentException();
		}
		this.majorRadius = majorRadius;
		this.outerMinorRadius = outerMinorRadius;
		this.innerMinorRadius = innerMinorRadius;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		PolarVector2D polar = PolarVector2D.valueOf(x, y);
		double angle = polar.getArgument();
		Vector3D q = new Vector3D(this.majorRadius * Math.cos(angle), this.majorRadius * Math.sin(angle), 0);
		double s = new Vector3D(x, y, z).getDistance(q);
		return this.innerMinorRadius <= s && s<= this.outerMinorRadius;
	}

}
