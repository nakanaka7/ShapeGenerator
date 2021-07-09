package tokyo.nakanaka.commandHandler;

import java.util.List;

import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.player.Player;

public interface BranchCommandHandler {
	String getLabel();
	BranchCommandHelp getCommandHelp(Player player);
	void onCommand(Player player, String[] args);
	List<String> onTabComplete(Player player, String[] args);
}
