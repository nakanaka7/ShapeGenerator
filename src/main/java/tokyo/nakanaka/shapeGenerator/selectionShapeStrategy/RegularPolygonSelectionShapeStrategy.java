package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region2D.Region2D;
import tokyo.nakanaka.shapeGenerator.math.region2D.RegularPolygon;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3Ds;
import tokyo.nakanaka.shapeGenerator.math.region3D.ThickenedRegion3D;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegularPolygonRegionData;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.AxisCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.LengthCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.PosCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.RegularPolygonSideCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;

public class RegularPolygonSelectionShapeStrategy implements SelectionShapeStrategy {

	@Override
	public RegionData newRegionData() {
		return new RegularPolygonRegionData();
	}
	
	@Override
	public String leftClickDescription() {
		return "Set center";
	}

	@Override
	public String rightClickDescription() {
		return "Set radius by the center coordinates";
	}
	
	@Override
	public Map<String, SelSubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SelSubCommandHandler> map = new HashMap<>();
		map.put("center", new PosCommandHandler("center"));
		map.put("radius", new LengthCommandHandler("radius"));
		map.put("side", new RegularPolygonSideCommandHandler());
		map.put("thickness", new LengthCommandHandler("thickness"));
		map.put("axis", new AxisCommandHandler());
		return map;
	}
	
	private BoundRegion3D buildBoundRegion3D(Map<String, Object> regionDataMap) {
		Vector3D center = (Vector3D) regionDataMap.get("center");
		Double radius = (Double) regionDataMap.get("radius");
		Integer side = (Integer) regionDataMap.get("side");
		Double thickness = (Double) regionDataMap.get("thickness");
		if(center == null || radius == null || side == null || thickness == null) {
			throw new IllegalStateException();
		}
		Axis axis = Axis.valueOf(((String)regionDataMap.get("axis")).toUpperCase());
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
	public void setFirstClickData(RegionData regData, BlockVector3D blockPos) {
		RegularPolygonRegionData rpRegData = (RegularPolygonRegionData)regData;
		rpRegData.setCenter(blockPos.toVector3D());
	}

	@Override
	public void setAdditionalClickData(RegionData regData, BlockVector3D blockPos) {
		RegularPolygonRegionData rpRegData = (RegularPolygonRegionData)regData;
		Vector3D pos = blockPos.toVector3D();
		Vector3D center = rpRegData.getCenter();
		double radius = Math.floor(pos.negate(center).getAbsolute()) + 0.5;
		rpRegData.setRadius(radius);
	}
	
	@Override
	public BoundRegion3D buildBoundRegion3D(RegionData regData) {
		RegularPolygonRegionData rpRegData = (RegularPolygonRegionData)regData;
		Vector3D center = rpRegData.getCenter();
		Double radius = rpRegData.getRadius();
		Integer side = rpRegData.getSide();
		Double thickness = rpRegData.getThickness();
		if(center == null || radius == null || side == null || thickness == null) {
			throw new IllegalStateException();
		}
		Axis axis = rpRegData.getAxis();
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
	public Vector3D defaultOffset(RegionData regData) {
		RegularPolygonRegionData rpRegData = (RegularPolygonRegionData)regData;
		return rpRegData.getCenter();
	}

}
