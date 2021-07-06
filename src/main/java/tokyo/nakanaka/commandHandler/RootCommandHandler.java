package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogColor.GOLD;
import static tokyo.nakanaka.logger.LogColor.RESET;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class RootCommandHandler implements CommandHandler{
	private String label;
	private SubCommandHandlerRepository cmdHandlerRepo;
	
	public RootCommandHandler(String label, SubCommandHandlerRepository cmdHandlerRepo) {
		this.label = label;
		this.cmdHandlerRepo = cmdHandlerRepo;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void onHelp(Logger logger) {
		List<Pair<String, String>> list = this.getSubCommmandDescriptions();
		for(Pair<String, String> pair : list) {
			logger.print(GOLD + pair.getFirst() + ": " + RESET + pair.getSecond());
		}
	}
	
	@Deprecated
	public List<Pair<String, String>> getSubCommmandDescriptions(){
		Set<SubCommandHandler> set = this.cmdHandlerRepo.getAll();
		List<Pair<String, String>> list = new ArrayList<>();
		for(SubCommandHandler handler : set) {
			CommandHelp help = handler.getCommandHelp();
			list.add(new Pair<>(help.getLabel(), help.getDescription()));
		}
		return list;
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		if(args.length == 0) {
			return false;
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		SubCommandHandler cmdHandler = this.cmdHandlerRepo.findBy(label);
		if(cmdHandler == null) {
			return false;
		}
		boolean success = cmdHandler.onCommand(player, shiftArgs);
		if(!success) {
			CommandHelp help = cmdHandler.getCommandHelp();
			for(String line : help.getHelp()) {
				player.getLogger().print(line);
			}
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(Player player, String[] args){
		if(args.length == 1) {
			return new ArrayList<>(this.cmdHandlerRepo.getAliases());
		}
		String label = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, args.length - 1);
		SubCommandHandler cmdHandler = this.cmdHandlerRepo.findBy(label);
		if(cmdHandler != null) {
			return cmdHandler.onTabComplete(player, shiftArgs);
		}else {
			return new ArrayList<>();
		}
	}
		
}
