package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.region3D.Cuboid;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Tetrahedron;

import java.util.HashMap;
import java.util.Map;

import static tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.MaxMinCalculator.max;
import static tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.MaxMinCalculator.min;

public class TetrahedronSelectionShapeStrategy {
	private static final String POS1 = "pos1";
	private static final String POS2 = "pos2";
	private static final String POS3 = "pos3";
	private static final String POS4 = "pos4";

	private TetrahedronSelectionShapeStrategy(){
	}

	public static SelectionData newSelectionData(World world) {
		return new SelectionData(world, POS1, POS1, POS2, POS3, POS4);
	}

	public static Map<String, SubCommandHandler> selSubCommandHandlerMap(){
		Map<String,  SubCommandHandler> map = new HashMap<>();
		map.put(POS1, new PosCommandHandler(POS1, TetrahedronSelectionShapeStrategy::newSelectionData));
		map.put(POS2, new PosCommandHandler(POS2, TetrahedronSelectionShapeStrategy::newSelectionData));
		map.put(POS3, new PosCommandHandler(POS3, TetrahedronSelectionShapeStrategy::newSelectionData));
		map.put(POS4, new PosCommandHandler(POS4, TetrahedronSelectionShapeStrategy::newSelectionData));
		return map;
	}

	public static String leftClickDescription() {
		return "Set pos1";
	}

	public static String rightClickDescription() {
		return "Set pos2, pos3, pos4";
	}

	public static void onLeftClick(SelectionData selData, BlockVector3D blockPos) {
		selData.setExtraData(POS1, blockPos.toVector3D());
		selData.setExtraData(POS2, null);
		selData.setExtraData(POS3, null);
		selData.setExtraData(POS4, null);
	}

	public static void onRightClick(SelectionData selData, BlockVector3D blockPos) {
		if(selData.getExtraData(POS1) == null) {
			throw new IllegalStateException();
		}
		Vector3D pos = blockPos.toVector3D();
		if(selData.getExtraData(POS2) == null) {
			selData.setExtraData(POS2, pos);
		}else if(selData.getExtraData(POS3) == null) {
			selData.setExtraData(POS3, pos);
		}else if(selData.getExtraData(POS4) == null) {
			selData.setExtraData(POS4, pos);
		}else{
			throw new IllegalStateException();
		}
	}

	/**
	 * @throws IllegalStateException if pos1, pos2, pos3 or pos4 is not specified
	 */
	public static Selection buildSelection(SelectionData selData) {
		Vector3D pos1 = (Vector3D) selData.getExtraData(POS1);
		Vector3D pos2 = (Vector3D) selData.getExtraData(POS2);
		Vector3D pos3 = (Vector3D) selData.getExtraData(POS3);
		Vector3D pos4 = (Vector3D) selData.getExtraData(POS4);
		if(pos1 == null || pos2 == null || pos3 == null || pos4 == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Tetrahedron(pos1.getX(), pos1.getY(), pos1.getZ(),
				pos2.getX(), pos2.getY(), pos2.getZ(),
				pos3.getX(), pos3.getY(), pos3.getZ(),
				pos4.getX(), pos4.getY(), pos4.getZ());
		double ubx = max(pos1.getX(), pos2.getX(), pos3.getX(), pos4.getX());
		double uby = max(pos1.getY(), pos2.getY(), pos3.getY(), pos4.getY());
		double ubz = max(pos1.getZ(), pos2.getZ(), pos3.getZ(), pos4.getZ());
		double lbx = min(pos1.getX(), pos2.getX(), pos3.getX(), pos4.getX());
		double lby = min(pos1.getY(), pos2.getY(), pos3.getY(), pos4.getY());
		double lbz = min(pos1.getZ(), pos2.getZ(), pos3.getZ(), pos4.getZ());
		var bound = new Cuboid(ubx, uby, ubz, lbx, lby, lbz);
		return new Selection(selData.world(), selData.getOffset(), region, bound);
	}
	
}
