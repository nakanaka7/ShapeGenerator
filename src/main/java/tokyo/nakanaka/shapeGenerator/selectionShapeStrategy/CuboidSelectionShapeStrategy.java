package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.regionData.CuboidRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
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
	public Map<String, SubCommandHandler> selSubCommandHandlerMap(){
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
	
}
