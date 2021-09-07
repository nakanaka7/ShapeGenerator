package tokyo.nakanaka.shapeGenerator.regionData;

import static tokyo.nakanaka.shapeGenerator.MaxMinCalculator.max;
import static tokyo.nakanaka.shapeGenerator.MaxMinCalculator.min;

import java.util.LinkedHashMap;

import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Triangle;

public class TriangleRegionData implements RegionData {
	private Vector3D pos1;
	private Vector3D pos2;
	private Vector3D pos3;
	private Double thickness = 1.0;
	
	public void setPos1(Vector3D pos1) {
		this.pos1 = pos1;
	}
	
	public void setPos2(Vector3D pos2) {
		this.pos2 = pos2;
	}
	
	public void setPos3(Vector3D pos3) {
		this.pos3 = pos3;
	}
	
	public void setThickness(Double thickness) {
		this.thickness = thickness;
	}
	
	@Override
	public void onLeftClick(BlockVector3D blockPos) {
		this.pos1 = blockPos.toVector3D();
		this.pos2 = null;
		this.pos3 = null;
	}

	@Override
	public void onRightClick(BlockVector3D blockPos) {
		if(this.pos1 == null) {
			throw new IllegalArgumentException();
		}
		Vector3D pos = blockPos.toVector3D();
		if(this.pos2 == null) {
			this.pos2 = pos;
		}else {
			this.pos3 = pos;
		}	
	}
	
	@Override
	public BoundRegion3D buildBoundRegion3D() {
		if(pos1 == null || pos2 == null || pos3 == null || thickness == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Triangle(pos1.getX(), pos1.getY(), pos1.getZ(),
				pos2.getX(), pos2.getY(), pos2.getZ(),
				pos3.getX(), pos3.getY(), pos3.getZ(), thickness);
		double ubx = max(pos1.getX(), pos2.getX(), pos3.getX()) + thickness / 2;
		double uby = max(pos1.getY(), pos2.getY(), pos3.getY()) + thickness / 2;
		double ubz = max(pos1.getZ(), pos2.getZ(), pos3.getZ()) + thickness / 2;
		double lbx = min(pos1.getX(), pos2.getX(), pos3.getX()) - thickness / 2;
		double lby = min(pos1.getY(), pos2.getY(), pos3.getY()) - thickness / 2;
		double lbz = min(pos1.getZ(), pos2.getZ(), pos3.getZ()) - thickness / 2;
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
		map.put("pos3", "");
		map.put("thickness", "");
		if(pos1 != null) {
			map.put("pos1", pos1.toString());
		}
		if(pos2 != null) {
			map.put("pos2", pos2.toString());
		}
		if(pos3 != null) {
			map.put("pos3", pos3.toString());
		}
		if(thickness != null) {
			map.put("thickness", String.valueOf(thickness));
		}
		return map;
	}
	
}
