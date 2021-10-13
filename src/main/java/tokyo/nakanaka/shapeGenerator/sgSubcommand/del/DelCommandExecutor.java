package tokyo.nakanaka.shapeGenerator.sgSubcommand.del;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.UndoableCommand;
import tokyo.nakanaka.shapeGenerator.*;
import tokyo.nakanaka.shapeGenerator.command.AdjustCommand;
import tokyo.nakanaka.shapeGenerator.command.DeleteCommand;
import tokyo.nakanaka.shapeGenerator.command.GenerateCommand;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SgSubcommandHelps;

import java.util.ArrayList;
import java.util.List;

public class DelCommandExecutor implements SgSubcommandExecutor {

    @Override
    public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
        String usage = Main.SG + " " + SgSublabel.DEL + " " + String.join(" ", SgSubcommandHelps.DEL.parameterSyntaxes());
        if(args.length > 1) {
            player.print(cmdLogColor.error() + "Usage: " + usage);
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
        List<GenerateCommand> originalList = new ArrayList<>();
        for(int i = undoManager.undoSize() - 1; i >= 0; --i) {
            UndoableCommand cmd = undoManager.getUndoCommand(i);
            GenerateCommand originalCmd = null;
            if(cmd instanceof GenerateCommand) {
                originalCmd = (GenerateCommand) cmd;
            }else if(cmd instanceof AdjustCommand) {
                originalCmd = ((AdjustCommand)cmd).getLastCommand();
            }
            if(originalCmd != null && !originalCmd.hasUndone()) {
                originalList.add(originalCmd);
                if(originalList.size() == num) {
                    break;
                }
            }
        }
        int delNum = originalList.size();
        DeleteCommand deleteCmd
                = new DeleteCommand(originalList.toArray(new GenerateCommand[delNum]));
        deleteCmd.execute();
        undoManager.add(deleteCmd);
        if(delNum == 0) {
            player.print(cmdLogColor.error() + "Generate blocks first");
            return;
        }
        player.print(cmdLogColor.main() + "Deleted " + delNum + " generation(s)");
        if(delNum < num) {
            player.print(cmdLogColor.error() + "reached the first generation");
        }
    }

}
