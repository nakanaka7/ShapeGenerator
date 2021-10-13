package tokyo.nakanaka.shapeGenerator.sgSubcommand.phy;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import tokyo.nakanaka.shapeGenerator.Main;
import tokyo.nakanaka.shapeGenerator.SgSubcommandExecutor;
import tokyo.nakanaka.shapeGenerator.SgSublabel;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.commandHelp.SgSubcommandHelps;

public class PhyCommandExecutor implements SgSubcommandExecutor {

    @Override
    public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
        String usage = Main.SG + " " + SgSublabel.PHY + " " + String.join(" ", SgSubcommandHelps.PHY.parameterSyntaxes());
        String usageMsg = cmdLogColor.error() + "Usage: " + usage;
        if(args.length != 1) {
            player.print(usageMsg);
            return;
        }
        boolean physics;
        String bool = args[0];
        if(bool.equals("true")) {
            physics = true;
        }else if(bool.equals("false")) {
            physics = false;
        }else {
            player.print(usageMsg);
            return;
        }
        playerData.setBlockPhysics(physics);
        player.print(cmdLogColor.main() + "Set physics -> " + bool);
    }

}
