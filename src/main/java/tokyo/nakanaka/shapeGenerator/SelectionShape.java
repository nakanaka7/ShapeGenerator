package tokyo.nakanaka.shapeGenerator;

import java.util.Map;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeDelegator.*;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public enum SelectionShape {
	CUBOID(new CuboidSelectionShapeDelegator()),
	DIAMOND(new DiamondSelectionShapeDelegator()),
	SPHERE(new SphereSelectionShapeDelegator()),
	TORUS(new TorusSelectionShapeDelegator()),
	LINE(new LineSelectionShapeDelegator()),
	TRIANGLE(new TriangleSelectionShapeDelegator()),
	TETRAHEDRON(new TetrahedronSelectionShapeDelegator()),
	REGULAR_POLYGON(new RegularPolygonSelectionShapeDelegator());
	
	private SelectionShapeDelegator selDlgt;
	
	private SelectionShape(SelectionShapeDelegator selDlgt) {
		this.selDlgt = selDlgt;
	}
	
	/**
	 * Get SelSubCommandHandlerMap
	 * @return SelSubCommandHandlerMap
	 */
	public Map<String, SelSubCommandHandler> selSubCommandHandlerMap() {
		return this.selDlgt.selSubCommandHandlerMap();
	}
	
	/**
	 * Get SelectionData
	 * @param world a world of the selection data
	 * @return SelectionData
	 */
	public SelectionData newSelectionData(World world) {
		return this.selDlgt.newSelectionData(world);
	}
	
	/**
	 * Returns a selection from the selection data
	 * @param selData a selection data
	 * @return a selection from the selection data
	 * @throws IllegalArgumentException if the selection data cannot create a selection
	 */
	public Selection buildSelection(SelectionData selData) {
		return this.selDlgt.buildSelection(selData);
	}

	/**
	 * Handles a left click block event
	 * @param userData the user data
	 * @param player the player of the event
	 * @param blockPos the clicked block position 
	 */
	public void onLeftClickBlock(UserData userData, Player player, BlockPosition blockPos) {
		this.selDlgt.onLeftClickBlock(userData, player, blockPos);
	}
	
	/**
	 * Handles a right click block event
	 * @param userData the user data
	 * @param player the player of the event
	 * @param blockPos the clicked block position 
	 */
	public void onRightClickBlock(UserData userData, Player player, BlockPosition blockPos) {
		this.selDlgt.onRightClickBlock(userData, player, blockPos);
	}
	
}
