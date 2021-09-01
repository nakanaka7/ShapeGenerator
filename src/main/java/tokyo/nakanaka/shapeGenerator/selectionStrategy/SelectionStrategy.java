package tokyo.nakanaka.shapeGenerator.selectionStrategy;

import java.util.List;
import java.util.Map;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public interface SelectionStrategy {
	RegionBuildingData newRegionBuildingData();
	String leftClickDescription();
	String rightClickDescription();
	void onLeftClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos);
	void onRightClickBlock(RegionBuildingData data, Logger logger, BlockVector3D blockPos);
	/**
	 * Handles a left click block event
	 * @param userData the user data
	 * @param player the player of the event
	 * @param blockPos the clicked block position 
	 */
	void onLeftClickBlock(UserData userData, Player player, BlockPosition blockPos);
	/**
	 * Handles a right click block event
	 * @param userData the user data
	 * @param player the player of the event
	 * @param blockPos the clicked block position 
	 */
	void onRightClickBlock(UserData userData, Player player, BlockPosition blockPos);
	/**
	 * Returns a list of keys for the selection region. It does not contain "world" or "offset".
	 * @return a list of keys for the selection region. It does not contain "world" or "offset".
	 */
	List<String> regionKeyList();
	String defaultOffsetKey();
	/**
	 * Returns new selection data for the selection shape
	 * @param world a world
	 * @return new selection data for the selection shape
	 */
	SelectionData newSelectionData(World world);
	/**
	 * Returns a map which key is "/sg sel" subcommand's subLabel and which value is SelSubCommandHandler object
	 * @return a map which key is "/sg sel" subcommand's subLabel and which value is SelSubCommandHandler object
	 */
	Map<String, SelSubCommandHandler> selSubCommandHandlerMap();
	@Deprecated
	BoundRegion3D buildBoundRegion3D(RegionBuildingData data);
	/**
	 * Return a bound region from the map of region data
	 * @param regionDataMap the map of region data
	 * @return a bound region from the map of region data
	 * @throws IllegalStateException if this cannot build a bound region from the map
	 */
	BoundRegion3D buildBoundRegion3D(Map<String, Object> regionDataMap);
	
}
