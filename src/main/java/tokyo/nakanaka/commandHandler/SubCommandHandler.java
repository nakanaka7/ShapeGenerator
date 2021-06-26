package tokyo.nakanaka.commandHandler;

import tokyo.nakanaka.commadHelp.CommandHelp;

public interface SubCommandHandler extends CommandHandler{
	CommandHelp getCommandHelp();
}
