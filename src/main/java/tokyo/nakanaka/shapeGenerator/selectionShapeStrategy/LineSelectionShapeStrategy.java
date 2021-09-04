package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.RegionBuildingData.DataType;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Line;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.regionData.LineRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.LengthCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.PosCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class LineSelectionShapeStrategy implements SelectionShapeStrategy {
	
	public RegionData newRegionData() {
		return new LineRegionData();
	}
	
	@Override
	public SelectionData newSelectionData(World world) {
		LinkedHashMap<String, Object> regDataMap = new LinkedHashMap<>();
		regDataMap.put("pos1", null);
		regDataMap.put("pos2", null);
		regDataMap.put("thickness", 1);
		return new SelectionData(world, regDataMap, "pos1");
	}
	
	@Override
	public String leftClickDescription() {
		return "Set pos1";
	}

	@Override
	public String rightClickDescription() {
		return "Set pos2";
	}
	
	public void onLeftClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		Vector3D pos1 = blockPos.toVector3D();
		data.putVector3D("pos1", pos1);
	}

	public void onRightClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		Vector3D pos2 = blockPos.toVector3D();
		data.putVector3D("pos2", pos2);
	}
	
	@Override
	public void setFirstClickData(RegionData regData, BlockVector3D blockPos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAdditionalClickData(RegionData regData, BlockVector3D blockPos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, SelSubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SelSubCommandHandler> map = new HashMap<>();
		map.put("pos1", new PosCommandHandler("pos1"));
		map.put("pos2", new PosCommandHandler("pos2"));
		map.put("thickness", new LengthCommandHandler("thickness"));
		return map;
	}

	@Override
	public Selection buildSelection(SelectionData selData) {
		World world = selData.getWorld();
		Map<String, Object> regDataMap = selData.getRegionDataMap();
		BoundRegion3D boundReg = this.buildBoundRegion3D(regDataMap);
		Vector3D offset = selData.getOffset();
		if(offset == null) {
			offset = (Vector3D) regDataMap.get("pos1");
		}
		return new Selection(world, boundReg, offset);
	}
	
	private BoundRegion3D buildBoundRegion3D(Map<String, Object> regionDataMap) {
		Vector3D pos1 = (Vector3D) regionDataMap.get("pos1");
		Vector3D pos2 = (Vector3D) regionDataMap.get("pos2");
		Double thickness = (Double) regionDataMap.get("thickness");
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
	public BoundRegion3D buildBoundRegion3D(RegionData regData) {
		LineRegionData lineRegData = (LineRegionData)regData;
		Vector3D pos1 = lineRegData.getPos1();
		Vector3D pos2 = lineRegData.getPos1();
		Double thickness = lineRegData.getThickness();
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
	public Vector3D defaultOffset(RegionData regData) {
		LineRegionData lineRegData = (LineRegionData)regData;
		return lineRegData.getPos1();
	}

}