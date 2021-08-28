package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public interface SgSubCommandHandler {
	void onCommand(UserData userData, Player player, String[] args);
	List<String> onTabComplete(UserData userData, Player player, String[] args);
}
