package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

import java.util.List;

@PrivateAPI
public interface SubCommandHandler {
	void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor);
	List<String> onTabComplete(PlayerData playerData, Player player, String[] args);
}
