package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.tetrahedronSelSubCommandHandler;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.TetrahedronRegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.AbstractPosCommandHandler;

public class Pos4CommandHandler extends AbstractPosCommandHandler {

	public Pos4CommandHandler() {
		super("pos4");
	}

	@Override
	protected void setPos(RegionData regData, Vector3D pos) {
		TetrahedronRegionData tetraRegData = (TetrahedronRegionData)regData;
		tetraRegData.setPos4(pos);
	}
	
}
