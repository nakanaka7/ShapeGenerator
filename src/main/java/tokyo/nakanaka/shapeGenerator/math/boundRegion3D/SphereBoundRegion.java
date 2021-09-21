package tokyo.nakanaka.shapeGenerator.math.boundRegion3D;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;

public class SphereBoundRegion implements BoundRegion3D {
	private Vector3D center;
	private double radius;
	
	@Deprecated
	public SphereBoundRegion(Region3D region, Vector3D center, double radius) {
		this.center = center;
		this.radius = radius;
	}
	
	public SphereBoundRegion(Vector3D center, double radius) {
		this.center = center;
		this.radius = radius;
	}
	
	public SphereBoundRegion(double radius) {
		this.center = Vector3D.ZERO;
		this.radius = radius;
	}
	
	@Override
	public double upperBoundX() {
		return this.center.getX() + this.radius;
	}
	
	@Override
	public double upperBoundY() {
		return this.center.getY() + this.radius;
	}
	
	@Override
	public double upperBoundZ() {
		return this.center.getZ() + this.radius;
	}
	
	@Override
	public double lowerBoundX() {
		return this.center.getX() - this.radius;
	}
	
	@Override
	public double lowerBoundY() {
		return this.center.getY() - this.radius;
	}
	
	@Override
	public double lowerBoundZ() {
		return this.center.getZ() - this.radius;
	}
	
	@Override
	public BoundRegion3D shift(Vector3D dis) {
		return new SphereBoundRegion(this.center.add(dis), this.radius);
	}

	@Override
	public BoundRegion3D scale(Axis axis, double factor, Vector3D offset) {
		double cx = this.center.getX();
		double cy = this.center.getY();
		double cz = this.center.getZ();
		CuboidBoundRegion cubound = new CuboidBoundRegion( 
				cx + radius, cy + radius, cz + radius,
				cx - radius, cy - radius, cz - radius);
		return cubound.scale(axis, factor, offset);
	}
	
	@Override
	public BoundRegion3D mirror(Axis axis, Vector3D offset) {
		LinearTransformation trans = LinearTransformation.ofMirror(axis);
		Vector3D newCenter = trans.apply(this.center.negate(offset)).add(offset);
		return new SphereBoundRegion(newCenter, this.radius);
	}
	
	@Override
	public BoundRegion3D rotate(Axis axis, double degree, Vector3D offset) {
		LinearTransformation trans = LinearTransformation.ofRotation(axis, degree);
		Vector3D newCenter = trans.apply(this.center.negate(offset)).add(offset);
		return new SphereBoundRegion(newCenter, this.radius);
	}
	
}
