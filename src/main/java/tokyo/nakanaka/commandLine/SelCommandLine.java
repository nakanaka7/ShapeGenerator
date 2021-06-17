package tokyo.nakanaka.commandLine;

import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.selection.SelectionBuilder;
import tokyo.nakanaka.world.World;

public class SelCommandLine implements CommandLine{

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
		return success;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		SelectionBuilder builder = player.getSelectionBuilder();
		return builder.onTabComplete(args);
	}

}
