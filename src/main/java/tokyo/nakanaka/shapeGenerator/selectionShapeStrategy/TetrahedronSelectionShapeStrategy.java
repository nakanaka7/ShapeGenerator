package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import static tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.MaxMinCalculator.max;
import static tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.MaxMinCalculator.min;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.region3D.Cuboid;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Tetrahedron;

public class TetrahedronSelectionShapeStrategy implements SelectionShapeStrategy {
	private static final String POS1 = "pos1";
	private static final String POS2 = "pos2";
	private static final String POS3 = "pos3";
	private static final String POS4 = "pos4";
	
	@Override
	public SelectionData newSelectionData(World world) {
		return new SelectionData(world, POS1, POS1, POS2, POS3, POS4);
	}
	
	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap(){
		Map<String,  SubCommandHandler> map = new HashMap<>();
		map.put(POS1, new PosCommandHandler(POS1, this::newSelectionData));
		map.put(POS2, new PosCommandHandler(POS2, this::newSelectionData));
		map.put(POS3, new PosCommandHandler(POS3, this::newSelectionData));
		map.put(POS4, new PosCommandHandler(POS4, this::newSelectionData));
		return map;
	}
	
	@Override
	public String leftClickDescription() {
		return "Set pos1";
	}

	@Override
	public String rightClickDescription() {
		return "Set pos2, pos3, pos4";
	}
	
	@Override
	public void onLeftClick(SelectionData selData, BlockVector3D blockPos) {
		selData.setExtraData(POS1, blockPos.toVector3D());
		selData.setExtraData(POS2, null);
		selData.setExtraData(POS3, null);
		selData.setExtraData(POS4, null);
	}
	
	@Override
	public void onRightClick(SelectionData selData, BlockVector3D blockPos) {
		if(selData.getExtraData(POS1) == null) {
			throw new IllegalStateException();
		}
		Vector3D pos = blockPos.toVector3D();
		if(selData.getExtraData(POS2) == null) {
			selData.setExtraData(POS2, pos);
		}else if(selData.getExtraData(POS3) == null) {
			selData.setExtraData(POS3, pos);
		}else {
			selData.setExtraData(POS4, pos);
		}
	}
	
	@Override
	public Selection buildSelection(SelectionData selData) {
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
