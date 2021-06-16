package tokyo.nakanaka.shapeGenerator;

import region3D.Region3D;

public class BlockRegion3D {
	private Region3D region;
	private int upperBoundX;
	private int upperBoundY;
	private int upperBoundZ;
	private int lowerBoundX;
	private int lowerBoundY;
	private int lowerBoundZ;
	
	public BlockRegion3D(Region3D region, int upperBoundX, int upperBoundY, int upperBoundZ
			, int lowerBoundX, int lowerBoundY, int lowerBoundZ) {
		this.region = region;
		this.upperBoundX = upperBoundX;
		this.upperBoundY = upperBoundY;
		this.upperBoundZ = upperBoundZ;
		this.lowerBoundX = lowerBoundX;
		this.lowerBoundY = lowerBoundY;
		this.lowerBoundZ = lowerBoundZ;
	}
	
	public boolean contains(int x, int y, int z) {
		if(x < lowerBoundX || upperBoundX < x || y < lowerBoundY || upperBoundY < y || z < lowerBoundZ || upperBoundZ < z) {
			return false;
		}
		return region.contains(x, y, z);
	}
}
