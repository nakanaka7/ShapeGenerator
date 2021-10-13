package tokyo.nakanaka.shapeGenerator.sgSubcommand.scale;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.UndoableCommand;
import tokyo.nakanaka.shapeGenerator.*;
import tokyo.nakanaka.shapeGenerator.command.AdjustCommand;
import tokyo.nakanaka.shapeGenerator.command.GenerateCommand;
import tokyo.nakanaka.shapeGenerator.command.ScaleCommand;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

public class ScaleCommandExecutor implements SgSubcommandExecutor {

    @Override
    public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
        String usage = Main.SG + " " + SgSublabel.SCALE + " " + String.join(" ", ScaleConstants.HELP.parameterSyntaxes());
        if(args.length != 2) {
            player.print(cmdLogColor.error() + "Usage: " + usage);
            return;
        }
        Axis axis;
        try{
            axis = Axis.valueOf(args[0].toUpperCase());
        }catch(IllegalArgumentException e) {
            player.print(cmdLogColor.error() + "Can not parse axis");
            return;
        }
        double factor;
        try {
            factor = Double.valueOf(args[1]);
        }catch(IllegalArgumentException e) {
            player.print(cmdLogColor.error() + "Can not parse double");
            return;
        }
        if(factor == 0) {
            player.print(cmdLogColor.error() + "The scale factor must not be zero");
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
        ScaleCommand scaleCmd = new ScaleCommand(originalCmd, axis, factor, playerData.getBlockPhysics());
        scaleCmd.execute();
        undoManager.add(scaleCmd);
        player.print(cmdLogColor.main() + "Scaled " + factor + " times along the " + axis.toString().toLowerCase() + " axis");

    }
}
