package tokyo.nakanaka.math.region2D;

import tokyo.nakanaka.math.PolarVector2D;
import tokyo.nakanaka.math.Vector2D;

/**
 * Represents RegularPolygon on x-y coordinate. The vertexes are on the circular line
 * which radius is 1 and the origin is (0,0). (1,0) has always the vertex, and the other vetexes'  
 * will be fixed.
 */
public class RegularPolygon implements Region2D {
	private int VertexNumber;
	
	public RegularPolygon(int VertexNumber) {
		this.VertexNumber = VertexNumber;
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
		if(d > 1) {
			return false;
		}
		arg = arg % (2 * Math.PI / this.VertexNumber);
		arg = arg - (Math.PI / this.VertexNumber);
		return d * Math.cos(arg) <= Math.cos(Math.PI / this.VertexNumber);
	}
	
}
