package tokyo.nakanaka.math.region3D;

public class MaxXRegion3D implements Region3D {
	private double maxX;

	public MaxXRegion3D(double maxX) {
		this.maxX = maxX;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		return x <= this.maxX;
	}
	
}
