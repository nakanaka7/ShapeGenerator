package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.redo;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.*;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SgSubcommandHelps;

public class RedoCommandExecutor implements SgSubcommandExecutor {

    @Override
    public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
        String usage = Main.SG + " " + SgSublabel.REDO + " " + String.join(" ", SgSubcommandHelps.REDO.parameterSyntaxes());
        String usageMsg = cmdLogColor.error() + "Usage: " + usage;
        if(args.length > 1) {
            player.print(usageMsg);
            return;
        }
        int num = 1;
        if(args.length == 1) {
            try {
                num = Integer.parseInt(args[0]);
            }catch(IllegalArgumentException e) {
                player.print(cmdLogColor.error() + "Can not parse the number");
                return;
            }
            if(num <= 0) {
                player.print(cmdLogColor.error() + "The number must be larger than 0");
                return;
            }
        }
        UndoCommandManager undoManager = playerData.getUndoCommandManager();
        int totalNum = 0;
        for(int i = 0; i < num; ++i) {
            boolean success = undoManager.redo();
            if(!success) {
                break;
            }
            ++totalNum;
        }
        if(totalNum == 0) {
            player.print(cmdLogColor.error() + "Nothing to redo");
            return;
        }
        player.print(cmdLogColor.main() + "Redid " + totalNum + " command(s)");
        if(totalNum < num) {
            player.print(cmdLogColor.error() + "Reached the end command");
        }
    }

}
