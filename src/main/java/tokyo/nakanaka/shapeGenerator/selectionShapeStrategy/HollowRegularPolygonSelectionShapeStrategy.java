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
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region2D.HollowRegularPolygon;
import tokyo.nakanaka.shapeGenerator.math.region2D.Region2D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3Ds;
import tokyo.nakanaka.shapeGenerator.math.region3D.ThickenedRegion3D;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.regularPolygonSelSubCommandHandler.SideCommandHandler;

public class HollowRegularPolygonSelectionShapeStrategy implements SelectionShapeStrategy {
	private String CENTER = "center";
	private String OUTER_RADIUS = "outer_radius";
	private String INNER_RADIUS = "inner_radius";
	private String SIDE = "side";
	private String THICKNESS = "thickness";
	private String AXIS = "axis";
	
	@Override
	public SelectionData newSelectionData(World world) {
		SelectionData selData = new SelectionData(world, CENTER, CENTER, OUTER_RADIUS, INNER_RADIUS, SIDE, THICKNESS, AXIS);
		selData.setExtraData(SIDE, 3);
		selData.setExtraData(THICKNESS, 1.0);
		selData.setExtraData(AXIS, Axis.Y);
		return selData;
	}

	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put(CENTER, new PosCommandHandler(CENTER, this::newSelectionData));
		map.put(OUTER_RADIUS, new LengthCommandHandler(OUTER_RADIUS, this::newSelectionData));
		map.put(INNER_RADIUS, new LengthCommandHandler(INNER_RADIUS, this::newSelectionData));
		map.put(SIDE, new SideCommandHandler(this::newSelectionData));
		map.put(THICKNESS, new LengthCommandHandler(THICKNESS, this::newSelectionData));
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
		var outerRadius = (Double)selData.getExtraData(OUTER_RADIUS);
		var innerRadius = (Double)selData.getExtraData(INNER_RADIUS);
		var side = (Integer)selData.getExtraData(SIDE);
		var thickness = (Double)selData.getExtraData(THICKNESS);
		var axis = (Axis)selData.getExtraData(AXIS);
		if(center == null || outerRadius == null || innerRadius == null || side == null || thickness == null || axis == null) {
			throw new IllegalStateException();
		}
		if(innerRadius >= outerRadius) {
			throw new IllegalStateException();
		}
		Region2D regularPoly = new HollowRegularPolygon(outerRadius, innerRadius, side);
		Region3D region = new ThickenedRegion3D(regularPoly, thickness);
		double ubx = outerRadius;
		double uby = outerRadius;
		double ubz = outerRadius;
		double lbx = - outerRadius;
		double lby = - outerRadius;
		double lbz = - outerRadius;
		switch(axis) {
		case X:
			region = Region3Ds.linearTransform(region, LinearTransformation.ofYRotation(90));
			region = Region3Ds.linearTransform(region, LinearTransformation.ofXRotation(90));
			ubx = thickness/ 2;
			lbx = - thickness/ 2;
			break;
		case Y:
			region = Region3Ds.linearTransform(region, LinearTransformation.ofXRotation(-90));
			region = Region3Ds.linearTransform(region, LinearTransformation.ofYRotation(-90));
			uby = thickness/ 2;
			lby = - thickness/ 2;
			break;
		case Z:
			ubz = thickness/ 2;
			lbz = - thickness/ 2;
			break;
		default:
			break;
		}
		CuboidBoundRegion boundReg = new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
		boundReg = boundReg.createShifted(center);
		return new Selection(selData.world(), boundReg, selData.getOffset());
	}

}
