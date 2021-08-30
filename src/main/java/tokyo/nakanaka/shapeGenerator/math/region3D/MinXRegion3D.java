package tokyo.nakanaka.shapeGenerator.math.region3D;

public class MinXRegion3D implements Region3D {
	private double minX;

	public MinXRegion3D(double minX) {
		this.minX = minX;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		return x >= this.minX;
	}

}
