package tokyo.nakanaka;

import java.util.List;

import tokyo.nakanaka.commandHandler.CommandEntry;
import tokyo.nakanaka.commandHandler.CommandHandler;
import tokyo.nakanaka.commandHandler.SelCommandDirectory;
import tokyo.nakanaka.commandHandler.SgCommandDirectory;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class SgCommandHandler {
	private SgCommandDirectory sgCmdDir;

	public SgCommandHandler(SgCommandDirectory sgCmdDir) {
		this.sgCmdDir = sgCmdDir;
	}
	
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		List<CommandEntry> subEntryList = sgCmdDir.getSubList(player);
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		for(CommandEntry subEntry : subEntryList) {
			if(subEntry.getLabel().equals(subLabel)) {
				if(subEntry instanceof CommandHandler) {
					CommandHandler subHandler = (CommandHandler)subEntry;
					subHandler.onCommand(player, subArgs);
					return;
				}else if(subEntry instanceof SelCommandDirectory) {
					SelCommandDirectory selCmdDir = (SelCommandDirectory)subEntry;
					List<CommandEntry> selSubList = selCmdDir.getSubList(player);
					if(subArgs.length == 0) {
						return;
					}
					String[] selSubArgs = new String[subArgs.length - 1];
					System.arraycopy(subArgs, 1, selSubArgs, 0, subArgs.length - 1);
					for(CommandEntry selEntry : selSubList) {
						CommandHandler cmdHandler = (CommandHandler)selEntry;
						cmdHandler.onCommand(player, selSubArgs);
					}
				}else {
					//unreachable
					return;
				}
			}
		}
		logger.print(LogColor.RED + "See help by " + LogColor.GOLD + "/sg help");
	}
	
	public List<String> onTabComplete(Player player, String[] args){
		return new TabCompleter(this.sgCmdDir).onTabComplete(player, args);
	}

}
