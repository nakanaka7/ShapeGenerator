package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.cuboidSelSubCommandHandler;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.regionData.CuboidRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.AbstractPosCommandHandler;

public class Pos2CommandHandler extends AbstractPosCommandHandler {
	
	public Pos2CommandHandler() {
		super("pos2");
	}

	@Override
	protected void setPos(RegionData regData, Vector3D pos) {
		CuboidRegionData cuboidRegData = (CuboidRegionData)regData;
		cuboidRegData.setPos2(pos);
	}
	
}
