package tokyo.nakanaka.shapeGenerator.regionData;

import java.util.LinkedHashMap;

import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Cuboid;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;

public class CuboidRegionData implements RegionData {
	private Vector3D pos1;
	private Vector3D pos2;
	
	public void setPos1(Vector3D pos1) {
		this.pos1 = pos1;
	}
	
	public void setPos2(Vector3D pos2) {
		this.pos2 = pos2;
	}
	
	@Override
	public void onLeftClick(BlockVector3D blockPos) {
		this.pos1 = blockPos.toVector3D();
	}
	
	@Override
	public void onRightClick(BlockVector3D blockPos) {
		this.pos2 = blockPos.toVector3D();
	}
	
	@Override
	public BoundRegion3D buildBoundRegion3D() {
		if(pos1 == null || pos2 == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Cuboid(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ());
		double ubx = Math.max(pos1.getX(), pos2.getX());
		double uby = Math.max(pos1.getY(), pos2.getY());
		double ubz = Math.max(pos1.getZ(), pos2.getZ());
		double lbx = Math.min(pos1.getX(), pos2.getX());
		double lby = Math.min(pos1.getY(), pos2.getY());
		double lbz = Math.min(pos1.getZ(), pos2.getZ());
		return new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
	}

	@Override
	public Vector3D defaultOffset() {
		return pos1;
	}
	
	@Override
	public LinkedHashMap<String, String> toLinkedHashMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("pos1", "");
		map.put("pos2", "");
		map.put("width", "");
		map.put("height", "");
		map.put("length", "");
		if(pos1 != null) {
			map.put("pos1", pos1.toString());
		}
		if(pos2 != null) {
			map.put("pos2", pos2.toString());
		}
		if(pos1 != null && pos2 != null) {
			map.put("width", String.valueOf(pos2.getX() - pos1.getX()));
			map.put("height", String.valueOf(pos2.getY() - pos1.getY()));
			map.put("length", String.valueOf(pos2.getZ() - pos1.getZ()));
		}
		return map;
	}

}
