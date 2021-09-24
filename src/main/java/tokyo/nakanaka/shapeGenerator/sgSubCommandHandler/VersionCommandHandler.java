package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

public class VersionCommandHandler implements SubCommandHandler {
	private static final int major = 1;
	private static final int minor = 1;
	private static final int patch = 0;
	
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
