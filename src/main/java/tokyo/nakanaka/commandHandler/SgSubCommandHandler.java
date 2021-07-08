package tokyo.nakanaka.commandHandler;

import java.util.List;

import tokyo.nakanaka.player.Player;

public interface SgSubCommandHandler {
	String getLabel();
	String getDescription();
	String getUsage();
	void onCommand(Player player, String[] args);
	List<String> onTabComplete(Player player, String[] args);
}
