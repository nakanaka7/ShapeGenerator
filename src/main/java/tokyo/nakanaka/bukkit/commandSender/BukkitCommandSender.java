package tokyo.nakanaka.bukkit.commandSender;

import tokyo.nakanaka.commandSender.CommandSender;

public class BukkitCommandSender implements CommandSender{
	protected org.bukkit.command.CommandSender sender;
	
	public BukkitCommandSender(org.bukkit.command.CommandSender sender) {
		this.sender = sender;
	}

	@Override
	public void print(String msg) {
		this.sender.sendMessage(msg);
	}

}


