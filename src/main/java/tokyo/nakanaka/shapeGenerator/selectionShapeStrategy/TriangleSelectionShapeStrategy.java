package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.TriangleRegionData;

public class TriangleSelectionShapeStrategy implements SelectionShapeStrategy {

	@Override
	public RegionData newRegionData() {
		return new TriangleRegionData();
	}
	
	@Override
	public String leftClickDescription() {
		return "Set pos1";
	}

	@Override
	public String rightClickDescription() {
		return "Set pos2, pos3";
	}
	
	public void onLeftClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		data.putVector3D("pos1", blockPos.toVector3D());
		data.putVector3D("pos2", null);
		data.putVector3D("pos3", null);
	}

	public void onRightClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		Vector3D pos1 = data.getVector3D("pos1");
		if(pos1 == null) {
			logger.print(LogColor.RED + "Set pos1 first");
			return;
		}
		Vector3D pos = blockPos.toVector3D();
		if(data.getVector3D("pos2") == null) {
			data.putVector3D("pos2", pos);
		}else {
			data.putVector3D("pos3", pos);
		}
	}
	
	@Override
	public void setFirstClickData(RegionData regData, BlockVector3D blockPos) {
		TriangleRegionData triRegData = (TriangleRegionData) regData;
		Vector3D pos = blockPos.toVector3D();
		triRegData.setPos1(pos);
	}

	@Override
	public void setAdditionalClickData(RegionData regData, BlockVector3D blockPos) {
		TriangleRegionData triRegData = (TriangleRegionData) regData;
		Vector3D pos = blockPos.toVector3D();
		if(triRegData.getPos2() == null) {
			triRegData.setPos2(pos);
		}else {
			triRegData.setPos3(pos);
		}
	}

	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		return map;
	}
	
}
