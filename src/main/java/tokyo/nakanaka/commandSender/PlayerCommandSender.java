package tokyo.nakanaka.commandSender;

import java.util.UUID;

public interface PlayerCommandSender extends CommandSender {
	public UUID getUniqueID();
}
