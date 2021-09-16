package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.shapeGenerator.math.region2D.HollowRegularPolygon;

/**
 * Represents a hollow prism which base is hollow regular polygon. The base is on x-y plane with its center the origin of the plane.
 * It extends to positive z. The x axis always has outer and inner vertexes of the base polygon, which is the necessary and sufficient information
 * to decide other vertexes.
 */
@PublicAPI
public class HollowRegularPrism implements Region3D {
	private HollowRegularPolygon hrp;
	private double height;
	
	/**
	 * @param outerRadius the outer radius of the base hollow regular prism
	 * @param innerRadius the inner radius of the base hollow regular prism
	 * @param vertexNum a vertex number, must larger than or equal to 3
	 * @param height the height of the cylinder
	 * @throws if outer radius is smaller than 0, inner radius is smaller than 0, inner radius is larger than outer radius,
	 * vertex number is smaller than 3, or height is smaller than zero
	 */
	public HollowRegularPrism(double outerRadius, double innerRadius, int vertexNum, double height) {
		this.hrp = new HollowRegularPolygon(outerRadius, innerRadius, vertexNum);
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
		return this.hrp.contains(x, y);
	}

}
