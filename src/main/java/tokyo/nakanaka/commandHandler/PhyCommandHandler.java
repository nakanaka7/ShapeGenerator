package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class PhyCommandHandler implements CommandHandler{
	private BranchCommandHelp cmdHelp;
	
	public PhyCommandHandler() {
		this.cmdHelp = new BranchCommandHelp.Builder("phy")
				.description("Toggle physics option for generating blocks")
				.addParameter(ParameterType.REQUIRED, new String[] {"true", "false"})
				.build();
	}

	@Override
	public String getLabel() {
		return "phy";
	}
	
	@Override
	public BranchCommandHelp getCommandHelp(Player player) {
		return this.cmdHelp;
	}
	
	@Override
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 1) {
			logger.print(LogColor.RED + "Usage: " + "/sg " + this.cmdHelp.getUsage());
			return;
		}
		boolean physics;
		String bool = args[0];
		if(bool.equals("true")) {
			physics = true;
		}else if(bool.equals("false")) {
			physics = false;
		}else {
			logger.print(LogColor.RED + "Usage: " + "/sg " + this.cmdHelp.getUsage());
			return;
		}
		player.setBlockPhysics(physics);
		if(physics) {
			logger.print(LogColor.DARK_AQUA + "Set physics" + LogColor.RESET + " -> " + LogColor.GREEN + bool);
		}else {
			logger.print(LogColor.DARK_AQUA + "Set physics" + LogColor.RESET + " -> " + LogColor.RED + bool);
		}
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
