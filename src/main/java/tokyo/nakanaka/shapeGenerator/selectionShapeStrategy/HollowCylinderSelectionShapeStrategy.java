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
import tokyo.nakanaka.shapeGenerator.math.region3D.HollowCylinder;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3Ds;

public class HollowCylinderSelectionShapeStrategy implements SelectionShapeStrategy {
	private String CENTER = "center";
	private String OUTER_RADIUS = "outer_radius";
	private String INNER_RADIUS = "inner_radius";
	private String HEIGHT = "height";
	private String AXIS = "axis";
	
	@Override
	public SelectionData newSelectionData(World world) {
		SelectionData selData = new SelectionData(world, CENTER, CENTER, OUTER_RADIUS, INNER_RADIUS, HEIGHT, AXIS);
		selData.setExtraData(AXIS, Axis.Y);
		return selData;
	}

	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put(CENTER, new PosCommandHandler(CENTER, this::newSelectionData));
		map.put(OUTER_RADIUS, new LengthCommandHandler(OUTER_RADIUS, this::newSelectionData));
		map.put(INNER_RADIUS, new LengthCommandHandler(INNER_RADIUS, this::newSelectionData));
		map.put(HEIGHT, new LengthCommandHandler(HEIGHT, this::newSelectionData));
		map.put(AXIS, new AxisCommandHandler(this::newSelectionData));
		return map;
	}

	@Override
	public String leftClickDescription() {
		return "Set center";
	}

	@Override
	public String rightClickDescription() {
		return "Set outer_radius, inner_radius, and height";
	}

	@Override
	public void onLeftClick(SelectionData selData, BlockVector3D blockPos) {
		selData.setExtraData(CENTER, blockPos.toVector3D());
	}

	@Override
	public void onRightClick(SelectionData selData, BlockVector3D blockPos) {
		var center = (Vector3D)selData.getExtraData(CENTER);
		if(center == null) {
			throw new IllegalStateException();
		}
		Vector3D pos = blockPos.toVector3D();
		double dx = Math.abs(pos.getX() - center.getX());
		double dy = Math.abs(pos.getY() - center.getY());
		double dz = Math.abs(pos.getZ() - center.getZ());
		double outerRadius;
		double height;
		Axis axis = (Axis)selData.getExtraData(AXIS);
		switch(axis) {
		case X -> {
			outerRadius = Math.max(dy, dz) + 0.5;
			height = dx + 0.5;
		}
		case Y -> {
			outerRadius = Math.max(dz, dx) + 0.5;
			height = dy + 0.5;
		}
		case Z -> {
			outerRadius = Math.max(dx, dy) + 0.5;
			height = dz + 0.5;
		}
		default -> throw new IllegalArgumentException();
		}
		selData.setExtraData(OUTER_RADIUS, outerRadius);
		selData.setExtraData(INNER_RADIUS, Math.abs(outerRadius - 1));
		selData.setExtraData(HEIGHT, height);
	}

	@Override
	public Selection buildSelection(SelectionData selData) {
		var center = (Vector3D)selData.getExtraData(CENTER);
		var outerRadius = (Double)selData.getExtraData(OUTER_RADIUS);
		var innerRadius = (Double)selData.getExtraData(INNER_RADIUS);
		var height = (Double)selData.getExtraData(HEIGHT);
		var axis = (Axis)selData.getExtraData(AXIS);
		if(center == null || outerRadius == null || innerRadius == null || height == null || axis == null) {
			throw new IllegalStateException();
		}
		if(innerRadius >= outerRadius) {
			throw new IllegalStateException();
		}
		Region3D region = new HollowCylinder(outerRadius, innerRadius, height);
		double ubx = 0;
		double uby = 0; 
		double ubz = 0; 
		double lbx = 0; 
		double lby = 0; 
		double lbz = 0; 
		switch(axis) {
		case X:
			region = Region3Ds.linearTransform(region, LinearTransformation.ofYRotation(90));
			ubx += height;
			lby -= outerRadius;
			lbz -= outerRadius;
			uby += outerRadius;
			ubz += outerRadius;
			break;
		case Y:
			region = Region3Ds.linearTransform(region, LinearTransformation.ofXRotation(-90));
			uby += height;
			lbz -= outerRadius;
			lbx -= outerRadius;
			ubz += outerRadius;
			ubx += outerRadius;
			break;
		case Z:
			ubz += height;
			lbx -= outerRadius;
			lby -= outerRadius;
			ubx += outerRadius;
			uby += outerRadius;
			break;
		}
		ubx += center.getX();
		uby += center.getY();
		ubz += center.getZ();
		lbx += center.getX();
		lby += center.getY();
		lbz += center.getZ();
		region = Region3Ds.shift(region, center);
		BoundRegion3D boundReg = new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);	
		return new Selection(selData.world(), boundReg, selData.getOffset());
	}

}