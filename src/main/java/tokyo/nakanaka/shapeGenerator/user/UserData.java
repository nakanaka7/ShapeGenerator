package tokyo.nakanaka.shapeGenerator.user;

import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.UndoCommandManager;

public class UserData {
	private SelectionShape shape;
	private SelectionData selData;
	private UndoCommandManager undoCmdManager = new UndoCommandManager();
	private boolean blockPhysics = false;
	
	/**
	 * Returns a selection shape for the user
	 * @return a selection shape for the user
	 */
	public SelectionShape getSelectionShape() {
		return shape;
	}

	/**
	 * Set a selection shape for the user
	 * @param shape a selection shape
	 */
	public void setSelectionShape(SelectionShape shape) {
		this.shape = shape;
	}
	
	public SelectionData getSelectionData() {
		return selData;
	}

	public void setSelectionData(SelectionData selData) {
		this.selData = selData;
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
