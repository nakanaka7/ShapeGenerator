package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.math.Vector3D;
/**
 * Represents sphere which center is (0, 0, 0)
 */
@PublicAPI
public class Sphere implements Region3D{
	private double r;
	
	/**
	 * @param r a radius of the sphere
	 */	
	public Sphere(double r) {
		if(r < 0) {
			throw new IllegalArgumentException();
		}
		this.r = r;
	}
	
	@Override
	public boolean contains(double x, double y, double z) {
		double distance = new Vector3D(x, y, z).getAbsolute();
		return distance <= this.r;
	}


}
