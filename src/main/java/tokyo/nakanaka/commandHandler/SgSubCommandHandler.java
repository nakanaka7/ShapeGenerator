package tokyo.nakanaka.commandHandler;

import java.util.List;

import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.player.Player;

public interface SgSubCommandHandler {
	String getLabel();
	CommandHelp getCommandHelp();
	@Deprecated
	String getDescription();
	@Deprecated
	String getUsage();
	void onCommand(Player player, String[] args);
	List<String> onTabComplete(Player player, String[] args);
}
