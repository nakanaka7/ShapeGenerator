package tokyo.nakanaka.shapeGenerator.commandHandler;

import java.util.List;

import tokyo.nakanaka.shapeGenerator.user.User;

public interface CommandDirectory extends CommandEntry {
	List<CommandEntry> getSubList(User user);
}
