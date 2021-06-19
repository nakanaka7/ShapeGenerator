package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static tokyo.nakanaka.logger.LogConstant.*;

import tokyo.nakanaka.BlockRegion3D;
import tokyo.nakanaka.Pair;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.block.Block;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.commandArgument.BlockCommandArgument;
import tokyo.nakanaka.selection.Selection;
import tokyo.nakanaka.selection.SelectionBuilder;
import tokyo.nakanaka.world.World;

public class GenerateCommandHandler implements CommandHandler{
	private BlockCommandArgument blockArg;
	
	public GenerateCommandHandler(BlockCommandArgument blockArg) {
		this.blockArg = blockArg;
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
		World world = sel.getWorld();
		BlockRegion3D region = sel.getBlockRegion3D();
		GenerateCommand generateCmd = new GenerateCommand.Builder(world, region, block)
				.physics(player.getBlockPhysics())
				.build();
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
