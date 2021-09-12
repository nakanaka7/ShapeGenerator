package tokyo.nakanaka.shapeGenerator.math.region3D;

public class Diamond implements Region3D {
	private double width;
	private double height;
	private double length;
	
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
