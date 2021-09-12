package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.math.Vector3D;

/**
 * Represents hollow sphere
 */
@PublicAPI
public class HollowSphere implements Region3D {
	private double outerRadius;
	private double innerRadius;
	
	/**
	 * @param outerRadius outer radius
	 * @param innerRadius inner radius
	 * @throws innerRadius is larger than outerRadius
	 */
	public HollowSphere(double outerRadius, double innerRadius) {
		if(innerRadius > outerRadius) {
			throw new IllegalArgumentException();
		}
		this.outerRadius = outerRadius;
		this.innerRadius = innerRadius;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		double r = new Vector3D(x, y, z).getAbsolute();
		return innerRadius <= r && r <= outerRadius;
	}

}
