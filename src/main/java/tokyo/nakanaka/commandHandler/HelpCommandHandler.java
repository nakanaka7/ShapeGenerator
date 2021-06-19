package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogConstant.HEAD_NORMAL;
import static tokyo.nakanaka.logger.LogConstant.INDENT_NORMAL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
	public boolean onCommand(Player player, String[] args) {
		if(args.length == 0) {
			player.getLogger().print(HEAD_NORMAL + "Command Help");
			Set<CommandHandler> cmdHandlerSet = this.cmdRepo.getAll();
			for(CommandHandler cmdHandler : cmdHandlerSet) {
				List<String> cmdAliasList = cmdHandler.getAliases();
				player.getLogger().print(INDENT_NORMAL + String.join("/ ", cmdAliasList) + ": " + cmdHandler.getDescription());
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
