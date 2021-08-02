package tokyo.nakanaka.commandSender;

import java.util.UUID;

public interface Player extends BlockPositionalCommandSender {
	public UUID getUniqueID();
}
