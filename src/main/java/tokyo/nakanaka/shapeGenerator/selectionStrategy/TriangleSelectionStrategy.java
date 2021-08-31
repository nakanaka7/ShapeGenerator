package tokyo.nakanaka.shapeGenerator.selectionStrategy;

import static tokyo.nakanaka.shapeGenerator.MaxMinCalculator.max;
import static tokyo.nakanaka.shapeGenerator.MaxMinCalculator.min;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.logger.LogColor;
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
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Triangle;

public class TriangleSelectionStrategy implements SelectionStrategy {

	@Override
	public RegionBuildingData newRegionBuildingData() {
		RegionBuildingData data = new RegionBuildingData.Builder()
				.addDataTag("pos1", DataType.VECTOR3D)
				.addDataTag("pos2", DataType.VECTOR3D)
				.addDataTag("pos3", DataType.VECTOR3D)
				.addDataTag("thickness", DataType.DOUBLE)
				.build();
		data.putDouble("thickness", 1.0);
		return data;
	}
	
	@Override
	public String getLeftClickDescription() {
		return "Set pos1";
	}

	@Override
	public String getRightClickDescription() {
		return "Set pos2, pos3";
	}

	@Override
	public void onLeftClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		data.putVector3D("pos1", blockPos.toVector3D());
		data.putVector3D("pos2", null);
		data.putVector3D("pos3", null);
	}

	@Override
	public void onRightClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos) {
		Vector3D pos1 = data.getVector3D("pos1");
		if(pos1 == null) {
			logger.print(LogColor.RED + "Set pos1 first");
			return;
		}
		Vector3D pos = blockPos.toVector3D();
		if(data.getVector3D("pos2") == null) {
			data.putVector3D("pos2", pos);
		}else {
			data.putVector3D("pos3", pos);
		}
	}

	@Override
	public List<String> regionKeyList() {
		return List.of("pos1", "pos2", "pos3", "thickness");
	}
	
	@Override
	public String getDefaultOffsetKey() {
		return "pos1";
	}

	@Override
	public Map<String, SelSubCommandHandler> getSelSubCommandHandlerMap() {
		Map<String, SelSubCommandHandler> map = new HashMap<>();
		map.put("pos1", new PosCommandHandler("pos1"));
		map.put("pos2", new PosCommandHandler("pos2"));
		map.put("pos3", new PosCommandHandler("pos3"));
		map.put("thickness", new LengthCommandHandler("thickness"));
		return map;
	}

	@Override
	public CuboidBoundRegion buildBoundRegion3D(RegionBuildingData data) {
		Vector3D pos1 = data.getVector3D("pos1");
		Vector3D pos2 = data.getVector3D("pos2");
		Vector3D pos3 = data.getVector3D("pos3");
		Double thickness = data.getDouble("thickness");
		if(pos1 == null || pos2 == null || pos3 == null || thickness == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Triangle(pos1.getX(), pos1.getY(), pos1.getZ(),
				pos2.getX(), pos2.getY(), pos2.getZ(),
				pos3.getX(), pos3.getY(), pos3.getZ(), thickness);
		double ubx = max(pos1.getX(), pos2.getX(), pos3.getX()) + thickness / 2;
		double uby = max(pos1.getY(), pos2.getY(), pos3.getY()) + thickness / 2;
		double ubz = max(pos1.getZ(), pos2.getZ(), pos3.getZ()) + thickness / 2;
		double lbx = min(pos1.getX(), pos2.getX(), pos3.getX()) - thickness / 2;
		double lby = min(pos1.getY(), pos2.getY(), pos3.getY()) - thickness / 2;
		double lbz = min(pos1.getZ(), pos2.getZ(), pos3.getZ()) - thickness / 2;
		return new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
	}

	@Override
	public BoundRegion3D buildBoundRegion3D(Map<String, Object> regionDataMap) {
		Vector3D pos1 = (Vector3D) regionDataMap.get("pos1");
		Vector3D pos2 = (Vector3D) regionDataMap.get("pos2");
		Vector3D pos3 = (Vector3D) regionDataMap.get("pos3");
		Double thickness = (Double) regionDataMap.get("thickness");
		if(pos1 == null || pos2 == null || pos3 == null || thickness == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Triangle(pos1.getX(), pos1.getY(), pos1.getZ(),
				pos2.getX(), pos2.getY(), pos2.getZ(),
				pos3.getX(), pos3.getY(), pos3.getZ(), thickness);
		double ubx = max(pos1.getX(), pos2.getX(), pos3.getX()) + thickness / 2;
		double uby = max(pos1.getY(), pos2.getY(), pos3.getY()) + thickness / 2;
		double ubz = max(pos1.getZ(), pos2.getZ(), pos3.getZ()) + thickness / 2;
		double lbx = min(pos1.getX(), pos2.getX(), pos3.getX()) - thickness / 2;
		double lby = min(pos1.getY(), pos2.getY(), pos3.getY()) - thickness / 2;
		double lbz = min(pos1.getZ(), pos2.getZ(), pos3.getZ()) - thickness / 2;
		return new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
	}

}
