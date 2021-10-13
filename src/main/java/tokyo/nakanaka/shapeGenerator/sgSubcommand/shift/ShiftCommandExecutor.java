package tokyo.nakanaka.shapeGenerator.sgSubcommand.shift;

import tokyo.nakanaka.Direction;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.UndoableCommand;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.*;
import tokyo.nakanaka.shapeGenerator.command.AdjustCommand;
import tokyo.nakanaka.shapeGenerator.command.GenerateCommand;
import tokyo.nakanaka.shapeGenerator.command.ShiftCommand;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SgSubcommandHelps;

public class ShiftCommandExecutor implements SgSubcommandExecutor {

    @Override
    public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
        String usage = Main.SG + " " + SgSublabel.SHIFT + " " + String.join(" ", SgSubcommandHelps.SHIFT.parameterSyntaxes());
        if(args.length != 2) {
            player.print(cmdLogColor.error() + "Usage: " + usage);
            return;
        }
        Direction dir;
        double blocks;
        try {
            dir = Direction.valueOf(args[0].toUpperCase());
        }catch(IllegalArgumentException e) {
            player.print(cmdLogColor.error() + "Can not parse direction");
            return;
        }
        try {
            blocks = Double.parseDouble(args[1]);
        }catch(IllegalArgumentException e) {
            player.print(cmdLogColor.error() + "Can not parse integer");
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
        double dx = dir.getX() * blocks;
        double dy = dir.getY() * blocks;
        double dz = dir.getZ() * blocks;
        Vector3D displacement = new Vector3D(dx, dy, dz);
        ShiftCommand shiftCmd = new ShiftCommand(originalCmd, displacement, playerData.getBlockPhysics());
        shiftCmd.execute();
        undoManager.add(shiftCmd);
        player.print(cmdLogColor.main() + "Shifted block(s) " + blocks + " " + dir.toString().toLowerCase());
    }

}
