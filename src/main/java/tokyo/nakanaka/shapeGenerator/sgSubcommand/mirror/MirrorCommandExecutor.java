package tokyo.nakanaka.shapeGenerator.sgSubcommand.mirror;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.UndoableCommand;
import tokyo.nakanaka.shapeGenerator.*;
import tokyo.nakanaka.shapeGenerator.command.AdjustCommand;
import tokyo.nakanaka.shapeGenerator.command.GenerateCommand;
import tokyo.nakanaka.shapeGenerator.command.MirrorCommand;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

public class MirrorCommandExecutor implements SgSubcommandExecutor {

    @Override
    public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
        String usage = Main.SG + " " + SgSublabel.MIRROR + " " + String.join(" ", MirrorConstants.HELP.parameterSyntaxes());
        if(args.length != 1) {
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
        MirrorCommand mirrorCmd = new MirrorCommand(originalCmd, axis, playerData.getBlockPhysics());
        mirrorCmd.execute();
        undoManager.add(mirrorCmd);
        player.print(cmdLogColor.main() + "Mirrored along the " + axis.toString().toLowerCase() + " axis");
    }

}
