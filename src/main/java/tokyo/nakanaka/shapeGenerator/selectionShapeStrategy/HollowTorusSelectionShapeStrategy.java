package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.LinearTransformation;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.HollowTorus;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3Ds;

public class HollowTorusSelectionShapeStrategy implements SelectionShapeStrategy {
	private String CENTER = "center";
	private String MAJOR_RADIUS = "major_radius";
	private String OUTER_MINOR_RADIUS = "outer_minor_radius";
	private String INNER_MINOR_RADIUS = "inner_minor_radius";
	private String AXIS = "axis";
	
	@Override
	public SelectionData newSelectionData(World world) {
		SelectionData selData = new SelectionData(world, CENTER, CENTER, MAJOR_RADIUS, OUTER_MINOR_RADIUS, INNER_MINOR_RADIUS, AXIS);
		selData.setExtraData(AXIS, Axis.Y);
		return selData;
	}

	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put(CENTER, new PosCommandHandler(CENTER, this::newSelectionData));
		map.put(MAJOR_RADIUS, new LengthCommandHandler(MAJOR_RADIUS, this::newSelectionData));
		map.put(OUTER_MINOR_RADIUS, new LengthCommandHandler(OUTER_MINOR_RADIUS, this::newSelectionData));
		map.put(INNER_MINOR_RADIUS, new LengthCommandHandler(INNER_MINOR_RADIUS, this::newSelectionData));
		map.put(AXIS, new AxisCommandHandler(this::newSelectionData));
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
		var majorRadius = (Double)selData.getExtraData(MAJOR_RADIUS);
		var outerMinorRadius = (Double)selData.getExtraData(OUTER_MINOR_RADIUS);
		var innerMinorRadius = (Double)selData.getExtraData(INNER_MINOR_RADIUS);
		var axis = (Axis)selData.getExtraData(AXIS);
		if(center == null || majorRadius == null || outerMinorRadius == null || innerMinorRadius == null || axis == null) {
			throw new IllegalStateException();
		}
		Region3D region = new HollowTorus(majorRadius, outerMinorRadius, innerMinorRadius);
		switch(axis) {
		case X:
			region = Region3Ds.linearTransform(region, LinearTransformation.ofYRotation(90));
			break;
		case Y:
			region = Region3Ds.linearTransform(region, LinearTransformation.ofXRotation(90));
			break;
		case Z:
			break;
		}
		region = Region3Ds.shift(region, center);
		double ubx = center.getX() + majorRadius + outerMinorRadius;
		double uby = center.getY() + majorRadius + outerMinorRadius;
		double ubz = center.getZ() + majorRadius + outerMinorRadius;
		double lbx = center.getX() - majorRadius - outerMinorRadius;
		double lby = center.getY() - majorRadius - outerMinorRadius;
		double lbz = center.getZ() - majorRadius - outerMinorRadius;
		BoundRegion3D boundReg = new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
		return new Selection(selData.world(), boundReg, selData.getOffset());
	}

}
