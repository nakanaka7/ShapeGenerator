package tokyo.nakanaka.shapeGenerator.regionData;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Line;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;

public class LineRegionData implements RegionData {
	private Vector3D pos1;
	private Vector3D pos2;
	private Double thickness;
	
	public Vector3D getPos1() {
		return pos1;
	}
	
	public void setPos1(Vector3D pos1) {
		this.pos1 = pos1;
	}
	
	public Vector3D getPos2() {
		return pos2;
	}
	
	public void setPos2(Vector3D pos2) {
		this.pos2 = pos2;
	}
	
	public Double getThickness() {
		return thickness;
	}
	
	public void setThickness(Double thickness) {
		this.thickness = thickness;
	}
	
	@Override
	public BoundRegion3D buildBoundRegion3D() {
		if(pos1 == null || pos2 == null || thickness == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Line(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ(), thickness);
		double ubx = Math.max(pos1.getX(), pos2.getX()) + thickness;
		double uby = Math.max(pos1.getY(), pos2.getY()) + thickness;
		double ubz = Math.max(pos1.getZ(), pos2.getZ()) + thickness;
		double lbx = Math.min(pos1.getX(), pos2.getX()) - thickness;
		double lby = Math.min(pos1.getY(), pos2.getY()) - thickness;
		double lbz = Math.min(pos1.getZ(), pos2.getZ()) - thickness;
		return new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
	}
	
}
