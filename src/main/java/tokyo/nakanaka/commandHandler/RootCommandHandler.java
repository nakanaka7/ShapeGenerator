package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class RootCommandHandler {
	private CommandEntry cmdEntry;

	public RootCommandHandler(CommandEntry cmdEntry) {
		this.cmdEntry = cmdEntry;
	}
	
	public void onCommand(Player player, String[] args) {
		if(cmdEntry instanceof CommandHandler) {
			CommandHandler cmdHandler = (CommandHandler)cmdEntry;
			cmdHandler.onCommand(player, args);
		}else if(cmdEntry instanceof CommandDirectory) {
			CommandDirectory cmdDirectory = (CommandDirectory)cmdEntry;
			List<CommandEntry> subEntryList = cmdDirectory.getSubList(player);
			String subLabel = args[0];
			String[] subArgs = new String[args.length - 1];
			System.arraycopy(args, 1, subArgs, 0, args.length - 1);
			for(CommandEntry subEntry : subEntryList) {
				if(subEntry.getLabel().equals(subLabel)) {
					new RootCommandHandler(subEntry).onCommand(player, subArgs);
					return;
				}
			}
			Logger logger = player.getLogger();
			logger.print(LogColor.RED + "Unkown command");
		}
	}

	public List<String> onTabComplete(Player player, String[] args){
		if(cmdEntry instanceof CommandHandler) {
			return ((CommandHandler)cmdEntry).onTabComplete(player, args);
		}else if(cmdEntry instanceof CommandDirectory) {
			CommandDirectory cmdDirectory = (CommandDirectory)cmdEntry;
			List<CommandEntry> subEntryList = cmdDirectory.getSubList(player);
			String subLabel = args[0];
			String[] subArgs = new String[args.length - 1];
			System.arraycopy(args, 1, subArgs, 0, args.length - 1);
			if(args.length == 1) {
				return subEntryList.stream()
						.map(s-> s.getLabel())
						.collect(Collectors.toList());
			}
			for(CommandEntry subEntry : subEntryList) {
				if(subEntry.getLabel().equals(subLabel)) {
					return new RootCommandHandler(subEntry).onTabComplete(player, subArgs);
				}
			}
			return new ArrayList<>();
		}else {
			return new ArrayList<>();
		}
	}
	
}
