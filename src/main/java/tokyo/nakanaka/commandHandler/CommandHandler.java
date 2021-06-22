package tokyo.nakanaka.commandHandler;

import java.util.List;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.player.Player;

public interface CommandHandler {
	String getDescription();
	String getLabel();
	List<String> getAliases();
	String getUsage();
	/**
	 * @param player
	 * @param args
	 * @return false if it should print usage, otherwise true
	 */
	List<Pair<String, String>> getParameterDescriptionList();
	boolean onCommand(Player player, String[] args);
	List<String> onTabComplete(Player player, String[] args);
}
