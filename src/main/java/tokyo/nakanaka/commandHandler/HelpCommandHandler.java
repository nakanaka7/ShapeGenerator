package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.commadHelp.RootCommandHelp;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.logger.shapeGenerator.LogDesignColor;
import tokyo.nakanaka.logger.shapeGenerator.LogTemplate;
import tokyo.nakanaka.shapeGenerator.user.User;

public class HelpCommandHandler implements CommandHandler {
	private SgCommandDirectory sgCmdDir;
	
	public HelpCommandHandler(SgCommandDirectory sgCmdDir) {
		this.sgCmdDir = sgCmdDir;
	}
	
	@Override
	public String getLabel() {
		return "help";
	}
	
	@Override
	public String getDescription() {
		return "Print command help";
	}
	
	@Override
	public List<ParameterHelp> getParameterHelpList() {
		List<ParameterHelp> list = new ArrayList<>();
		list.add(new ParameterHelp(ParameterType.OPTIONAL, "subcommand", ""));
		return list;
	}
			
	public boolean onCommand(User user, String[] args) {
		return onRecursiveCommand(new String[0], this.sgCmdDir, user, args);
	}
	
	private static boolean onRecursiveCommand(String[] parentLabels, CommandEntry cmdEntry, User user, String[] args) {
		Logger logger = user.getLogger();
		if(cmdEntry instanceof CommandHandler) {
			if(args.length != 0) {
				return false;
			}
			CommandHandler cmdHandler = (CommandHandler)cmdEntry;
			BranchCommandHelp cmdHelp = new BranchCommandHelp(parentLabels, cmdHandler);
			logger.print(LogTemplate.ofLine("Help for " + cmdHelp.getSubject()));
			logger.print(LogTemplate.ofKeyValue("Description", cmdHelp.getDescription()));
			logger.print(LogTemplate.ofKeyValue("Usage", cmdHelp.getUsage()));
			logger.print(LogTemplate.ofKeyValue("Arguments", ""));
			cmdHelp.getParameterDescriptionList().stream()
				.forEach(s -> logger.print("  " + LogTemplate.ofKeyValue(s.getFirst(), s.getSecond())));
			return true;
		}else if(cmdEntry instanceof CommandDirectory) {
			CommandDirectory cmdDir = (CommandDirectory)cmdEntry;
			if(args.length == 0) {
				RootCommandHelp cmdHelp = new RootCommandHelp(parentLabels, cmdDir);
				logger.print(LogTemplate.ofLine("Help for " + cmdHelp.getSubject()));
				logger.print(LogTemplate.ofKeyValue("Description", cmdHelp.getDescription()));
				logger.print(LogTemplate.ofKeyValue("Usage", cmdHelp.getUsage()));
				logger.print(LogTemplate.ofKeyValue("SubCommand", ""));
				cmdHelp.getSubCommandDescriptionList(user).stream()
				.forEach(s -> logger.print("  " + LogTemplate.ofKeyValue(s.getFirst(), s.getSecond())));
				return true;
			}
			List<CommandEntry> subList = cmdDir.getSubList(user);
			String subLabel = args[0];
			CommandEntry subEntry = null;
			for(CommandEntry e : subList) {
				if(e.getLabel().equals(subLabel)) {
					subEntry = e;
					break;
				}
			}
			if(subEntry == null) {
				logger.print(LogDesignColor.ERROR + "Unknown subcommand");
				return true;
			}
			String[] subParentLabels = new String[parentLabels.length + 1];
			System.arraycopy(parentLabels, 0, subParentLabels, 0, parentLabels.length);
			subParentLabels[parentLabels.length] = cmdEntry.getLabel();
			String[] subArgs = new String[args.length - 1];
			System.arraycopy(args, 1, subArgs, 0, args.length - 1);
			return onRecursiveCommand(subParentLabels, subEntry, user, subArgs);
		}else {
			//unreachable
			return false;
		}
	}

	public List<String> onTabComplete(User user, String[] args) {
		return onRecursiveTabComplete(this.sgCmdDir, user, args);
	}
	
	private static List<String> onRecursiveTabComplete(CommandEntry cmdEntry, User user, String[] args) {
		if(cmdEntry instanceof CommandHandler) {
			return new ArrayList<>();
		}else if(cmdEntry instanceof CommandDirectory) {
			CommandDirectory cmdDir = (CommandDirectory)cmdEntry;
			List<CommandEntry> subList = cmdDir.getSubList(user);
			if(args.length == 1) {
				return subList.stream()
						.map(s -> s.getLabel())
						.collect(Collectors.toList());
			}else {
				String subLabel = args[0];
				CommandEntry subEntry = null;
				for(CommandEntry e : subList) {
					if(e.getLabel().equals(subLabel)) {
						subEntry = e;
						break;
					}
				}
				if(subEntry == null) {
					return new ArrayList<>();
				}
				String[] subArgs = new String[args.length - 1];
				System.arraycopy(args, 1, subArgs, 0, args.length - 1);
				return onRecursiveTabComplete(subEntry, user, subArgs);
			}
		}else {
			return new ArrayList<>();
		}
	}
	
}
