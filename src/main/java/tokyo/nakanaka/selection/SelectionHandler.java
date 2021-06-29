package tokyo.nakanaka.selection;

import java.util.List;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.ValuePool;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.region3D.BoundRegion3D;

public interface SelectionHandler {
	void onLeftClickBlock(ValuePool regionPool, Logger logger, BlockVector3D blockPos);
	void onRightClickBlock(ValuePool regionPool, Logger logger, BlockVector3D blockPos);
	String getDefaultOffsetLabel();
	List<String> getCommandLabelList();
	boolean onCommand(ValuePool regionPool, Logger logger, String label, BlockVector3D playerPos, String[] args);
	List<String> onTabComplete(String label, String[] args);
	List<Pair<String, String>> onRegionMessage(ValuePool regionPool);
	BoundRegion3D build(ValuePool regionPool);
}
