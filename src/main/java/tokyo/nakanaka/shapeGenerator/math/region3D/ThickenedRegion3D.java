package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.shapeGenerator.math.region2D.Region2D;

/**
 * Represents thickened region used by Region2D. It is thickened along z axis,
 * keeping its center origin.
 */
@PrivateAPI
public class ThickenedRegion3D implements Region3D {
	private Region2D region2D;
	private double thickness;
	
	public ThickenedRegion3D(Region2D region2D, double thickness) {
		this.region2D = region2D;
		this.thickness = thickness;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		if(z < - thickness/ 2 || thickness/ 2 < z) {
			return false;
		}
		return this.region2D.contains(x, y);
	}

}
