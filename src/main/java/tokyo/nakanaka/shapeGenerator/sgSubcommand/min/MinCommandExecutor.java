package tokyo.nakanaka.shapeGenerator.sgSubcommand.min;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.UndoableCommand;
import tokyo.nakanaka.shapeGenerator.*;
import tokyo.nakanaka.shapeGenerator.command.AdjustCommand;
import tokyo.nakanaka.shapeGenerator.command.GenerateCommand;
import tokyo.nakanaka.shapeGenerator.command.MinCommand;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SgSubcommandHelps;

public class MinCommandExecutor implements SgSubcommandExecutor {

    @Override
    public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
        //check args length
        String usage = Main.SG + " " + SgSublabel.MIN + " " + String.join(" ", SgSubcommandHelps.MIN.parameterSyntaxes());
        if(args.length != 2) {
            player.print(cmdLogColor.error() + "Usage: " + usage);
            return;
        }
        Axis axis;
        try {
            axis = Axis.valueOf(args[0].toUpperCase());
        }catch(IllegalArgumentException e) {
            player.print(cmdLogColor.error() + "Can not parse axis");
            return;
        }
        double coord;
        try {
            coord = Double.valueOf(args[1]);
        }catch(IllegalArgumentException e) {
            player.print(cmdLogColor.error() + "Can not parse coordinate");
            return;
        }
        UndoCommandManager undoManager = playerData.getUndoCommandManager();
        GenerateCommand originalCmd = null;
        for(int i = undoManager.undoSize() - 1; i >= 0; --i) {
            UndoableCommand cmd = undoManager.getUndoCommand(i);
            GenerateCommand genrCmd = null;
            if(cmd instanceof GenerateCommand) {
                genrCmd = (GenerateCommand) cmd;
            }else if(cmd instanceof AdjustCommand) {
                genrCmd = ((AdjustCommand)cmd).getLastCommand();
            }
            if(genrCmd != null && !genrCmd.hasUndone()) {
                originalCmd = genrCmd;
                break;
            }
        }
        if(originalCmd == null) {
            player.print(cmdLogColor.error() + "Generate blocks first");
            return;
        }
        UndoableCommand minCmd = new MinCommand(originalCmd, axis, coord, playerData.getBlockPhysics());
        minCmd.execute();
        undoManager.add(minCmd);
        player.print(cmdLogColor.main() + "Set min" + axis.toString().toUpperCase() + " -> " + coord);
    }

}
