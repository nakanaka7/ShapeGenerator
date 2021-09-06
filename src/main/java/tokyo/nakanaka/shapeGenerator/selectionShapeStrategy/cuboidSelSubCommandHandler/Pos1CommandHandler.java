package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.cuboidSelSubCommandHandler;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.regionData.CuboidRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.PosCommandHandler;

public class Pos1CommandHandler extends PosCommandHandler {

	public Pos1CommandHandler() {
		super("pos1");
	}

	@Override
	protected RegionData newRegionData() {
		return new CuboidRegionData();
	}
	
	@Override
	protected void setPos(RegionData regData, Vector3D pos) {
		CuboidRegionData cuboidRegData = (CuboidRegionData)regData;
		cuboidRegData.setPos1(pos);
	}
	
}
