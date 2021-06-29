package tokyo.nakanaka.selection.sphere;

import static tokyo.nakanaka.logger.LogConstant.HEAD_ERROR;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.ClickBlockHandler;
import tokyo.nakanaka.selection.RegionBuilder;
import tokyo.nakanaka.selection.RegionBuildingData;

public class SphereClickBlockHandler implements ClickBlockHandler{

	@Override
	public void onLeftClickBlock(RegionBuilder builder, Logger logger, BlockVector3D blockPos) {
		SphereRegionBuilder sphereBuilder = (SphereRegionBuilder)builder;
		Vector3D pos = new Vector3D(blockPos.getX(), blockPos.getY(), blockPos.getZ());
		sphereBuilder.setCenter(pos);
		sphereBuilder.setRadius(0);
	}

	@Override
	public void onRightClickBlock(RegionBuilder builder, Logger logger, BlockVector3D blockPos) {
		SphereRegionBuilder sphereBuilder = (SphereRegionBuilder)builder;
		Vector3D pos = new Vector3D(blockPos.getX(), blockPos.getY(), blockPos.getZ());
		Vector3D center = sphereBuilder.getCenter();
		if(center == null) {
			logger.print(HEAD_ERROR + "Set center first");
		}
		double radius = Math.floor(pos.negate(center).getAbsolute()) + 0.5;
		sphereBuilder.setRadius(radius);
	}

	@Override
	public void onLeftClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		data.putVector3D("center", blockPos.toVector3D());
		data.putDouble("radius", 0);
	}

	@Override
	public void onRightClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		Vector3D center = data.getVector3D("center");
		if(center == null) {
			logger.print(HEAD_ERROR + "Set center first");
		}
		double radius = Math.floor(blockPos.toVector3D().negate(center).getAbsolute()) + 0.5;
		if(radius < 0) {
			logger.print("radius must be larger than 0");
			return;
		}
		data.putDouble("radius", radius);
	}

}
