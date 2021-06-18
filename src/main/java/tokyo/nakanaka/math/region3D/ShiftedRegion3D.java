package tokyo.nakanaka.math.region3D;

import tokyo.nakanaka.math.Vector3D;

public class ShiftedRegion3D implements Region3D{
	private Region3D original;
	private Vector3D displacement;
	
	public ShiftedRegion3D(Region3D original, Vector3D displacement) {
		this.original = original;
		this.displacement = displacement;
	}
	
	public ShiftedRegion3D(Region3D original, double dx, double dy, double dz) {
		this(original, new Vector3D(dx, dy, dz));
	}

	@Override
	public boolean contains(double x, double y, double z) {
		Vector3D pos = new Vector3D(x, y, z).negate(this.displacement);
		return this.original.contains(pos.getX(), pos.getY(), pos.getZ());
	}
}
