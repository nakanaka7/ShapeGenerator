package tokyo.nakanaka.commandSender;

import tokyo.nakanaka.BlockPosition;

public interface PositionalCommandSender extends CommandSender {
	public BlockPosition getBlockPosition();
}
