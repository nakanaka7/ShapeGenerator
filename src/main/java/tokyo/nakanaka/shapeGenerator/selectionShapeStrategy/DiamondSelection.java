package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

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

import java.util.HashMap;
import java.util.Map;

public class DiamondSelection {
	private static final String CENTER = "center";
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	private static final String LENGTH = "length";

	private DiamondSelection(){
	}

	public static SelectionData newSelectionData(World world) {
		return new SelectionData(world, CENTER, CENTER, WIDTH, HEIGHT, LENGTH);
	}

	public static Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put(CENTER, new PosCommandHandler(CENTER, DiamondSelection::newSelectionData));
		map.put(WIDTH, new LengthCommandHandler(WIDTH, DiamondSelection::newSelectionData));
		map.put(HEIGHT, new LengthCommandHandler(HEIGHT, DiamondSelection::newSelectionData));
		map.put(LENGTH, new LengthCommandHandler(LENGTH, DiamondSelection::newSelectionData));
		return map;
	}

	public static String leftClickDescription() {
		return "Set center";
	}

	public static String rightClickDescription() {
		return "Set width, height, length";
	}

	public static void onLeftClick(SelectionData selData, BlockVector3D blockPos) {
		selData.setExtraData(CENTER, blockPos.toVector3D());
	}

	public static void onRightClick(SelectionData selData, BlockVector3D blockPos) {
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

	public static Selection buildSelection(SelectionData selData) {
		var center = (Vector3D)selData.getExtraData(CENTER);
		var width = (Double)selData.getExtraData(WIDTH);
		var height = (Double)selData.getExtraData(HEIGHT);
		var length = (Double)selData.getExtraData(LENGTH);
		if(center == null || width == null || height == null || length == null) {
			throw new IllegalStateException();
		}
		if(width <= 0 || height <= 0 || length <= 0){
			throw new IllegalStateException();
		}
		Region3D region = new Diamond(width, height, length);
		RegionBound bound = new CuboidBound(width/2, height/2, length/2, -width/2, -height/2, -length/2);
		Selection sel = new Selection(selData.world(), Vector3D.ZERO, region, bound);
		return sel.createShifted(center).withOffset(selData.getOffset());
	}
	
}
