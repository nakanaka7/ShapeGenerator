package tokyo.nakanaka.selection;

import java.util.List;

import tokyo.nakanaka.world.World;

public interface SelectionBuilder {
	World getWorld();
	boolean onLeftClickBlock(int x, int y, int z);
	boolean onRightClickBlock(int x, int y, int z);
	boolean onCommand(int offsetX, int offsetY, int offsetZ, String[] args);
	List<String> onTabComplete(String[] args);
	List<String> getStateLines();
	/**
	 * @throw IllegalStateException
	 */
	Selection build();
}
