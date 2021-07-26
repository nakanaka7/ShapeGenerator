package tokyo.nakanaka.commandHandler;

import java.util.List;

import tokyo.nakanaka.player.Player;

public interface CommandDirectory extends CommandEntry {
	List<CommandEntry> getSubList(Player player);
}
