package tokyo.nakanaka.shapeGenerator.math.region3D;

import tokyo.nakanaka.annotation.PrivateAPI;

/**
 * Represents a region that was shifted
 */
@PrivateAPI
class ShiftedRegion3D implements Region3D {
	private Region3D original;
	private double dx;
	private double dy;
	private double dz;
	
	/**
	 * Constructs a shifted region from an original region and a displacement
	 * @param original an original region
	 * @param dx the displacement of x axis
	 * @param dy the displacement of y axis
	 * @param dz the displacement of z axis
	 */
	public ShiftedRegion3D(Region3D original, double dx, double dy, double dz) {
		this.original = original;
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}

	@Override
	public boolean contains(double x, double y, double z) {
		return this.original.contains(x - dx, y - dy, z - dz);
	}

}
