package tokyo.nakanaka.commandHandler;

import java.util.List;

import tokyo.nakanaka.player.Player;

public interface RootCommandHandler extends CommandEntry {
	String getLabel();
	String getDescription();
	List<BranchCommandHandler> getSubList(Player player);
}
