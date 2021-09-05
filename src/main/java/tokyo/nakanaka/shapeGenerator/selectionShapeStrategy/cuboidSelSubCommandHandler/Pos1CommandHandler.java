package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.cuboidSelSubCommandHandler;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.regionData.CuboidRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.AbstractPosCommandHandler;

public class Pos1CommandHandler extends AbstractPosCommandHandler {

	public Pos1CommandHandler() {
		super("pos1");
	}

	@Override
	protected void setPos(RegionData regData, Vector3D pos) {
		CuboidRegionData cuboidRegData = (CuboidRegionData)regData;
		cuboidRegData.setPos1(pos);
	}
	
}
