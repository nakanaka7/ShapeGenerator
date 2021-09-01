package tokyo.nakanaka.shapeGenerator.selectionStrategyNew.cuboid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Cuboid;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selectionStrategyNew.SelSubCommandHandlerNew;
import tokyo.nakanaka.shapeGenerator.selectionStrategyNew.SelectionStrategyNew;

/**
 * SelectionStrategy for cuboid selection
 */
public class CuboidSelectionStrategy implements SelectionStrategyNew {

	@Override
	public Map<String, SelSubCommandHandlerNew> getSelSubCommandHandlerMap() {
		Map<String, SelSubCommandHandlerNew> map = new HashMap<>();
		map.put("pos1", new Pos1CommandHandler());
		map.put("pos2", new Pos2CommandHandler());
		return map;
	}

	@Override
	public RegionData newRegionData() {
		return new CuboidRegionData();
	}

	@Override
	public BoundRegion3D createBoundRegion3D(RegionData regData) {
		if(!(regData instanceof CuboidRegionData cuboidRegData)) {
			throw new IllegalArgumentException();
		}
		Vector3D pos1 = cuboidRegData.getPos1();
		Vector3D pos2 = cuboidRegData.getPos2();
		Region3D region = new Cuboid(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ());
		double ubx = Math.max(pos1.getX(), pos2.getX());
		double uby = Math.max(pos1.getY(), pos2.getY());
		double ubz = Math.max(pos1.getZ(), pos2.getZ());
		double lbx = Math.min(pos1.getX(), pos2.getX());
		double lby = Math.min(pos1.getY(), pos2.getY());
		double lbz = Math.min(pos1.getZ(), pos2.getZ());
		return new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
	}

	@Override
	public List<String> regionDataKeyList() {
		return List.of("pos1", "pos2");
	}

	@Override
	public String defaultOffsetKey() {
		return "pos1";
	}

	@Override
	public BoundRegion3D buildBoundRegion3D(Map<String, Object> regData) {
		Vector3D pos1 = ((Vector3D)regData.get("pos1"));
		Vector3D pos2 = ((Vector3D)regData.get("pos2"));
		if(pos1 == null || pos2 == null) {
			throw new IllegalArgumentException();
		}
		Region3D region = new Cuboid(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ());
		double ubx = Math.max(pos1.getX(), pos2.getX());
		double uby = Math.max(pos1.getY(), pos2.getY());
		double ubz = Math.max(pos1.getZ(), pos2.getZ());
		double lbx = Math.min(pos1.getX(), pos2.getX());
		double lby = Math.min(pos1.getY(), pos2.getY());
		double lbz = Math.min(pos1.getZ(), pos2.getZ());
		return new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
	}

}