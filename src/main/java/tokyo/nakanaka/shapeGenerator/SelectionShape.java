package tokyo.nakanaka.shapeGenerator;

import java.util.Map;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.selection.Selection;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.*;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public enum SelectionShape {
	CUBOID(new CuboidSelectionStrategy()),
	DIAMOND(new DiamondSelectionStrategy()),
	SPHERE(new SphereSelectionStrategy()),
	TORUS(new TorusSelectionStrategy()),
	LINE(new LineSelectionStrategy()),
	TRIANGLE(new TriangleSelectionStrategy()),
	TETRAHEDRON(new TetrahedronSelectionStrategy()),
	REGULAR_POLYGON(new RegularPolygonSelectionStrategy());
	
	private SelectionStrategy selStrtg;
	
	private SelectionShape(SelectionStrategy selStrtg) {
		this.selStrtg = selStrtg;
	}
	
	/**
	 * Get SelSubCommandHandlerMap
	 * @return SelSubCommandHandlerMap
	 */
	public Map<String, SelSubCommandHandler> selSubCommandHandlerMap() {
		return this.selStrtg.selSubCommandHandlerMap();
	}
	
	/**
	 * Get SelectionData
	 * @param world a world of the selection data
	 * @return SelectionData
	 */
	public SelectionData newSelectionData(World world) {
		return this.selStrtg.newSelectionData(world);
	}
	
	/**
	 * Returns a selection from the selection data
	 * @param selData a selection data
	 * @return a selection from the selection data
	 * @throws IllegalArgumentException if the selection data cannot create a selection
	 */
	public Selection buildSelection(SelectionData selData) {
		return this.selStrtg.buildSelection(selData);
	}

	/**
	 * Handles a left click block event
	 * @param userData the user data
	 * @param player the player of the event
	 * @param blockPos the clicked block position 
	 */
	public void onLeftClickBlock(UserData userData, Player player, BlockPosition blockPos) {
		this.selStrtg.onLeftClickBlock(userData, player, blockPos);
	}
	
	/**
	 * Handles a right click block event
	 * @param userData the user data
	 * @param player the player of the event
	 * @param blockPos the clicked block position 
	 */
	public void onRightClickBlock(UserData userData, Player player, BlockPosition blockPos) {
		this.selStrtg.onRightClickBlock(userData, player, blockPos);
	}
	
}
