package tokyo.nakanaka;

import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.SelectionBuilder;
import tokyo.nakanaka.selection.SelectionManager;
import tokyo.nakanaka.selection.SelectionMessengerOld;
import tokyo.nakanaka.world.World;

public class ClickBlockEventHandlerOld {
	private SelectionManager selManager;
	private SelectionMessengerOld messenger;
	
	public ClickBlockEventHandlerOld(SelectionManager selManager) {
		this.selManager = selManager;
		this.messenger = new SelectionMessengerOld(selManager);
	}

	public void onLeftClickBlock(Player player, int x, int y, int z) {
		World world = player.getWorld();
		SelectionBuilder builder = player.getSelectionBuilder();
		if(!world.equals(builder.getWorld())) {
			builder = this.selManager.newInstance(this.selManager.getShape(builder), world);
		}
		player.setSelectionBuilder(builder);
		builder.onLeftClickBlock(player.getLogger(), new BlockVector3D(x, y, z));
		this.messenger.sendMessage(player);
	}
	
	public void onRightClickBlock(Player player, int x, int y, int z) {
		World world = player.getWorld();
		SelectionBuilder builder = player.getSelectionBuilder();
		if(!world.equals(builder.getWorld())) {
			builder = this.selManager.newInstance(this.selManager.getShape(builder), world);
		}
		player.setSelectionBuilder(builder);
		builder.onRightClickBlock(player.getLogger(), new BlockVector3D(x, y, z));
		this.messenger.sendMessage(player);
	}
}
