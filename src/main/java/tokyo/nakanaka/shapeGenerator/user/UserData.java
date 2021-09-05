package tokyo.nakanaka.shapeGenerator.user;

import java.util.UUID;

import tokyo.nakanaka.World;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.UndoCommandManager;

public class UserData {
	private UUID uid;
	private String name;
	private Logger logger;
	private World world;
	private SelectionShape shape;
	private SelectionData selData;
	private UndoCommandManager undoCmdManager = new UndoCommandManager();
	private boolean blockPhysics = false;
	
	public UserData() {
	}
	
	public UserData(UUID uid, String name) {
		this.uid = uid;
		this.name = name;
	}

	public UserData(UUID uid) {
		this.uid = uid;
	}
	
	public UUID getUniqueId() {
		return uid;
	}
	
	public String getName() {
		return name;
	}
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
		
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
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
