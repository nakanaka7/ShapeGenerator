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
import tokyo.nakanaka.shapeGenerator.math.region3D.Cylinder;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3Ds;

public class CylinderSelectionShapeStrategy implements SelectionShapeStrategy {
	private String CENTER = "center";
	private String RADIUS = "radius";
	private String HEIGHT = "height";
	private String AXIS = "axis";
	
	@Override
	public SelectionData newSelectionData(World world) {
		SelectionData selData = new SelectionData(world, CENTER, CENTER, RADIUS, HEIGHT, AXIS);
		selData.setExtraData("axis", Axis.Y);
		return selData;
	}

	@Override
	public Map<String, SubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put(CENTER, new PosCommandHandler(CENTER, this::newSelectionData));
		map.put(RADIUS, new LengthCommandHandler(RADIUS, this::newSelectionData));
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
		return "Set radius and height";
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
		double radius;
		double height;
		Axis axis = (Axis)selData.getExtraData(AXIS);
		switch(axis) {
		case X -> {
			radius = Math.max(dy, dz) + 0.5;
			height = dx + 0.5;
		}
		case Y -> {
			radius = Math.max(dz, dx) + 0.5;
			height = dy + 0.5;
		}
		case Z -> {
			radius = Math.max(dx, dy) + 0.5;
			height = dz + 0.5;
		}
		default -> throw new IllegalArgumentException();
		}
		selData.setExtraData(RADIUS, radius);
		selData.setExtraData(HEIGHT, height);
	}

	@Override
	public Selection buildSelection(SelectionData selData) {
		var center = (Vector3D)selData.getExtraData(CENTER);
		var radius = (Double)selData.getExtraData(RADIUS);
		var height = (Double)selData.getExtraData(HEIGHT);
		var axis = (Axis)selData.getExtraData(AXIS);
		if(center == null || radius == null || height == null || axis == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Cylinder(radius, height);
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
			lby -= radius;
			lbz -= radius;
			uby += radius;
			ubz += radius;
			break;
		case Y:
			region = Region3Ds.linearTransform(region, LinearTransformation.ofXRotation(-90));
			uby += height;
			lbz -= radius;
			lbx -= radius;
			ubz += radius;
			ubx += radius;
			break;
		case Z:
			ubz += height;
			lbx -= radius;
			lby -= radius;
			ubx += radius;
			uby += radius;
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
