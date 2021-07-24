package tokyo.nakanaka;

import java.util.List;

import tokyo.nakanaka.commadHelp.RootCommandHelp;
import tokyo.nakanaka.commandHandler.CommandEntry;
import tokyo.nakanaka.commandHandler.CommandHandler;
import tokyo.nakanaka.commandHandler.SgCommandDirectory;
import tokyo.nakanaka.commandHandler.SgSubCommandHandlerRepository;
import tokyo.nakanaka.player.Player;

public class SgRootCommandHelpFactory {
	private SgCommandDirectory sgCmdDir;
	public SgRootCommandHelpFactory(SgCommandDirectory sgCmdDir) {
		this.sgCmdDir = sgCmdDir;
	}

	public RootCommandHelp create(Player player) {
		RootCommandHelp rootCmdHelp = new RootCommandHelp.Builder("sg")
				.description("Root command of ShapeGenerator")
				.build();
		List<String> subLabelList = this.getCommandRepository().getAliases();
		for(String subLabel : subLabelList) {
			rootCmdHelp.register(this.getCommandRepository().findBy(subLabel).getCommandHelp(player));
		}
		return rootCmdHelp;
	}
	
	private SgSubCommandHandlerRepository getCommandRepository() {
		SgSubCommandHandlerRepository cmdRepo = new SgSubCommandHandlerRepository();
		for(CommandEntry cmdEntry : this.sgCmdDir.getSubList(null)) {
			cmdRepo.register((CommandHandler)cmdEntry);
		}
		return cmdRepo;
	}
	
}
