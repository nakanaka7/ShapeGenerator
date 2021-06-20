package tokyo.nakanaka.selection;

import tokyo.nakanaka.BlockRegion3D;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.world.World;

public class Selection {
	private World world;
	private BoundRegion3D region;
	private BlockVector3D offset;
	
	public Selection(World world, BoundRegion3D region, BlockVector3D offset) {
		this.world = world;
		this.region = region;
		this.offset = offset;
	}

	
	public Selection(World world, Region3D region, int upperBoundX, int upperBoundY, int upperBoundZ, int lowerBoundX,
			int lowerBoundY, int lowerBoundZ, int offsetX, int offsetY, int offsetZ) {
		this.world = world;
		this.region = new BoundRegion3D(region, upperBoundX + 0.5, upperBoundY + 0.5, upperBoundZ + 0.5, lowerBoundX - 0.5, lowerBoundY -0.5, lowerBoundZ - 0.5); 
		this.offset = new BlockVector3D(offsetX, offsetY, offsetZ);
	}
		
	public World getWorld() {
		return world;
	}
	
	public BoundRegion3D getBoundRegion3D() {
		return region;
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
		BoundRegion3D region = this.region.getShiftedRegion(dx, dy, dz);
		BlockVector3D offset = this.offset.add(new BlockVector3D(dx, dy, dz));
		return new Selection(world, region, offset);
	}
	
	public Selection getTransformedSelection(LinearTransformation trans) {
		int x = this.offset.getX();
		int y = this.offset.getY();
		int z = this.offset.getZ();
		BoundRegion3D region = this.region.getTransformedRegion(trans, new Vector3D(x, y, z));
		return new Selection(this.world, region, this.offset);
	}

}
