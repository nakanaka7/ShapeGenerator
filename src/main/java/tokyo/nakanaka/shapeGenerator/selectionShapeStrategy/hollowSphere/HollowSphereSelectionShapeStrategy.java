package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.hollowSphere;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.region3D.HollowSphere;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.regionBound.RegionBound;
import tokyo.nakanaka.shapeGenerator.math.regionBound.SphereBound;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.LengthCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.MaxMinCalculator;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.PosCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

public class HollowSphereSelectionShapeStrategy implements SelectionShapeStrategy {
	private static final String CENTER = "center";
	private static final String OUTER_RADIUS = "outer_radius";
	private static final String INNER_RADIUS = "inner_radius";

	public SelectionData newSelectionData(World world) {
		return new SelectionData(world, CENTER, CENTER, OUTER_RADIUS, INNER_RADIUS);
	}

	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put(CENTER, new PosCommandHandler(CENTER, this::newSelectionData));
		map.put(OUTER_RADIUS, new LengthCommandHandler(OUTER_RADIUS, this::newSelectionData));
		map.put(INNER_RADIUS, new LengthCommandHandler(INNER_RADIUS, this::newSelectionData));
		return map;
	}

	@Override
	public String leftClickDescription() {
		return "Set center";
	}

	@Override
	public String rightClickDescription() {
		return "Set outer_radius, inner_radius";
	}

	@Override
	public void onLeftClick(SelectionData selData, BlockVector3D blockPos) {
		selData.setExtraData(CENTER, blockPos.toVector3D());
	}

	@Override
	public void onRightClick(SelectionData selData, BlockVector3D blockPos) {
		Vector3D center = (Vector3D) selData.getExtraData(CENTER);
		if(center == null) {
			throw new IllegalStateException();
		}
		Vector3D dp = blockPos.toVector3D().negate(center);
		double abdx = Math.abs(dp.getX());
		double abdy = Math.abs(dp.getY());
		double abdz = Math.abs(dp.getZ());
		double outerRadius = MaxMinCalculator.max(abdx, abdy, abdz) + 0.5;
		selData.setExtraData(OUTER_RADIUS, outerRadius);
		selData.setExtraData(INNER_RADIUS, outerRadius - 1);
	}

	/**
	 * @throws IllegalStateException if the center, outer radius or innter radius
	 * is not specified, outer radius <= 0, inner radius <= 0, or inner radius >= outer radius
	 */
	@Override
	public Selection buildSelection(SelectionData selData) {
		var center = (Vector3D)selData.getExtraData(CENTER);
		var outerRadius = (Double)selData.getExtraData(OUTER_RADIUS);
		var innerRadius = (Double)selData.getExtraData(INNER_RADIUS);
		if(center == null || outerRadius == null || innerRadius == null) {
			throw new IllegalStateException();
		}
		if(outerRadius <= 0 || innerRadius <= 0){
			throw new IllegalStateException();
		}
		if(innerRadius >= outerRadius) {
			throw new IllegalStateException();
		}
		Region3D region = new HollowSphere(outerRadius, innerRadius);
		RegionBound bound = new SphereBound(outerRadius);
		var sel = new Selection(selData.world(), Vector3D.ZERO, region, bound);
		return sel.createShifted(center).withOffset(selData.getOffset());
	}

}