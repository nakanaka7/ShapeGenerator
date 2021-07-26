package tokyo.nakanaka.commandHandler;

import java.util.List;

import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.player.Player;

public interface CommandHandler extends CommandEntry {
	List<ParameterHelp> getParameterHelpList();
	void onCommand(Player player, String[] args);
	List<String> onTabComplete(Player player, String[] args);
}
