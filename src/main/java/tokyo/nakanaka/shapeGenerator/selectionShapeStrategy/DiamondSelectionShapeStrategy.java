package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

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
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.SphereBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Diamond;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3Ds;
import tokyo.nakanaka.shapeGenerator.math.region3D.Sphere;
import tokyo.nakanaka.shapeGenerator.regionData.DiamondRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.diamondSelSubCommandHandler.*;

public class DiamondSelectionShapeStrategy implements SelectionShapeStrategy {

	@Override
	public RegionData newRegionData() {
		return new DiamondRegionData();
	}
	
	@Override
	public SelectionData newSelectionData(World world) {
		return new SelectionData(world, "center", "center", "width", "height", "length");
	}
	
	@Override
	public String leftClickDescription() {
		return "Set center";
	}

	@Override
	public String rightClickDescription() {
		return "Set width, height, length";
	}
	
	@Override
	public void onLeftClick(SelectionData selData, BlockVector3D blockPos) {
		selData.setExtraData("center", blockPos.toVector3D());
	}
	
	@Override
	public void onRightClick(SelectionData selData, BlockVector3D blockPos) {
		Vector3D center = (Vector3D) selData.getExtraData("center");
		if(center == null) {
			throw new IllegalStateException();
		}
		Double width = 2 * Math.abs(center.getX() - blockPos.getX()) + 1;
		Double height = 2 * Math.abs(center.getY() - blockPos.getY()) + 1;
		Double length = 2 * Math.abs(center.getZ() - blockPos.getZ()) + 1;
		selData.setExtraData("width", width);
		selData.setExtraData("height", height);
		selData.setExtraData("length", length);
	}

	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put("center", new PosCommandHandlerNew("center"));
		map.put("width", new LengthCommandHandlerNew("width"));
		map.put("height", new LengthCommandHandlerNew("height"));
		map.put("length", new LengthCommandHandlerNew("length"));
		return map;
	}
	
	@Override
	public Selection buildSelection(SelectionData selData) {
		var center = (Vector3D)selData.getExtraData("center");
		var width = (Double)selData.getExtraData("width");
		var height = (Double)selData.getExtraData("height");
		var length = (Double)selData.getExtraData("length");
		if(center == null || width == null || height == null || length == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Diamond(width / 2, height / 2, length / 2);
		region = Region3Ds.shift(region, center);
		double ubx = center.getX() + width / 2;
		double uby = center.getY() + height / 2;
		double ubz = center.getZ() + length / 2;
		double lbx = center.getX() - width / 2;
		double lby = center.getY() - height / 2;
		double lbz = center.getZ() - length / 2;
		BoundRegion3D boundReg = new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
		return new Selection(selData.world(), boundReg, selData.getOffset());
	}
	
}
