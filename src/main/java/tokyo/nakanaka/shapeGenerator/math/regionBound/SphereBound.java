package tokyo.nakanaka.shapeGenerator.math.regionBound;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;

public class SphereBound implements RegionBound {
	private Vector3D center;
	private double radius;
	
	public SphereBound(Vector3D center, double radius) {
		this.center = center;
		this.radius = radius;
	}
	
	public SphereBound(double radius) {
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
	public RegionBound createShifted(Vector3D dis) {
		return new SphereBound(this.center.add(dis), this.radius);
	}

	@Override
	public RegionBound createScaled(Axis axis, double factor, Vector3D offset) {
		double cx = this.center.getX();
		double cy = this.center.getY();
		double cz = this.center.getZ();
		CuboidBound cubound = new CuboidBound( 
				cx + radius, cy + radius, cz + radius,
				cx - radius, cy - radius, cz - radius);
		return cubound.createScaled(axis, factor, offset);
	}
	
	@Override
	public RegionBound createMirrored(Axis axis, Vector3D offset) {
		LinearTransformation trans = LinearTransformation.ofMirror(axis);
		Vector3D newCenter = trans.apply(this.center.negate(offset)).add(offset);
		return new SphereBound(newCenter, this.radius);
	}
	
	@Override
	public RegionBound createRotated(Axis axis, double degree, Vector3D offset) {
		LinearTransformation trans = LinearTransformation.ofRotation(axis, degree);
		Vector3D newCenter = trans.apply(this.center.negate(offset)).add(offset);
		return new SphereBound(newCenter, this.radius);
	}
	
}
