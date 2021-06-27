package tokyo.nakanaka.selection;

import java.util.List;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.world.World;

public interface SelectionBuilder {
	World getWorld();
	void onLeftClickBlock(Logger logger, BlockVector3D blockPos);
	void onRightClickBlock(Logger logger, BlockVector3D blockPos);
	List<String> getLabelList();
	void onCommand(Logger logger, BlockVector3D playerPos, String label, String[] args);
	List<String> onTabComplete(String label, String[] args);
	List<String> getStateLines();
	Selection build();
}
