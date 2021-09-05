package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.TorusRegionData;

public class TorusSelectionShapeStrategy implements SelectionShapeStrategy {

	@Override
	public RegionData newRegionData() {
		return new TorusRegionData();
	}
	
	@Override
	public String leftClickDescription() {
		return "Set center";
	}

	@Override
	public String rightClickDescription() {
		return "Set radius_main, radius_sub";
	}
	
	@Override
	public void setFirstClickData(RegionData regData, BlockVector3D blockPos) {
		TorusRegionData torusRegData = (TorusRegionData) regData;
		torusRegData.setCenter(blockPos.toVector3D());
	}

	@Override
	public void setAdditionalClickData(RegionData regData, BlockVector3D blockPos) {
		TorusRegionData torusRegData = (TorusRegionData) regData;
		Vector3D pos = blockPos.toVector3D();
		Vector3D center = torusRegData.getCenter();
		Double radiusMain = torusRegData.getRadiusMain();
		Double radiusSub = torusRegData.getRadiusSub();
		Axis axis = torusRegData.getAxis();
		if(radiusMain == null) {
			radiusMain = pos.negate(center).getAbsolute();
			torusRegData.setRadiusMain(radiusMain);
		}else {
			Vector3D e1 = axis.toVector3D();
			Vector3D p = pos.negate(center);
			double p1 = p.innerProduct(e1);
			Vector3D p2e2 = p.negate(e1.multiply(p1));
			Vector3D e2 = p2e2.divide(p2e2.getAbsolute());
			radiusSub = p.negate(e2.multiply(radiusMain)).getAbsolute() + 0.5;
			torusRegData.setRadiusSub(radiusSub);
		}
	}
	
	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		return map;
	}
	
}
