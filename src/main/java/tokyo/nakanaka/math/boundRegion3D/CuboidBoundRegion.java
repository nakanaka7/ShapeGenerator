package tokyo.nakanaka.math.boundRegion3D;

import static tokyo.nakanaka.MaxMinCalculator.*;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BlockRegion3D;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.math.region3D.Region3Ds;

public class CuboidBoundRegion implements BoundRegion3D {
	private Region3D region;
	private double upperBoundX;
	private double upperBoundY;
	private double upperBoundZ;
	private double lowerBoundX;
	private double lowerBoundY;
	private double lowerBoundZ;

	public CuboidBoundRegion(Region3D region, double upperBoundX, double upperBoundY, double upperBoundZ, double lowerBoundX,
			double lowerBoundY, double lowerBoundZ) {
		this.region = region;
		this.upperBoundX = upperBoundX;
		this.upperBoundY = upperBoundY;
		this.upperBoundZ = upperBoundZ;
		this.lowerBoundX = lowerBoundX;
		this.lowerBoundY = lowerBoundY;
		this.lowerBoundZ = lowerBoundZ;
	}
	
	@Override
	public Region3D getRegion3D() {
		return this.region;
	}
	
	@Override
	public double getUpperBoundX() {
		return upperBoundX;
	}
	
	@Override
	public double getUpperBoundY() {
		return upperBoundY;
	}
	
	@Override
	public double getUpperBoundZ() {
		return upperBoundZ;
	}
	
	@Override
	public double getLowerBoundX() {
		return lowerBoundX;
	}
	
	@Override
	public double getLowerBoundY() {
		return lowerBoundY;
	}
	
	@Override
	public double getLowerBoundZ() {
		return lowerBoundZ;
	}

	@Override
	public CuboidBoundRegion createShiftedRegion(Vector3D displacement) {
		Region3D region = Region3Ds.shift(this.region, displacement);
		double ubx = this.upperBoundX + displacement.getX();
		double uby = this.upperBoundY + displacement.getY();
		double ubz = this.upperBoundZ + displacement.getZ();
		double lbx = this.lowerBoundX + displacement.getX();
		double lby = this.lowerBoundY + displacement.getY();
		double lbz = this.lowerBoundZ + displacement.getZ();
		return new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
	}
	
	@Override
	public BoundRegion3D createScaledRegion(Axis axis, double factor, Vector3D offset) {
		LinearTransformation trans = LinearTransformation.ofScale(axis, factor);
		Region3D newRegion = Region3Ds.linearTransform(this.region, trans, offset);
		double ubx = this.upperBoundX;
		double uby = this.upperBoundY;
		double ubz = this.upperBoundZ;
		double lbx = this.lowerBoundX;
		double lby = this.lowerBoundY;
		double lbz = this.lowerBoundZ;
		switch(axis) {
		case X:
			ubx = factor * (ubx - offset.getX()) + offset.getX();
			lbx = factor * (lbx - offset.getX()) + offset.getX();
			break;
		case Y:
			uby = factor * (uby - offset.getY()) + offset.getY();
			lby = factor * (lby - offset.getY()) + offset.getY();	
			break;
		case Z:
			ubz = factor * (ubz - offset.getZ()) + offset.getZ();
			lbz = factor * (lbz - offset.getZ()) + offset.getZ();
			break;
		default:
			throw new UnsupportedOperationException();
		}
		return new CuboidBoundRegion(newRegion, ubx, uby, ubz, lbx, lby, lbz);
	}
	
	@Override
	public BoundRegion3D createMirroredRegion(Axis axis, Vector3D offset) {
		LinearTransformation trans = LinearTransformation.ofMirror(axis);
		Region3D newRegion = Region3Ds.linearTransform(this.region, trans, offset);
		double ubx = this.upperBoundX;
		double uby = this.upperBoundY;
		double ubz = this.upperBoundZ;
		double lbx = this.lowerBoundX;
		double lby = this.lowerBoundY;
		double lbz = this.lowerBoundZ;
		switch(axis) {
		case X:
			ubx = - (ubx - offset.getX()) + offset.getX();
			lbx = - (lbx - offset.getX()) + offset.getX();
			break;
		case Y:
			uby = - (uby - offset.getY()) + offset.getY();
			lby = - (lby - offset.getY()) + offset.getY();
			break;
		case Z:
			ubz = - (ubz - offset.getZ()) + offset.getZ();
			lbz = - (lbz - offset.getZ()) + offset.getZ();
			break;
		default:
			throw new UnsupportedOperationException();
		}
		return new CuboidBoundRegion(newRegion, ubx, uby, ubz, lbx, lby, lbz);
	}
	
	@Override
	public BoundRegion3D createRotatedRegion(Axis axis, double degree, Vector3D offset) {
		double cx = (this.upperBoundX + this.lowerBoundX) / 2.0;
		double cy = (this.upperBoundY + this.lowerBoundY) / 2.0;
		double cz = (this.upperBoundZ + this.lowerBoundZ) / 2.0;
		Vector3D center = new Vector3D(cx, cy, cz);
		Vector3D maxPos = new Vector3D(this.upperBoundX, this.upperBoundY, this.upperBoundZ);
		double radius = maxPos.negate(center).getAbsolute();
		SphereBoundRegion spbound = new SphereBoundRegion(this.region, center, radius);
		return spbound.createRotatedRegion(axis, degree, offset);
	}
	
	@Deprecated
	public CuboidBoundRegion createTransformedRegion(LinearTransformation trans, Vector3D offset) {
		Region3D newRegion = Region3Ds.shift(this.region, Vector3D.ORIGIN.negate(offset));
		newRegion = Region3Ds.linearTransform(newRegion, trans);
		newRegion = Region3Ds.shift(newRegion, offset);
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
		return new CuboidBoundRegion(newRegion, ubx, uby, ubz, lbx, lby, lbz);
	}
	
	@Override
	public BlockRegion3D toBlockRegion3D() {
		int ubx = (int)Math.floor(this.upperBoundX);
		int uby = (int)Math.floor(this.upperBoundY);
		int ubz = (int)Math.floor(this.upperBoundZ);
		int lbx = (int)Math.floor(this.lowerBoundX);
		int lby = (int)Math.floor(this.lowerBoundY);
		int lbz = (int)Math.floor(this.lowerBoundZ);
		return new BlockRegion3D(this.region, ubx, uby, ubz, lbx, lby, lbz);
	}

	@Deprecated
	public CuboidBoundRegion changeUpperBoundX(double maxX) {
		return new CuboidBoundRegion(this.region, maxX, this.upperBoundY, this.upperBoundZ,
				this.lowerBoundX, this.lowerBoundY, this.lowerBoundZ);
	}

}
