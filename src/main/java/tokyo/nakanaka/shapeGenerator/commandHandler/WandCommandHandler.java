package tokyo.nakanaka.shapeGenerator.commandHandler;

import java.util.List;

import tokyo.nakanaka.Item;
import tokyo.nakanaka.NamespacedID;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.commandHelp.WandHelp;
import tokyo.nakanaka.shapeGenerator.user.User;

public class WandCommandHandler implements UserCommandHandler {

	@Override
	public void onCommand(User user, CommandSender cmdSender, String[] args) {
		if(!(cmdSender instanceof Player player)) {
			cmdSender.print(LogColor.RED + "Player only command");
			return;
		}
		if(args.length != 0) {
			cmdSender.print(LogColor.RED + "Usage: " + new WandHelp().getUsage());
			return;
		}
		Item item = new Item(new NamespacedID("minecraft", "blaze_rod"));
		player.giveItem(item, 1);
	}

	@Override
	public List<String> onTabComplete(User user, CommandSender cmdSender, String[] args) {
		return List.of();
	}

}
