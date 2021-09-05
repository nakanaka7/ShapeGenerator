package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import static tokyo.nakanaka.shapeGenerator.MaxMinCalculator.max;
import static tokyo.nakanaka.shapeGenerator.MaxMinCalculator.min;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.CuboidBoundRegion;
import tokyo.nakanaka.shapeGenerator.math.region3D.Region3D;
import tokyo.nakanaka.shapeGenerator.math.region3D.Tetrahedron;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.TetrahedronRegionData;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.PosCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.tetrahedronSelSubCommandHandler.Pos1CommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.tetrahedronSelSubCommandHandler.Pos2CommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.tetrahedronSelSubCommandHandler.Pos3CommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.tetrahedronSelSubCommandHandler.Pos4CommandHandler;

public class TetrahedronSelectionShapeStrategy implements SelectionShapeStrategy {

	@Override
	public RegionData newRegionData() {
		return new TetrahedronRegionData();
	}
	
	@Override
	public String leftClickDescription() {
		return "Set pos1";
	}

	@Override
	public String rightClickDescription() {
		return "Set pos2, pos3, pos4";
	}
	
	@Override
	public void setFirstClickData(RegionData regData, BlockVector3D blockPos) {
		TetrahedronRegionData tetraRegData = (TetrahedronRegionData)regData;
		Vector3D pos = blockPos.toVector3D();
		tetraRegData.setPos1(pos);
	}

	@Override
	public void setAdditionalClickData(RegionData regData, BlockVector3D blockPos) {
		TetrahedronRegionData tetraRegData = (TetrahedronRegionData)regData;
		Vector3D pos = blockPos.toVector3D();
		if(tetraRegData.getPos2() == null) {
			tetraRegData.setPos2(pos);
		}else if(tetraRegData.getPos2() == null) {
			tetraRegData.setPos3(pos);
		}else {
			tetraRegData.setPos4(pos);
		}
	}
		
	@Override
	public Map<String, SelSubCommandHandler> selSubCommandHandlerMap() {
		Map<String, SelSubCommandHandler> map = new HashMap<>();
		map.put("pos1", new PosCommandHandler("pos1"));
		map.put("pos2", new PosCommandHandler("pos2"));
		map.put("pos3", new PosCommandHandler("pos3"));
		map.put("pos4", new PosCommandHandler("pos4"));
		return map;
	}

	public Map<String, SubCommandHandler> selSubCommandHandlerMapNew() {
		Map<String, SubCommandHandler> map = new HashMap<>();
		map.put("pos1", new Pos1CommandHandler());
		map.put("pos2", new Pos2CommandHandler());
		map.put("pos3", new Pos3CommandHandler());
		map.put("pos4", new Pos4CommandHandler());
		return map;
	}
	
	private BoundRegion3D buildBoundRegion3D(Map<String, Object> regionDataMap) {
		Vector3D pos1 = (Vector3D) regionDataMap.get("pos1");
		Vector3D pos2 = (Vector3D) regionDataMap.get("pos2");
		Vector3D pos3 = (Vector3D) regionDataMap.get("pos3");
		Vector3D pos4 = (Vector3D) regionDataMap.get("pos4");
		Region3D region = new Tetrahedron(pos1.getX(), pos1.getY(), pos1.getZ(),
				pos2.getX(), pos2.getY(), pos2.getZ(),
				pos3.getX(), pos3.getY(), pos3.getZ(),
				pos4.getX(), pos4.getY(), pos4.getZ());
		double ubx = max(pos1.getX(), pos2.getX(), pos3.getX(), pos4.getX());
		double uby = max(pos1.getY(), pos2.getY(), pos3.getY(), pos4.getY());
		double ubz = max(pos1.getZ(), pos2.getZ(), pos3.getZ(), pos4.getZ());
		double lbx = min(pos1.getX(), pos2.getX(), pos3.getX(), pos4.getX());
		double lby = min(pos1.getY(), pos2.getY(), pos3.getY(), pos4.getY());
		double lbz = min(pos1.getZ(), pos2.getZ(), pos3.getZ(), pos4.getZ());
		return new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
	}

	@Override
	public BoundRegion3D buildBoundRegion3D(RegionData regData) {
		TetrahedronRegionData tetraRegData = (TetrahedronRegionData)regData;
		Vector3D pos1 = tetraRegData.getPos1();
		Vector3D pos2 = tetraRegData.getPos2();
		Vector3D pos3 = tetraRegData.getPos3();
		Vector3D pos4 = tetraRegData.getPos4();
		Region3D region = new Tetrahedron(pos1.getX(), pos1.getY(), pos1.getZ(),
				pos2.getX(), pos2.getY(), pos2.getZ(),
				pos3.getX(), pos3.getY(), pos3.getZ(),
				pos4.getX(), pos4.getY(), pos4.getZ());
		double ubx = max(pos1.getX(), pos2.getX(), pos3.getX(), pos4.getX());
		double uby = max(pos1.getY(), pos2.getY(), pos3.getY(), pos4.getY());
		double ubz = max(pos1.getZ(), pos2.getZ(), pos3.getZ(), pos4.getZ());
		double lbx = min(pos1.getX(), pos2.getX(), pos3.getX(), pos4.getX());
		double lby = min(pos1.getY(), pos2.getY(), pos3.getY(), pos4.getY());
		double lbz = min(pos1.getZ(), pos2.getZ(), pos3.getZ(), pos4.getZ());
		return new CuboidBoundRegion(region, ubx, uby, ubz, lbx, lby, lbz);
	}

	@Override
	public Vector3D defaultOffset(RegionData regData) {
		TetrahedronRegionData tetraRegData = (TetrahedronRegionData)regData;
		return tetraRegData.getPos1();
	}

}
