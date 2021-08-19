package tokyo.nakanaka.shapeGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commandArgument.BlockCommandArgument;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.logger.shapeGenerator.LogDesignColor;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.shapeGenerator.user.User;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.CommandDirectory;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.CommandEntry;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.UserCommandHandler;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.SgCommandDirectory;

public class SgCommandHandler {
	private SgCommandDirectory sgCmdDir;

	public SgCommandHandler(SgCommandDirectory sgCmdDir) {
		this.sgCmdDir = sgCmdDir;
	}
	
	public SgCommandHandler(BlockCommandArgument blockArg, SelectionStrategySource selStrtgSource) {
		this.sgCmdDir = new SgCommandDirectory(blockArg, selStrtgSource);
	}
	
	public void onCommand(User user, String[] args) {
		onRecursiveCommand(new String[0], this.sgCmdDir, user, args);
	}
	
	private static void onRecursiveCommand(String[] parentLabels, CommandEntry cmdEntry, User user, String[] args) {
		Logger logger = user.getLogger();
		if(cmdEntry instanceof UserCommandHandler) {
			UserCommandHandler cmdHandler = (UserCommandHandler)cmdEntry;
			boolean success = cmdHandler.onCommand(user, args);
			if(!success) {
				BranchCommandHelp help = new BranchCommandHelp(parentLabels, cmdHandler);
				logger.print(LogDesignColor.ERROR + "Usage: " + help.getUsage());
				return;
			}
		}else if(cmdEntry instanceof CommandDirectory) {
			CommandDirectory cmdDir = (CommandDirectory)cmdEntry;
			List<CommandEntry> subEntryList = cmdDir.getSubList(user);
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
			onRecursiveCommand(subParentLabels, subEntry, user, subArgs);
		}
	}

	public List<String> onTabComplete(User user, String[] args) {
		return onRecursiveTabComplete(this.sgCmdDir, user, args);
	}
	
	private List<String> onRecursiveTabComplete(CommandEntry cmdEntry, User user, String[] args) {
		if(args.length == 0) {
			return new ArrayList<>();
		}
		if(cmdEntry instanceof UserCommandHandler) {
			UserCommandHandler cmdHandler = (UserCommandHandler)cmdEntry;
			return cmdHandler.onTabComplete(user, args);
		}else if(cmdEntry instanceof CommandDirectory) {
			CommandDirectory cmdDir = (CommandDirectory)cmdEntry;
			List<CommandEntry> subCmdEntryList = cmdDir.getSubList(user);
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
			return onRecursiveTabComplete(subCmdEntry, user, subArgs);
		}else {
			//unreachable
			return new ArrayList<>();
		}
	}

}
