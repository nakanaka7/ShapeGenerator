package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public interface UserCommandHandler extends CommandEntry {
	void onCommand(UserData userData, Player player, String[] args);
	List<String> onTabComplete(UserData userData, Player player, String[] args);
}
