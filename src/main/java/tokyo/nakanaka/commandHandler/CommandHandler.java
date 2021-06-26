package tokyo.nakanaka.commandHandler;

import java.util.List;

import tokyo.nakanaka.player.Player;

public interface CommandHandler {
	boolean onCommand(Player player, String[] args);
	List<String> onTabComplete(Player player, String[] args);
}
