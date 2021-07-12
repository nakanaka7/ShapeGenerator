package tokyo.nakanaka.selection.selectionStrategy;

import static tokyo.nakanaka.logger.LogConstant.HEAD_ERROR;

import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.CuboidBoundRegion;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.math.region3D.Region3Ds;
import tokyo.nakanaka.math.region3D.Sphere;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.RegionBuildingData.DataType;
import tokyo.nakanaka.selection.selSubCommandHandler.LengthCommandHandler;
import tokyo.nakanaka.selection.selSubCommandHandler.PosCommandHandler;
import tokyo.nakanaka.selection.selSubCommandHandler.SelSubCommandHandler;

public class SphereSelectionStrategy implements SelectionStrategy{

	@Override
	public RegionBuildingData newRegionBuildingData() {
		return new RegionBuildingData.Builder()
				.addDataTag("center", DataType.VECTOR3D)
				.addDataTag("radius", DataType.DOUBLE)
				.build();
	}

	@Override
	public String getLeftClickDescription() {
		return "Set center";
	}

	@Override
	public String getRightClickDescription() {
		return "Set radius by the center coordinates";
	}
	
	@Override
	public void onLeftClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		Vector3D center = blockPos.toVector3D();
		data.putVector3D("center", center);
	}

	@Override
	public void onRightClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		Vector3D center = data.getVector3D("center");
		if(center == null) {
			logger.print(HEAD_ERROR + "Set center first");
			return;
		}
		Vector3D pos = blockPos.toVector3D();
		double radius = Math.floor(pos.negate(center).getAbsolute()) + 0.5;
		data.putDouble("radius", radius);
	}

	@Override
	public String getDefaultOffsetLabel() {
		return "center";
	}

	@Override
	public List<SelSubCommandHandler> getSelSubCommandHandlers() {
		return Arrays.asList(new PosCommandHandler("center"), new LengthCommandHandler("radius"));
	}

	@Override
	public CuboidBoundRegion buildBoundRegion3D(RegionBuildingData data) {
		Vector3D center = data.getVector3D("center");
		Double radius = data.getDouble("radius");
		if(center == null || radius == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Sphere(radius);
		region = Region3Ds.shift(region, center);
		double ubx = center.getX() + radius;
		double uby = center.getY() + radius;
		double ubz = center.getZ() + radius;
		double lbx = center.getX() - radius;
		double lby = center.getY() - radius;
		double lbz = center.getZ() - radius;
		return new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
	}

}
