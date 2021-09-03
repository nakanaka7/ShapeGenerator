package tokyo.nakanaka.shapeGenerator.selectionShapeDelegator;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
import tokyo.nakanaka.shapeGenerator.Utils;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Cuboid;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.regionData.CuboidRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.CuboidPos1CommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.CuboidPos2CommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.LengthCalculator;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class CuboidSelectionShapeDelegator implements SelectionShapeDelegator{
	private LengthCalculator lengthCalc = new LengthCalculator();
	
	@Override
	public RegionData newRegionData() {
		return new CuboidRegionData();
	}
	
	@Override
	public SelectionData newSelectionData(World world) {
		SelectionData selData = new SelectionData();
		selData.setWorld(world);
		LinkedHashMap<String, Object> regDataMap = new LinkedHashMap<>();
		regDataMap.put("pos1", null);
		regDataMap.put("pos2", null);
		regDataMap.put("width", null);
		regDataMap.put("height", null);
		regDataMap.put("length", null);
		selData.setRegionDataMap(regDataMap);
		return selData;
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
		Vector3D pos2 = data.getVector3D("pos2");
		if(pos2 != null) {
			data.putDouble("width", this.lengthCalc.calcWidth(pos1, pos2));
			data.putDouble("height", this.lengthCalc.calcHeight(pos1, pos2));
			data.putDouble("length", this.lengthCalc.calcLength(pos1, pos2));
		}
	}

	public void onRightClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		Vector3D pos2 = blockPos.toVector3D();
		data.putVector3D("pos2", pos2);
		Vector3D pos1 = data.getVector3D("pos1");
		if(pos1 != null) {
			data.putDouble("width", this.lengthCalc.calcWidth(pos1, pos2));
			data.putDouble("height", this.lengthCalc.calcHeight(pos1, pos2));
			data.putDouble("length", this.lengthCalc.calcLength(pos1, pos2));
		}
	}
	
	@Override
	public Map<String, SelSubCommandHandler> selSubCommandHandlerMap() {
		Map<String,  SelSubCommandHandler> map = new HashMap<>();
		map.put("pos1", new CuboidPos1CommandHandler());
		map.put("pos2", new CuboidPos2CommandHandler());
		return map;
	}

	@Override
	public Selection buildSelection(SelectionData selData) {
		World world = selData.getWorld();
		Map<String, Object> regDataMap = selData.getRegionDataMap();
		Vector3D pos1 = (Vector3D) regDataMap.get("pos1");
		Vector3D pos2 = (Vector3D) regDataMap.get("pos2");
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
		BoundRegion3D boundReg = new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
		Vector3D offset = selData.getOffset();
		if(offset == null) {
			offset = pos1;
		}
		return new Selection(world, boundReg, offset);
	}
	
	@Override
	public void onLeftClickBlock(UserData userData, Player player, BlockPosition blockPos) {
		
		
	}

	@Override
	public void onRightClickBlock(UserData userData, Player player, BlockPosition blockPos) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public BoundRegion3D buildBoundRegion3D(RegionData regData) {
		CuboidRegionData cuboidRegData = (CuboidRegionData)regData;
		Vector3D pos1 = cuboidRegData.getPos1();
		Vector3D pos2 = cuboidRegData.getPos2();
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
	public Vector3D defaultOffset(RegionData regData) {
		CuboidRegionData cuboidRegData = (CuboidRegionData)regData;
		return cuboidRegData.getPos2();
	}

}
