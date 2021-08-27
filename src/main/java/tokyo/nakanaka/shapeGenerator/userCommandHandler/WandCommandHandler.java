package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.List;

import tokyo.nakanaka.Item;
import tokyo.nakanaka.NamespacedID;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.commandHelp.WandHelp;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class WandCommandHandler implements UserCommandHandler {

	@Override
	public void onCommand(UserData userData, CommandSender cmdSender, String[] args) {
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
	public List<String> onTabComplete(UserData userData, CommandSender cmdSender, String[] args) {
		return List.of();
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ParameterHelp> getParameterHelpList() {
		// TODO Auto-generated method stub
		return null;
	}

}
