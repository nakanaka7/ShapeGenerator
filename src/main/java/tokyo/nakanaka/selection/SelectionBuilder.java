package tokyo.nakanaka.selection;

import java.util.List;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.world.World;

public interface SelectionBuilder {
	World getWorld();
	boolean onLeftClickBlock(Logger logger, BlockVector3D blockPos);
	boolean onRightClickBlock(Logger logger, BlockVector3D blockPos);
	/**
	 * @param posX player position x
	 * @param posY player position y
	 * @param posZ player position z
	 * @param args
	 * @return
	 */
	boolean onCommand(Logger logger, BlockVector3D playerPos, String label, String[] args);
	List<String> onLabelList();
	List<String> onTabComplete(String[] args);
	List<String> getStateLines();
	/**
	 * @throw IllegalStateException
	 */
	Selection build();
}
