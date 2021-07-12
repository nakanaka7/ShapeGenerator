package tokyo.nakanaka.math.boundRegion3D;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BlockRegion3D;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.math.region3D.Region3Ds;

public class SphereBoundRegion {
	private Region3D region;
	private Vector3D center;
	private double radius;
	
	public SphereBoundRegion(Region3D region, Vector3D center, double radius) {
		this.region = region;
		this.center = center;
		this.radius = radius;
	}
	
	public Region3D getRegion3D() {
		return this.region;
	}
	
	@Override
	public SphereBoundRegion clone() {
		return new SphereBoundRegion(this.region, this.center, this.radius);
	}
	
	public void shift(Vector3D displacement) {
		this.region = Region3Ds.shift(this.region, displacement);
	}
	
	public BlockRegion3D getBlockRegion3D() {
		double cx = this.center.getX();
		double cy = this.center.getY();
		double cz = this.center.getZ();
		return new BlockRegion3D(this.region, cx + radius, cy + radius, cz + radius, cx - radius, cy - radius, cz - radius);
	}
	
}
