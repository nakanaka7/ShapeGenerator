package tokyo.nakanaka.commandHandler;

import java.util.Arrays;
import java.util.List;

import static tokyo.nakanaka.logger.LogConstant.*;

import tokyo.nakanaka.BlockRegion3D;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.block.Block;
import tokyo.nakanaka.command.FillCommand;
import tokyo.nakanaka.selection.Selection;
import tokyo.nakanaka.selection.SelectionBuilder;
import tokyo.nakanaka.world.World;

public class GenerateCommandHandler implements CommandHandler{

	@Override
	public String getLabel() {
		return "generate";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("generate");
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
			block = Block.valueOf(args[0]);
		}catch(IllegalArgumentException e) {
			player.getLogger().print(HEAD_ERROR + "Invalid block specification");
			return true;
		}
		World world = sel.getWorld();
		BlockRegion3D region = sel.getBlockRegion3D();
		FillCommand fillCmd = new FillCommand.Builder(world, region, block)
				.physics(player.getBlockPhysics())
				.build();
		try {
			fillCmd.execute();
		}catch(IllegalArgumentException e) {
			player.getLogger().print(HEAD_ERROR + "Unsettable block");
			return true;
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
