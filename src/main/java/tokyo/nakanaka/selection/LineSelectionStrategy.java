package tokyo.nakanaka.selection;

import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
import tokyo.nakanaka.math.region3D.LineRegion3D;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.selection.RegionBuildingData.DataType;

public class LineSelectionStrategy implements SelectionStrategy {
	
	@Override
	public RegionBuildingData newRegionBuildingData() {
		RegionBuildingData data = new RegionBuildingData.Builder()
				.addDataTag("pos1", DataType.VECTOR3D)
				.addDataTag("pos2", DataType.VECTOR3D)
				.addDataTag("thickness", DataType.DOUBLE)
				.build();
		data.putDouble("thickness", 0.5);
		return data;
	}

	@Override
	public void onLeftClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		Vector3D pos1 = blockPos.toVector3D();
		data.putVector3D("pos1", pos1);
	}

	@Override
	public void onRightClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		Vector3D pos2 = blockPos.toVector3D();
		data.putVector3D("pos2", pos2);
	}

	@Override
	public String getDefaultOffsetLabel() {
		return "pos1";
	}

	@Override
	public List<SelSubCommandHandler> getSelSubCommandHandlers() {
		return Arrays.asList(new PosCommandHandler("pos1"),
				new PosCommandHandler("pos2"),
				new NonNegativeDoubleCommandHandler("thickness"));
	}

	@Override
	public BoundRegion3D buildBoundRegion3D(RegionBuildingData data) {
		Vector3D pos1 = data.getVector3D("pos1");
		Vector3D pos2 = data.getVector3D("pos2");
		Double thickness = data.getDouble("thickness");
		if(pos1 == null || pos2 == null || thickness == null) {
			throw new IllegalStateException();
		}
		Region3D region = new LineRegion3D(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ(), thickness);
		double ubx = Math.max(pos1.getX(), pos2.getX()) + thickness;
		double uby = Math.max(pos1.getY(), pos2.getY()) + thickness;
		double ubz = Math.max(pos1.getZ(), pos2.getZ()) + thickness;
		double lbx = Math.min(pos1.getX(), pos2.getX()) - thickness;
		double lby = Math.min(pos1.getY(), pos2.getY()) - thickness;
		double lbz = Math.min(pos1.getZ(), pos2.getZ()) - thickness;
		return new BoundRegion3D(region, ubx, uby, ubz, lbx, lby, lbz);
	}

}
