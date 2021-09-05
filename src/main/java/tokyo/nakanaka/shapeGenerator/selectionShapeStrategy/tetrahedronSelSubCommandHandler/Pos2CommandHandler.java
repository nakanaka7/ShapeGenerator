package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.tetrahedronSelSubCommandHandler;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.TetrahedronRegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.AbstractPosCommandHandler;

public class Pos2CommandHandler extends AbstractPosCommandHandler {

	public Pos2CommandHandler() {
		super("pos2");
	}

	@Override
	protected void setPos(RegionData regData, Vector3D pos) {
		TetrahedronRegionData tetraRegData = (TetrahedronRegionData)regData;
		tetraRegData.setPos2(pos);
	}
	
}
