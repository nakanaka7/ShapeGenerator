package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.Direction;
import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.region3D.Cuboid;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.RegularPrism;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.regularPolygonSelSubCommandHandler.SideCommandHandler;

public class RegularPrismSelectionShapeStrategy implements SelectionShapeStrategy {
	private static final String CENTER = "center";
	private static final String RADIUS = "radius";
	private static final String SIDE = "side";
	private static final String HEIGHT = "height";
	private static final String DIRECTION = "direction";
	
	@Override
	public SelectionData newSelectionData(World world) {
		SelectionData selData = new SelectionData(world, CENTER, CENTER, RADIUS, SIDE, HEIGHT, DIRECTION);
		selData.setExtraData(SIDE, 3);
		selData.setExtraData(DIRECTION, Direction.UP);
		return selData;
	}
	
	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put(CENTER, new PosCommandHandler(CENTER, this::newSelectionData));
		map.put(RADIUS, new LengthCommandHandler(RADIUS, this::newSelectionData));
		map.put(SIDE, new SideCommandHandler(this::newSelectionData));
		map.put(HEIGHT, new LengthCommandHandler(HEIGHT, this::newSelectionData));
		map.put(DIRECTION, new DirectionCommandHandler(this::newSelectionData));
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
	
	@Override
	public void onLeftClick(SelectionData selData, BlockVector3D blockPos) {
		selData.setExtraData(CENTER, blockPos.toVector3D());
	}

	@Override
	public void onRightClick(SelectionData selData,BlockVector3D blockPos) {
		var center = (Vector3D)selData.getExtraData(CENTER);
		if(center == null) {
			throw new IllegalStateException();
		}
		Vector3D pos = blockPos.toVector3D();
		double radius = Math.floor(pos.negate(center).getAbsolute()) + 0.5;
		selData.setExtraData(RADIUS, radius);
	}
	
	@Override
	public Selection buildSelection(SelectionData selData) {
		var center = (Vector3D)selData.getExtraData(CENTER);
		var radius = (Double)selData.getExtraData(RADIUS);
		var side = (Integer)selData.getExtraData(SIDE);
		var height = (Double)selData.getExtraData(HEIGHT);
		var dir = (Direction)selData.getExtraData(DIRECTION);
		if(center == null || radius == null || side == null || height == null || dir == null) {
			throw new IllegalStateException();
		}
		Region3D region = new RegularPrism(radius, side, height);
		Selection sel = new Selection(selData.world(), Vector3D.ORIGIN, region, new Cuboid(radius, radius, height, -radius, -radius, 0));
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
