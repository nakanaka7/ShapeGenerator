package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.World;
import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Cuboid;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;

/**
 * Represents a selection
 */
@PublicAPI
public class Selection {
	private World world;
	private Vector3D offset;
	private Region3D region;
	private BoundRegion3D boundReg;
	
	@PrivateAPI
	public Selection(World world, BoundRegion3D boundReg, Vector3D offset) {
		this.world = world;
		this.region = boundReg.getRegion3D();
		this.boundReg = boundReg;
		this.offset = offset;
	}
	
	/**
	 * Constructs a selection, which bound is a cuboid(It is not the region of the selection).
	 * The cuboid bound must include all the points of the region. 
	 * @param world a world
	 * @param offset an offset
	 * @param region a region 
	 * @param bound a cuboid which surround the region
	 */
	@PublicAPI
	public Selection(World world, Vector3D offset, Region3D region, Cuboid bound) {
		this.world = world;
		this.offset = offset;
		this.region = region;
		this.boundReg = new CuboidBoundRegion(region, bound.maxX(), bound.maxX(), bound.maxZ(), bound.minX(), bound.minY(), bound.minZ());
	}
	
	/**
	 * Returns the world of this selection
	 * @return the world of this selection
	 */
	@PrivateAPI
	public World world() {
		return world;
	}
	
	/**
	 * Returns a block region made from this selection
	 * @return block region made from this selection
	 */
	@PrivateAPI
	public BlockRegion3D createBlockRegion3D() {
		double ubx = this.boundReg.upperBoundX();
		double uby = this.boundReg.upperBoundY();
		double ubz = this.boundReg.upperBoundZ();
		double lbx = this.boundReg.lowerBoundX();
		double lby = this.boundReg.lowerBoundY();
		double lbz = this.boundReg.lowerBoundZ();
		return new BlockRegion3D(this.region, ubx, uby, ubz, lbx, lby, lbz);
	}
	
	@PrivateAPI
	public BoundRegion3D getBoundRegion3D() {
		return boundReg;
	}
	
	@PrivateAPI
	public Vector3D getOffset() {
		return offset;
	}
	
	@PrivateAPI
	public Selection createShifted(Vector3D displacement) {
		BoundRegion3D region = this.boundReg.createShifted(displacement);
		Vector3D offset = this.offset.add(displacement);
		return new Selection(world, region, offset);
	}
	
	@PrivateAPI
	public Selection createScaled(Axis axis, double factor) {
		BoundRegion3D newRegion = this.boundReg.createScaled(axis, factor, this.offset);
		return new Selection(this.world, newRegion, this.offset);
	}
	
	@PrivateAPI
	public Selection createMirroed(Axis axis) {
		BoundRegion3D newRegion = this.boundReg.createMirrored(axis, this.offset);
		return new Selection(this.world, newRegion, this.offset);
	}
	
	@PrivateAPI
	public Selection createRotated(Axis axis, double degree) {
		BoundRegion3D newRegion = this.boundReg.createRotated(axis, degree, this.offset);
		return new Selection(this.world, newRegion, this.offset);
	}
	
}
