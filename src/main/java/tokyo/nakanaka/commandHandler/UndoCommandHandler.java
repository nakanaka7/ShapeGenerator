package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.UndoCommandManager;
import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.logger.LogDesignColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class UndoCommandHandler implements CommandHandler{
	private BranchCommandHelp cmdHelp;
	
	public UndoCommandHandler() {
		this.cmdHelp = new BranchCommandHelp.Builder("undo")
				.description("Undo block changing command(s)")
				.addParameter(ParameterType.OPTIONAL, "number")
				.build();
	}
	
	@Override
	public String getLabel() {
		return "undo";
	}
	
	@Override
	public String getDescription() {
		return "Undo block changing command(s)";
	}
	
	@Override
	public List<ParameterHelp> getParameterHelpList() {
		List<ParameterHelp> list = new ArrayList<>();
		list.add(new ParameterHelp(ParameterType.OPTIONAL, "number", ""));
		return list;
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length > 1) {
			logger.print(LogDesignColor.ERROR + "Usage: " + "/sg " + this.cmdHelp.getUsage());
			return false;
		}
		int num = 1;
		if(args.length == 1) {
			try {
				num = Integer.parseInt(args[0]);
			}catch(IllegalArgumentException e) {
				logger.print(LogDesignColor.ERROR + "Can not parse the number");
				return false;
			}
			if(num <= 0) {
				logger.print(LogDesignColor.ERROR + "The number must be larger than 0");
				return false;
			}
		}
		UndoCommandManager undoManager = player.getUndoCommandManager();
		int totalNum = 0;
		for(int i = 0; i < num; ++i) {
			boolean success = undoManager.undo();
			if(!success) {
				break;
			}
			++totalNum;
		}
		if(totalNum == 0) {
			logger.print(LogDesignColor.ERROR + "Nothing to undo");
			return false;
		}
		logger.print(LogDesignColor.NORMAL + "Undid " + totalNum + " command(s)");
		if(totalNum < num) {
			logger.print(LogDesignColor.ERROR + "Reached the beginning command");
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		}
		return new ArrayList<>();
	}

}
