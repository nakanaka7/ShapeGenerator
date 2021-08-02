package tokyo.nakanaka.bukkit.commandSender;

import tokyo.nakanaka.commandSender.ConsoleCommandSender;

public class BukkitConsoleCommandSender extends BukkitCommandSender implements ConsoleCommandSender {	
	
	public BukkitConsoleCommandSender(org.bukkit.command.ConsoleCommandSender consoleCmdSender) {
		super(consoleCmdSender);
	}

}
