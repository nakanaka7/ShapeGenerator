package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.math.Vector2D;

/**
 * Represents a cone which base is on x-y plane, which base center is the space origin,
 * and which extends to positive z.
 */
@PublicAPI
public class HollowCone implements Region3D {
	private double outerRadius;
	private double innerRadius;
	private double height;
	
	/**
	 * @param outerRadius an outer radius
	 * @param innerRadius an inner radius
	 * @param height a height
	 */
	public HollowCone(double outerRadius, double innerRadius, double height) {
		this.outerRadius = outerRadius;
		this.innerRadius = innerRadius;
		this.height = height;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		if(z < 0 || this.height < z) {
			return false;
		}
		double l = new Vector2D(x, y).getAbsolute();
		return this.innerRadius - z * this.outerRadius / this.height <= l 
				&& l <= this.outerRadius * (1 - z / this.height);
	}

}
