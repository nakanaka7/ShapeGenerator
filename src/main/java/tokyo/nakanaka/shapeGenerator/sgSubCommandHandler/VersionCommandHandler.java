package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.SemVer;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

public class VersionCommandHandler implements SubCommandHandler {
	private int major = 1;
	private int minor = 1;
	private int patch = 0;
	
	public VersionCommandHandler(SemVer semVer) {
		this.major = semVer.major();
		this.minor = semVer.minor();
		this.patch = semVer.patch();
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
		String ver = major + "." + minor + "." + patch;
		player.print(LogColor.GOLD + "Version: " + ver);
	}

	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return List.of();
	}

}
