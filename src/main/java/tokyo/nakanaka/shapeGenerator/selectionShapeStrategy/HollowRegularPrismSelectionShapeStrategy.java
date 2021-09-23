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
import tokyo.nakanaka.shapeGenerator.math.region3D.HollowRegularPrism;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.regularPolygonSelSubCommandHandler.SideCommandHandler;

public class HollowRegularPrismSelectionShapeStrategy implements SelectionShapeStrategy {
	private static final String CENTER = "center";
	private static final String OUTER_RADIUS = "outer_radius";
	private static final String INNER_RADIUS = "inner_radius";
	private static final String SIDE = "side";
	private static final String HEIGHT = "height";
	private static final String DIRECTION = "direction";
	
	
	@Override
	public SelectionData newSelectionData(World world) {
		SelectionData selData = new SelectionData(world, CENTER, CENTER, OUTER_RADIUS, INNER_RADIUS, SIDE, HEIGHT, DIRECTION);
		selData.setExtraData(SIDE, 3);
		selData.setExtraData(DIRECTION, Direction.UP);
		return selData;
	}

	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put(CENTER, new PosCommandHandler(CENTER, this::newSelectionData));
		map.put(OUTER_RADIUS, new LengthCommandHandler(OUTER_RADIUS, this::newSelectionData));
		map.put(INNER_RADIUS, new LengthCommandHandler(INNER_RADIUS, this::newSelectionData));
		map.put(SIDE, new SideCommandHandler(this::newSelectionData));
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRightClick(SelectionData selData, BlockVector3D blockPos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Selection buildSelection(SelectionData selData) {
		var center = (Vector3D)selData.getExtraData(CENTER);
		var outerRadius = (Double)selData.getExtraData(OUTER_RADIUS);
		var innerRadius = (Double)selData.getExtraData(INNER_RADIUS);
		var side = (Integer)selData.getExtraData(SIDE);
		var height = (Double)selData.getExtraData(HEIGHT);
		var dir = (Direction)selData.getExtraData(DIRECTION);
		if(center == null || outerRadius == null || innerRadius == null || side == null || height == null || dir == null) {
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
