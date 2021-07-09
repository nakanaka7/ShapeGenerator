package tokyo.nakanaka.commandHandler;

import java.util.List;

import tokyo.nakanaka.player.Player;

public interface CommandHandler {
	String getLabel();
	void onCommand(Player player, String[] args);
	List<String> onTabComplete(Player player, String[] args);
}
