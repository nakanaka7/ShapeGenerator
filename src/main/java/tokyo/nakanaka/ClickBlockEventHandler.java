package tokyo.nakanaka;

import tokyo.nakanaka.world.World;

public class ClickBlockEventHandler {
	public void onLeftClickBlock(Player player, World world, int x, int y, int z) {
		player.getSelectionBuilder().onLeftClickBlock(world, x, y, z);
	}
	
	public void onRightClickBlock(Player player, World world, int x, int y, int z) {
		player.getSelectionBuilder().onRightClickBlock(world, x, y, z);
	}
}
