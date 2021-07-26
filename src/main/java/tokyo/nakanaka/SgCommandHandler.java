package tokyo.nakanaka;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.commadHelp.BranchCommandHelpNew;
import tokyo.nakanaka.commandHandler.CommandDirectory;
import tokyo.nakanaka.commandHandler.CommandEntry;
import tokyo.nakanaka.commandHandler.CommandHandler;
import tokyo.nakanaka.commandHandler.SgCommandDirectory;
import tokyo.nakanaka.logger.LogDesignColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class SgCommandHandler {
	private SgCommandDirectory sgCmdDir;

	public SgCommandHandler(SgCommandDirectory sgCmdDir) {
		this.sgCmdDir = sgCmdDir;
	}
	
	public void onCommand(Player player, String[] args) {
		onRecursiveCommand(new String[0], this.sgCmdDir, player, args);
	}
	
	private static void onRecursiveCommand(String[] parentLabels, CommandEntry cmdEntry, Player player, String[] args) {
		Logger logger = player.getLogger();
		if(cmdEntry instanceof CommandHandler) {
			CommandHandler cmdHandler = (CommandHandler)cmdEntry;
			boolean success = cmdHandler.onCommand(player, args);
			if(!success) {
				BranchCommandHelpNew help = new BranchCommandHelpNew(parentLabels, cmdHandler);
				logger.print(LogDesignColor.ERROR + "Usage: " + help.getUsage());
				return;
			}
		}else if(cmdEntry instanceof CommandDirectory) {
			CommandDirectory cmdDir = (CommandDirectory)cmdEntry;
			List<CommandEntry> subEntryList = cmdDir.getSubList(player);
			if(args.length == 0) {
				logger.print(LogDesignColor.NORMAL + "See help");
				return;
			}
			String subLabel = args[0];
			CommandEntry subEntry = null;
			for(CommandEntry e : subEntryList) {
				if(e.getLabel().equals(subLabel)) {
					subEntry = e;
				}
			}
			if(subEntry == null) {
				logger.print(LogDesignColor.ERROR + "Unknown subcommand");
				return;
			}
			String[] subParentLabels = new String[parentLabels.length + 1];
			System.arraycopy(parentLabels, 0, subParentLabels, 0, parentLabels.length);
			subParentLabels[parentLabels.length] = cmdEntry.getLabel();
			String[] subArgs = new String[args.length - 1];
			System.arraycopy(args, 1, subArgs, 0, args.length - 1);
			onRecursiveCommand(subParentLabels, subEntry, player, subArgs);
		}
	}

	public List<String> onTabComplete(Player player, String[] args) {
		return onRecursiveTabComplete(this.sgCmdDir, player, args);
	}
	
	private List<String> onRecursiveTabComplete(CommandEntry cmdEntry, Player player, String[] args) {
		if(cmdEntry instanceof CommandHandler) {
			CommandHandler cmdHandler = (CommandHandler)cmdEntry;
			return cmdHandler.onTabComplete(player, args);
		}else if(cmdEntry instanceof CommandDirectory) {
			CommandDirectory cmdDir = (CommandDirectory)cmdEntry;
			List<CommandEntry> subCmdEntryList = cmdDir.getSubList(player);
			if(args.length == 1) {
				return subCmdEntryList.stream()
						.map(s -> s.getLabel())
						.collect(Collectors.toList());
			}
			CommandEntry subCmdEntry = null;
			String subLabel = args[0];
			for(CommandEntry e : subCmdEntryList) {
				if(e.getLabel().equals(subLabel)) {
					subCmdEntry = e;
					break;
				}
			}
			if(subCmdEntry == null) {
				return new ArrayList<>();
			}
			String[] subArgs = new String[args.length - 1];
			System.arraycopy(args, 1, subArgs, 0, args.length - 1);
			return onRecursiveTabComplete(subCmdEntry, player, subArgs);
		}else {
			//unreachable
			return new ArrayList<>();
		}
	}

}
