package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.shapeGenerator.regionData.LineRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.LengthCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.PosCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;

public class LineSelectionShapeStrategy implements SelectionShapeStrategy {
	
	public RegionData newRegionData() {
		return new LineRegionData();
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
	
}
