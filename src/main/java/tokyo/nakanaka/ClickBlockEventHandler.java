package tokyo.nakanaka;

import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.SelectionBuilder;
import tokyo.nakanaka.selection.SelectionMessenger;
import tokyo.nakanaka.world.World;

public class ClickBlockEventHandler {
	public SelectionMessenger messenger;
	
	public ClickBlockEventHandler(SelectionMessenger messenger) {
		this.messenger = messenger;
	}

	public void onLeftClickBlock(Player player, World world, int x, int y, int z) {
		SelectionBuilder builder = player.getSelectionBuilder();
		builder.onLeftClickBlock(x, y, z);
		this.messenger.sendMessage(player);
	}
	
	public void onRightClickBlock(Player player, World world, int x, int y, int z) {
		player.getSelectionBuilder().onRightClickBlock(x, y, z);
		this.messenger.sendMessage(player);
	}
}
