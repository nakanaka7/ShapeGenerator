package tokyo.nakanaka;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.commandHandler.CommandDirectory;
import tokyo.nakanaka.commandHandler.CommandEntry;
import tokyo.nakanaka.commandHandler.CommandHandler;
import tokyo.nakanaka.commandHandler.RootCommandHandler;
import tokyo.nakanaka.player.Player;

public class TabCompleter {
	private CommandEntry cmdEntry;
	
	public TabCompleter(CommandEntry cmdEntry) {
		this.cmdEntry = cmdEntry;
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
