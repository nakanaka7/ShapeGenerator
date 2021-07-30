package tokyo.nakanaka.commandHandler;

import java.util.List;

import tokyo.nakanaka.player.User;

public interface CommandDirectory extends CommandEntry {
	List<CommandEntry> getSubList(User user);
}
