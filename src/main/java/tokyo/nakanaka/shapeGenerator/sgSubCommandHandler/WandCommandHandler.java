package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Item;
import tokyo.nakanaka.NamespacedID;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.commandHelp.WandHelp;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Handles "/sg wand" command
 */
public class WandCommandHandler implements SgSubCommandHandler {

	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
		if(args.length != 0) {
			player.print(LogColor.RED + "Usage: " + new WandHelp().getUsage());
			return;
		}
		Item item = new Item(new NamespacedID("minecraft", "blaze_rod"));
		player.giveItem(item, 1);
	}

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		return List.of();
	}

}