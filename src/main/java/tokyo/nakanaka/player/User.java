package tokyo.nakanaka.player;

import java.util.UUID;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.World;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.shapeGenerator.UndoCommandManager;

public class User {
	private UUID uid;
	private String name;
	private Logger logger;
	private World world;
	private int x;
	private int y;
	private int z;
	private SelectionShape shape;
	private SelectionBuildingData selData;
	private UndoCommandManager undoCmdManager = new UndoCommandManager();
	private boolean blockPhysics = false;
	
	public User(UUID uid, String name) {
		this.uid = uid;
		this.name = name;
	}

	public User(UUID uid) {
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
	
	public void setBlockPosition(BlockPosition blockPos) {
		this.world = blockPos.world();
		this.x = blockPos.x();
		this.y = blockPos.y();
		this.z = blockPos.z();
	}
	
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public SelectionShape getSelectionShape() {
		return shape;
	}

	public void setSelectionShape(SelectionShape shape) {
		this.shape = shape;
	}

	public SelectionBuildingData getSelectionBuildingData() {
		return selData;
	}

	public void setSelectionBuildingData(SelectionBuildingData selData) {
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
