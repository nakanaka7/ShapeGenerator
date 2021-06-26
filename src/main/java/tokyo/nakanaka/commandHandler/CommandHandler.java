package tokyo.nakanaka.commandHandler;

import java.util.List;

import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.player.Player;

public interface CommandHandler {
	CommandHelp getCommandHelp();
	/**
	 * @param player
	 * @param args
	 * @return false if it should print usage, otherwise true
	 */
	boolean onCommand(Player player, String[] args);
	List<String> onTabComplete(Player player, String[] args);
}
