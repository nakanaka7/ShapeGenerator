package tokyo.nakanaka;

import java.util.UUID;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.selection.SelectionBuilder;
import tokyo.nakanaka.world.World;

public class Player {
	private UUID uid;
	private String name;
	private Logger logger;
	private World world;
	private int x;
	private int y;
	private int z;
	private SelectionBuilder builder;
	private boolean blockPhysics;
	
	public Player(UUID uid, String name) {
		this.uid = uid;
		this.name = name;
	}

	public Player(UUID uid) {
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

	public SelectionBuilder getSelectionBuilder() {
		return builder;
	}

	public void setBuilder(SelectionBuilder builder) {
		this.builder = builder;
	}

	public boolean getBlockPhysics() {
		return blockPhysics;
	}

	public void setBlockPhysics(boolean blockPhysics) {
		this.blockPhysics = blockPhysics;
	}

}
