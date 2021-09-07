package tokyo.nakanaka.shapeGenerator;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

public interface SubCommandHandler {
	void onCommand(PlayerData playerData, Player player, String[] args);
	List<String> onTabComplete(PlayerData playerData, Player player, String[] args);
}
