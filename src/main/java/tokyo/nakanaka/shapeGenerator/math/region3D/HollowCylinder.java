package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.math.Vector2D;

/**
 * Represents hollow cylinder which base is on x-y plane, which base center is the space origin,
 * and which axis is z-axis.
 */
@PublicAPI
public class HollowCylinder implements Region3D {
	private double outerRadius;
	private double innerRadius;
	private double height;
	
	/**
	 * @param outerRadius outer radius
	 * @param innerRadius inner radius
	 * @param height a height of the cylinder
	 * @throws innerRadius is larger than outerRadius
	 */
	public HollowCylinder(double outerRadius, double innerRadius, double height) {
		if(innerRadius > outerRadius || height < 0) {
			throw new IllegalArgumentException();
		}
		this.outerRadius = outerRadius;
		this.innerRadius = innerRadius;
		this.height = height;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		if(z < 0 || this.height < z) {
			return false;
		}
		double radius = new Vector2D(x, y).getAbsolute();
		return this.innerRadius <= radius && radius <= this.outerRadius;
	}
	
}
