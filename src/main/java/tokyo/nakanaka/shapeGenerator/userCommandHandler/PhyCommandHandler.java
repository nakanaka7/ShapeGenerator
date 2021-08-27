package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.List;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.commandHelp.PhyHelp;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class PhyCommandHandler implements UserCommandHandler {

	@Override
	public void onCommand(UserData userData, CommandSender cmdSender, String[] args) {
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
		userData.setBlockPhysics(physics);
		cmdSender.print(LogColor.GOLD + "Set physics -> " + bool);
	}

	@Override
	public List<String> onTabComplete(UserData userData, CommandSender cmdSender, String[] args) {
		if(args.length == 1) {
			return List.of("true", "false");
		}else {
			return List.of();
		}
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
