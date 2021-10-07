package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.*;
import tokyo.nakanaka.shapeGenerator.Main;
import tokyo.nakanaka.shapeGenerator.SgSublabel;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.ParameterUsage;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SgSubcommandHelps;

/**
 * Handles "/sg wand" command
 */
public class WandCommandHandler implements SubCommandHandler {

	@Override
	public String label() {
		return "wand";
	}

	@Override
	public String description() {
		return "Give a player a wand";
	}

	public ParameterUsage[] parameterUsages() {
		return new ParameterUsage[]{};
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
		String usage = Main.SG + " " + SgSublabel.WAND + " " + String.join(" ", SgSubcommandHelps.WAND.parameterSyntaxes());
		if(args.length != 0) {
			player.print(cmdLogColor.error() + "Usage: " + usage);
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
