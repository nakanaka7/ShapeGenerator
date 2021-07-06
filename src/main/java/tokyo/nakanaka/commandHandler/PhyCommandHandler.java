package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commadHelp.Parameter;
import tokyo.nakanaka.commadHelp.Parameter.Type;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class PhyCommandHandler implements SgSubCommandHandler{
	private CommandHelp help = new CommandHelp.Builder("phy")
			.description("Toggle physics option for generating block")
			.addParameter(new Parameter(Type.REQUIRED, "true", "false"), "option for physics when setting blocks")
			.build();
	private String usage = "/sg phy <true|false>";
	
	@Override
	public CommandHelp getCommandHelp() {
		return this.help;
	}
	
	@Override
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 1) {
			logger.print(LogColor.RED + "Usage: " + this.usage);
			return;
		}
		boolean physics;
		String bool = args[0];
		if(bool.equals("true")) {
			physics = true;
		}else if(bool.equals("false")) {
			physics = false;
		}else {
			logger.print(LogColor.RED + "Usage: " + this.usage);
			return;
		}
		player.setBlockPhysics(physics);
		logger.print("Set physics -> " + bool);
		return;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("true", "false");
		}else {
			return new ArrayList<>();
		}
	}

}
