package tokyo.nakanaka.selection;

import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BlockRegion3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
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
		return this.region.getBlockRegion3D();
	}
	
	public BoundRegion3D getBoundRegion3D() {
		return region;
	}
	
	public Vector3D getOffset() {
		return offset;
	}

	public Selection getShiftedSelection(Vector3D displacement) {
		BoundRegion3D region = this.region.getShiftedRegion(displacement);
		Vector3D offset = this.offset.add(displacement);
		return new Selection(world, region, offset);
	}
	
	public Selection getTransformedSelection(LinearTransformation trans) {
		BoundRegion3D region = this.region.getTransformedRegion(trans, this.offset);
		return new Selection(this.world, region, this.offset);
	}

	public Selection getXLimitedSelection(double maxX) {
		BoundRegion3D region = this.region.changeUpperBoundX(maxX);
		return new Selection(this.world, region, this.offset);
	}

}
