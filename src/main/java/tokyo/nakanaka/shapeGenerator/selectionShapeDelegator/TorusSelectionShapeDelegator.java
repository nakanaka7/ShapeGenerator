package tokyo.nakanaka.shapeGenerator.selectionShapeDelegator;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.RegionBuildingData.DataType;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3Ds;
import tokyo.nakanaka.shapeGenerator.math.region3D.Torus;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.AxisCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.LengthCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.PosCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class TorusSelectionShapeDelegator implements SelectionShapeDelegator {

	@Override
	public SelectionData newSelectionData(World world) {
		LinkedHashMap<String, Object> regDataMap = new LinkedHashMap<>();
		regDataMap.put("center", null);
		regDataMap.put("radius_main", null);
		regDataMap.put("radius_sub", null);
		regDataMap.put("axis", "y");
		return new SelectionData(world, regDataMap, "center");
	}

	@Override
	public String leftClickDescription() {
		return "Set center";
	}

	@Override
	public String rightClickDescription() {
		return "Set radius_main, radius_sub";
	}
	
	public void onLeftClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		Vector3D center = blockPos.toVector3D();
		data.putVector3D("center", center);
		data.putDouble("radius_main", null);
		data.putDouble("radius_sub", null);
	}

	public void onRightClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		Vector3D pos = blockPos.toVector3D();
		Vector3D center = data.getVector3D("center");
		Double radiusMain = data.getDouble("radius_main");
		Double radiusSub = data.getDouble("radius_sub");
		Axis axis = Axis.valueOf(data.getString("axis").toUpperCase());
		if(center == null) {
			logger.print(LogColor.RED + "Set center first");
			return;
		}else if(radiusMain == null) {
			radiusMain = pos.negate(center).getAbsolute();
			data.putDouble("radius_main", radiusMain);
		}else {
			Vector3D e1 = axis.toVector3D();
			Vector3D p = pos.negate(center);
			double p1 = p.innerProduct(e1);
			Vector3D p2e2 = p.negate(e1.multiply(p1));
			Vector3D e2 = p2e2.divide(p2e2.getAbsolute());
			radiusSub = p.negate(e2.multiply(radiusMain)).getAbsolute() + 0.5;
			data.putDouble("radius_sub", radiusSub);
		}
	}

	@Override
	public Map<String, SelSubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SelSubCommandHandler> map = new HashMap<>();
		map.put("center", new PosCommandHandler("center"));
		map.put("radius_main", new LengthCommandHandler("radius_main"));
		map.put("radius_sub", new LengthCommandHandler("radius_sub"));
		map.put("axis", new AxisCommandHandler());
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
		Double radiusMain = (Double) regionDataMap.get("radius_main");
		Double radiusSub = (Double) regionDataMap.get("radius_sub");
		if(center == null || radiusMain == null || radiusSub == null) {
			throw new IllegalStateException();
		}
		Axis axis = Axis.valueOf(((String)regionDataMap.get("axis")).toUpperCase());
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
	public void onLeftClickBlock(UserData userData, Player player, BlockPosition blockPos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRightClickBlock(UserData userData, Player player, BlockPosition blockPos) {
		// TODO Auto-generated method stub
		
	}

}
