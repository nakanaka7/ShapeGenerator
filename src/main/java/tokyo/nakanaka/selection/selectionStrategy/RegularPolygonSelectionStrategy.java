package tokyo.nakanaka.selection.selectionStrategy;

import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.math.region2D.Region2D;
import tokyo.nakanaka.math.region2D.RegularPolygon;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.math.region3D.Region3Ds;
import tokyo.nakanaka.math.region3D.ThickenedRegion3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.RegionBuildingData.DataType;
import tokyo.nakanaka.selection.selSubCommandHandler.AxisCommandHandler;
import tokyo.nakanaka.selection.selSubCommandHandler.LengthCommandHandler;
import tokyo.nakanaka.selection.selSubCommandHandler.PosCommandHandler;
import tokyo.nakanaka.selection.selSubCommandHandler.RegularPolygonSideCommandHandler;
import tokyo.nakanaka.selection.selSubCommandHandler.SelSubCommandHandler;

public class RegularPolygonSelectionStrategy implements SelectionStrategy {

	@Override
	public RegionBuildingData newRegionBuildingData() {
		RegionBuildingData data = new RegionBuildingData.Builder()
				.addDataTag("center", DataType.VECTOR3D)
				.addDataTag("radius", DataType.DOUBLE)
				.addDataTag("side", DataType.INTEGER)
				.addDataTag("thickness", DataType.DOUBLE)
				.addDataTag("axis", DataType.STRING)
				.build();
		data.putInteger("side", 3);
		data.putDouble("thickness", 1.0);
		data.putString("axis", "y");
		return data;
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
		data.putVector3D("center", blockPos.toVector3D());
		data.putDouble("radius", null);
	}

	@Override
	public void onRightClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		Vector3D center = data.getVector3D("center");
		if(center == null) {
			logger.print(LogColor.RED + "Set center first");
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
		return Arrays.asList(new PosCommandHandler("center"),
				new LengthCommandHandler("radius"),
				new RegularPolygonSideCommandHandler(),
				new LengthCommandHandler("thickness"),
				new AxisCommandHandler());
	}

	@Override
	public CuboidBoundRegion buildBoundRegion3D(RegionBuildingData data) {
		Vector3D center = data.getVector3D("center");
		Double radius = data.getDouble("radius");
		Integer side = data.getInteger("side");
		Double thickness = data.getDouble("thickness");
		if(center == null || radius == null || side == null || thickness == null) {
			throw new IllegalStateException();
		}
		Axis axis = Axis.valueOf(data.getString("axis").toUpperCase());
		Region2D regularPoly = new RegularPolygon(side);
		Region3D region = new ThickenedRegion3D(regularPoly, thickness);
		region = Region3Ds.linearTransform(region, LinearTransformation.ofXScale(radius));
		region = Region3Ds.linearTransform(region, LinearTransformation.ofYScale(radius));
		double ubx = radius;
		double uby = radius;
		double ubz = radius;
		double lbx = - radius;
		double lby = - radius;
		double lbz = - radius;
		switch(axis) {
		case X:
			region = Region3Ds.linearTransform(region, LinearTransformation.ofYRotation(90));
			region = Region3Ds.linearTransform(region, LinearTransformation.ofXRotation(90));
			ubx = thickness/ 2;
			lbx = - thickness/ 2;
			break;
		case Y:
			region = Region3Ds.linearTransform(region, LinearTransformation.ofXRotation(-90));
			region = Region3Ds.linearTransform(region, LinearTransformation.ofYRotation(-90));
			uby = thickness/ 2;
			lby = - thickness/ 2;
			break;
		case Z:
			ubz = thickness/ 2;
			lbz = - thickness/ 2;
			break;
		default:
			break;
		}
		CuboidBoundRegion bound = new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
		return bound.createShiftedRegion(center);
	}

}
