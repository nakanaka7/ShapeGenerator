package tokyo.nakanaka.math.region3D;

public class MaxYRegion3D implements Region3D {
	private double maxY;

	public MaxYRegion3D(double maxY) {
		this.maxY = maxY;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		return y <= this.maxY;
	}
	
}
