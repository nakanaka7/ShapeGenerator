package tokyo.nakanaka.shapeGenerator.selectionShapeDelegator;

import java.util.Map;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.math.boundRegion3D.BoundRegion3D;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Holds SelectionShape delegating methods
 */
public interface SelectionShapeDelegator {
	
	/**
	 * Returns new region data of a selection
	 * @return new region data of a selection
	 */
	RegionData newRegionData();
	/**
	 * Returns new selection data for the selection shape
	 * @param world a world
	 * @return new selection data for the selection shape
	 */
	@Deprecated
	SelectionData newSelectionData(World world);
	
	/**
	 * Returns a map which key is "/sg sel" subcommand's subLabel and which value is SelSubCommandHandler object
	 * @return a map which key is "/sg sel" subcommand's subLabel and which value is SelSubCommandHandler object
	 */
	Map<String, SelSubCommandHandler> selSubCommandHandlerMap();

	/**
	 * Returns a short description for left clicking block event
	 * @return a short description for left clicking block event
	 */
	String leftClickDescription();
	
	/**
	 * Returns a short description for right clicking block event
	 * @return a short description for right clicking block event
	 */
	String rightClickDescription();
	/**
	 * Handles a left clicking block event
	 * @param userData the user data
	 * @param player the player of the event
	 * @param blockPos the clicked block position 
	 */
	@Deprecated
	void onLeftClickBlock(UserData userData, Player player, BlockPosition blockPos);
	
	/**
	 * Handles a right clicking block event
	 * @param userData the user data
	 * @param player the player of the event
	 * @param blockPos the clicked block position 
	 */
	@Deprecated
	void onRightClickBlock(UserData userData, Player player, BlockPosition blockPos);
		
	/**
	 * Returns a selection from the selection data
	 * @param selData a selection data
	 * @return a selection from the selection data
	 * @throws IllegalArgumentException if the selection data cannot create a selection
	 */
	@Deprecated
	Selection buildSelection(SelectionData selData);
	
	/**
	 * Returns a bound region from the region data
	 * @param regData a region data
	 * @return a bound region from the region data
	 */
	BoundRegion3D buildBoundRegion3D(RegionData regData);
	
	/**
	 * Returns default offset
	 * @param regData a region data
	 * @return default offset
	 */
	Vector3D defaultOffset(RegionData regData);
	
}
