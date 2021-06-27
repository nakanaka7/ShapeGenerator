package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogConstant.HEAD_NORMAL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commadHelp.Parameter;
import tokyo.nakanaka.commadHelp.Parameter.Type;
import tokyo.nakanaka.player.Player;

public class PhyCommandHandler implements SubCommandHandler{
	private CommandHelp help = new CommandHelp.Builder("phy")
			.description("Toggle physics option for generating block")
			.addParameter(new Parameter(Type.REQUIRED, "true", "false"), "option for physics when setting blocks")
			.build();
	
	@Override
	public CommandHelp getCommandHelp() {
		return this.help;
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		if(args.length != 1) {
			return false;
		}
		boolean physics;
		String bool = args[0];
		if(bool.equals("true")) {
			physics = true;
		}else if(bool.equals("false")) {
			physics = false;
		}else {
			return false;
		}
		player.setBlockPhysics(physics);
		player.getLogger().print(HEAD_NORMAL + "Set physics -> " + bool);
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
