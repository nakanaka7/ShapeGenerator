package tokyo.nakanaka.selection;

import tokyo.nakanaka.BlockRegion3D;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.region3D.BoundRegion;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.world.World;

public class Selection {
	private World world;
	private BoundRegion region;
	private BlockVector3D offset;
	
	public Selection(World world, BoundRegion region, BlockVector3D offset) {
		this.world = world;
		this.region = region;
		this.offset = offset;
	}

	public Selection(World world, Region3D region, int upperBoundX, int upperBoundY, int upperBoundZ, int lowerBoundX,
			int lowerBoundY, int lowerBoundZ, int offsetX, int offsetY, int offsetZ) {
		this.world = world;
		this.region = new BoundRegion(region, upperBoundX, upperBoundY, upperBoundZ, lowerBoundX, lowerBoundY, lowerBoundZ); 
		this.offset = new BlockVector3D(offsetX, offsetY, offsetZ);
	}
		
	public World getWorld() {
		return world;
	}

	public int getOffsetX() {
		return offset.getX();
	}

	public int getOffsetY() {
		return offset.getY();
	}

	public int getOffsetZ() {
		return offset.getZ();
	}
	
	public Selection getShiftedSelection(int dx, int dy, int dz) {
		BoundRegion region = this.region.getShiftedRegion(dx, dy, dz);
		BlockVector3D offset = this.offset.add(new BlockVector3D(dx, dy, dz));
		return new Selection(world, region, offset);
	}
	
	public BlockRegion3D getBlockRegion3D() {
		return this.region.getBlockRegion3D();
	}
	
}
