package tokyo.nakanaka.selection.torus;

import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.geometricProperty.Axis;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;
import tokyo.nakanaka.math.region3D.Region3D;
import tokyo.nakanaka.math.region3D.Region3Ds;
import tokyo.nakanaka.math.region3D.TorusRegion3D;
import tokyo.nakanaka.selection.NonNegativeDoubleCommandHandler;
import tokyo.nakanaka.selection.PosCommandHandler;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelSubCommandHandler;
import tokyo.nakanaka.selection.SelectionStrategy;
import tokyo.nakanaka.selection.RegionBuildingData.DataType;

public class TorusSelectionStrategy implements SelectionStrategy {

	@Override
	public RegionBuildingData newRegionBuildingData() {
		RegionBuildingData data = new RegionBuildingData.Builder()
				.addDataTag("center", DataType.VECTOR3D)
				.addDataTag("radius_main", DataType.DOUBLE)
				.addDataTag("radius_sub", DataType.DOUBLE)
				.addDataTag("axis", DataType.STRING)
				.build();
		data.putString("axis", "y");
		return data;
	}

	@Override
	public void onLeftClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		Vector3D center = blockPos.toVector3D();
		data.putVector3D("center", center);
		data.putDouble("radius_main", null);
		data.putDouble("radius_sub", null);
	}

	@Override
	public void onRightClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		Vector3D pos = blockPos.toVector3D();
		Vector3D center = data.getVector3D("center");
		Double radiusMain = data.getDouble("radius_main");
		Double radiusSub = data.getDouble("radius_sub");
		Axis axis = Axis.valueOf(data.getString("axis").toUpperCase());
		if(center == null) {
			logger.print(LogColor.RED + "Set center first");
			return;
		}else if(radiusMain == null) {
			radiusMain = pos.negate(center).getAbsolute();
			data.putDouble("radius_main", radiusMain);
		}else {
			Vector3D e1 = axis.toVector3D();
			Vector3D p = pos.negate(center);
			double p1 = p.innerProduct(e1);
			Vector3D p2e2 = p.negate(e1.multiply(p1));
			Vector3D e2 = p2e2.divide(p2e2.getAbsolute());
			radiusSub = p.negate(e2.multiply(radiusMain)).getAbsolute() + 0.5;
			data.putDouble("radius_sub", radiusSub);
		}
	}

	@Override
	public String getDefaultOffsetLabel() {
		return "center";
	}

	@Override
	public List<SelSubCommandHandler> getSelSubCommandHandlers() {
		return Arrays.asList(new PosCommandHandler("center"),
				new NonNegativeDoubleCommandHandler("radius_main"),
				new NonNegativeDoubleCommandHandler("radius_sub"),
				new AxisCommandHandler());
	}

	@Override
	public BoundRegion3D buildBoundRegion3D(RegionBuildingData data) {
		Vector3D center = data.getVector3D("center");
		Double radiusMain = data.getDouble("radius_main");
		Double radiusSub = data.getDouble("radius_sub");
		if(center == null || radiusMain == null || radiusSub == null) {
			throw new IllegalStateException();
		}
		Axis axis = Axis.valueOf(data.getString("axis").toUpperCase());
		Region3D region = new TorusRegion3D(radiusMain, radiusSub);
		switch(axis) {
		case X:
			region = Region3Ds.linearTransform(region, LinearTransformation.ofYRotation(90));
			break;
		case Y:
			region = Region3Ds.linearTransform(region, LinearTransformation.ofXRotation(90));
			break;
		case Z:
			break;
		}
		region = Region3Ds.shift(region, center);
		double ubx = center.getX() + radiusMain + radiusSub;
		double uby = center.getY() + radiusMain + radiusSub;
		double ubz = center.getZ() + radiusMain + radiusSub;
		double lbx = center.getX() - radiusMain - radiusSub;
		double lby = center.getY() - radiusMain - radiusSub;
		double lbz = center.getZ() - radiusMain - radiusSub;
		return new BoundRegion3D(region, ubx, uby, ubz, lbx, lby, lbz);
	}

}
