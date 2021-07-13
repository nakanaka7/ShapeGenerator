package tokyo.nakanaka.math.region3D;

public class MaxZRegion3D implements Region3D {
	private double maxZ;

	public MaxZRegion3D(double maxZ) {
		this.maxZ = maxZ;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		return z <= this.maxZ;
	}
	
}
