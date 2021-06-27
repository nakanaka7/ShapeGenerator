package tokyo.nakanaka.selection;

import java.util.List;

import tokyo.nakanaka.math.BlockVector3D;

public interface SelCommandHandler {
	void onCommand(BlockVector3D playerPos, String[] args);
	List<String> onTabComplete(String[] args);
}
