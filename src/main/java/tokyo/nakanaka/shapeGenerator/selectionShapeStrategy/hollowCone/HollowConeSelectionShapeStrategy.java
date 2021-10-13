package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.hollowCone;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.Direction;
import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.region3D.HollowCone;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.regionBound.CuboidBound;
import tokyo.nakanaka.shapeGenerator.math.regionBound.RegionBound;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.DirectionCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.LengthCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.PosCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

public class HollowConeSelectionShapeStrategy implements SelectionShapeStrategy {
	private static final String CENTER = "center";
	private static final String OUTER_RADIUS = "outer_radius";
	private static final String INNER_RADIUS = "inner_radius";
	private static final String HEIGHT = "height";
	private static final String DIRECTION = "direction";

	public SelectionData newSelectionData(World world) {
		SelectionData selData = new SelectionData(world, CENTER, CENTER, OUTER_RADIUS, INNER_RADIUS, HEIGHT, DIRECTION);
		selData.setExtraData(DIRECTION, Direction.UP);
		return selData;
	}

	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put(CENTER, new PosCommandHandler(CENTER, this::newSelectionData));
		map.put(OUTER_RADIUS, new LengthCommandHandler(OUTER_RADIUS, this::newSelectionData));
		map.put(INNER_RADIUS, new LengthCommandHandler(INNER_RADIUS, this::newSelectionData));
		map.put(HEIGHT, new LengthCommandHandler(HEIGHT, this::newSelectionData));
		map.put(DIRECTION, new DirectionCommandHandler(this::newSelectionData));
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
		Direction dir = (Direction)selData.getExtraData(DIRECTION);
		Vector3D pos = blockPos.toVector3D();
		Vector3D center = switch (dir) {
			case NORTH -> pos.add(new Vector3D(0, 0, 0.5));
			case SOUTH -> pos.negate(new Vector3D(0, 0, 0.5));
			case EAST -> pos.negate(new Vector3D(0.5, 0, 0));
			case WEST -> pos.add(new Vector3D(0.5, 0, 0));
			case UP -> pos.negate(new Vector3D(0, 0.5, 0));
			case DOWN -> pos.add(new Vector3D(0, 0.5, 0));
		};
		selData.setExtraData(CENTER, center);
	}

	@Override
	public void onRightClick(SelectionData selData, BlockVector3D blockPos) {
		var center = (Vector3D)selData.getExtraData(CENTER);
		if(center == null) {
			throw new IllegalStateException();
		}
		Vector3D pos = blockPos.toVector3D();
		double dx = pos.getX() - center.getX();
		double dy = pos.getY() - center.getY();
		double dz = pos.getZ() - center.getZ();
		double outerRadius;
		double height;
		Direction dir = (Direction)selData.getExtraData(DIRECTION);
		outerRadius = switch(dir) {
			case NORTH, SOUTH -> Math.max(Math.abs(dx), Math.abs(dy)) + 0.5;
			case EAST, WEST -> Math.max(Math.abs(dy), Math.abs(dz)) + 0.5;
			case UP, DOWN -> Math.max(Math.abs(dz), Math.abs(dx)) + 0.5;
		};
		selData.setExtraData(OUTER_RADIUS, outerRadius);
		selData.setExtraData(INNER_RADIUS, outerRadius - 1);
		height = switch(dir) {
			case NORTH -> Math.max(-dz + 0.5, 0);
			case SOUTH -> Math.max(dz + 0.5, 0);
			case EAST -> Math.max(dx + 0.5, 0);
			case WEST -> Math.max(-dx + 0.5, 0);
			case UP -> Math.max(dy + 0.5, 0);
			case DOWN -> Math.max(-dy + 0.5, 0);
		};
		selData.setExtraData(HEIGHT, height);
	}

	/**
	 * @throws IllegalStateException if the center, outer radius or innter radius
	 * is not specified, or inner radius >= outer radius
	 */
	@Override
	public Selection buildSelection(SelectionData selData) {
		var center = (Vector3D)selData.getExtraData(CENTER);
		var outerRadius = (Double)selData.getExtraData(OUTER_RADIUS);
		var innerRadius = (Double)selData.getExtraData(INNER_RADIUS);
		var height = (Double)selData.getExtraData(HEIGHT);
		var dir = (Direction)selData.getExtraData(DIRECTION);
		if(center == null || outerRadius == null || innerRadius == null || height == null || dir == null) {
			throw new IllegalStateException();
		}
		if(outerRadius <= 0 || innerRadius <= 0){
			throw new IllegalStateException();
		}
		if(innerRadius >= outerRadius) {
			throw new IllegalStateException();
		}
		Region3D region = new HollowCone(outerRadius, innerRadius, height);
		RegionBound bound = new CuboidBound(outerRadius, outerRadius, height, -outerRadius, -outerRadius, 0);
		Selection sel = new Selection(selData.world(), Vector3D.ZERO, region, bound);
		switch(dir) {
		case NORTH -> sel = sel.createRotated(Axis.Y, 180);
		case SOUTH -> {}
		case EAST -> sel = sel.createRotated(Axis.Y, 90);
		case WEST -> sel = sel.createRotated(Axis.Y, -90);
		case UP -> sel = sel.createRotated(Axis.X, -90);
		case DOWN -> sel = sel.createRotated(Axis.X, 90);
		}
		return sel.createShifted(center).withOffset(selData.getOffset());
	}
	
}