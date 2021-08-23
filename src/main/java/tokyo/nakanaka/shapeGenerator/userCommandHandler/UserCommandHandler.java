package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.List;

import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public interface UserCommandHandler extends CommandEntry {
	List<ParameterHelp> getParameterHelpList();
	void onCommand(UserData userData, CommandSender cmdSender, String[] args);
	List<String> onTabComplete(UserData userData, CommandSender cmdSender, String[] args);
}
