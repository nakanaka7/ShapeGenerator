package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.List;

import tokyo.nakanaka.shapeGenerator.user.UserData;

public interface CommandDirectory extends CommandEntry {
	List<CommandEntry> getSubList(UserData user);
}
