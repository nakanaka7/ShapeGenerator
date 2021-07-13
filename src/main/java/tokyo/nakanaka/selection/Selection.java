package tokyo.nakanaka.selection;

import tokyo.nakanaka.geometricProperty.Axis;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.math.region3D.BlockRegion3D;
import tokyo.nakanaka.world.World;

public class Selection {
	private World world;
	private BoundRegion3D region;
	private Vector3D offset;
	
	public Selection(World world, BoundRegion3D region, Vector3D offset) {
		this.world = world;
		this.region = region;
		this.offset = offset;
	}
		
	public World getWorld() {
		return world;
	}
	
	public BlockRegion3D getBlockRegion3D() {
		return this.region.toBlockRegion3D();
	}
	
	public BoundRegion3D getBoundRegion3D() {
		return region;
	}
	
	public Vector3D getOffset() {
		return offset;
	}

	public Selection shift(Vector3D displacement) {
		BoundRegion3D region = this.region.createShiftedRegion(displacement);
		Vector3D offset = this.offset.add(displacement);
		return new Selection(world, region, offset);
	}
	
	public Selection getScaledSelection(Axis axis, double factor) {
		BoundRegion3D newRegion = this.region.createScaledRegion(axis, factor, this.offset);
		return new Selection(this.world, newRegion, this.offset);
	}
	
	public Selection getMirroedSelection(Axis axis) {
		BoundRegion3D newRegion = this.region.createMirroredRegion(axis, this.offset);
		return new Selection(this.world, newRegion, this.offset);
	}
	
	public Selection getRotatedSelection(Axis axis, double degree) {
		BoundRegion3D newRegion = this.region.createRotatedRegion(axis, degree, this.offset);
		return new Selection(this.world, newRegion, this.offset);
	}
	
	public Selection getXLimitedSelection(double maxX) {
		return null;
	}

}
