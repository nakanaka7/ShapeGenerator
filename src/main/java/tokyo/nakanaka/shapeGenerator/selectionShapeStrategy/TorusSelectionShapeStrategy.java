package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.region3D.Cuboid;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Torus;

public class TorusSelectionShapeStrategy implements SelectionShapeStrategy {
	private String CENTER = "center";
	private String MAJOR_RADIUS = "major_radius";
	private String MINOR_RADIUS = "minor_radius";
	private String AXIS = "axis";
	
	@Override
	public SelectionData newSelectionData(World world) {
		SelectionData selData = new SelectionData(world, CENTER, CENTER, MAJOR_RADIUS, MINOR_RADIUS, AXIS);
		selData.setExtraData(AXIS, Axis.Y);
		return selData;
	}
	
	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put(CENTER, new PosCommandHandler(CENTER, this::newSelectionData));
		map.put(MAJOR_RADIUS, new LengthCommandHandler(MAJOR_RADIUS, this::newSelectionData));
		map.put(MINOR_RADIUS, new LengthCommandHandler(MINOR_RADIUS, this::newSelectionData));
		map.put(AXIS, new AxisCommandHandler(this::newSelectionData));
		return map;
	}
	
	@Override
	public String leftClickDescription() {
		return "Set center";
	}

	@Override
	public String rightClickDescription() {
		return "Set major_radius, minor_radius";
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
		double majorRadius = length / 2 - minorRadius;
		selData.setExtraData(MAJOR_RADIUS, majorRadius);
		selData.setExtraData(MINOR_RADIUS, minorRadius);
	}
	
	@Override
	public Selection buildSelection(SelectionData selData) {
		var center = (Vector3D)selData.getExtraData(CENTER);
		var majorRadius = (Double)selData.getExtraData(MAJOR_RADIUS);
		var minorRadius = (Double)selData.getExtraData(MINOR_RADIUS);
		var axis = (Axis)selData.getExtraData(AXIS);
		if(center == null || majorRadius == null || minorRadius == null || axis == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Torus(majorRadius, minorRadius);
		double a = majorRadius + minorRadius;
		double b = minorRadius;
		Selection sel = new Selection(selData.world(), Vector3D.ORIGIN, region, new Cuboid(a, a, b, -a, -a, -b));
		switch(axis) {
		case X -> sel = sel.createRotated(Axis.Y, 90);
		case Y -> sel = sel.createRotated(Axis.X, -90);
		case Z -> {}
		};
		return sel.createShifted(center).withOffset(selData.getOffset());
	}
	
}
