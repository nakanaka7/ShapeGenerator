package tokyo.nakanaka.shapeGenerator.user;

import java.util.Map;
import java.util.UUID;

import tokyo.nakanaka.World;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.shapeGenerator.SelectionShapeNew;

public class UserFactory {
	private Map<SelectionShapeNew, SelectionStrategy> strategyMap;
	private SelectionShapeNew defaultShape;
	
	public UserFactory(Map<SelectionShapeNew, SelectionStrategy> strategyMap, SelectionShapeNew defaultShape) {
		this.strategyMap = strategyMap;
		this.defaultShape = defaultShape;
	}

	public UserData createConsoleUser() {
		return new UserData(UUID.randomUUID());
	}
	
	public UserData createHumanUser(UUID uid, World world) {
		UserData user = new UserData(uid);
		user.setSelectionShape(this.defaultShape);
		RegionBuildingData regionData = this.strategyMap.get(this.defaultShape).newRegionBuildingData();
		SelectionBuildingData selData = new SelectionBuildingData(world, regionData);
		user.setSelectionBuildingData(selData);
		return user;
	}
	
	public UserData createBlockUser(String name, World world, BlockVector3D pos) {
		UserData user = new UserData(UUID.randomUUID() ,name);
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
