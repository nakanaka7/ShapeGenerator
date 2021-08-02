package tokyo.nakanaka.commandSender;

import java.util.UUID;

public interface PlayerCommandSender extends BlockPositionalCommandSender {
	public UUID getUniqueID();
}
