package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;

@PrivateAPI
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
