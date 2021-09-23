package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.region3D.Diamond;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.regionBound.CuboidBound;
import tokyo.nakanaka.shapeGenerator.math.regionBound.RegionBound;

public class DiamondSelectionShapeStrategy implements SelectionShapeStrategy {
	private static final String CENTER = "center";
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	private static final String LENGTH = "length";
	
	@Override
	public SelectionData newSelectionData(World world) {
		return new SelectionData(world, CENTER, CENTER, WIDTH, HEIGHT, LENGTH);
	}
	
	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put(CENTER, new PosCommandHandler(CENTER, this::newSelectionData));
		map.put(WIDTH, new LengthCommandHandler(WIDTH, this::newSelectionData));
		map.put(HEIGHT, new LengthCommandHandler(HEIGHT, this::newSelectionData));
		map.put(LENGTH, new LengthCommandHandler(LENGTH, this::newSelectionData));
		return map;
	}
	
	@Override
	public String leftClickDescription() {
		return "Set center";
	}

	@Override
	public String rightClickDescription() {
		return "Set width, height, length";
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
		Double width = 2 * Math.abs(center.getX() - blockPos.getX()) + 1;
		Double height = 2 * Math.abs(center.getY() - blockPos.getY()) + 1;
		Double length = 2 * Math.abs(center.getZ() - blockPos.getZ()) + 1;
		selData.setExtraData(WIDTH, width);
		selData.setExtraData(HEIGHT, height);
		selData.setExtraData(LENGTH, length);
	}

	@Override
	public Selection buildSelection(SelectionData selData) {
		var center = (Vector3D)selData.getExtraData(CENTER);
		var width = (Double)selData.getExtraData(WIDTH);
		var height = (Double)selData.getExtraData(HEIGHT);
		var length = (Double)selData.getExtraData(LENGTH);
		if(center == null || width == null || height == null || length == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Diamond(width, height, length);
		RegionBound bound = new CuboidBound(width/2, height/2, length/2, -width/2, -height/2, -length/2);
		Selection sel = new Selection(selData.world(), Vector3D.ZERO, region, bound);
		return sel.createShifted(center).withOffset(selData.getOffset());
	}
	
}
