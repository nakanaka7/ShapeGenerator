package tokyo.nakanaka.commadHelp;

import static tokyo.nakanaka.logger.LogColor.GOLD;
import static tokyo.nakanaka.logger.LogColor.RESET;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.commandHandler.SubCommandHandler;
import tokyo.nakanaka.commandHandler.SubCommandHandlerRepository;
import tokyo.nakanaka.logger.Logger;

public class CommandHelpMessenger {
	public void sendMessage(Logger logger, String[] preCmds, CommandHelp help){
		String cmdHead = "/";
		if(preCmds.length != 0) {
			cmdHead += String.join(" ", preCmds) + " ";
		}
		cmdHead += help.getLabel();
		logger.print("Help for " + cmdHead);
		String usage = "Usage: " + cmdHead + " ";
		List<String> params = new ArrayList<>();
		for(Pair<Parameter, String> e : help.getParameterList()) {
			params.add(e.getFirst().toString());
		}
		usage += String.join(" ", params);
		logger.print(usage);
		logger.print("Description: " + help.getDescription());
		for(Pair<Parameter, String> e : help.getParameterList()) {
			logger.print(GOLD.toString() + e.getFirst() + RESET + ": " + e.getSecond());
		}
	}
	
	public void onSgHelp(Logger logger, SubCommandHandlerRepository cmdRepo) {
		Set<SubCommandHandler> set = cmdRepo.getAll();
		List<Pair<String, String>> list = new ArrayList<>();
		for(SubCommandHandler handler : set) {
			CommandHelp help = handler.getCommandHelp();
			list.add(new Pair<>(help.getLabel(), help.getDescription()));
		}
		for(Pair<String, String> pair : list) {
			logger.print(GOLD + "/sg " + pair.getFirst() + ": " + RESET + pair.getSecond());
		}
	}
}
