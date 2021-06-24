package tokyo.nakanaka.selection;

import java.util.List;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.region3D.BoundRegion3D;

public interface RegionBuilder {
	boolean onLeftClickBlock(Logger logger, int x, int y, int z);
	boolean onRightClickBlock(Logger logger, int x, int y, int z);
	boolean onCommand(Logger logger, int posX, int posY, int posZ, String[] args);
	List<String> onTabComplete(String[] args);
	List<String> getStateLines();
	BoundRegion3D build();
}
