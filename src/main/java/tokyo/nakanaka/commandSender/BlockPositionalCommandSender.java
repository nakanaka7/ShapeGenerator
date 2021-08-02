package tokyo.nakanaka.commandSender;

import tokyo.nakanaka.BlockPosition;

public interface BlockPositionalCommandSender extends CommandSender {
	public BlockPosition getBlockPosition();
}
