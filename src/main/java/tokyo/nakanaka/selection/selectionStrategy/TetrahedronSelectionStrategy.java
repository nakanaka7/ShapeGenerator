package tokyo.nakanaka.selection.selectionStrategy;

import static tokyo.nakanaka.MaxMinCalculator.max;
import static tokyo.nakanaka.MaxMinCalculator.min;

import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.math.region3D.Tetrahedron;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.RegionBuildingData.DataType;
import tokyo.nakanaka.selection.selSubCommandHandler.PosCommandHandler;
import tokyo.nakanaka.selection.selSubCommandHandler.SelSubCommandHandler;

public class TetrahedronSelectionStrategy implements SelectionStrategy {

	@Override
	public RegionBuildingData newRegionBuildingData() {
		return new RegionBuildingData.Builder()
				.addDataTag("pos1", DataType.VECTOR3D)
				.addDataTag("pos2", DataType.VECTOR3D)
				.addDataTag("pos3", DataType.VECTOR3D)
				.addDataTag("pos4", DataType.VECTOR3D)
				.build();
	}

	@Override
	public String getLeftClickDescription() {
		return "Set pos1";
	}

	@Override
	public String getRightClickDescription() {
		return "Set pos2, pos3, pos4";
	}
	
	@Override
	public void onLeftClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		data.putVector3D("pos1", blockPos.toVector3D());
		data.putVector3D("pos2", null);
		data.putVector3D("pos3", null);
		data.putVector3D("pos4", null);
	}

	@Override
	public void onRightClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		if(data.get("pos2") == null) {
			data.putVector3D("pos2", blockPos.toVector3D());
		}else if(data.get("pos3") == null) {
			data.putVector3D("pos3", blockPos.toVector3D());
		}else {
			data.putVector3D("pos4", blockPos.toVector3D());
		}	
	}

	@Override
	public String getDefaultOffsetLabel() {
		return "pos1";
	}

	@Override
	public List<SelSubCommandHandler> getSelSubCommandHandlers() {
		return Arrays.asList(new PosCommandHandler("pos1"),
				new PosCommandHandler("pos2"),
				new PosCommandHandler("pos3"),
				new PosCommandHandler("pos4"));
	}

	@Override
	public CuboidBoundRegion buildBoundRegion3D(RegionBuildingData data) {
		Vector3D pos1 = data.getVector3D("pos1");
		Vector3D pos2 = data.getVector3D("pos2");
		Vector3D pos3 = data.getVector3D("pos3");
		Vector3D pos4 = data.getVector3D("pos4");
		Region3D region = new Tetrahedron(pos1.getX(), pos1.getY(), pos1.getZ(),
				pos2.getX(), pos2.getY(), pos2.getZ(),
				pos3.getX(), pos3.getY(), pos3.getZ(),
				pos4.getX(), pos4.getY(), pos4.getZ());
		double ubx = max(pos1.getX(), pos2.getX(), pos3.getX(), pos4.getX());
		double uby = max(pos1.getY(), pos2.getY(), pos3.getY(), pos4.getY());
		double ubz = max(pos1.getZ(), pos2.getZ(), pos3.getZ(), pos4.getZ());
		double lbx = min(pos1.getX(), pos2.getX(), pos3.getX(), pos4.getX());
		double lby = min(pos1.getY(), pos2.getY(), pos3.getY(), pos4.getY());
		double lbz = min(pos1.getZ(), pos2.getZ(), pos3.getZ(), pos4.getZ());
		return new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
	}

}
