package tokyo.nakanaka.shapeGenerator.sgSubcommand.genr;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.block.Block;
import tokyo.nakanaka.shapeGenerator.*;
import tokyo.nakanaka.shapeGenerator.command.GenerateCommand;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

import java.util.Map;

public class GenrCommandExecutor implements SgSubcommandExecutor {
    private Map<SelectionShape, SelectionBuilder> selBuilderMap;

    public GenrCommandExecutor(Map<SelectionShape, SelectionBuilder> selBuilderMap){
        this.selBuilderMap = selBuilderMap;
    }

    @Override
    public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
        String usage = Main.SG + " " + SgSublabel.GENR + " " + String.join(" ", GenrConstants.HELP.parameterSyntaxes());
        if(args.length != 1) {
            player.print(cmdLogColor.error() + "Usage: " + usage);
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
        SelectionBuilder selBuilder = this.selBuilderMap.get(playerData.getSelectionShape());
        try {
            sel = selBuilder.buildSelection(playerData.getSelectionData());
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
