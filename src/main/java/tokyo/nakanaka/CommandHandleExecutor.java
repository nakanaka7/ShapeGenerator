package tokyo.nakanaka;

import java.util.List;

import tokyo.nakanaka.commandHandler.BranchCommandHandler;
import tokyo.nakanaka.commandHandler.CommandDirectory;
import tokyo.nakanaka.player.Player;

public class CommandHandleExecutor {
	private CommandDirectory root;
	
	public CommandHandleExecutor(CommandDirectory root) {
		this.root = root;
	}

	public void onCommand(Player player, String[] args) {
		if(args.length == 0) {
			//help
			return;
		}
		String subLabel = args[0];
		String[] shiftArg = new String[args.length - 1];
		List<BranchCommandHandler> subList = this.root.getSubList(player);
		
	}
	
	
}
