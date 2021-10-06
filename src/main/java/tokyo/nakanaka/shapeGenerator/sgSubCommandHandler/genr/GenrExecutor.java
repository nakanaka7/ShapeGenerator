package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.genr;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.block.Block;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import tokyo.nakanaka.shapeGenerator.Selection;
import tokyo.nakanaka.shapeGenerator.SelectionShapeStrategyRepository;
import tokyo.nakanaka.shapeGenerator.SgSubCommandExecutor;
import tokyo.nakanaka.shapeGenerator.command.GenerateCommand;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SgBranchHelpConstants;

public class GenrExecutor implements SgSubCommandExecutor {
    private CommandLogColor cmdLogColor;
    private SelectionShapeStrategyRepository shapeStrtgRepo;

    public GenrExecutor(CommandLogColor cmdLogColor, SelectionShapeStrategyRepository shapeStrtgRepo) {
        this.cmdLogColor = cmdLogColor;
        this.shapeStrtgRepo = shapeStrtgRepo;
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

}
