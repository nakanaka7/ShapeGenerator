package tokyo.nakanaka.shapeGenerator.regionData;

import java.util.LinkedHashMap;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region2D.Region2D;
import tokyo.nakanaka.shapeGenerator.math.region2D.RegularPolygon;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3Ds;
import tokyo.nakanaka.shapeGenerator.math.region3D.ThickenedRegion3D;

public class RegularPolygonRegionData implements RegionData {
	private Vector3D center;
	private Double radius;
	private Integer side;
	private Double thickness;
	private Axis axis;
	
	public Vector3D getCenter() {
		return center;
	}
	
	public void setCenter(Vector3D center) {
		this.center = center;
	}
	
	public Double getRadius() {
		return radius;
	}
	
	public void setRadius(Double radius) {
		this.radius = radius;
	}
	
	public Integer getSide() {
		return side;
	}
	
	public void setSide(Integer side) {
		this.side = side;
	}
	
	public Double getThickness() {
		return thickness;
	}
	
	public void setThickness(Double thickness) {
		this.thickness = thickness;
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
	}

	@Override
	public void onRightClick(BlockVector3D blockPos) {
		Vector3D pos = blockPos.toVector3D();
		double radius = Math.floor(pos.negate(this.center).getAbsolute()) + 0.5;
		this.radius = radius;
	}
	
	@Override
	public BoundRegion3D buildBoundRegion3D() {
		if(center == null || radius == null || side == null || thickness == null || axis == null) {
			throw new IllegalStateException();
		}
		Region2D regularPoly = new RegularPolygon(side);
		Region3D region = new ThickenedRegion3D(regularPoly, thickness);
		region = Region3Ds.linearTransform(region, LinearTransformation.ofXScale(radius));
		region = Region3Ds.linearTransform(region, LinearTransformation.ofYScale(radius));
		double ubx = radius;
		double uby = radius;
		double ubz = radius;
		double lbx = - radius;
		double lby = - radius;
		double lbz = - radius;
		switch(axis) {
		case X:
			region = Region3Ds.linearTransform(region, LinearTransformation.ofYRotation(90));
			region = Region3Ds.linearTransform(region, LinearTransformation.ofXRotation(90));
			ubx = thickness/ 2;
			lbx = - thickness/ 2;
			break;
		case Y:
			region = Region3Ds.linearTransform(region, LinearTransformation.ofXRotation(-90));
			region = Region3Ds.linearTransform(region, LinearTransformation.ofYRotation(-90));
			uby = thickness/ 2;
			lby = - thickness/ 2;
			break;
		case Z:
			ubz = thickness/ 2;
			lbz = - thickness/ 2;
			break;
		default:
			break;
		}
		CuboidBoundRegion bound = new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
		return bound.createShiftedRegion(center);
	}

	@Override
	public Vector3D defaultOffset() {
		return center;
	}
	
	@Override
	public LinkedHashMap<String, String> toLinkedHashMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("center", "");
		map.put("radius", "");
		map.put("side", "");
		map.put("thickness", "");
		map.put("axis", "");
		if(center != null) {
			map.put("center", center.toString());
		}
		if(radius != null) {
			map.put("radius", String.valueOf(radius));
		}
		if(side != null) {
			map.put("side", String.valueOf(side));
		}
		if(thickness != null) {
			map.put("thickness", String.valueOf(thickness));
		}
		if(axis != null) {
			map.put("axis", axis.toString().toLowerCase());
		}
		return map;
	}

}
