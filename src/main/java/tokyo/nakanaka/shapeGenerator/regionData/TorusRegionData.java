package tokyo.nakanaka.shapeGenerator.regionData;

import java.util.LinkedHashMap;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3Ds;
import tokyo.nakanaka.shapeGenerator.math.region3D.Torus;

public class TorusRegionData implements RegionData {
	private Vector3D center;
	private Double radiusMain;
	private Double radiusSub;
	private Axis axis = Axis.Y;
	
	public Vector3D getCenter() {
		return center;
	}
	
	public void setCenter(Vector3D center) {
		this.center = center;
	}
	
	public Double getRadiusMain() {
		return radiusMain;
	}

	public void setRadiusMain(Double radiusMain) {
		this.radiusMain = radiusMain;
	}

	public Double getRadiusSub() {
		return radiusSub;
	}

	public void setRadiusSub(Double radiusSub) {
		this.radiusSub = radiusSub;
	}

	public Axis getAxis() {
		return axis;
	}
	
	public void setAxis(Axis axis) {
		this.axis = axis;
	}
	
	@Override
	public void onLeftClick(BlockVector3D blockPos) {
		this.center = blockPos.toVector3D();
		this.radiusMain = null;
		this.radiusSub = null;
	}

	@Override
	public void onRightClick(BlockVector3D blockPos) {
		Vector3D pos = blockPos.toVector3D();
		if(this.radiusMain == null) {
			this.radiusMain = pos.negate(this.center).getAbsolute();
		}else {
			Vector3D e1 = this.axis.toVector3D();
			Vector3D p = pos.negate(this.center);
			double p1 = p.innerProduct(e1);
			Vector3D p2e2 = p.negate(e1.multiply(p1));
			Vector3D e2 = p2e2.divide(p2e2.getAbsolute());
			this.radiusSub = p.negate(e2.multiply(this.radiusMain)).getAbsolute() + 0.5;
		}
	}
	
	@Override
	public BoundRegion3D buildBoundRegion3D() {	
		if(center == null || radiusMain == null || radiusSub == null || axis == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Torus(radiusMain, radiusSub);
		switch(axis) {
		case X:
			region = Region3Ds.linearTransform(region, LinearTransformation.ofYRotation(90));
			break;
		case Y:
			region = Region3Ds.linearTransform(region, LinearTransformation.ofXRotation(90));
			break;
		case Z:
			break;
		}
		region = Region3Ds.shift(region, center);
		double ubx = center.getX() + radiusMain + radiusSub;
		double uby = center.getY() + radiusMain + radiusSub;
		double ubz = center.getZ() + radiusMain + radiusSub;
		double lbx = center.getX() - radiusMain - radiusSub;
		double lby = center.getY() - radiusMain - radiusSub;
		double lbz = center.getZ() - radiusMain - radiusSub;
		return new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
	}

	@Override
	public Vector3D defaultOffset() {
		return center;
	}
	
	@Override
	public LinkedHashMap<String, String> toLinkedHashMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("center", "");
		map.put("radius_main", "");
		map.put("radius_sub", "");
		if(center != null) {
			map.put("center", center.toString());
		}
		if(radiusMain != null) {
			map.put("radius_main", String.valueOf(radiusMain));
		}
		if(radiusSub != null) {
			map.put("radius_sub", String.valueOf(radiusSub));
		}
		return map;
	}

}
