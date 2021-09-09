package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3Ds;
import tokyo.nakanaka.shapeGenerator.math.region3D.Torus;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.TorusRegionData;

public class TorusSelectionShapeStrategy implements SelectionShapeStrategy {

	@Override
	public SelectionData newSelectionData(World world) {
		SelectionData selData = new SelectionData(world, "center", "center", "radius_main", "radius_sub", "axis");
		selData.setExtraData("axis", Axis.Y);
		return selData;
	}
	
	@Override
	public RegionData newRegionData() {
		return new TorusRegionData();
	}
	
	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put("center", new PosCommandHandlerNew("center"));
		map.put("radius_main", new LengthCommandHandlerNew("radius_main"));
		map.put("radius_sub", new LengthCommandHandlerNew("radius_sub"));
		map.put("axis", new AxisCommandHandlerNew());
		return map;
	}
	
	@Override
	public String leftClickDescription() {
		return "Set center";
	}

	@Override
	public String rightClickDescription() {
		return "Set radius_main, radius_sub";
	}
		
	@Override
	public void onLeftClick(SelectionData selData, BlockVector3D blockPos) {
		selData.setExtraData("center", blockPos.toVector3D());
	}

	@Override
	public void onRightClick(SelectionData selData, BlockVector3D blockPos) {
		var center = (Vector3D)selData.getExtraData("center");
		if(center == null) {
			throw new IllegalStateException();
		}
		Vector3D pos = blockPos.toVector3D();
		double dx = 2 * Math.abs(pos.getX() - center.getX()) + 1;
		double dy = 2 * Math.abs(pos.getY() - center.getY()) + 1;
		double dz = 2 * Math.abs(pos.getZ() - center.getZ()) + 1;
		double length;
		double height;
		Axis axis = (Axis)selData.getExtraData("axis");
		switch(axis) {
		case X -> {
			length = Math.max(dy, dz);
			height = dx;
		}
		case Y -> {
			length = Math.max(dz, dx);
			height = dy;
		}
		case Z -> {
			length = Math.max(dx, dy);
			height = dz;
		}
		default -> throw new IllegalArgumentException();
		}
		double radiusSub = height / 2;
		double radiusMain = length / 2 - radiusSub;
		selData.setExtraData("radius_main", radiusMain);
		selData.setExtraData("radius_sub", radiusSub);
	}
	
	@Override
	public Selection buildSelection(SelectionData selData) {
		var center = (Vector3D)selData.getExtraData("center");
		var radiusMain = (Double)selData.getExtraData("radius_main");
		var radiusSub = (Double)selData.getExtraData("radius_sub");
		var axis = (Axis)selData.getExtraData("axis");
		if(center == null || radiusMain == null || radiusSub == null || axis == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Torus(radiusMain, radiusSub);
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
		BoundRegion3D boundReg = new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
		return new Selection(selData.world(), boundReg, selData.getOffset());
	}
	
}
