package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.logger.LogDesignColor;
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
	public String getDescription() {
		return "Toggle physics option for generating blocks";
	}

	@Override
	public List<ParameterHelp> getParameterHelpList() {
		List<ParameterHelp> list = new ArrayList<>();
		list.add(new ParameterHelp(ParameterType.REQUIRED, new String[] {"true", "false"}, ""));
		return list;
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 1) {
			logger.print(LogDesignColor.ERROR + "Usage: " + "/sg " + this.cmdHelp.getUsage());
			return false;
		}
		boolean physics;
		String bool = args[0];
		if(bool.equals("true")) {
			physics = true;
		}else if(bool.equals("false")) {
			physics = false;
		}else {
			logger.print(LogDesignColor.ERROR + "Usage: " + "/sg " + this.cmdHelp.getUsage());
			return false;
		}
		player.setBlockPhysics(physics);
		if(physics) {
			logger.print(LogDesignColor.NORMAL + "Set physics -> " + bool);
		}else {
			logger.print(LogDesignColor.NORMAL+ "Set physics -> " + bool);
		}
		return true;
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
