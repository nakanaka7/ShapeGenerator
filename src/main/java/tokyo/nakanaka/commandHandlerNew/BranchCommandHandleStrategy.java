package tokyo.nakanaka.commandHandlerNew;

import java.util.List;

import tokyo.nakanaka.player.Player;

public interface BranchCommandHandleStrategy {
	String getLabel();
	String getDescription();
	List<Parameter> getParameterList();
	boolean onCommand(Player player, String[] args);
	List<String> onTabComplete(String[] args);
}
