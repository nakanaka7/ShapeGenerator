package tokyo.nakanaka.player;

import java.util.Map;
import java.util.UUID;

import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.SelectionStrategy;
import tokyo.nakanaka.world.World;

public class PlayerFactory {
	private Map<SelectionShape, SelectionStrategy> strategyMap;
	private SelectionShape defaultShape;
	
	public PlayerFactory(Map<SelectionShape, SelectionStrategy> strategyMap, SelectionShape defaultShape) {
		this.strategyMap = strategyMap;
		this.defaultShape = defaultShape;
	}

	public Player createConsolePlayer() {
		return new Player(UUID.randomUUID());
	}
	
	public Player createHumanPlayer(UUID uid, World world) {
		Player player = new Player(uid);
		player.setSelectionShape(this.defaultShape);
		RegionBuildingData regionData = this.strategyMap.get(this.defaultShape).newRegionBuildingData();
		SelectionBuildingData selData = new SelectionBuildingData(world, regionData);
		player.setSelectionBuildingData(selData);
		return player;
	}
	
	public Player createBlockPlayer(String name, World world, BlockVector3D pos) {
		Player player = new Player(UUID.randomUUID() ,name);
		player.setWorld(world);
		player.setX(pos.getX());
		player.setY(pos.getY());
		player.setZ(pos.getZ());
		player.setSelectionShape(this.defaultShape);
		RegionBuildingData regionData = this.strategyMap.get(this.defaultShape).newRegionBuildingData();
		SelectionBuildingData selData = new SelectionBuildingData(world, regionData);
		player.setSelectionBuildingData(selData);
		return player;
	}
	
}
