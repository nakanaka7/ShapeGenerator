package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.List;

import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.shapeGenerator.user.UserOld;

public interface UserCommandHandler extends CommandEntry {
	List<ParameterHelp> getParameterHelpList();
	void onCommand(UserOld user, String[] args);
	List<String> onTabComplete(UserOld user, String[] args);
}
