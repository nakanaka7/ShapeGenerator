package tokyo.nakanaka.shapeGenerator.playerData;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.CuboidSelection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataRepository {
	private Map<UUID, PlayerData> playerDataMap = new HashMap<>();

	/**
	 * Returns a player data. If there is a player data for the player, return it, otherwise create new one and return it.
	 * @param player player
	 * @return a player data
	 */
	public PlayerData preparePlayerData(Player player) {
		UUID uid = player.getUniqueID();
		PlayerData playerData = this.playerDataMap.get(uid);
		if(playerData == null) {
			playerData = new PlayerData();
			SelectionShape defaultShape = SelectionShape.CUBOID;
			playerData.setSelectionShape(defaultShape);
			World world = player.getEntityPosition().world();
			SelectionData selData = CuboidSelection.newSelectionData(world);
			playerData.setSelectionData(selData);
			this.playerDataMap.put(uid, playerData);
		}
		return playerData;
	}
	
}
