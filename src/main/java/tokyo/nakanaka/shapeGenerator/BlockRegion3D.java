package tokyo.nakanaka.shapeGenerator;

import java.util.HashSet;
import java.util.Set;

import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;

@PrivateAPI
public class BlockRegion3D {
	private Region3D region;
	private int upperBoundX;
	private int upperBoundY;
	private int upperBoundZ;
	private int lowerBoundX;
	private int lowerBoundY;
	private int lowerBoundZ;
	private Set<BlockVector3D> vectorSetCache;
	
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
	
	public BlockRegion3D(Region3D region, double upperBoundX, double upperBoundY, double upperBoundZ
			, double lowerBoundX, double lowerBoundY, double lowerBoundZ) {
		this.region = region;
		this.upperBoundX = (int)Math.floor(upperBoundX);
		this.upperBoundY = (int)Math.floor(upperBoundY);
		this.upperBoundZ = (int)Math.floor(upperBoundZ);
		this.lowerBoundX = (int)Math.floor(lowerBoundX);
		this.lowerBoundY = (int)Math.floor(lowerBoundY);
		this.lowerBoundZ = (int)Math.floor(lowerBoundZ);
	}
	
	public boolean contains(int x, int y, int z) {
		if(x < lowerBoundX || upperBoundX < x || y < lowerBoundY || upperBoundY < y || z < lowerBoundZ || upperBoundZ < z) {
			return false;
		}
		return region.contains(x, y, z);
	}
	
	public Set<BlockVector3D> asVectorSet(){
		if(this.vectorSetCache != null) {
			return this.vectorSetCache;
		}
		Set<BlockVector3D> set = new HashSet<>();
		for(int x = this.lowerBoundX; x <= this.upperBoundX; ++x) {
			for(int y = this.lowerBoundY; y <= this.upperBoundY; ++y) {
				for(int z = this.lowerBoundZ; z <= this.upperBoundZ; ++z) {
					if(this.region.contains(x, y, z)) {
						set.add(new BlockVector3D(x, y, z));
					}
				}
			}
		}
		this.vectorSetCache = set;
		return set;
	}
}
