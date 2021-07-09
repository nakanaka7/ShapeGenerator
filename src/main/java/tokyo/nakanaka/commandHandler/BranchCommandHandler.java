package tokyo.nakanaka.commandHandler;

import tokyo.nakanaka.commadHelp.BranchCommandHelp;

public interface BranchCommandHandler extends CommandHandler {
	BranchCommandHelp getCommandHelp();
}
