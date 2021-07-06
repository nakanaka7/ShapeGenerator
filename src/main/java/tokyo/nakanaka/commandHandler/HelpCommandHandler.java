package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogColor.GOLD;
import static tokyo.nakanaka.logger.LogColor.RESET;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commadHelp.Parameter;
import tokyo.nakanaka.commadHelp.Parameter.Type;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class HelpCommandHandler implements SgSubCommandHandler{
	private SgSubCommandHandlerRepository cmdRepo;
	private CommandHelp help = new CommandHelp.Builder("help")
			.description("Print command help")
			.addParameter(new Parameter(Type.OPTIONAL, "command"), "command for help")
			.build();
	private String usage = "/sg help [command]";
	
	public HelpCommandHandler(SgSubCommandHandlerRepository cmdRepo) {
		this.cmdRepo = cmdRepo;
	}

	@Override
	public CommandHelp getCommandHelp() {
		return this.help;
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
	public String getUsage() {
		return this.usage;
	}
	
	@Override
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length == 0) {
			for(SgSubCommandHandler handler : this.cmdRepo.getAll()) {
				logger.print(GOLD + "/sg " + handler.getLabel() + ": " + RESET + handler.getDescription());
			}
			return;
		}else if(args.length == 1) {
			SgSubCommandHandler cmdHandler = this.cmdRepo.findBy(args[0]);
			if(cmdHandler == null) {
				logger.print(LogColor.RED + "Unknown command");
				return;
			}
			List<String> lineList = cmdHandler.getCommandHelp().getHelp();
			for(String line : lineList) {
				player.getLogger().print(line);
			}
			return;
		}else {
			logger.print(LogColor.RED + "Usage: " + this.usage);
			return;
		}
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return new ArrayList<>(this.cmdRepo.getAliases());
		}else {
			return new ArrayList<>();
		}
	}

}
