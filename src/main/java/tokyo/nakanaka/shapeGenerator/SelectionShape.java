package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public enum SelectionShape {
	CUBOID,
	DIAMOND,
	SPHERE,
	TORUS,
	LINE,
	TRIANGLE,
	TETRAHEDRON,
	REGULAR_POLYGON;
	
	/**
	 * Handles a left click block event
	 * @param userData the user data
	 * @param player the player of the event
	 * @param blockPos the clicked block position 
	 */
	public void onLeftClickBlock(UserData userData, Player player, BlockPosition blockPos) {
		
	}
	
	/**
	 * Handles a right click block event
	 * @param userData the user data
	 * @param player the player of the event
	 * @param blockPos the clicked block position 
	 */
	public void onRightClickBlock(UserData userData, Player player, BlockPosition blockPos) {
		
	}
}
