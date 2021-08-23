package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.List;

import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public interface UserCommandHandler extends CommandEntry {
	List<ParameterHelp> getParameterHelpList();
	void onCommand(UserData user, String[] args);
	List<String> onTabComplete(UserData user, String[] args);
}
