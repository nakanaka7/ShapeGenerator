package tokyo.nakanaka.shapeGenerator.selectionShapeDelegator;

import java.util.Map;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Holds SelectionShape delegating methods
 */
public interface SelectionShapeDelegator {
	
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
	void onLeftClickBlock(UserData userData, Player player, BlockPosition blockPos);
	
	/**
	 * Handles a right clicking block event
	 * @param userData the user data
	 * @param player the player of the event
	 * @param blockPos the clicked block position 
	 */
	void onRightClickBlock(UserData userData, Player player, BlockPosition blockPos);
		
	/**
	 * Returns a selection from the selection data
	 * @param selData a selection data
	 * @return a selection from the selection data
	 * @throws IllegalArgumentException if the selection data cannot create a selection
	 */
	Selection buildSelection(SelectionData selData);
	
}
