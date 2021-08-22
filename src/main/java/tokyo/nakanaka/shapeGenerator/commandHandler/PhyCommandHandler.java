package tokyo.nakanaka.shapeGenerator.commandHandler;

import java.util.List;
import java.util.Map;

import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.commandHelp.PhyHelp;
import tokyo.nakanaka.shapeGenerator.user.User;

public class PhyCommandHandler implements UserCommandHandler {
	private Map<User, Boolean> phyMap;
	
	public PhyCommandHandler(Map<User, Boolean> phyMap) {
		this.phyMap = phyMap;
	}

	@Override
	public void onCommand(User user, CommandSender cmdSender, String[] args) {
		String usageMsg = LogColor.RED + "Usage: " + new PhyHelp().getUsage();
		if(args.length != 1) {
			cmdSender.print(usageMsg);
			return;
		}
		boolean physics;
		String bool = args[0];
		if(bool.equals("true")) {
			physics = true;
		}else if(bool.equals("false")) {
			physics = false;
		}else {
			cmdSender.print(usageMsg);
			return;
		}
		this.phyMap.put(user, physics);
		cmdSender.print(LogColor.GOLD + "Set physics -> " + bool);
	}

	@Override
	public List<String> onTabComplete(User user, CommandSender cmdSender, String[] args) {
		if(args.length == 1) {
			return List.of("true", "false");
		}else {
			return List.of();
		}
	}

}
