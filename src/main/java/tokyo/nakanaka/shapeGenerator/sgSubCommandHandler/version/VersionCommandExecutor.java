package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.version;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.SemVer;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import tokyo.nakanaka.shapeGenerator.SgSubcommandExecutor;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

public class VersionCommandExecutor implements SgSubcommandExecutor {
    private int major;
    private int minor;
    private int patch;

    public VersionCommandExecutor(SemVer semVer) {
        this.major = semVer.major();
        this.minor = semVer.minor();
        this.patch = semVer.patch();
    }

    @Override
    public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
        String ver = major + "." + minor + "." + patch;
        player.print(cmdLogColor.main() + "Version: " + ver);
    }

}
