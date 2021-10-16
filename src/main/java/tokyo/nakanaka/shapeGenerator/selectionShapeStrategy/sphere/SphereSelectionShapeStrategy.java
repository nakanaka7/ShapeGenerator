package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.sphere;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Sphere;
import tokyo.nakanaka.shapeGenerator.math.regionBound.RegionBound;
import tokyo.nakanaka.shapeGenerator.math.regionBound.SphereBound;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.LengthCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.MaxMinCalculator;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.PosCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

public class SphereSelectionShapeStrategy implements SelectionShapeStrategy {
	private static final String CENTER = "center";
	private static final String RADIUS = "radius";

	public static SelectionData newSelectionData(World world) {
		return new SelectionData(world, CENTER, CENTER, RADIUS);
	}

	public static Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put(CENTER, new PosCommandHandler(CENTER, SphereSelectionShapeStrategy::newSelectionData));
		map.put(RADIUS, new LengthCommandHandler(RADIUS, SphereSelectionShapeStrategy::newSelectionData));
		return map;
	}
	
	@Override
	public String leftClickDescription() {
		return "Set center";
	}

	@Override
	public String rightClickDescription() {
		return "Set radius by the center coordinates";
	}

	public static void onLeftClick(SelectionData selData, BlockVector3D blockPos) {
		selData.setExtraData("center", blockPos.toVector3D());
	}

	public static void onRightClick(SelectionData selData, BlockVector3D blockPos) {
		Vector3D center = (Vector3D) selData.getExtraData(CENTER);
		if(center == null) {
			throw new IllegalStateException();
		}
		Vector3D dp = blockPos.toVector3D().negate(center);
		double abdx = Math.abs(dp.getX());
		double abdy = Math.abs(dp.getY());
		double abdz = Math.abs(dp.getZ());
		double radius = MaxMinCalculator.max(abdx, abdy, abdz) + 0.5;
		selData.setExtraData(RADIUS, radius);
	}

	/**
	 * @throws IllegalStateException if the center or radius is not specified, or radius is smaller than
	 * or equals to 0
	 */
	public static Selection buildSelection(SelectionData selData) {
		var center = (Vector3D)selData.getExtraData(CENTER);
		var radius = (Double)selData.getExtraData(RADIUS);
		if(center == null || radius == null) {
			throw new IllegalStateException();
		}
		if(radius <= 0) {
			throw new IllegalStateException();
		}
		Region3D region = new Sphere(radius).createShifted(center);
		RegionBound bound = new SphereBound(radius).createShifted(center);
		return new Selection(selData.world(), selData.getOffset(), region, bound);
	}
	
}
