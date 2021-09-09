package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import static tokyo.nakanaka.shapeGenerator.MaxMinCalculator.max;
import static tokyo.nakanaka.shapeGenerator.MaxMinCalculator.min;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Triangle;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.TriangleRegionData;

public class TriangleSelectionShapeStrategy implements SelectionShapeStrategy {

	@Override
	public RegionData newRegionData() {
		return new TriangleRegionData();
	}
	
	@Override
	public SelectionData newSelectionData(World world) {
		SelectionData selData = new SelectionData(world, "pos1", "pos1", "pos2", "pos3", "thickness");
		selData.setExtraData("thickness", 1.0);
		return selData;
	}
	
	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put("pos1", new PosCommandHandlerNew("pos1"));
		map.put("pos2", new PosCommandHandlerNew("pos2"));
		map.put("pos3", new PosCommandHandlerNew("pos3"));
		map.put("thickness", new LengthCommandHandlerNew("thickness"));
		return map;
	}
	
	@Override
	public String leftClickDescription() {
		return "Set pos1";
	}

	@Override
	public String rightClickDescription() {
		return "Set pos2, pos3";
	}
	
	@Override
	public void onLeftClick(SelectionData selData, BlockVector3D blockPos) {
		selData.setExtraData("pos1", blockPos.toVector3D());
		selData.setExtraData("pos2", null);
		selData.setExtraData("pos3", null);
	}
	
	@Override
	public void onRightClick(SelectionData selData, BlockVector3D blockPos) {
		if(selData.getExtraData("pos1") == null) {
			throw new IllegalStateException();
		}
		Vector3D pos = blockPos.toVector3D();
		if(selData.getExtraData("pos2") == null) {
			selData.setExtraData("pos2", pos);
		}else {
			selData.setExtraData("pos3", pos);
		}
	}
	
	@Override
	public Selection buildSelection(SelectionData selData) {
		BoundRegion3D boundReg;
		Vector3D pos1 = (Vector3D) selData.getExtraData("pos1");
		Vector3D pos2 = (Vector3D) selData.getExtraData("pos2");
		Vector3D pos3 = (Vector3D) selData.getExtraData("pos3");
		Double thickness = (Double) selData.getExtraData("thickness");
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
		boundReg = new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
		return new Selection(selData.world(), boundReg, selData.getOffset());
	}
	
}
