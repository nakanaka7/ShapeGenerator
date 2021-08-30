package tokyo.nakanaka.selection.selectionStrategy;

import static tokyo.nakanaka.logger.shapeGenerator.LogConstant.HEAD_ERROR;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.RegionBuildingData.DataType;
import tokyo.nakanaka.selection.selSubCommandHandler.LengthCommandHandler;
import tokyo.nakanaka.selection.selSubCommandHandler.PosCommandHandler;
import tokyo.nakanaka.selection.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Diamond;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3Ds;

public class DiamondSelectionStrategy implements SelectionStrategy {

	@Override
	public RegionBuildingData newRegionBuildingData() {
		return new RegionBuildingData.Builder()
				.addDataTag("center", DataType.VECTOR3D)
				.addDataTag("radius_x", DataType.DOUBLE)
				.addDataTag("radius_y", DataType.DOUBLE)
				.addDataTag("radius_z", DataType.DOUBLE)
				.build();
	}

	@Override
	public String getLeftClickDescription() {
		return "Set center";
	}

	@Override
	public String getRightClickDescription() {
		return "Set radius_x, radius_y, radius_z";
	}

	@Override
	public void onLeftClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		Vector3D center = blockPos.toVector3D();
		data.putVector3D("center", center);
		data.putDouble("radius_x", null);
		data.putDouble("radius_y", null);
		data.putDouble("radius_z", null);
	}

	@Override
	public void onRightClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		Vector3D center = data.getVector3D("center");
		if(center == null) {
			logger.print(HEAD_ERROR + "Set center first");
			return;
		}
		Vector3D pos = blockPos.toVector3D();
		double radius = pos.negate(center).getAbsolute() + 0.5;
		if(data.get("radius_x") == null) {
			data.putDouble("radius_x", radius);
		}else if(data.get("radius_y") == null) {
			data.putDouble("radius_y", radius);
		}else {
			data.putDouble("radius_z", radius);
		}	
	}

	@Override
	public String getDefaultOffsetLabel() {
		return "center";
	}

	@Override
	public List<SelSubCommandHandler> getSelSubCommandHandlers() {
		List<SelSubCommandHandler> list = new ArrayList<>();
		list.add(new PosCommandHandler("center"));
		list.add(new LengthCommandHandler("radius_x"));
		list.add(new LengthCommandHandler("radius_y"));
		list.add(new LengthCommandHandler("radius_z"));
		return list;
	}

	@Override
	public BoundRegion3D buildBoundRegion3D(RegionBuildingData data) {
		Vector3D center = data.getVector3D("center");
		Double radiusX = data.getDouble("radius_x");
		Double radiusY = data.getDouble("radius_y");
		Double radiusZ = data.getDouble("radius_z");
		if(center == null || radiusX == null || radiusY == null || radiusZ == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Diamond(radiusX, radiusY, radiusZ);
		region = Region3Ds.shift(region, center);
		double ubx = center.getX() + radiusX;
		double uby = center.getY() + radiusY;
		double ubz = center.getZ() + radiusZ;
		double lbx = center.getX() - radiusX;
		double lby = center.getY() - radiusY;
		double lbz = center.getZ() - radiusZ;
		return new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
	}

}
