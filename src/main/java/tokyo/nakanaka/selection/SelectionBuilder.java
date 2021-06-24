package tokyo.nakanaka.selection;

import java.util.List;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.world.World;

public interface SelectionBuilder {
	World getWorld();
	boolean onLeftClickBlock(Logger logger, int x, int y, int z);
	boolean onRightClickBlock(Logger logger, int x, int y, int z);
	/**
	 * @param posX player position x
	 * @param posY player position y
	 * @param posZ player position z
	 * @param args
	 * @return
	 */
	boolean onCommand(Logger logger, int posX, int posY, int posZ, String[] args);
	List<String> onTabComplete(String[] args);
	List<String> getStateLines();
	/**
	 * @throw IllegalStateException
	 */
	Selection build();
}
