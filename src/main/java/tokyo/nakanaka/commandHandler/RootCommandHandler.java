package tokyo.nakanaka.commandHandler;

import java.util.List;

import tokyo.nakanaka.player.Player;

public interface RootCommandHandler {
	String getLabel();
	String getDescription();
	List<BranchCommandHandler> getSubList(Player player);
}
