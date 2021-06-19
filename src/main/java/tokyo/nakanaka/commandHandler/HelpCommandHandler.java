package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogConstant.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.Player;

public class HelpCommandHandler implements CommandHandler{
	private CommandHandlerRepository cmdRepo;
	
	public HelpCommandHandler(CommandHandlerRepository cmdRepo) {
		this.cmdRepo = cmdRepo;
	}

	@Override
	public String getDescription() {
		return "Print command help";
	}

	@Override
	public String getLabel() {
		return "help";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("help");
	}

	@Override
	public String getUsage() {
		return "help [command]";
	}

	@Override
	public List<Pair<String, String>> getParameterDescriptionList() {
		Pair<String, String> desCommand = new Pair<>("[command]", "command for help");
		return Arrays.asList(desCommand);
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		if(args.length == 0) {
			player.getLogger().print(HEAD_NORMAL + "Command Help");
			Set<CommandHandler> cmdHandlerSet = this.cmdRepo.getAll();
			for(CommandHandler cmdHandler : cmdHandlerSet) {
				List<String> cmdAliasList = cmdHandler.getAliases();
				player.getLogger().print(INDENT_NORMAL + String.join("/ ", cmdAliasList) + ": " + cmdHandler.getDescription());
			}
			return true;
		}else if(args.length == 1) {
			CommandHandler cmdHandler = this.cmdRepo.findBy(args[0]);
			if(cmdHandler == null) {
				player.getLogger().print(HEAD_ERROR + "Unknown command");
				return true;
			}
			player.getLogger().print(HEAD_NORMAL + "Help for " + args[0]);
			player.getLogger().print(INDENT_NORMAL + cmdHandler.getDescription());
			player.getLogger().print(INDENT_NORMAL + cmdHandler.getUsage());
			for(Pair<String, String> pair : cmdHandler.getParameterDescriptionList()) {
				player.getLogger().print(INDENT_NORMAL + pair.getE1() + ": " + pair.getE2());	
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
