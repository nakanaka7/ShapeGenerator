package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.hollowRegularPrism;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.Direction;
import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.region3D.Cuboid;
import tokyo.nakanaka.shapeGenerator.math.region3D.HollowRegularPrism;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.DirectionCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.LengthCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.PosCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.regularPolygonSelSubCommandHandler.SideCommandHandler;

import java.util.HashMap;
import java.util.Map;

public class HollowRegularPrismSelectionShapeStrategy {
	private static final String CENTER = "center";
	private static final String OUTER_RADIUS = "outer_radius";
	private static final String INNER_RADIUS = "inner_radius";
	private static final String SIDE = "side";
	private static final String HEIGHT = "height";
	private static final String DIRECTION = "direction";

	private HollowRegularPrismSelectionShapeStrategy(){
	}

	public static SelectionData newSelectionData(World world) {
		SelectionData selData = new SelectionData(world, CENTER, CENTER, OUTER_RADIUS, INNER_RADIUS, SIDE, HEIGHT, DIRECTION);
		selData.setExtraData(SIDE, 3);
		selData.setExtraData(DIRECTION, Direction.UP);
		return selData;
	}

	public static Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put(CENTER, new PosCommandHandler(CENTER, HollowRegularPrismSelectionShapeStrategy::newSelectionData));
		map.put(OUTER_RADIUS, new LengthCommandHandler(OUTER_RADIUS, HollowRegularPrismSelectionShapeStrategy::newSelectionData));
		map.put(INNER_RADIUS, new LengthCommandHandler(INNER_RADIUS, HollowRegularPrismSelectionShapeStrategy::newSelectionData));
		map.put(SIDE, new SideCommandHandler(HollowRegularPrismSelectionShapeStrategy::newSelectionData));
		map.put(HEIGHT, new LengthCommandHandler(HEIGHT, HollowRegularPrismSelectionShapeStrategy::newSelectionData));
		map.put(DIRECTION, new DirectionCommandHandler(HollowRegularPrismSelectionShapeStrategy::newSelectionData));
		return map;
	}

	public static String leftClickDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public static String rightClickDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void onLeftClick(SelectionData selData, BlockVector3D blockPos) {
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

	public static void onRightClick(SelectionData selData, BlockVector3D blockPos) {
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
		selData.setExtraData(INNER_RADIUS, outerRadius - 2);
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
	 * @throws IllegalStateException if the center, outer radius, innter radius, side, height, direction
	 * is not specified, outer radius <= 0, inner radius <= 0, side <3, height <=0, or inner radius >= outer radius
	 */
	public static Selection buildSelection(SelectionData selData) {
		var center = (Vector3D)selData.getExtraData(CENTER);
		var outerRadius = (Double)selData.getExtraData(OUTER_RADIUS);
		var innerRadius = (Double)selData.getExtraData(INNER_RADIUS);
		var side = (Integer)selData.getExtraData(SIDE);
		var height = (Double)selData.getExtraData(HEIGHT);
		var dir = (Direction)selData.getExtraData(DIRECTION);
		if(center == null || outerRadius == null || innerRadius == null || side == null || height == null || dir == null) {
			throw new IllegalStateException();
		}
		if(outerRadius <= 0 || innerRadius <= 0 || side < 3 || height <= 0){
			throw new IllegalStateException();
		}
		if(innerRadius >= outerRadius) {
			throw new IllegalStateException();
		}
		var region = new HollowRegularPrism(outerRadius, innerRadius, side, height);
		Cuboid bound = new Cuboid(outerRadius, outerRadius, height, -outerRadius, -outerRadius, 0);
		Selection sel = new Selection(selData.world(), Vector3D.ORIGIN, region, bound);
		switch(dir) {
		//north(-z) -> first vertex(-x)
		case NORTH -> sel = sel.createRotated(Axis.Y, 180);
		//south(+z) -> first vertex(+x)
		case SOUTH -> {}
		//east(+x) -> first vertex(+y)
		case EAST -> sel = sel.createRotated(Axis.Z, 90).createRotated(Axis.Y, 90);
		//west(-x) -> first vertex(-y)
		case WEST -> sel = sel.createRotated(Axis.Z, -90).createRotated(Axis.Y, -90);
		//up(+y) -> first vertex(+z)
		case UP -> sel = sel.createRotated(Axis.Z, -90).createRotated(Axis.X, -90);
		//down(-y) -> first vertex(-z)
		case DOWN -> sel = sel.createRotated(Axis.Z, -90).createRotated(Axis.X, 90);
		}
		return sel.createShifted(center).withOffset(selData.getOffset());
	}

}
