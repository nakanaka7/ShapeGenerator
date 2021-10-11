package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.hollowTorus;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.region3D.Cuboid;
import tokyo.nakanaka.shapeGenerator.math.region3D.HollowTorus;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.AxisCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.LengthCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.PosCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

public class HollowTorusSelectionShapeStrategy implements SelectionShapeStrategy {
	private static final String CENTER = "center";
	private static final String MAJOR_RADIUS = "major_radius";
	private static final String OUTER_MINOR_RADIUS = "outer_minor_radius";
	private static final String INNER_MINOR_RADIUS = "inner_minor_radius";
	private static final String AXIS = "axis";
	
	@Override
	public SelectionData newSelectionData(World world) {
		SelectionData selData = new SelectionData(world, CENTER, CENTER, MAJOR_RADIUS, OUTER_MINOR_RADIUS, INNER_MINOR_RADIUS, AXIS);
		selData.setExtraData(AXIS, Axis.Y);
		return selData;
	}

	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put(CENTER, new PosCommandHandler(CENTER, this::newSelectionData));
		map.put(MAJOR_RADIUS, new LengthCommandHandler(MAJOR_RADIUS, this::newSelectionData));
		map.put(OUTER_MINOR_RADIUS, new LengthCommandHandler(OUTER_MINOR_RADIUS, this::newSelectionData));
		map.put(INNER_MINOR_RADIUS, new LengthCommandHandler(INNER_MINOR_RADIUS, this::newSelectionData));
		map.put(AXIS, new AxisCommandHandler(this::newSelectionData));
		return map;
	}

	@Override
	public String leftClickDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String rightClickDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLeftClick(SelectionData selData, BlockVector3D blockPos) {
		selData.setExtraData(CENTER, blockPos.toVector3D());
	}

	@Override
	public void onRightClick(SelectionData selData, BlockVector3D blockPos) {
		var center = (Vector3D)selData.getExtraData(CENTER);
		if(center == null) {
			throw new IllegalStateException();
		}
		Vector3D pos = blockPos.toVector3D();
		double dx = 2 * Math.abs(pos.getX() - center.getX()) + 1;
		double dy = 2 * Math.abs(pos.getY() - center.getY()) + 1;
		double dz = 2 * Math.abs(pos.getZ() - center.getZ()) + 1;
		double length;
		double height;
		Axis axis = (Axis)selData.getExtraData(AXIS);
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
		double minorRadius = height / 2;
		double outerMajorRadius = length / 2 - minorRadius;
		if(outerMajorRadius <= 0) {
			throw new IllegalStateException();
		}
		selData.setExtraData(MAJOR_RADIUS, outerMajorRadius);
		selData.setExtraData(OUTER_MINOR_RADIUS, minorRadius);
		selData.setExtraData(INNER_MINOR_RADIUS, minorRadius - 1);
	}

	/**
	 * @throws IllegalStateException if center, major radius, outer minor radius,
	 * inner minor radius, or axis is not specified, or major radius <= 0,
	 * outer minor radius <= 0, inner minor radius <= 0, inner minor radius >= outer minor radius
	 */
	@Override
	public Selection buildSelection(SelectionData selData) {
		var center = (Vector3D)selData.getExtraData(CENTER);
		var majorRadius = (Double)selData.getExtraData(MAJOR_RADIUS);
		var outerMinorRadius = (Double)selData.getExtraData(OUTER_MINOR_RADIUS);
		var innerMinorRadius = (Double)selData.getExtraData(INNER_MINOR_RADIUS);
		var axis = (Axis)selData.getExtraData(AXIS);
		if(center == null || majorRadius == null || outerMinorRadius == null || innerMinorRadius == null || axis == null) {
			throw new IllegalStateException();
		}
		if(majorRadius <= 0 || outerMinorRadius <= 0 || innerMinorRadius <= 0){
			throw new IllegalStateException();
		}
		if(innerMinorRadius >= outerMinorRadius) {
			throw new IllegalStateException();
		}
		Region3D region = new HollowTorus(majorRadius, outerMinorRadius, innerMinorRadius);
		double a = majorRadius + outerMinorRadius;
		double b = outerMinorRadius;
		var sel = new Selection(selData.world(), Vector3D.ZERO, region, new Cuboid(a, a, b, -a, -a, -b));
		switch(axis) {
		case X -> sel = sel.createRotated(Axis.Y, 90);
		case Y -> sel = sel.createRotated(Axis.X, -90);
		case Z -> {}
		};
		return sel.createShifted(center).withOffset(selData.getOffset());
	}

}
