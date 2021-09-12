package tokyo.nakanaka.shapeGenerator.math.boundRegion3D;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.BlockRegion3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3Ds;

@PublicAPI
public class SphereBoundRegion implements BoundRegion3D {
	private Region3D region;
	private Vector3D center;
	private double radius;
	
	@PublicAPI
	public SphereBoundRegion(Region3D region, Vector3D center, double radius) {
		this.region = region;
		this.center = center;
		this.radius = radius;
	}
	
	@PrivateAPI
	@Override
	public Region3D getRegion3D() {
		return this.region;
	}
	
	@PrivateAPI
	@Override
	public double getUpperBoundX() {
		return this.center.getX() + this.radius;
	}
	
	@PrivateAPI
	@Override
	public double getUpperBoundY() {
		return this.center.getY() + this.radius;
	}
	
	@PrivateAPI
	@Override
	public double getUpperBoundZ() {
		return this.center.getZ() + this.radius;
	}
	
	@PrivateAPI
	@Override
	public double getLowerBoundX() {
		return this.center.getX() - this.radius;
	}
	
	@PrivateAPI
	@Override
	public double getLowerBoundY() {
		return this.center.getY() - this.radius;
	}
	
	@PrivateAPI
	@Override
	public double getLowerBoundZ() {
		return this.center.getZ() - this.radius;
	}
	
	@PrivateAPI
	@Override
	public BoundRegion3D createShiftedRegion(Vector3D displacement) {
		Region3D newRegion = Region3Ds.shift(this.region, displacement);
		Vector3D newCenter = this.center.add(displacement);
		return new SphereBoundRegion(newRegion, newCenter, this.radius);
	}

	@PrivateAPI
	@Override
	public BoundRegion3D createScaledRegion(Axis axis, double factor, Vector3D offset) {
		double cx = this.center.getX();
		double cy = this.center.getY();
		double cz = this.center.getZ();
		CuboidBoundRegion cubound = new CuboidBoundRegion(region, 
				cx + radius, cy + radius, cz + radius,
				cx - radius, cy - radius, cz - radius);
		return cubound.createScaledRegion(axis, factor, offset);
	}
	
	@PrivateAPI
	@Override
	public BoundRegion3D createMirroredRegion(Axis axis, Vector3D offset) {
		LinearTransformation trans = LinearTransformation.ofMirror(axis);
		Region3D newRegion = Region3Ds.linearTransform(this.region, trans, offset);
		Vector3D newCenter = trans.apply(this.center.negate(offset)).add(offset);
		return new SphereBoundRegion(newRegion, newCenter, this.radius);
	}
	
	@PrivateAPI
	@Override
	public BoundRegion3D createRotatedRegion(Axis axis, double degree, Vector3D offset) {
		LinearTransformation trans = LinearTransformation.ofRotation(axis, degree);
		Region3D newRegion = Region3Ds.linearTransform(this.region, trans, offset);
		Vector3D newCenter = trans.apply(this.center.negate(offset)).add(offset);
		return new SphereBoundRegion(newRegion, newCenter, this.radius);
	}
	
	@PrivateAPI
	@Override
	public BlockRegion3D toBlockRegion3D() {
		double cx = this.center.getX();
		double cy = this.center.getY();
		double cz = this.center.getZ();
		return new BlockRegion3D(this.region, cx + radius, cy + radius, cz + radius, cx - radius, cy - radius, cz - radius);
	}

}
