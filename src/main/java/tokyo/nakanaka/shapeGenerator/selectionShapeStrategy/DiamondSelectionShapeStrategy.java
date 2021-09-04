package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Diamond;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3Ds;
import tokyo.nakanaka.shapeGenerator.regionData.DiamondRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.LengthCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.PosCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;

public class DiamondSelectionShapeStrategy implements SelectionShapeStrategy {

	@Override
	public RegionData newRegionData() {
		return new DiamondRegionData();
	}
	
	@Override
	public SelectionData newSelectionData(World world) {
		LinkedHashMap<String, Object> regDataMap = new LinkedHashMap<>();
		regDataMap.put("center", null);
		regDataMap.put("radius_x", null);
		regDataMap.put("radius_y", null);
		regDataMap.put("radius_z", null);
		return new SelectionData(world, regDataMap, "center");
	}
	
	@Override
	public String leftClickDescription() {
		return "Set center";
	}

	@Override
	public String rightClickDescription() {
		return "Set radius_x, radius_y, radius_z";
	}

	@Override
	public Map<String, SelSubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SelSubCommandHandler> map = new HashMap<>();
		map.put("center", new PosCommandHandler("center"));
		map.put("radius_x", new LengthCommandHandler("radius_x"));
		map.put("radius_y", new LengthCommandHandler("radius_y"));
		map.put("radius_z", new LengthCommandHandler("radius_z"));
		return map;
	}

	@Override
	public Selection buildSelection(SelectionData selData) {
		World world = selData.getWorld();
		Map<String, Object> regDataMap = selData.getRegionDataMap();
		BoundRegion3D boundReg = this.buildBoundRegion3D(regDataMap);
		Vector3D offset = selData.getOffset();
		if(offset == null) {
			offset = (Vector3D) regDataMap.get("center");
		}
		return new Selection(world, boundReg, offset);
	}
	
	private BoundRegion3D buildBoundRegion3D(Map<String, Object> regionDataMap) {
		Vector3D center = (Vector3D) regionDataMap.get("center");
		Double radiusX = (Double) regionDataMap.get("radius_x");
		Double radiusY = (Double) regionDataMap.get("radius_y");
		Double radiusZ = (Double) regionDataMap.get("radius_z");
		if(center == null || radiusX == null || radiusY == null || radiusZ == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Diamond(radiusX, radiusY, radiusZ);
		region = Region3Ds.shift(region, center);
		double ubx = center.getX() + radiusX;
		double uby = center.getY() + radiusY;
		double ubz = center.getZ() + radiusZ;
		double lbx = center.getX() - radiusX;
		double lby = center.getY() - radiusY;
		double lbz = center.getZ() - radiusZ;
		return new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
	}

	@Override
	public void setFirstClickData(RegionData regData, BlockVector3D blockPos) {
		DiamondRegionData diamondRegData = (DiamondRegionData)regData;
		Vector3D center = blockPos.toVector3D();
		diamondRegData.setCenter(center);
	}

	@Override
	public void setAdditionalClickData(RegionData regData, BlockVector3D blockPos) {
		DiamondRegionData diamondRegData = (DiamondRegionData)regData;
		Vector3D center = diamondRegData.getCenter();
		Vector3D pos = blockPos.toVector3D();
		double radius = pos.negate(center).getAbsolute() + 0.5;
		if(diamondRegData.getRadius_x() == null) {
			diamondRegData.setRadius_x(radius);
		}else if(diamondRegData.getRadius_y() == null) {
			diamondRegData.setRadius_y(radius);
		}else {
			diamondRegData.setRadius_z(radius);
		}	
	}
	
	@Override
	public BoundRegion3D buildBoundRegion3D(RegionData regData) {
		DiamondRegionData diamondRegData = (DiamondRegionData)regData;
		Vector3D center = diamondRegData.getCenter();
		Double radiusX = diamondRegData.getRadius_x();
		Double radiusY = diamondRegData.getRadius_y();
		Double radiusZ = diamondRegData.getRadius_z();
		if(center == null || radiusX == null || radiusY == null || radiusZ == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Diamond(radiusX, radiusY, radiusZ);
		region = Region3Ds.shift(region, center);
		double ubx = center.getX() + radiusX;
		double uby = center.getY() + radiusY;
		double ubz = center.getZ() + radiusZ;
		double lbx = center.getX() - radiusX;
		double lby = center.getY() - radiusY;
		double lbz = center.getZ() - radiusZ;
		return new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
	}

	@Override
	public Vector3D defaultOffset(RegionData regData) {
		DiamondRegionData diamondRegData = (DiamondRegionData)regData;
		return diamondRegData.getCenter();
	}

}
