package tokyo.nakanaka.shapeGenerator.math.region2D;

import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.math.PolarVector2D;
import tokyo.nakanaka.math.Vector2D;

/**
 * Represents a regular polygon on x-y coordinate. All the points in the boundary is included in this region.
 * The center is (0,0). The polygon has its one point on positive x axis,
 * which is the necessary and sufficient information to decide other vertexes.
 */
@PublicAPI
public class RegularPolygon implements Region2D {
	private double radius;
	private int vertexNumber;
	
	/**
	 * @param radius the radius of the regular polygon
	 * @param vertexNumber the vertex numbers
	 */
	public RegularPolygon(double radius, int vertexNumber) {
		if(radius < 0 || vertexNumber < 3) {
			throw new IllegalArgumentException();
		}
		this.radius = radius;
		this.vertexNumber = vertexNumber;
	}

	@Override
	public boolean contains(double x, double y) {
		if(y < 0) {
			y = - y;
		}
		Vector2D pos = new Vector2D(x, y);
		PolarVector2D polar = PolarVector2D.valueOf(pos);
		double d = polar.getRadius();
		double arg = polar.getArgument();
		if(d > this.radius) {
			return false;
		}
		arg = arg % (2 * Math.PI / this.vertexNumber);
		arg = arg - (Math.PI / this.vertexNumber);
		return d * Math.cos(arg) <= this.radius * Math.cos(Math.PI / this.vertexNumber);
	}
	
}
