package tokyo.nakanaka.bukkit.commandSender;

import org.bukkit.command.CommandSender;

import tokyo.nakanaka.logger.Logger;

public class BukkitCommandSender implements Logger{
	protected CommandSender sender;
	
	public BukkitCommandSender(CommandSender sender) {
		this.sender = sender;
	}

	@Override
	public void print(String msg) {
		this.sender.sendMessage(msg);
	}

}


