package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.World;
import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.*;
import tokyo.nakanaka.shapeGenerator.math.regionBound.CuboidBound;
import tokyo.nakanaka.shapeGenerator.math.regionBound.RegionBound;

/**
 * Represents a selection
 */
@PublicAPI
public class Selection {
	private World world;
	private Vector3D offset;
	private Region3D region;
	private RegionBound bound;
	
	@PrivateAPI
	public Selection(World world, Vector3D offset, Region3D region, RegionBound bound) {
		this.world = world;
		this.offset = offset;
		this.region = region;
		this.bound = bound;
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
		this.bound = new CuboidBound(bound.maxX(), bound.maxY(), bound.maxZ(), bound.minX(), bound.minY(), bound.minZ());
	}
	
	/**
	 * Returns the world of this selection
	 * @return the world of this selection
	 */
	@PrivateAPI
	public World world() {
		return world;
	}
	
	@PrivateAPI
	public Region3D region() {
		return region;
	}
	
	@PrivateAPI
	public Vector3D offset() {
		return offset;
	}
	
	/**
	 * Returns new selection with specified offset
	 * @param offset new offset
	 * @return new selection with specified offset
	 */
	@PrivateAPI
	public Selection withOffset(Vector3D offset) {
		return new Selection(this.world, offset, this.region, this.bound);
	}
	
	/**
	 * Returns a block region made from this selection
	 * @return block region made from this selection
	 */
	@PrivateAPI
	public BlockRegion3D createBlockRegion3D() {
		double ubx = this.bound.upperBoundX();
		double uby = this.bound.upperBoundY();
		double ubz = this.bound.upperBoundZ();
		double lbx = this.bound.lowerBoundX();
		double lby = this.bound.lowerBoundY();
		double lbz = this.bound.lowerBoundZ();
		return new BlockRegion3D(this.region, ubx, uby, ubz, lbx, lby, lbz);
	}
	
	@PrivateAPI
	public RegionBound regionBound() {
		return bound;
	}
	
	/**
	 * Create a selection which is shifted
	 * @param displacement the displacement of shift
	 * @return a selection which is shifted
	 */
	@PublicAPI
	public Selection createShifted(Vector3D displacement) {
		Vector3D newOffset = this.offset.add(displacement);
		Region3D newRegion = this.region.createShifted(displacement);
		RegionBound newBound = this.bound.createShifted(displacement);
		return new Selection(this.world, newOffset, newRegion, newBound);
	}
	
	/**
	 * Create a selection which is scaled
	 * @param axis the axis for scaling
	 * @param factor a scale factor
	 * @return a selection which is scaled
	 * @throws IllegalArgumentException if factor is zero
	 */
	@PublicAPI
	public Selection createScaled(Axis axis, double factor) {
		Region3D newRegion = this.region.createScaled(axis, factor, this.offset);
		RegionBound newBound = this.bound.createScaled(axis, factor, this.offset);
		return new Selection(this.world, this.offset, newRegion, newBound);
	}
	
	/**
	 * Create a selection which is mirrored
	 * @param axis the axis for mirroring
	 * @return a selection which is mirrored
	 */
	@PublicAPI
	public Selection createMirroed(Axis axis) {
		Region3D newRegion = this.region.createMirrored(axis, this.offset);
		RegionBound newBound = this.bound.createMirrored(axis, this.offset);
		return new Selection(this.world, this.offset, newRegion, newBound);
	}
	
	/**
	 * Create a selection which is rotated about the axis which go throw the offset
	 * @param axis x y, or z axis which is parallel to the rotating axis
	 * @param degree a degree
	 * @return a selection which is rotated about the axis which go throw the offset
	 */
	@PublicAPI
	public Selection createRotated(Axis axis, double degree) {
		Region3D newRegion = this.region.createRotated(axis, degree, this.offset);
		RegionBound newBound = this.bound.createRotated(axis, degree, this.offset);
		return new Selection(this.world, this.offset, newRegion, newBound);
	}

	@PrivateAPI
	public Selection createMaxLimited(Axis axis, double max){
		Region3D newRegion = switch (axis){
			case X -> new LogicalConjunctRegion3D(this.region, new MaxXRegion3D(max));
			case Y -> new LogicalConjunctRegion3D(this.region, new MaxYRegion3D(max));
			case Z -> new LogicalConjunctRegion3D(this.region, new MaxZRegion3D(max));
		};
		double ubx = this.bound.upperBoundX();
		double uby = this.bound.upperBoundY();
		double ubz = this.bound.upperBoundZ();
		double lbx = this.bound.lowerBoundX();
		double lby = this.bound.lowerBoundY();
		double lbz = this.bound.lowerBoundZ();
		if(axis == Axis.X){
			ubx = max;
		}else if(axis == Axis.Y){
			uby = max;
		}else if(axis == Axis.Z) {
			ubz = max;
		}
		RegionBound newBound = new CuboidBound(ubx, uby, ubz, lbx, lby, lbz);
		return  new Selection(this.world, this.offset, newRegion, newBound);
	}

	@PrivateAPI
	public Selection createMinLimited(Axis axis, double min){
		Region3D newRegion = switch (axis){
			case X -> new LogicalConjunctRegion3D(this.region, new MinXRegion3D(min));
			case Y -> new LogicalConjunctRegion3D(this.region, new MinYRegion3D(min));
			case Z -> new LogicalConjunctRegion3D(this.region, new MinZRegion3D(min));
		};
		double ubx = this.bound.upperBoundX();
		double uby = this.bound.upperBoundY();
		double ubz = this.bound.upperBoundZ();
		double lbx = this.bound.lowerBoundX();
		double lby = this.bound.lowerBoundY();
		double lbz = this.bound.lowerBoundZ();
		if(axis == Axis.X){
			lbx = min;
		}else if(axis == Axis.Y){
			lby = min;
		}else if(axis == Axis.Z) {
			lbz = min;
		}
		RegionBound newBound = new CuboidBound(ubx, uby, ubz, lbx, lby, lbz);
		return  new Selection(this.world, this.offset, newRegion, newBound);
	}

}
