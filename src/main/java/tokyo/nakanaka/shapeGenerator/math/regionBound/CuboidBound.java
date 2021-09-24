package tokyo.nakanaka.shapeGenerator.math.regionBound;

import static tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.MaxMinCalculator.max;
import static tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.MaxMinCalculator.min;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Cuboid;

public class CuboidBound implements RegionBound {
	private Cuboid cuboid;
	private LinearTransformation trans;
	private Vector3D dis;
	private Double ubx;
	private Double uby;
	private Double ubz;
	private Double lbx;
	private Double lby;
	private Double lbz;
	
	/**
	 * Constructs the region bound by the following procedure. First, make a cuboid bound. 
	 * Next, apply a linear transformation to the cuboid. Finally, displace it parallelly.
	 * @param cuboid a cuboid for the first bound.
	 * @param trans a linear transformation which is applied to the first cuboid. The origin of 3D space is unchanged.
	 * @param dis a displacement for the last procedure.
	 */
	public CuboidBound(Cuboid cuboid, LinearTransformation trans, Vector3D dis) {
		this.cuboid = cuboid;
		this.trans = trans;
		this.dis = dis;
	}

	public CuboidBound(Cuboid cuboid) {
		this(cuboid, LinearTransformation.IDENTITY, Vector3D.ZERO);
	}
	
	public CuboidBound(double upperBoundX, double upperBoundY, double upperBoundZ, double lowerBoundX,
			double lowerBoundY, double lowerBoundZ) {
		this(new Cuboid(upperBoundX, upperBoundY, upperBoundZ, lowerBoundX, lowerBoundY, lowerBoundZ));
	}
	
	private void calcBounds() {
		Vector3D pos1 = new Vector3D(cuboid.maxX(), cuboid.maxY(), cuboid.maxZ());
		Vector3D pos2 = new Vector3D(cuboid.maxX(), cuboid.maxY(), cuboid.minZ());
		Vector3D pos3 = new Vector3D(cuboid.maxX(), cuboid.minY(), cuboid.maxZ());
		Vector3D pos4 = new Vector3D(cuboid.maxX(), cuboid.minY(), cuboid.minZ());
		Vector3D pos5 = new Vector3D(cuboid.minX(), cuboid.maxY(), cuboid.maxZ());
		Vector3D pos6 = new Vector3D(cuboid.minX(), cuboid.maxY(), cuboid.minZ());
		Vector3D pos7 = new Vector3D(cuboid.minX(), cuboid.minY(), cuboid.maxZ());
		Vector3D pos8 = new Vector3D(cuboid.minX(), cuboid.minY(), cuboid.minZ());
		Vector3D q1 = trans.apply(pos1).add(dis);
		Vector3D q2 = trans.apply(pos2).add(dis);
		Vector3D q3 = trans.apply(pos3).add(dis);
		Vector3D q4 = trans.apply(pos4).add(dis);
		Vector3D q5 = trans.apply(pos5).add(dis);
		Vector3D q6 = trans.apply(pos6).add(dis);
		Vector3D q7 = trans.apply(pos7).add(dis);
		Vector3D q8 = trans.apply(pos8).add(dis);
		ubx = max(q1.getX(), q2.getX(), q3.getX(), q4.getX(), q5.getX(), q6.getX(), q7.getX(), q8.getX());
		uby = max(q1.getY(), q2.getY(), q3.getY(), q4.getY(), q5.getY(), q6.getY(), q7.getY(), q8.getY());
		ubz = max(q1.getZ(), q2.getZ(), q3.getZ(), q4.getZ(), q5.getZ(), q6.getZ(), q7.getZ(), q8.getZ());
		lbx = min(q1.getX(), q2.getX(), q3.getX(), q4.getX(), q5.getX(), q6.getX(), q7.getX(), q8.getX());
		lby = min(q1.getY(), q2.getY(), q3.getY(), q4.getY(), q5.getY(), q6.getY(), q7.getY(), q8.getY());
		lbz = min(q1.getZ(), q2.getZ(), q3.getZ(), q4.getZ(), q5.getZ(), q6.getZ(), q7.getZ(), q8.getZ());
	}
	
	@Override
	public double upperBoundX() {
		if(this.ubx == null) {
			calcBounds();
		}
		return this.ubx;
	}

	@Override
	public double upperBoundY() {
		if(this.uby == null) {
			calcBounds();
		}
		return this.uby;
	}

	@Override
	public double upperBoundZ() {
		if(this.ubz == null) {
			calcBounds();
		}
		return this.ubz;
	}

	@Override
	public double lowerBoundX() {
		if(this.lbx == null) {
			calcBounds();
		}
		return this.lbx;
	}

	@Override
	public double lowerBoundY() {
		if(this.lby == null) {
			calcBounds();
		}
		return this.lby;
	}

	@Override
	public double lowerBoundZ() {
		if(this.lbz == null) {
			calcBounds();
		}
		return this.lbz;
	}

	@Override
	public RegionBound createShifted(Vector3D dis) {
		return new CuboidBound(this.cuboid, this.trans, this.dis.add(dis));
	}

	@Override
	public RegionBound createScaled(Axis axis, double factor, Vector3D offset) {
		LinearTransformation trans = LinearTransformation.ofScale(axis, factor);
		return this.createLinearTransformed(trans, offset);
	}

	@Override
	public RegionBound createMirrored(Axis axis, Vector3D offset) {
		LinearTransformation trans = LinearTransformation.ofMirror(axis);
		return this.createLinearTransformed(trans, offset);
	}

	@Override
	public RegionBound createRotated(Axis axis, double degree, Vector3D offset) {
		LinearTransformation trans = LinearTransformation.ofRotation(axis, degree);
		return this.createLinearTransformed(trans, offset);
	}
	
	private RegionBound createLinearTransformed(LinearTransformation trans, Vector3D offset) {
		LinearTransformation a = this.trans;
		Vector3D b = this.dis;
		b = b.negate(offset);
		a = trans.multipy(a);
		b = trans.apply(b);
		b = b.add(offset);
		return new CuboidBound(this.cuboid, a, b);
	}
	
}
