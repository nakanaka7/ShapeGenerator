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
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3Ds;
import tokyo.nakanaka.shapeGenerator.math.region3D.Torus;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.TorusRegionData;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.AxisCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.LengthCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.PosCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;

public class TorusSelectionShapeStrategy implements SelectionShapeStrategy {

	@Override
	public RegionData newRegionData() {
		return new TorusRegionData();
	}
	
	@Override
	public String leftClickDescription() {
		return "Set center";
	}

	@Override
	public String rightClickDescription() {
		return "Set radius_main, radius_sub";
	}
	
	@Override
	public void setFirstClickData(RegionData regData, BlockVector3D blockPos) {
		TorusRegionData torusRegData = (TorusRegionData) regData;
		torusRegData.setCenter(blockPos.toVector3D());
	}

	@Override
	public void setAdditionalClickData(RegionData regData, BlockVector3D blockPos) {
		TorusRegionData torusRegData = (TorusRegionData) regData;
		Vector3D pos = blockPos.toVector3D();
		Vector3D center = torusRegData.getCenter();
		Double radiusMain = torusRegData.getRadiusMain();
		Double radiusSub = torusRegData.getRadiusSub();
		Axis axis = torusRegData.getAxis();
		if(radiusMain == null) {
			radiusMain = pos.negate(center).getAbsolute();
			torusRegData.setRadiusMain(radiusMain);
		}else {
			Vector3D e1 = axis.toVector3D();
			Vector3D p = pos.negate(center);
			double p1 = p.innerProduct(e1);
			Vector3D p2e2 = p.negate(e1.multiply(p1));
			Vector3D e2 = p2e2.divide(p2e2.getAbsolute());
			radiusSub = p.negate(e2.multiply(radiusMain)).getAbsolute() + 0.5;
			torusRegData.setRadiusSub(radiusSub);
		}
	}
	
	@Override
	public Map<String, SelSubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SelSubCommandHandler> map = new HashMap<>();
		map.put("center", new PosCommandHandler("center"));
		map.put("radius_main", new LengthCommandHandler("radius_main"));
		map.put("radius_sub", new LengthCommandHandler("radius_sub"));
		map.put("axis", new AxisCommandHandler());
		return map;
	}
	
	@Override
	public Selection buildSelection(SelectionData selData) {
		World world = selData.getWorld();
		Map<String, Object> regDataMap = selData.getRegionDataMap();
		BoundRegion3D boundReg = this.buildBoundRegion3D(regDataMap);
		Vector3D offset = selData.getOffset();
		if(offset == null) {
			offset = (Vector3D) regDataMap.get("center");
		}
		return new Selection(world, boundReg, offset);
	}

	private BoundRegion3D buildBoundRegion3D(Map<String, Object> regionDataMap) {
		Vector3D center = (Vector3D) regionDataMap.get("center");
		Double radiusMain = (Double) regionDataMap.get("radius_main");
		Double radiusSub = (Double) regionDataMap.get("radius_sub");
		if(center == null || radiusMain == null || radiusSub == null) {
			throw new IllegalStateException();
		}
		Axis axis = Axis.valueOf(((String)regionDataMap.get("axis")).toUpperCase());
		Region3D region = new Torus(radiusMain, radiusSub);
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
		double ubx = center.getX() + radiusMain + radiusSub;
		double uby = center.getY() + radiusMain + radiusSub;
		double ubz = center.getZ() + radiusMain + radiusSub;
		double lbx = center.getX() - radiusMain - radiusSub;
		double lby = center.getY() - radiusMain - radiusSub;
		double lbz = center.getZ() - radiusMain - radiusSub;
		return new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
	}

	@Override
	public BoundRegion3D buildBoundRegion3D(RegionData regData) {	
		TorusRegionData torusRegData = (TorusRegionData) regData;
		Vector3D center = torusRegData.getCenter();
		Double radiusMain = torusRegData.getRadiusMain();
		Double radiusSub = torusRegData.getRadiusSub();
		if(center == null || radiusMain == null || radiusSub == null) {
			throw new IllegalStateException();
		}
		Axis axis = torusRegData.getAxis();
		Region3D region = new Torus(radiusMain, radiusSub);
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
		double ubx = center.getX() + radiusMain + radiusSub;
		double uby = center.getY() + radiusMain + radiusSub;
		double ubz = center.getZ() + radiusMain + radiusSub;
		double lbx = center.getX() - radiusMain - radiusSub;
		double lby = center.getY() - radiusMain - radiusSub;
		double lbz = center.getZ() - radiusMain - radiusSub;
		return new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
	}

	@Override
	public Vector3D defaultOffset(RegionData regData) {
		TorusRegionData torusRegData = (TorusRegionData) regData;
		return torusRegData.getCenter();
	}

}
