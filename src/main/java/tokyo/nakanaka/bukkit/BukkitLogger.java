package tokyo.nakanaka.bukkit;

import org.bukkit.command.CommandSender;

import tokyo.nakanaka.logger.Logger;

public class BukkitLogger implements Logger{
	protected CommandSender sender;
	
	public BukkitLogger(CommandSender sender) {
		this.sender = sender;
	}

	@Override
	public void print(String msg) {
		this.sender.sendMessage(msg);
	}

}


