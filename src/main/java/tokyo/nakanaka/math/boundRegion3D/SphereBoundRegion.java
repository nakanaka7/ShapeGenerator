package tokyo.nakanaka.math.boundRegion3D;

import tokyo.nakanaka.geometricProperty.Axis;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BlockRegion3D;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.math.region3D.Region3Ds;

public class SphereBoundRegion implements BoundRegion3D {
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
	
	public BoundRegion3D createShiftedRegion(Vector3D displacement) {
		Region3D newRegion = Region3Ds.shift(this.region, displacement);
		Vector3D newCenter = this.center.add(displacement);
		return new SphereBoundRegion(newRegion, newCenter, this.radius);
	}

	public BoundRegion3D createRotatedRegion(Vector3D offset, Axis axis, double degree) {
		return null;
	}
	
	
	public BlockRegion3D toBlockRegion3D() {
		double cx = this.center.getX();
		double cy = this.center.getY();
		double cz = this.center.getZ();
		return new BlockRegion3D(this.region, cx + radius, cy + radius, cz + radius, cx - radius, cy - radius, cz - radius);
	}

}
