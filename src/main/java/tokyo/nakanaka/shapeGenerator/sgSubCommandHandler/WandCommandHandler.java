package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.*;
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
	public String label() {
		return "wand";
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
		if(args.length != 0) {
			player.print(cmdLogColor.error() + "Usage: " + "/sg " + SgBranchHelpConstants.WAND.syntax());
			return;
		}
		Item item = new Item(new NamespacedID("minecraft", "blaze_rod"));
		Enchantment ench = new Enchantment(new NamespacedID("minecraft", "power"), 1);
		ItemStackData itemStackData = new ItemStackData(item, 1, ench);
		player.giveItem(itemStackData);
	}

	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return List.of();
	}

}
