package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PrivateAPI;

@PrivateAPI
@Deprecated
public class Region3Ds {
	private Region3Ds() {
	}
	
	public static Region3D and(Region3D region1, Region3D region2) {
		return new LogicalConjunctRegion3D(region1, region2);
	}
	
	public static Region3D or(Region3D region1, Region3D region2) {
		return new LogicalDisjunctRegion3D(region1, region2);
	}

}
