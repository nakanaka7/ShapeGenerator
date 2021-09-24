package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Sphere;
import tokyo.nakanaka.shapeGenerator.math.regionBound.RegionBound;
import tokyo.nakanaka.shapeGenerator.math.regionBound.SphereBound;

public class SphereSelectionShapeStrategy implements SelectionShapeStrategy{
	private static final String CENTER = "center";
	private static final String RADIUS = "radius";
	
	@Override
	public SelectionData newSelectionData(World world) {
		return new SelectionData(world, CENTER, CENTER, RADIUS);
	}
	
	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put(CENTER, new PosCommandHandler(CENTER, this::newSelectionData));
		map.put(RADIUS, new LengthCommandHandler(RADIUS, this::newSelectionData));
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
		selData.setExtraData("center", blockPos.toVector3D());
	}
	
	@Override
	public void onRightClick(SelectionData selData, BlockVector3D blockPos) {
		Vector3D center = (Vector3D) selData.getExtraData("center");
		if(center == null) {
			throw new IllegalStateException();
		}
		Vector3D pos = blockPos.toVector3D();
		Double radius = Math.floor(pos.negate(center).getAbsolute()) + 0.5;
		selData.setExtraData("radius", radius);
	}
	
	@Override
	public Selection buildSelection(SelectionData selData) {
		var center = (Vector3D)selData.getExtraData(CENTER);
		var radius = (Double)selData.getExtraData(RADIUS);
		if(center == null || radius == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Sphere(radius).createShifted(center);
		RegionBound bound = new SphereBound(radius).createShifted(center);
		return new Selection(selData.world(), selData.getOffset(), region, bound);
	}
	
}
