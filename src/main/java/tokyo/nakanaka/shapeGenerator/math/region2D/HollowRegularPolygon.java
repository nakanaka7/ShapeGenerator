package tokyo.nakanaka.shapeGenerator.math.region2D;

import tokyo.nakanaka.annotation.PublicAPI;

/**
 * Represents a hollow regular polygon on x-y coordinate. The center is (0,0). Either outer or inner polygon 
 * has its one point on positive x axis, which is the necessary and sufficient information to decide other vertexes.
 */
@PublicAPI
public class HollowRegularPolygon implements Region2D {
	private double outerRadius;
	private double innerRadius;
	private int vertexNumber;
	
	/**
	 * @param outerRadius an outer radius
	 * @param innerRadius an inner radius 
	 * @param vertexNumber the vertex numbers
	 * @throws if outerRadius is smaller than 0, innerRadius is smaller than 0, innerRadius is larger than outerRadius,
	 * or vertexNumber is smaller than 3
	 */
	public HollowRegularPolygon(double outerRadius, double innerRadius, int vertexNumber) {
		if(outerRadius < 0 || innerRadius < 0 || innerRadius > outerRadius || vertexNumber < 3) {
			throw new IllegalArgumentException();
		}
		this.outerRadius = outerRadius;
		this.innerRadius = innerRadius;
		this.vertexNumber = vertexNumber;
	}
	
	@Override
	public boolean contains(double x, double y) {
		RegularPolygon outerRy = new RegularPolygon(this.outerRadius, this.vertexNumber);
		RegularPolygon innerRy = new RegularPolygon(this.innerRadius, this.vertexNumber);
		return outerRy.contains(x, y) && !innerRy.contains(x, y);
	}

}
