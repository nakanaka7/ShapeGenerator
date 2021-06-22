package tokyo.nakanaka.math.region3D;

import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;

public class BoundRegion3D {
	private Region3D region;
	private double upperBoundX;
	private double upperBoundY;
	private double upperBoundZ;
	private double lowerBoundX;
	private double lowerBoundY;
	private double lowerBoundZ;

	public BoundRegion3D(Region3D region, double upperBoundX, double upperBoundY, double upperBoundZ, double lowerBoundX,
			double lowerBoundY, double lowerBoundZ) {
		this.region = region;
		this.upperBoundX = upperBoundX;
		this.upperBoundY = upperBoundY;
		this.upperBoundZ = upperBoundZ;
		this.lowerBoundX = lowerBoundX;
		this.lowerBoundY = lowerBoundY;
		this.lowerBoundZ = lowerBoundZ;
	}

	public BoundRegion3D getShiftedRegion(Vector3D displacement) {
		Region3D region = Region3Ds.shift(this.region, displacement);
		double ubx = this.upperBoundX + displacement.getX();
		double uby = this.upperBoundY + displacement.getY();
		double ubz = this.upperBoundZ + displacement.getZ();
		double lbx = this.lowerBoundX + displacement.getX();
		double lby = this.lowerBoundY + displacement.getY();
		double lbz = this.lowerBoundZ + displacement.getZ();
		return new BoundRegion3D(region, ubx, uby, ubz, lbx, lby, lbz);
	}
	
	public BoundRegion3D getTransformedRegion(LinearTransformation trans, Vector3D offset) {
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
		Vector3D q1 = pos1.negate(offset);
		Vector3D q2 = pos2.negate(offset);
		Vector3D q3 = pos3.negate(offset);
		Vector3D q4 = pos4.negate(offset);
		Vector3D q5 = pos5.negate(offset);
		Vector3D q6 = pos6.negate(offset);
		Vector3D q7 = pos7.negate(offset);
		Vector3D q8 = pos8.negate(offset);
		double r1 = q1.getAbsolute();
		double r2 = q2.getAbsolute();
		double r3 = q3.getAbsolute();
		double r4 = q4.getAbsolute();
		double r5 = q5.getAbsolute();
		double r6 = q6.getAbsolute();
		double r7 = q7.getAbsolute();
		double r8 = q8.getAbsolute();
		double radius = Math.max(r1, Math.max(r2, Math.max(r3, Math.max(r4, Math.max(r5, Math.max(r6, Math.max(r7, r8)))))));
		double ubx = offset.getX() + radius;
		double uby = offset.getY() + radius;
		double ubz = offset.getZ() + radius;
		double lbx = offset.getX() - radius;
		double lby = offset.getY() - radius;
		double lbz = offset.getZ() - radius;
		return new BoundRegion3D(newRegion, ubx, uby, ubz, lbx, lby, lbz);
	}
	
	public BlockRegion3D getBlockRegion3D() {
		int ubx = (int)Math.floor(this.upperBoundX);
		int uby = (int)Math.floor(this.upperBoundY);
		int ubz = (int)Math.floor(this.upperBoundZ);
		int lbx = - (int)Math.floor(- this.lowerBoundX);
		int lby = - (int)Math.floor(- this.lowerBoundY);
		int lbz = - (int)Math.floor(- this.lowerBoundZ);
		return new BlockRegion3D(this.region, ubx, uby, ubz, lbx, lby, lbz);
	}

}
