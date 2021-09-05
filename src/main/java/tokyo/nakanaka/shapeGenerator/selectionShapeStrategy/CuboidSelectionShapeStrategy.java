package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Cuboid;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.regionData.CuboidRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.CuboidPos1CommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.CuboidPos2CommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.cuboidSelSubCommandHandler.Pos1CommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.cuboidSelSubCommandHandler.Pos2CommandHandler;

public class CuboidSelectionShapeStrategy implements SelectionShapeStrategy{
	
	@Override
	public RegionData newRegionData() {
		return new CuboidRegionData();
	}
	
	@Override
	public String leftClickDescription() {
		return "Set pos1";
	}

	@Override
	public String rightClickDescription() {
		return "Set pos2";
	}

	@Override
	public Map<String, SelSubCommandHandler> selSubCommandHandlerMap() {
		Map<String,  SelSubCommandHandler> map = new HashMap<>();
		map.put("pos1", new CuboidPos1CommandHandler());
		map.put("pos2", new CuboidPos2CommandHandler());
		return map;
	}
	
	public Map<String, SubCommandHandler> selSubCommandHandlerMapNew(){
		Map<String,  SubCommandHandler> map = new HashMap<>();
		map.put("pos1", new Pos1CommandHandler());
		map.put("pos2", new Pos2CommandHandler());
		return map;
	}
	
	@Override
	public void setFirstClickData(RegionData regData, BlockVector3D blockPos) {
		CuboidRegionData cuboidRegData = (CuboidRegionData)regData;
		Vector3D pos1 = blockPos.toVector3D();
		cuboidRegData.setPos1(pos1);
	}

	@Override
	public void setAdditionalClickData(RegionData regData, BlockVector3D blockPos) {
		CuboidRegionData cuboidRegData = (CuboidRegionData)regData;
		Vector3D pos2 = blockPos.toVector3D();
		cuboidRegData.setPos2(pos2);
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
