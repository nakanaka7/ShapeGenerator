package tokyo.nakanaka.shapeGenerator.sgSubcommand.phy;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import tokyo.nakanaka.shapeGenerator.Main;
import tokyo.nakanaka.shapeGenerator.SgSubcommandExecutor;
import tokyo.nakanaka.shapeGenerator.SgSublabel;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

public class PhyCommandExecutor implements SgSubcommandExecutor {
    private CommandLogColor cmdLogColor;

    public PhyCommandExecutor(CommandLogColor cmdLogColor) {
        this.cmdLogColor = cmdLogColor;
    }

    @Override
    public void onCommand(PlayerData playerData, Player player, String[] args) {
        String usage = Main.SG + " " + SgSublabel.PHY + " " + String.join(" ", PhyConstants.HELP.parameterSyntaxes());
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
