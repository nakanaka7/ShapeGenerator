package tokyo.nakanaka.shapeGenerator.sgSubcommand.version;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.SemVer;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import tokyo.nakanaka.shapeGenerator.SgSubcommandExecutor;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

public class VersionCommandExecutor implements SgSubcommandExecutor {
    private CommandLogColor cmdLogColor;
    private int major;
    private int minor;
    private int patch;

    public VersionCommandExecutor(CommandLogColor cmdLogColor, SemVer semVer) {
        this.cmdLogColor = cmdLogColor;
        this.major = semVer.major();
        this.minor = semVer.minor();
        this.patch = semVer.patch();
    }

    @Override
    public void onCommand(PlayerData playerData, Player player, String[] args) {
        String ver = major + "." + minor + "." + patch;
        player.print(cmdLogColor.main() + "Version: " + ver);
    }

}
