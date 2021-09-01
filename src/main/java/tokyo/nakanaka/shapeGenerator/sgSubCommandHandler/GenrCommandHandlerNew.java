package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.block.Block;
import tokyo.nakanaka.command.GenerateCommand;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.shapeGenerator.LogDesignColor;
import tokyo.nakanaka.selection.Selection;
import tokyo.nakanaka.shapeGenerator.BlockIDListFactory;
import tokyo.nakanaka.shapeGenerator.commandHelp.GenrHelp;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Handles "/sg genr" command
 */
public class GenrCommandHandlerNew implements SgSubCommandHandler {
	private BlockIDListFactory blockIDFactory;
	
	public GenrCommandHandlerNew(BlockIDListFactory blockIDFactory) {
		this.blockIDFactory = blockIDFactory;
	}

	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
		if(args.length != 1) {
			player.print(LogColor.RED + "Usage: " +  new GenrHelp().getUsage());
			return;
		}
		Block block;
		try {
			block = Block.valueOf(args[0]);
		}catch(IllegalArgumentException e) {
			player.print(LogColor.RED + "Invalid block specification");
			return;
		}
		Selection sel;
		try {
			sel = userData.getSelectionShapeNew().createSelection(userData.getSelectionData());
		}catch(IllegalArgumentException e) {
			player.print(LogColor.RED + "Incomplete selection");
			return;
		}
		GenerateCommand generateCmd = new GenerateCommand(sel, block, userData.getBlockPhysics());	
		try {
			generateCmd.execute();
		}catch(IllegalArgumentException e) {
			player.print(LogColor.RED + "Unsettable block");
			return;
		}
		player.print(LogDesignColor.NORMAL + "Generated block(s)");
		userData.getUndoCommandManager().add(generateCmd);
	}

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		return switch(args.length) {
			case 1 -> blockIDFactory.getBlockIDList().stream()
				.map(s -> s.toString())
				.collect(Collectors.toList());
			default -> List.of();
		};
	}

}
