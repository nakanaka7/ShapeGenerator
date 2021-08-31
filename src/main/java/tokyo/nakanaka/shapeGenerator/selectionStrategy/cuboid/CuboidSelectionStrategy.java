package tokyo.nakanaka.shapeGenerator.selectionStrategy.cuboid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Cuboid;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.SelectionStrategy;

/**
 * SelectionStrategy for cuboid selection
 */
public class CuboidSelectionStrategy implements SelectionStrategy {

	@Override
	public Map<String, SelSubCommandHandler> getSelSubCommandHandlerMap() {
		Map<String, SelSubCommandHandler> map = new HashMap<>();
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

}
