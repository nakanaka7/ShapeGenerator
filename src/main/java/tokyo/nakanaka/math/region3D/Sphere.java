package tokyo.nakanaka.math.region3D;

import tokyo.nakanaka.math.Vector3D;

public class Sphere implements Region3D{
	private double r;
	/**
	 * Represents sphere which center is (0, 0, 0), and its radius is r
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
