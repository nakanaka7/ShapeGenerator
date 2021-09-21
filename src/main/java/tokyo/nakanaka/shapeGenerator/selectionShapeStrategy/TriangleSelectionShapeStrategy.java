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
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Triangle;
import tokyo.nakanaka.shapeGenerator.math.regionBound.CuboidBound;

public class TriangleSelectionShapeStrategy implements SelectionShapeStrategy {
	private static final String POS1 = "pos1";
	private static final String POS2 = "pos2";
	private static final String POS3 = "pos3";
	private static final String THICKNESS = "thickness";
	
	@Override
	public SelectionData newSelectionData(World world) {
		SelectionData selData = new SelectionData(world, POS1, POS1, POS2, POS3, THICKNESS);
		selData.setExtraData(THICKNESS, 1.0);
		return selData;
	}
	
	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put(POS1, new PosCommandHandler(POS1, this::newSelectionData));
		map.put(POS2, new PosCommandHandler(POS2, this::newSelectionData));
		map.put(POS3, new PosCommandHandler(POS3, this::newSelectionData));
		map.put(THICKNESS, new LengthCommandHandler(THICKNESS, this::newSelectionData));
		return map;
	}
	
	@Override
	public String leftClickDescription() {
		return "Set pos1";
	}

	@Override
	public String rightClickDescription() {
		return "Set pos2, pos3";
	}
	
	@Override
	public void onLeftClick(SelectionData selData, BlockVector3D blockPos) {
		selData.setExtraData(POS1, blockPos.toVector3D());
		selData.setExtraData(POS2, null);
		selData.setExtraData(POS3, null);
	}
	
	@Override
	public void onRightClick(SelectionData selData, BlockVector3D blockPos) {
		if(selData.getExtraData(POS1) == null) {
			throw new IllegalStateException();
		}
		Vector3D pos = blockPos.toVector3D();
		if(selData.getExtraData(POS2) == null) {
			selData.setExtraData(POS2, pos);
		}else {
			selData.setExtraData(POS3, pos);
		}
	}
	
	@Override
	public Selection buildSelection(SelectionData selData) {
		Vector3D pos1 = (Vector3D) selData.getExtraData(POS1);
		Vector3D pos2 = (Vector3D) selData.getExtraData(POS2);
		Vector3D pos3 = (Vector3D) selData.getExtraData(POS3);
		Double thickness = (Double) selData.getExtraData(THICKNESS);
		if(pos1 == null || pos2 == null || pos3 == null || thickness == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Triangle(pos1.getX(), pos1.getY(), pos1.getZ(),
				pos2.getX(), pos2.getY(), pos2.getZ(),
				pos3.getX(), pos3.getY(), pos3.getZ(), thickness);
		double ubx = max(pos1.getX(), pos2.getX(), pos3.getX()) + thickness / 2;
		double uby = max(pos1.getY(), pos2.getY(), pos3.getY()) + thickness / 2;
		double ubz = max(pos1.getZ(), pos2.getZ(), pos3.getZ()) + thickness / 2;
		double lbx = min(pos1.getX(), pos2.getX(), pos3.getX()) - thickness / 2;
		double lby = min(pos1.getY(), pos2.getY(), pos3.getY()) - thickness / 2;
		double lbz = min(pos1.getZ(), pos2.getZ(), pos3.getZ()) - thickness / 2;
		var bound = new CuboidBound(ubx, uby, ubz, lbx, lby, lbz);
		return new Selection(selData.world(), selData.getOffset(), region, bound);
	}
	
}
