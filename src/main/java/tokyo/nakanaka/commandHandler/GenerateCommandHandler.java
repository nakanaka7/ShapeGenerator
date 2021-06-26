package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogConstant.HEAD_ERROR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.block.Block;
import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commadHelp.Parameter;
import tokyo.nakanaka.commadHelp.Parameter.Type;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.commandArgument.BlockCommandArgument;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.Selection;
import tokyo.nakanaka.selection.SelectionBuilder;

public class GenerateCommandHandler implements CommandHandler{
	private BlockCommandArgument blockArg;
	
	public GenerateCommandHandler(BlockCommandArgument blockArg) {
		this.blockArg = blockArg;
	}

	@Override
	public CommandHelp getCommandHelp() {
		return new CommandHelp.Builder("generate")
				.description("Generate blocks in the selection")
				.addParameter(new Parameter(Type.REQUIRED, "block"), "block to generate")
				.build();
	}
	
	@Override
	public String getDescription() {
		return "Generate blocks in the selection";
	}
	
	@Override
	public String getLabel() {
		return "generate";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("generate");
	}

	@Override
	public String getUsage() {
		return "generate <block>";
	}
	
	@Override
	public List<Pair<String, String>> getParameterDescriptionList() {
		Pair<String, String> desBlock = new Pair<>("<block>", "block to generate");
		return Arrays.asList(desBlock);
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		SelectionBuilder builder = player.getSelectionBuilder();
		Selection sel;
		try {
			sel = builder.build();
		}catch(IllegalStateException e) {
			player.getLogger().print(HEAD_ERROR + "Incomplete selection");
			return true;
		}
		if(args.length != 1) {
			return false;
		}
		Block block;
		try {
			block = this.blockArg.onParsing(args[0]);
		}catch(IllegalArgumentException e) {
			player.getLogger().print(HEAD_ERROR + "Invalid block specification");
			return true;
		}
		GenerateCommand generateCmd = new GenerateCommand(sel, block, player.getBlockPhysics());	
		try {
			generateCmd.execute();
		}catch(IllegalArgumentException e) {
			player.getLogger().print(HEAD_ERROR + "Unsettable block");
			return true;
		}
		player.getUndoCommandManager().add(generateCmd);
		return true;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return this.blockArg.onTabComplete(args[0]);
		}else {
			return new ArrayList<>();
		}
	}

}
