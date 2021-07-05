package tokyo.nakanaka.commandHandlerNew;

import java.util.List;

import tokyo.nakanaka.player.Player;

public interface CommandHandleStrategy {
	String getDescription();
	String getLabel();
	List<ParameterNew> getParameterList();
	boolean onCommand(Player player, String[] args);
	List<String> onTabComplete(String[] args);
}
