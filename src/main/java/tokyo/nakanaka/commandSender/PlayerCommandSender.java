package tokyo.nakanaka.commandSender;

import java.util.UUID;

public interface PlayerCommandSender extends PositionalCommandSender {
	public UUID getUniqueID();
}
