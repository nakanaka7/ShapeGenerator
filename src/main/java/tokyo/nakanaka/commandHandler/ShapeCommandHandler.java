package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogColor.LIGHT_PURPLE;
import static tokyo.nakanaka.logger.LogConstant.HEAD_ERROR;
import static tokyo.nakanaka.logger.LogConstant.HEAD_NORMAL;
import static tokyo.nakanaka.logger.LogConstant.HEAD_WARN;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commadHelp.Parameter;
import tokyo.nakanaka.commadHelp.Parameter.Type;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.SelectionBuilder;
import tokyo.nakanaka.selection.SelectionManager;
import tokyo.nakanaka.selection.SelectionShape;

public class ShapeCommandHandler implements SubCommandHandler{
	private SelectionManager selManager;
	private CommandHelp help = new CommandHelp.Builder("shape")
			.description("Set selection shape")
			.addParameter(new Parameter(Type.REQUIRED, "shape"), "selection shape")
			.build();
		
	public ShapeCommandHandler(SelectionManager selManager) {
		this.selManager = selManager;
	}

	@Override
	public CommandHelp getCommandHelp() {
		return this.help;
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		if(args.length != 1) {
			return false;
		}
		SelectionShape shape;
		try{
			shape = SelectionShape.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			player.getLogger().print(HEAD_ERROR + "Invalid shape");
			return true;
		}
		SelectionBuilder builder = player.getSelectionBuilder();
		SelectionShape original = this.selManager.getShape(builder);
		if(shape == original) {
			player.getLogger().print(HEAD_WARN + "Already set : Nothing to change");
			return true;
		}else {
			player.setSelectionBuilder(this.selManager.newInstance(shape, player.getWorld()));
			player.getLogger().print(HEAD_NORMAL + "Set the shape -> " + LIGHT_PURPLE + shape);
			return true;
		}
	}
	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return this.selManager.getAllShape().stream()
					.map(s -> s.toString().toLowerCase())
					.collect(Collectors.toList());
		}else {
			return new ArrayList<>();
		}
	}

}
