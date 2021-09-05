package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import static tokyo.nakanaka.shapeGenerator.MaxMinCalculator.max;
import static tokyo.nakanaka.shapeGenerator.MaxMinCalculator.min;

import java.util.HashMap;
import java.util.Map;

import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
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
	
}
