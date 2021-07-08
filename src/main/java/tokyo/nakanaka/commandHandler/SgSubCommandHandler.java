package tokyo.nakanaka.commandHandler;

import java.util.List;

import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.player.Player;

public interface SgSubCommandHandler {
	String getLabel();
	BranchCommandHelp getCommandHelp();
	void onCommand(Player player, String[] args);
	List<String> onTabComplete(Player player, String[] args);
}
