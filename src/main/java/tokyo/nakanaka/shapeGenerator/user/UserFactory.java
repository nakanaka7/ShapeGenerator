package tokyo.nakanaka.shapeGenerator.user;

import java.util.Map;
import java.util.UUID;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;

public class UserFactory {
	private Map<SelectionShape, SelectionStrategy> strategyMap;
	private SelectionShape defaultShape;
	
	public UserFactory(Map<SelectionShape, SelectionStrategy> strategyMap, SelectionShape defaultShape) {
		this.strategyMap = strategyMap;
		this.defaultShape = defaultShape;
	}

	public UserOld createConsoleUser() {
		return new UserOld(UUID.randomUUID());
	}
	
	public UserOld createHumanUser(UUID uid, World world) {
		UserOld user = new UserOld(uid);
		user.setSelectionShape(this.defaultShape);
		RegionBuildingData regionData = this.strategyMap.get(this.defaultShape).newRegionBuildingData();
		SelectionBuildingData selData = new SelectionBuildingData(world, regionData);
		user.setSelectionBuildingData(selData);
		return user;
	}
	
	public UserOld createBlockUser(String name, World world, BlockVector3D pos) {
		UserOld user = new UserOld(UUID.randomUUID() ,name);
		user.setWorld(world);
		user.setX(pos.getX());
		user.setY(pos.getY());
		user.setZ(pos.getZ());
		user.setSelectionShape(this.defaultShape);
		RegionBuildingData regionData = this.strategyMap.get(this.defaultShape).newRegionBuildingData();
		SelectionBuildingData selData = new SelectionBuildingData(world, regionData);
		user.setSelectionBuildingData(selData);
		return user;
	}
	
}
