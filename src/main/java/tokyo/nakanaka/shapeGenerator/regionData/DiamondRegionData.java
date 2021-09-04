package tokyo.nakanaka.shapeGenerator.regionData;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Diamond;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3Ds;

public class DiamondRegionData implements RegionData {
	private Vector3D center;
	private Double radius_x;
	private Double radius_y;
	private Double radius_z;
	
	public Vector3D getCenter() {
		return center;
	}
	
	public void setCenter(Vector3D center) {
		this.center = center;
	}
	
	public Double getRadius_x() {
		return radius_x;
	}
	public void setRadius_x(Double radius_x) {
		this.radius_x = radius_x;
	}
	
	public Double getRadius_y() {
		return radius_y;
	}
	
	public void setRadius_y(Double radius_y) {
		this.radius_y = radius_y;
	}
	
	public Double getRadius_z() {
		return radius_z;
	}
	
	public void setRadius_z(Double radius_z) {
		this.radius_z = radius_z;
	}
	
	@Override
	public BoundRegion3D buildBoundRegion3D() {
		if(center == null || radius_x == null || radius_y == null || radius_z == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Diamond(radius_x, radius_y, radius_z);
		region = Region3Ds.shift(region, center);
		double ubx = center.getX() + radius_x;
		double uby = center.getY() + radius_y;
		double ubz = center.getZ() + radius_z;
		double lbx = center.getX() - radius_x;
		double lby = center.getY() - radius_y;
		double lbz = center.getZ() - radius_z;
		return new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
	}

	@Override
	public Vector3D defaultOffset() {
		return center;
	}
		
}
