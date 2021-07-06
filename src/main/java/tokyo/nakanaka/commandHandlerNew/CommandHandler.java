package tokyo.nakanaka.commandHandlerNew;

import java.util.List;

import tokyo.nakanaka.player.Player;

public interface CommandHandler {
	String getLabel();
	String getDescription();
	void onCommand(Player player, String[] parentLabels, String[] args);
	List<String> onTabComplete(String[] args);
}
