package tokyo.nakanaka.shapeGenerator.math.region3D;

public class MinYRegion3D implements Region3D {
	private double minY;

	public MinYRegion3D(double minY) {
		this.minY = minY;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		return y >= this.minY;
	}
	
}
