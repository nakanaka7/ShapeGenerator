package tokyo.nakanaka.math.region3D;

public class MinZRegion3D implements Region3D {
	private double minZ;

	public MinZRegion3D(double minZ) {
		this.minZ = minZ;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		return z >= this.minZ;
	}
	
}
