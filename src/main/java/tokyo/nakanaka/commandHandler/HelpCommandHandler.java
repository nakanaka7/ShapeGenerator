package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogConstant.HEAD_ERROR;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commadHelp.CommandHelpMessenger;
import tokyo.nakanaka.commadHelp.Parameter;
import tokyo.nakanaka.commadHelp.Parameter.Type;
import tokyo.nakanaka.player.Player;

public class HelpCommandHandler implements SubCommandHandler{
	private SubCommandHandlerRepository cmdRepo;
	private CommandHelp help = new CommandHelp.Builder("help")
			.description("Print command help")
			.addParameter(new Parameter(Type.OPTIONAL, "command"), "command for help")
			.build();
	
	public HelpCommandHandler(SubCommandHandlerRepository cmdRepo) {
		this.cmdRepo = cmdRepo;
	}

	@Override
	public CommandHelp getCommandHelp() {
		return this.help;
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		if(args.length == 0) {
			new CommandHelpMessenger().onSgHelp(player.getLogger(), this.cmdRepo);
			return true;
		}else if(args.length == 1) {
			SubCommandHandler cmdHandler = this.cmdRepo.findBy(args[0]);
			if(cmdHandler == null) {
				player.getLogger().print(HEAD_ERROR + "Unknown command");
				return true;
			}
			List<String> lineList = cmdHandler.getCommandHelp().getHelp();
			for(String line : lineList) {
				player.getLogger().print(line);
			}
			return true;
		}else {
			return false;
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
