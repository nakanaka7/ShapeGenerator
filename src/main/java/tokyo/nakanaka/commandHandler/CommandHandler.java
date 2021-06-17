package tokyo.nakanaka.commandHandler;

import java.util.List;

import tokyo.nakanaka.Player;

public interface CommandHandler {
	String getLabel();
	List<String> getAliases();
	
	boolean onCommand(Player player, String[] args);
	List<String> onTabComplete(Player player, String[] args);
}
