package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static tokyo.nakanaka.logger.LogConstant.*;
import tokyo.nakanaka.Pair;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commadHelp.Parameter;
import tokyo.nakanaka.commadHelp.Parameter.Type;
import tokyo.nakanaka.player.Player;

public class PhysicsCommandHandler implements CommandHandler{

	@Override
	public CommandHelp getCommandHelp() {
		return new CommandHelp.Builder("phy")
				.description("Toggle physics option for generating block")
				.addParameter(new Parameter(Type.REQUIRED, "true", "false"), "option for physics when setting blocks")
				.build();
	}
	
	@Override
	public String getDescription() {
		return "Toggle physics option for generating block";
	}

	@Override
	public String getLabel() {
		return "physics";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("phy", "physics");
	}

	@Override
	public String getUsage() {
		return "phy/physics <true/false>";
	}

	@Override
	public List<Pair<String, String>> getParameterDescriptionList() {
		Pair<String, String> desTF = new Pair<>("<true/false>", "option for physics when setting blocks");
		return Arrays.asList(desTF);
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
