package tokyo.nakanaka.commandHandler;

import tokyo.nakanaka.commadHelp.RootCommandHelp;
import tokyo.nakanaka.player.Player;

public interface RootCommandHandler extends CommandHandler {
	RootCommandHelp getCommandHelp(Player player);
}
