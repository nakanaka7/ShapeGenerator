package tokyo.nakanaka.commandHandler;

import java.util.Arrays;
import java.util.List;

import static tokyo.nakanaka.logger.LogConstant.*;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.selection.SelectionBuilder;
import tokyo.nakanaka.selection.SelectionManager;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.world.World;

public class SelCommandHandler implements CommandHandler{
	private SelectionManager selManager;
	
	public SelCommandHandler(SelectionManager selManager) {
		this.selManager = selManager;
	}

	@Override
	public String getLabel() {
		return "selection";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("sel");
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		World world = player.getWorld();
		int offsetX = player.getX();
		int offsetY = player.getY();
		int offsetZ = player.getZ();
		SelectionBuilder builder = player.getSelectionBuilder();
		boolean success = builder.onCommand(world, offsetX, offsetY, offsetZ, args);
		SelectionShape shape = this.selManager.getSelectionShape(builder);
		player.getLogger().print(HEAD_NORMAL + shape.toString() + " selection");
		List<String> lines = builder.getStateLines();
		for(String line : lines) {
			player.getLogger().print(INDENT_NORMAL + line);
		}
		return success;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		SelectionBuilder builder = player.getSelectionBuilder();
		return builder.onTabComplete(args);
	}

}
