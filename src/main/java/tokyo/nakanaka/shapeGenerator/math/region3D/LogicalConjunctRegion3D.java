package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PrivateAPI;

@PrivateAPI
public class LogicalConjunctRegion3D implements Region3D{
	private Region3D region1;
	private Region3D region2;
	
	public LogicalConjunctRegion3D(Region3D region1, Region3D region2) {
		this.region1 = region1;
		this.region2 = region2;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		return this.region1.contains(x, y, z) && this.region2.contains(x, y, z);
	}
	
}
