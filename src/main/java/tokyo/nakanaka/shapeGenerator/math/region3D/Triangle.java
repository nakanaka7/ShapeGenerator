package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Matrix3x3;
import tokyo.nakanaka.math.Vector3D;

/**
 * Represents a triangle created by 3 points and thickness
 */
@PublicAPI
public class Triangle implements Region3D {
	private Vector3D pos1;
	private TriangleElement element;
	
	/**
	 * @param x1 the x coordinate of the first position
	 * @param y1 the y coordinate of the first position
	 * @param z1 the z coordinate of the first position
	 * @param x2 the x coordinate of the second position
	 * @param y2 the y coordinate of the second position
	 * @param z2 the z coordinate of the second position
	 * @param x3 the z coordinate of the third position
	 * @param y3 the y coordinate of the third position
	 * @param z3 the z coordinate of the third position
	 * @param thickness the thickness of the triangle
	 */
	public Triangle(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double thickness) {
		if(thickness < 0) {
			throw new IllegalArgumentException();
		}
		this.pos1 = new Vector3D(x1, y1, z1);
		Vector3D vec1 = new Vector3D(x2 - x1, y2 - y1, z2 - z1);
		Vector3D vec2 = new Vector3D(x3 - x1, y3 - y1, z3 - z1);
		this.element = new TriangleElement(vec1, vec2, thickness);
	}
	
	@Override
	public boolean contains(double x, double y, double z) {
		double sx = x - pos1.getX();
		double sy = y - pos1.getY();
		double sz = z - pos1.getZ();
		return this.element.contains(sx, sy, sz);
	}
	
	private class TriangleElement implements Region3D {
		private Vector3D vec1;
		private Vector3D vec2;
		private double thickness;
		//perpendicular unit vector to vec1 and vec2
		private Vector3D vec3;
		//trans * (c1, c2, c3) = (x, y, z) in the contains method
		//c1, c2, c3 are coordinates of (x, y, z) in the vec1-vec2-vec3 system
		private LinearTransformation trans;
		
		public TriangleElement(Vector3D vec1, Vector3D vec2, double thickness) {
			if(vec1.getAbsolute() == 0 || vec2.getAbsolute() == 0) {
				throw new IllegalArgumentException();
			}
			this.vec1 = vec1;
			this.vec2 = vec2;
			this.thickness = thickness;
			Vector3D vec3a = vec1.crossProduct(vec2);
			double length = vec3a.getAbsolute();
			if(length == 0) {
				throw new IllegalArgumentException();
			}
			this.vec3 = vec3a.divide(length);
		}
		
		public boolean contains(double x, double y, double z) {
			if(this.trans == null) {
				Matrix3x3 matrix = new Matrix3x3(vec1.getX(), vec2.getX(), vec3.getX(),
						vec1.getY(), vec2.getY(), vec3.getY(),
						vec1.getZ(), vec2.getZ(), vec3.getZ());
				this.trans = new LinearTransformation(matrix);
			}
			Vector3D c = this.trans.getInverse().apply(new Vector3D(x, y, z));
			double c1 = c.getX();
			double c2 = c.getY();
			double c3 = c.getZ();
			return 0 <= c1 && 0 <= c2 && 0 <= c1 + c2 && c1 + c2 <= 1 && Math.abs(c3) <= thickness / 2;
		}
		
	}

}
