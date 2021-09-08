package tokyo.nakanaka.shapeGenerator.regionData;

import static tokyo.nakanaka.shapeGenerator.MaxMinCalculator.max;
import static tokyo.nakanaka.shapeGenerator.MaxMinCalculator.min;

import java.util.LinkedHashMap;

import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Tetrahedron;

@SuppressWarnings("deprecation")
public class TetrahedronRegionData implements RegionData {
	private Vector3D pos1;
	private Vector3D pos2;
	private Vector3D pos3;
	private Vector3D pos4;
	
	public void setPos1(Vector3D pos1) {
		this.pos1 = pos1;
	}
	
	public void setPos2(Vector3D pos2) {
		this.pos2 = pos2;
	}
	
	public void setPos3(Vector3D pos3) {
		this.pos3 = pos3;
	}
	
	public void setPos4(Vector3D pos4) {
		this.pos4 = pos4;
	}
	
	@Override
	public void onLeftClick(BlockVector3D blockPos) {
		this.pos1 = blockPos.toVector3D();
		this.pos2 = null;
		this.pos3 = null;
		this.pos4 = null;
	}

	@Override
	public void onRightClick(BlockVector3D blcokPos) {
		if(this.pos1 == null) {
			throw new IllegalStateException();
		}
		Vector3D pos = blcokPos.toVector3D();
		if(this.pos2 == null) {
			this.pos2 = pos;
		}else if(this.pos3 == null) {
			this.pos3 = pos;
		}else {
			this.pos4 = pos;
		}
	}
	
	@Override
	public BoundRegion3D buildBoundRegion3D() {
		if(pos1 == null || pos2 == null || pos3 == null || pos4 == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Tetrahedron(pos1.getX(), pos1.getY(), pos1.getZ(),
				pos2.getX(), pos2.getY(), pos2.getZ(),
				pos3.getX(), pos3.getY(), pos3.getZ(),
				pos4.getX(), pos4.getY(), pos4.getZ());
		double ubx = max(pos1.getX(), pos2.getX(), pos3.getX(), pos4.getX());
		double uby = max(pos1.getY(), pos2.getY(), pos3.getY(), pos4.getY());
		double ubz = max(pos1.getZ(), pos2.getZ(), pos3.getZ(), pos4.getZ());
		double lbx = min(pos1.getX(), pos2.getX(), pos3.getX(), pos4.getX());
		double lby = min(pos1.getY(), pos2.getY(), pos3.getY(), pos4.getY());
		double lbz = min(pos1.getZ(), pos2.getZ(), pos3.getZ(), pos4.getZ());
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
		map.put("pos4", "");
		if(pos1 != null) {
			map.put("pos1", pos1.toString());
		}
		if(pos2 != null) {
			map.put("pos2", pos2.toString());
		}
		if(pos3 != null) {
			map.put("pos3", pos3.toString());
		}
		if(pos4 != null) {
			map.put("pos4", pos4.toString());
		}
		return map;
	}
	
}
