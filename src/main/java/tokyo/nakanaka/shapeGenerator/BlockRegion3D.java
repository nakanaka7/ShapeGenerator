package tokyo.nakanaka.shapeGenerator;

import java.util.HashSet;
import java.util.Set;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.region3D.Region3D;

public class BlockRegion3D{
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
	
	public Set<Vector3D> asVectorSet(){
		Set<Vector3D> set = new HashSet<>();
		for(int x = this.lowerBoundX; x <= this.upperBoundX; ++x) {
			for(int y = this.lowerBoundY; y <= this.upperBoundY; ++y) {
				for(int z = this.lowerBoundZ; z <= this.upperBoundZ; ++z) {
					if(this.region.contains(x, y, z)) {
						set.add(new Vector3D(x, y, z));
					}
				}
			}
		}
		return set;
	}
}
