package tokyo.nakanaka.commandHandler;

import java.util.List;

import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.player.Player;

public interface BranchCommandHandler {
	String getLabel();
	String getDescription();
	CommandHelp getCommandHelp(Player player);
	void onCommand(Player player, String[] args);
	List<String> onTabComplete(Player player, String[] args);
}
