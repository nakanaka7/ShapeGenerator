package tokyo.nakanaka;

import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.CuboidSelectionBuilder;
import tokyo.nakanaka.selection.SelectionBuilder;
import tokyo.nakanaka.selection.SelectionManager;
import tokyo.nakanaka.selection.SelectionMessenger;
import tokyo.nakanaka.world.World;

public class ClickBlockEventHandler {
	private SelectionManager selManager;
	private SelectionMessenger messenger;
	
	public ClickBlockEventHandler(SelectionManager selManager) {
		this.selManager = selManager;
		this.messenger = new SelectionMessenger(selManager);
	}

	public void onLeftClickBlock(Player player, int x, int y, int z) {
		World world = player.getWorld();
		SelectionBuilder builder = player.getSelectionBuilder();
		if(builder == null) {
			builder = new CuboidSelectionBuilder(world);
		}
		if(!world.equals(builder.getWorld())) {
			builder = this.selManager.newInstance(this.selManager.getShape(builder), world);
		}
		player.setSelectionBuilder(builder);
		builder.onLeftClickBlock(player.getLogger(), x, y, z);
		this.messenger.sendMessage(player);
	}
	
	public void onRightClickBlock(Player player, int x, int y, int z) {
		World world = player.getWorld();
		SelectionBuilder builder = player.getSelectionBuilder();
		if(builder == null) {
			builder = new CuboidSelectionBuilder(world);
		}
		if(!world.equals(builder.getWorld())) {
			builder = this.selManager.newInstance(this.selManager.getShape(builder), world);
		}
		player.setSelectionBuilder(builder);
		builder.onRightClickBlock(player.getLogger(), x, y, z);
		this.messenger.sendMessage(player);
	}
}
