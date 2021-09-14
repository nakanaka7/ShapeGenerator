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
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Cone;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;

public class ConeSelectionShapeStrategy implements SelectionShapeStrategy {
	private String CENTER = "center";
	private String RADIUS = "radius";
	private String HEIGHT = "height";
	private String DIRECTION = "direction";
	
	@Override
	public SelectionData newSelectionData(World world) {
		SelectionData selData = new SelectionData(world, CENTER, CENTER, RADIUS, HEIGHT, DIRECTION);
		selData.setExtraData(DIRECTION, Direction.UP);
		return selData;
	}

	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put(CENTER, new PosCommandHandler(CENTER, this::newSelectionData));
		map.put(RADIUS, new LengthCommandHandler(RADIUS, this::newSelectionData));
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
		return "Set radius and height";
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
		double dx = Math.abs(pos.getX() - center.getX());
		double dy = Math.abs(pos.getY() - center.getY());
		double dz = Math.abs(pos.getZ() - center.getZ());
		double radius;
		double height;
		Direction dir = (Direction)selData.getExtraData(DIRECTION);
		radius = switch(dir) {
		case NORTH, SOUTH -> Math.max(dx, dy);
		case EAST, WEST -> Math.max(dy, dz);
		case UP, DOWN -> Math.max(dz, dx);
		};
		
		height = switch(dir) {
		case NORTH, SOUTH -> height = dz + 0.5;
		case EAST, WEST -> height = dx + 0.5;
		case UP, DOWN -> height = dy + 0.5;
		};
		selData.setExtraData(RADIUS, radius);
		selData.setExtraData(HEIGHT, height);
	}

	@Override
	public Selection buildSelection(SelectionData selData) {
		var center = (Vector3D)selData.getExtraData(CENTER);
		var radius = (Double)selData.getExtraData(RADIUS);
		var height = (Double)selData.getExtraData(HEIGHT);
		var dir = (Direction)selData.getExtraData(DIRECTION);
		if(center == null || radius == null || height == null || dir == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Cone(radius, height);
		BoundRegion3D boundReg = new CuboidBoundRegion(region, radius, radius, height, -radius, -radius, 0);
		Vector3D offset = selData.getOffset();
		boundReg = boundReg.createShifted(offset);
		switch(dir) {
		case NORTH -> boundReg = boundReg.createRotated(Axis.Y, 180, offset);
		case SOUTH -> {}
		case EAST -> boundReg = boundReg.createRotated(Axis.Y, 90, offset);
		case WEST -> boundReg = boundReg.createRotated(Axis.Y, -90, offset);
		case UP -> boundReg = boundReg.createRotated(Axis.X, -90, offset);
		case DOWN -> boundReg = boundReg.createRotated(Axis.X, 90, offset);
		};
		return new Selection(selData.world(), boundReg, offset);
	}

}
