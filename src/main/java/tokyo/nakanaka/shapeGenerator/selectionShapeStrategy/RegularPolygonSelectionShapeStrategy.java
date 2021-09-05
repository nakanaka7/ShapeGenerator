package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegularPolygonRegionData;

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
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		return map;
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

}
