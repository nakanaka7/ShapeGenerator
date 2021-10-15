package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.cuboid;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.region3D.Cuboid;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.PosCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

public class CuboidSelectionShapeStrategy implements SelectionShapeStrategy {
	private static final String POS1 = "pos1";
	private static final String POS2 = "pos2";

	public static SelectionData newSelectionData(World world) {
		return new SelectionData(world, POS1, POS1, POS2);
	}

	/**
	 * Returns a map which key is "/sg sel" subcommand's subLabel and which value is SubCommandHandler object
	 * @return a map which key is "/sg sel" subcommand's subLabel and which value is SubCommandHandler object
	 */
	public static Map<String, SubCommandHandler> selSubCommandHandlerMap(){
		Map<String,  SubCommandHandler> map = new HashMap<>();
		map.put(POS1, new PosCommandHandler(POS1, CuboidSelectionShapeStrategy::newSelectionData));
		map.put(POS2, new PosCommandHandler(POS2, CuboidSelectionShapeStrategy::newSelectionData));
		return map;
	}
	
	@Override
	public String leftClickDescription() {
		return "Set pos1";
	}

	@Override
	public String rightClickDescription() {
		return "Set pos2";
	}

	@Override
	public void onLeftClick(SelectionData selData, BlockVector3D blockPos) {
		selData.setExtraData(POS1, blockPos.toVector3D());
	}
	
	@Override
	public void onRightClick(SelectionData selData, BlockVector3D blockPos) {
		selData.setExtraData(POS2, blockPos.toVector3D());
	}

	public static Selection buildSelection(SelectionData selData) {
		Vector3D pos1 = (Vector3D) selData.getExtraData(POS1);
		Vector3D pos2 = (Vector3D) selData.getExtraData(POS2);
		if(pos1 == null || pos2 == null) {
			throw new IllegalStateException();
		}
		var cuboid = new Cuboid(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ());
		return new Selection(selData.world(), selData.getOffset(), cuboid, cuboid);
	}
	
}
