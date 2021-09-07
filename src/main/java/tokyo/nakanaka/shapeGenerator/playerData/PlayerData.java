package tokyo.nakanaka.shapeGenerator.playerData;

import tokyo.nakanaka.shapeGenerator.SelectionBuilder;
import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.UndoCommandManager;

public class PlayerData {
	private SelectionShape shape;
	private SelectionBuilder selBuilder;
	private UndoCommandManager undoCmdManager = new UndoCommandManager();
	private boolean blockPhysics = false;
	
	/**
	 * Returns a selection shape for the player
	 * @return a selection shape for the player
	 */
	public SelectionShape getSelectionShape() {
		return shape;
	}

	/**
	 * Set a selection shape for the player
	 * @param shape a selection shape
	 */
	public void setSelectionShape(SelectionShape shape) {
		this.shape = shape;
	}
	
	public SelectionBuilder getSelectionBuilder() {
		return selBuilder;
	}

	public void setSelectionBuilder(SelectionBuilder selBuilder) {
		this.selBuilder = selBuilder;
	}

	public UndoCommandManager getUndoCommandManager() {
		return undoCmdManager;
	}
	
	public boolean getBlockPhysics() {
		return blockPhysics;
	}

	public void setBlockPhysics(boolean blockPhysics) {
		this.blockPhysics = blockPhysics;
	}

}
