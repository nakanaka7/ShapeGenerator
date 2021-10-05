package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.SemVer;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;

public class VersionCommandHandler implements SubCommandHandler {
	private static final CommandLogColor cmdLogColor = new CommandLogColor(LogColor.GOLD, LogColor.RED);
	private int major;
	private int minor;
	private int patch;
	
	public VersionCommandHandler(SemVer semVer) {
		this.major = semVer.major();
		this.minor = semVer.minor();
		this.patch = semVer.patch();
	}

	@Override
	public String label() {
		return "version";
	}

	@Override
	public String description() {
		return "Print the version";
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
		String ver = major + "." + minor + "." + patch;
		player.print(cmdLogColor.main() + "Version: " + ver);
	}

	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return List.of();
	}

}
