package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PublicAPI;

/**
 * Represents diamond which center is (0, 0, 0)
 */
@PublicAPI
public class Diamond implements Region3D {
	private double width;
	private double height;
	private double length;
	
	/**
	 * @param width the range along x axis of the diamond
	 * @param height the range along y axis of the diamond
	 * @param length the range along z axis of the diamond
	 */
	public Diamond(double width, double height, double length) {
		this.width = width;
		this.height = height;
		this.length = length;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		double sx = 2 * x / width;
		double sy = 2 * y / height;
		double sz = 2 * z / length;
		return Math.abs(sx) + Math.abs(sy) + Math.abs(sz) <= 1;
	}

}
