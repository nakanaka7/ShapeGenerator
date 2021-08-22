package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.List;

import tokyo.nakanaka.shapeGenerator.user.UserOld;

public interface CommandDirectory extends CommandEntry {
	List<CommandEntry> getSubList(UserOld user);
}
