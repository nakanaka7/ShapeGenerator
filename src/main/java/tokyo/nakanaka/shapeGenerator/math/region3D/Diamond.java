package tokyo.nakanaka.shapeGenerator.math.region3D;

public class Diamond implements Region3D {
	private double radiusX;
	private double radiusY;
	private double radiusZ;
	
	public Diamond(double radiusX, double radiusY, double radiusZ) {
		this.radiusX = radiusX;
		this.radiusY = radiusY;
		this.radiusZ = radiusZ;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		double sx = x / radiusX;
		double sy = y / radiusY;
		double sz = z / radiusZ;
		return Math.abs(sx) + Math.abs(sy) + Math.abs(sz) <= 1;
	}

}
