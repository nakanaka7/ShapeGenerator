package tokyo.nakanaka.commandHandler;

import java.util.List;

import tokyo.nakanaka.player.Player;

public interface CommandDirectory extends CommandEntry {
	String getLabel();
	String getDescription();
	List<BranchCommandHandler> getSubList(Player player);
}
