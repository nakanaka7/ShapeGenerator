package tokyo.nakanaka.shapeGenerator.selectionStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.RegionBuildingData.DataType;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region2D.Region2D;
import tokyo.nakanaka.shapeGenerator.math.region2D.RegularPolygon;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3Ds;
import tokyo.nakanaka.shapeGenerator.math.region3D.ThickenedRegion3D;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.AxisCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.LengthCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.PosCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.RegularPolygonSideCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class RegularPolygonSelectionStrategy implements SelectionStrategy {

	@Override
	public RegionBuildingData newRegionBuildingData() {
		RegionBuildingData data = new RegionBuildingData.Builder()
				.addDataTag("center", DataType.VECTOR3D)
				.addDataTag("radius", DataType.DOUBLE)
				.addDataTag("side", DataType.INTEGER)
				.addDataTag("thickness", DataType.DOUBLE)
				.addDataTag("axis", DataType.STRING)
				.build();
		data.putInteger("side", 3);
		data.putDouble("thickness", 1.0);
		data.putString("axis", "y");
		return data;
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
	public void onLeftClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		data.putVector3D("center", blockPos.toVector3D());
		data.putDouble("radius", null);
	}

	@Override
	public void onRightClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		Vector3D center = data.getVector3D("center");
		if(center == null) {
			logger.print(LogColor.RED + "Set center first");
			return;
		}
		Vector3D pos = blockPos.toVector3D();
		double radius = Math.floor(pos.negate(center).getAbsolute()) + 0.5;
		data.putDouble("radius", radius);
	}

	@Override
	public List<String> regionKeyList() {
		return List.of("center", "radius", "side", "thickness", "axis");
	}
	
	@Override
	public String defaultOffsetKey() {
		return "center";
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

	@Override
	public CuboidBoundRegion buildBoundRegion3D(RegionBuildingData data) {
		Vector3D center = data.getVector3D("center");
		Double radius = data.getDouble("radius");
		Integer side = data.getInteger("side");
		Double thickness = data.getDouble("thickness");
		if(center == null || radius == null || side == null || thickness == null) {
			throw new IllegalStateException();
		}
		Axis axis = Axis.valueOf(data.getString("axis").toUpperCase());
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
	public BoundRegion3D buildBoundRegion3D(Map<String, Object> regionDataMap) {
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
	public void onLeftClickBlock(UserData userData, Player player, BlockPosition blockPos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRightClickBlock(UserData userData, Player player, BlockPosition blockPos) {
		// TODO Auto-generated method stub
		
	}

}
