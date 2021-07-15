package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.commandArgument.BlockCommandArgument;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;

public class SgCommandHandler implements CommandDirectory {
	private List<CommandEntry> subList = new ArrayList<>();
	
	public SgCommandHandler(BlockCommandArgument blockArg, SelectionStrategySource selStraSource) {
		subList.add(new SgHelpCommandHandler(this));
		subList.add(new WandCommandHandler(selStraSource));
		subList.add(new ShapeCommandHandler(selStraSource));
		subList.add(new SelCommandHandler(selStraSource));
		subList.add(new GenrCommandHandler(blockArg, selStraSource));
		subList.add(new PhyCommandHandler());
		subList.add(new ShiftCommandHandler());
		subList.add(new ScaleCommandHandler());
		subList.add(new MirrorCommandHandler());
		subList.add(new RotCommandHandler());
		subList.add(new MaxXCommandHandler());
		subList.add(new MaxYCommandHandler());
		subList.add(new MaxZCommandHandler());
		subList.add(new MinXCommandHandler());
		subList.add(new MinYCommandHandler());
		subList.add(new MinZCommandHandler());
		subList.add(new DelCommandHandler());
		subList.add(new UndoCommandHandler());
		subList.add(new RedoCommandHandler());
	}
	
	@Override
	public String getLabel() {
		return "sg";
	}
	
	@Override
	public String getDescription() {
		return "Root command of ShapeGenerator";
	}
	
	@Override
	public List<CommandEntry> getSubList(Player player) {
		return subList;
	}
	
	public SgSubCommandHandlerRepository getCommandRepository() {
		SgSubCommandHandlerRepository cmdRepo = new SgSubCommandHandlerRepository();
		for(CommandEntry cmdEntry : this.subList) {
			cmdRepo.register((CommandHandler)cmdEntry);
		}
		return cmdRepo;
	}
	
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		String helpMsg = "See help by " + LogColor.GOLD + "/sg help";
		if(args.length == 0) {
			logger.print(helpMsg);
			return;
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		CommandHandler cmdHandler = this.getCommandRepository().findBy(label);
		if(cmdHandler == null) {
			logger.print(helpMsg);
			return;
		}
		cmdHandler.onCommand(player, shiftArgs);
	}
	
	public List<String> onTabComplete(Player player, String[] args){
		if(args.length == 1) {
			return new ArrayList<>(this.getCommandRepository().getAliases());
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		CommandHandler cmdHandler = this.getCommandRepository().findBy(label);
		if(cmdHandler != null) {
			return cmdHandler.onTabComplete(player, shiftArgs);
		}else {
			return new ArrayList<>();
		}
	}
		
}
