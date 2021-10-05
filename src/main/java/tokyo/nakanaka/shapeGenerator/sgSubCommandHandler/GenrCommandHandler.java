package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.block.Block;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.BlockIDListFactory;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionShapeStrategyRepository;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.command.GenerateCommand;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SgBranchHelpConstants;

/**
 * Handles "/sg genr" command
 */
public class GenrCommandHandler implements SubCommandHandler {
	private static final CommandLogColor cmdLogColor = new CommandLogColor(LogColor.GOLD, LogColor.RED);
	private SelectionShapeStrategyRepository shapeStrtgRepo;
	private BlockIDListFactory blockIDFactory;

	public GenrCommandHandler(SelectionShapeStrategyRepository shapeStrtgRepo, BlockIDListFactory blockIDFactory) {
		this.shapeStrtgRepo = shapeStrtgRepo;
		this.blockIDFactory = blockIDFactory;
	}

	@Override
	public String label() {
		return "genr";
	}

	@Override
	public String description() {
		return "Generate block(s) in the selection";
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
		if(args.length != 1) {
			player.print(cmdLogColor.error() + "Usage: " + "/sg " + SgBranchHelpConstants.GENR.syntax());
			return;
		}
		Block block;
		try {
			block = Block.valueOf(args[0]);
		}catch(IllegalArgumentException e) {
			player.print(cmdLogColor.error() + "Invalid block specification");
			return;
		}
		Selection sel;
		SelectionShapeStrategy shapeStrtg = this.shapeStrtgRepo.get(playerData.getSelectionShape());
		try {
			sel = shapeStrtg.buildSelection(playerData.getSelectionData());
		}catch(IllegalStateException e) {
			player.print(cmdLogColor.error() + "Incomplete selection");
			return;
		}
		GenerateCommand generateCmd = new GenerateCommand(sel, block, playerData.getBlockPhysics());	
		try {
			generateCmd.execute();
		}catch(IllegalArgumentException e) {
			player.print(cmdLogColor.error() + "Unsettable block");
			return;
		}
		player.print(cmdLogColor.main() + "Generated block(s)");
		playerData.getUndoCommandManager().add(generateCmd);
	}

	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return switch(args.length) {
			case 1 -> this.blockIDFactory.getBlockIDList().stream()
				.map(s -> s.toString())
				.collect(Collectors.toList());
			default -> List.of();
		};
	}

}
