package tokyo.nakanaka.shapeGenerator.math.region2D;

import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.math.PolarVector2D;
import tokyo.nakanaka.math.Vector2D;

/**
 * Represents RegularPolygon on x-y coordinate. The vertexes are on the circular line
 * which radius is 1 and the origin is (0,0). (1,0) has always the vertex, and the other vetexes'  
 * will be fixed.
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
