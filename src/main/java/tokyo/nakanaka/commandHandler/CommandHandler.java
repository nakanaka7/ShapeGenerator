package tokyo.nakanaka.commandHandler;

import java.util.List;

import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.player.User;

public interface CommandHandler extends CommandEntry {
	List<ParameterHelp> getParameterHelpList();
	boolean onCommand(User user, String[] args);
	List<String> onTabComplete(User user, String[] args);
}
