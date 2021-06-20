package tokyo.nakanaka.math.region3D;

import tokyo.nakanaka.BlockRegion3D;

public class BoundRegion {
	private Region3D region;
	private int upperBoundX;
	private int upperBoundY;
	private int upperBoundZ;
	private int lowerBoundX;
	private int lowerBoundY;
	private int lowerBoundZ;
	
	public BoundRegion(Region3D region, int upperBoundX, int upperBoundY, int upperBoundZ, int lowerBoundX,
			int lowerBoundY, int lowerBoundZ) {
		this.region = region;
		this.upperBoundX = upperBoundX;
		this.upperBoundY = upperBoundY;
		this.upperBoundZ = upperBoundZ;
		this.lowerBoundX = lowerBoundX;
		this.lowerBoundY = lowerBoundY;
		this.lowerBoundZ = lowerBoundZ;
	}
	
	public BlockRegion3D getBlockRegion3D() {
		return new BlockRegion3D(this.region, this.upperBoundX, this.upperBoundY,
				this.upperBoundZ, this.lowerBoundX, this.lowerBoundY, this.lowerBoundZ);
	}
}
