package tokyo.nakanaka.shapeGenerator.commandHandler;

import java.util.List;

import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.shapeGenerator.user.User;

/**
 * A command handling class, which need a user
 */
public interface UserCommandHandler {
	/**
	 * Handles a command
	 * @param user a user 
	 * @param cmdSender a command sender
	 * @param args arguments of the command
	 */
	void onCommand(User user, CommandSender cmdSender, String[] args);
	/**
	 * Returns a list for tab complete for a command 
	 * @param user a user
	 * @param cmdSender a command sender
	 * @param args arguments of the command
	 * @return a list for tab complete for a command
	 */
	List<String> onTabComplete(User user, CommandSender cmdSender, String[] args);
}
