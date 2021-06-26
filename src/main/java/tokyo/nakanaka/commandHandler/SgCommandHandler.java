package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogConstant.HEAD_ERROR;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.player.Player;

public class SgCommandHandler {
	private CommandHandlerRepository cmdHandlerRepo;
	
	public SgCommandHandler(CommandHandlerRepository cmdHandlerRepo) {
		this.cmdHandlerRepo = cmdHandlerRepo;
	}
	
	public Set<CommandHandler> getAllRegisterdCommandHandler(){
		return this.cmdHandlerRepo.getAll();
	}
	
	public void onCommand(Player player, String alias, String[] args) {
		CommandHandler cmdHandler = this.cmdHandlerRepo.findBy(alias);
		if(cmdHandler != null) {
			boolean success = cmdHandler.onCommand(player, args);
			if(!success) {
				CommandHelp help = cmdHandler.getCommandHelp();
				for(String line : help.getHelp()) {
					player.getLogger().print(line);
				}
			}
		}else {
			player.getLogger().print(HEAD_ERROR + "Unknown command");
		}
	}
	
	public List<String> onTabComplete(Player player, String alias, String[] args){
		if(args.length == 0) {
			return new ArrayList<>(this.cmdHandlerRepo.getAliases());
		}
		CommandHandler cmdHandler = this.cmdHandlerRepo.findBy(alias);
		if(cmdHandler != null) {
			return cmdHandler.onTabComplete(player, args);
		}else {
			return new ArrayList<>();
		}
	}

}
