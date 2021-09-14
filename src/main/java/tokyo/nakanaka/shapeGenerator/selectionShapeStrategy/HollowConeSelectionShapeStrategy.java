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
import tokyo.nakanaka.shapeGenerator.math.region3D.HollowCone;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;

public class HollowConeSelectionShapeStrategy implements SelectionShapeStrategy {
	private String CENTER = "center";
	private String OUTER_RADIUS = "outer_radius";
	private String INNER_RADIUS = "inner_radius";
	private String HEIGHT = "height";
	private String DIRECTION = "direction";
	
	@Override
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
		var height = (Double)selData.getExtraData(HEIGHT);
		var dir = (Direction)selData.getExtraData(DIRECTION);
		if(center == null || outerRadius == null || innerRadius == null || height == null || dir == null) {
			throw new IllegalStateException();
		}
		if(innerRadius >= outerRadius) {
			throw new IllegalStateException();
		}
		Region3D region = new HollowCone(outerRadius, innerRadius, height);	
		BoundRegion3D boundReg = new CuboidBoundRegion(region, outerRadius, outerRadius, height, -outerRadius, -outerRadius, 0);
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
