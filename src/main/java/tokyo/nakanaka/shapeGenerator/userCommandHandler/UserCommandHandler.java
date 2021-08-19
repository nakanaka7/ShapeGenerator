package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.List;

import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.shapeGenerator.user.User;

public interface UserCommandHandler extends CommandEntry {
	List<ParameterHelp> getParameterHelpList();
	boolean onCommand(User user, String[] args);
	List<String> onTabComplete(User user, String[] args);
}
