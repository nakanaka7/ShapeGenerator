package tokyo.nakanaka.shapeGenerator.user;

import tokyo.nakanaka.shapeGenerator.UndoCommandManager;

public class UserData {
	private boolean physics = false;
	private UndoCommandManager undoCmdManager = new UndoCommandManager();

	public boolean getPhysics() {
		return physics;
	}
	
	public UndoCommandManager getUndoCmdManager() {
		return undoCmdManager;
	}
	
	
	
}
