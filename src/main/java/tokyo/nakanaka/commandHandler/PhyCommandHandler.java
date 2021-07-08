package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commadHelp.BranchCommandHelp;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class PhyCommandHandler implements SgSubCommandHandler{
	private BranchCommandHelp cmdHelp;
	
	public PhyCommandHandler() {
		String desc = "Toggle physics option for generating block";
		String usage = "/sg phy <true|false>";
		this.cmdHelp = new BranchCommandHelp("phy" ,desc, usage);
	}

	@Override
	public String getLabel() {
		return "phy";
	}
	
	@Override
	public BranchCommandHelp getCommandHelp() {
		return this.cmdHelp;
	}
	
	@Override
	public void onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 1) {
			logger.print(LogColor.RED + "Usage: " + this.cmdHelp.getUsageOld());
			return;
		}
		boolean physics;
		String bool = args[0];
		if(bool.equals("true")) {
			physics = true;
		}else if(bool.equals("false")) {
			physics = false;
		}else {
			logger.print(LogColor.RED + "Usage: " + this.cmdHelp.getUsageOld());
			return;
		}
		player.setBlockPhysics(physics);
		if(physics) {
			logger.print("Set physics -> " + LogColor.GREEN + bool);
		}else {
			logger.print("Set physics -> " + LogColor.RED + bool);
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
