package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
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
		if(diamondRegData.getRadiusX() == null) {
			diamondRegData.setRadiusX(radius);
		}else if(diamondRegData.getRadiusY() == null) {
			diamondRegData.setRadiusY(radius);
		}else {
			diamondRegData.setRadiusZ(radius);
		}	
	}
	
	@Override
	public Vector3D defaultOffset(RegionData regData) {
		DiamondRegionData diamondRegData = (DiamondRegionData)regData;
		return diamondRegData.getCenter();
	}

}
