package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.SphereBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3Ds;
import tokyo.nakanaka.shapeGenerator.math.region3D.Sphere;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.SphereRegionData;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.LengthCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.PosCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;

public class SphereSelectionShapeStrategy implements SelectionShapeStrategy{

	@Override
	public RegionData newRegionData() {
		return new SphereRegionData();
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
	public void setFirstClickData(RegionData regData, BlockVector3D blockPos) {
		SphereRegionData sphereRegData = (SphereRegionData)regData;
		Vector3D center = blockPos.toVector3D();
		sphereRegData.setCenter(center);
	}

	@Override
	public void setAdditionalClickData(RegionData regData, BlockVector3D blockPos) {
		SphereRegionData sphereRegData = (SphereRegionData)regData;
		Vector3D center = sphereRegData.getCenter();
		Vector3D pos = blockPos.toVector3D();
		double radius = Math.floor(pos.negate(center).getAbsolute()) + 0.5;
		sphereRegData.setRadius(radius);
	}

	
	@Override
	public Map<String, SelSubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SelSubCommandHandler> map = new HashMap<>();
		map.put("center", new PosCommandHandler("center"));
		map.put("radius", new LengthCommandHandler("radius"));
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
		Double radius = (Double) regionDataMap.get("radius");
		if(center == null || radius == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Sphere(radius);
		region = Region3Ds.shift(region, center);
		return new SphereBoundRegion(region, center, radius);
	}

	@Override
	public BoundRegion3D buildBoundRegion3D(RegionData regData) {
		SphereRegionData sphereRegData = (SphereRegionData)regData;
		Vector3D center = sphereRegData.getCenter();
		Double radius = sphereRegData.getRadius();
		if(center == null || radius == null) {
			throw new IllegalStateException();
		}
		Region3D region = new Sphere(radius);
		region = Region3Ds.shift(region, center);
		return new SphereBoundRegion(region, center, radius);
	}

	@Override
	public Vector3D defaultOffset(RegionData regData) {
		SphereRegionData sphereRegData = (SphereRegionData)regData;
		return sphereRegData.getCenter();
	}

}
