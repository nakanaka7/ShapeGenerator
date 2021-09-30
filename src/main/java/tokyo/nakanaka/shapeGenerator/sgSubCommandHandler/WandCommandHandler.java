package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Item;
import tokyo.nakanaka.NamespacedID;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SgBranchHelpConstants;

/**
 * Handles "/sg wand" command
 */
public class WandCommandHandler implements SubCommandHandler {
	private static final CommandLogColor cmdLogColor = new CommandLogColor(LogColor.GOLD, LogColor.RED);

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
		if(args.length != 0) {
			player.print(cmdLogColor.error() + "Usage: " + SgBranchHelpConstants.WAND.syntax());
			return;
		}
		Item item = new Item(new NamespacedID("minecraft", "blaze_rod"));
		player.giveItem(item, 1);
	}

	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return List.of();
	}

}
