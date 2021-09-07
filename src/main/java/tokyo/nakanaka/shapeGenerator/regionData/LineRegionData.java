package tokyo.nakanaka.shapeGenerator.regionData;

import java.util.LinkedHashMap;

import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Line;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;

public class LineRegionData implements RegionData {
	private Vector3D pos1;
	private Vector3D pos2;
	private Double thickness;
	
	public void setPos1(Vector3D pos1) {
		this.pos1 = pos1;
	}
	
	public void setPos2(Vector3D pos2) {
		this.pos2 = pos2;
	}
	
	public void setThickness(Double thickness) {
		this.thickness = thickness;
	}
	
	@Override
	public void onLeftClick(BlockVector3D pos) {
		this.pos1 = pos.toVector3D();
	}

	@Override
	public void onRightClick(BlockVector3D pos) {
		this.pos2 = pos.toVector3D();
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

	@Override
	public Vector3D defaultOffset() {
		return pos1;
	}
	
	@Override
	public String defaultOffsetLabel() {
		return "pos1";
	}
	
	@Override
	public LinkedHashMap<String, String> toLinkedHashMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("pos1", "");
		map.put("pos2", "");
		map.put("thickness", "");
		if(pos1 != null) {
			map.put("pos1", pos1.toString());
		}
		if(pos2 != null) {
			map.put("pos2", pos2.toString());
		}
		if(thickness != null) {
			map.put("thickness", String.valueOf(thickness));
		}
		return map;
	}

}
