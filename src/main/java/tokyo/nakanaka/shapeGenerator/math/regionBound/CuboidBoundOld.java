package tokyo.nakanaka.shapeGenerator.math.regionBound;

import static tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.MaxMinCalculator.max;
import static tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.MaxMinCalculator.min;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;
@Deprecated
public class CuboidBoundOld implements RegionBound {
	private double upperBoundX;
	private double upperBoundY;
	private double upperBoundZ;
	private double lowerBoundX;
	private double lowerBoundY;
	private double lowerBoundZ;

	public CuboidBoundOld(double upperBoundX, double upperBoundY, double upperBoundZ, double lowerBoundX,
			double lowerBoundY, double lowerBoundZ) {
		this.upperBoundX = upperBoundX;
		this.upperBoundY = upperBoundY;
		this.upperBoundZ = upperBoundZ;
		this.lowerBoundX = lowerBoundX;
		this.lowerBoundY = lowerBoundY;
		this.lowerBoundZ = lowerBoundZ;
	}
	
	@Override
	public double upperBoundX() {
		return upperBoundX;
	}
	
	@Override
	public double upperBoundY() {
		return upperBoundY;
	}
	
	@Override
	public double upperBoundZ() {
		return upperBoundZ;
	}
	
	@Override
	public double lowerBoundX() {
		return lowerBoundX;
	}
	
	@Override
	public double lowerBoundY() {
		return lowerBoundY;
	}
	
	@Override
	public double lowerBoundZ() {
		return lowerBoundZ;
	}

	@Override
	public CuboidBound createShifted(Vector3D displacement) {
		double ubx = this.upperBoundX + displacement.getX();
		double uby = this.upperBoundY + displacement.getY();
		double ubz = this.upperBoundZ + displacement.getZ();
		double lbx = this.lowerBoundX + displacement.getX();
		double lby = this.lowerBoundY + displacement.getY();
		double lbz = this.lowerBoundZ + displacement.getZ();
		return new CuboidBound(ubx, uby, ubz, lbx, lby, lbz);
	}
	
	@Override
	public RegionBound createScaled(Axis axis, double factor, Vector3D offset) {
		double ubx = this.upperBoundX;
		double uby = this.upperBoundY;
		double ubz = this.upperBoundZ;
		double lbx = this.lowerBoundX;
		double lby = this.lowerBoundY;
		double lbz = this.lowerBoundZ;
		switch(axis) {
		case X -> {
			ubx = factor * (ubx - offset.getX()) + offset.getX();
			lbx = factor * (lbx - offset.getX()) + offset.getX();
		}
		case Y -> {
			uby = factor * (uby - offset.getY()) + offset.getY();
			lby = factor * (lby - offset.getY()) + offset.getY();	
		}
		case Z -> {
			ubz = factor * (ubz - offset.getZ()) + offset.getZ();
			lbz = factor * (lbz - offset.getZ()) + offset.getZ();
		}
		}
		return new CuboidBound(ubx, uby, ubz, lbx, lby, lbz);
	}
	
	@Override
	public RegionBound createMirrored(Axis axis, Vector3D offset) {
		double ubx = this.upperBoundX;
		double uby = this.upperBoundY;
		double ubz = this.upperBoundZ;
		double lbx = this.lowerBoundX;
		double lby = this.lowerBoundY;
		double lbz = this.lowerBoundZ;
		switch(axis) {
		case X -> {
			double u = ubx;
			double l = lbx;
			lbx = - (u - offset.getX()) + offset.getX();
			ubx = - (l - offset.getX()) + offset.getX();
		}
		case Y -> {
			double u = uby;
			double l = lby;
			lby = - (u - offset.getY()) + offset.getY();
			uby = - (l - offset.getY()) + offset.getY();
		}
		case Z -> {
			double u = ubz;
			double l = lbz;
			lbz = - (u - offset.getZ()) + offset.getZ();
			ubz = - (l - offset.getZ()) + offset.getZ();
		}
		}
		return new CuboidBound(ubx, uby, ubz, lbx, lby, lbz);
	}
		
	@Override
	public RegionBound createRotated(Axis axis, double degree, Vector3D offset) {
		double cx = (this.upperBoundX + this.lowerBoundX) / 2.0;
		double cy = (this.upperBoundY + this.lowerBoundY) / 2.0;
		double cz = (this.upperBoundZ + this.lowerBoundZ) / 2.0;
		Vector3D center = new Vector3D(cx, cy, cz);
		Vector3D maxPos = new Vector3D(this.upperBoundX, this.upperBoundY, this.upperBoundZ);
		double radius = maxPos.negate(center).getAbsolute();
		SphereBound spbound = new SphereBound(center, radius);
		return spbound.createRotated(axis, degree, offset);
	}
	
	@Deprecated
	public CuboidBound createTransformedRegion(LinearTransformation trans, Vector3D offset) {
		Vector3D pos1 = new Vector3D(this.upperBoundX, this.upperBoundY, this.upperBoundZ);
		Vector3D pos2 = new Vector3D(this.upperBoundX, this.upperBoundY, this.lowerBoundZ);
		Vector3D pos3 = new Vector3D(this.upperBoundX, this.lowerBoundY, this.upperBoundZ);
		Vector3D pos4 = new Vector3D(this.upperBoundX, this.lowerBoundY, this.lowerBoundZ);
		Vector3D pos5 = new Vector3D(this.lowerBoundX, this.upperBoundY, this.upperBoundZ);
		Vector3D pos6 = new Vector3D(this.lowerBoundX, this.upperBoundY, this.lowerBoundZ);
		Vector3D pos7 = new Vector3D(this.lowerBoundX, this.lowerBoundY, this.upperBoundZ);
		Vector3D pos8 = new Vector3D(this.lowerBoundX, this.lowerBoundY, this.lowerBoundZ);
		Vector3D q1 = trans.apply(pos1.negate(offset)).add(offset);
		Vector3D q2 = trans.apply(pos2.negate(offset)).add(offset);
		Vector3D q3 = trans.apply(pos3.negate(offset)).add(offset);
		Vector3D q4 = trans.apply(pos4.negate(offset)).add(offset);
		Vector3D q5 = trans.apply(pos5.negate(offset)).add(offset);
		Vector3D q6 = trans.apply(pos6.negate(offset)).add(offset);
		Vector3D q7 = trans.apply(pos7.negate(offset)).add(offset);
		Vector3D q8 = trans.apply(pos8.negate(offset)).add(offset);
		double ubx = max(q1.getX(), q2.getX(), q3.getX(), q4.getX(), q5.getX(), q6.getX(), q7.getX(), q8.getX());
		double uby = max(q1.getY(), q2.getY(), q3.getY(), q4.getY(), q5.getY(), q6.getY(), q7.getY(), q8.getY());
		double ubz = max(q1.getZ(), q2.getZ(), q3.getZ(), q4.getZ(), q5.getZ(), q6.getZ(), q7.getZ(), q8.getZ());
		double lbx = min(q1.getX(), q2.getX(), q3.getX(), q4.getX(), q5.getX(), q6.getX(), q7.getX(), q8.getX());
		double lby = min(q1.getY(), q2.getY(), q3.getY(), q4.getY(), q5.getY(), q6.getY(), q7.getY(), q8.getY());
		double lbz = min(q1.getZ(), q2.getZ(), q3.getZ(), q4.getZ(), q5.getZ(), q6.getZ(), q7.getZ(), q8.getZ());
		return new CuboidBound(ubx, uby, ubz, lbx, lby, lbz);
	}
	
}
