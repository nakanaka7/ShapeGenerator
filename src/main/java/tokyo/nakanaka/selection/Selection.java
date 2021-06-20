package tokyo.nakanaka.selection;

import tokyo.nakanaka.BlockRegion3D;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.math.region3D.ShiftedRegion3D;
import tokyo.nakanaka.world.World;

public class Selection {
	private World world;
	private Region3D region;
	private int upperBoundX;
	private int upperBoundY;
	private int upperBoundZ;
	private int lowerBoundX;
	private int lowerBoundY;
	private int lowerBoundZ;
	private int offsetX;
	private int offsetY;
	private int offsetZ;
	
	public Selection(World world, Region3D region, int upperBoundX, int upperBoundY, int upperBoundZ, int lowerBoundX,
			int lowerBoundY, int lowerBoundZ, int offsetX, int offsetY, int offsetZ) {
		this.world = world;
		this.region = region;
		this.upperBoundX = upperBoundX;
		this.upperBoundY = upperBoundY;
		this.upperBoundZ = upperBoundZ;
		this.lowerBoundX = lowerBoundX;
		this.lowerBoundY = lowerBoundY;
		this.lowerBoundZ = lowerBoundZ;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
	}
		
	public World getWorld() {
		return world;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public int getOffsetZ() {
		return offsetZ;
	}
	
	public void shift(int dx, int dy, int dz) {
		this.region = new ShiftedRegion3D(this.region, dx, dy, dz);
		this.upperBoundX += dx;
		this.upperBoundY += dy;
		this.upperBoundZ += dz;
		this.lowerBoundX += dx;
		this.lowerBoundY += dy;
		this.lowerBoundZ += dz;
		this.offsetX += dx;
		this.offsetY += dy;
		this.offsetZ += dz;
	}
	
	public BlockRegion3D getBlockRegion3D() {
		return new BlockRegion3D(this.region, this.upperBoundX, this.upperBoundY,
				this.upperBoundZ, this.lowerBoundX, this.lowerBoundY, this.lowerBoundZ);
	}
	
}
