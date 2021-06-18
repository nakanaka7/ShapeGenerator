package tokyo.nakanaka.selection;

import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.math.region3D.ShiftedRegion3D;
import tokyo.nakanaka.math.region3D.SphereRegion3D;

public class SphereRegionBuilder {
	private BlockVector3D center;
	private Integer radius;
	
	public BlockVector3D getCenter() {
		return center;
	}
	
	public void setCenter(BlockVector3D center) {
		this.center = center;
	}
	
	public Integer getRadius() {
		return radius;
	}
	
	public void setRadius(int radius) {
		if(radius < 0) {
			throw new IllegalArgumentException();
		}
		this.radius = radius;
	}
	
	/**
	 * @throws IllegalStateException
	 */
	public Region3D build() {
		if(this.center == null || this.radius == null) {
			throw new IllegalStateException();
		}
		Region3D region = new SphereRegion3D(this.radius - 0.5);
		return new ShiftedRegion3D(region, center.getX(), center.getY(), center.getZ());
	}
	
}
