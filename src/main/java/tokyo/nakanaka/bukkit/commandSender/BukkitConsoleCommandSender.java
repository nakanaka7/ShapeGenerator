package tokyo.nakanaka.bukkit.commandSender;

import tokyo.nakanaka.commandSender.ConsoleCommandSender;

public class BukkitConsoleCommandSender implements ConsoleCommandSender {
	private org.bukkit.command.ConsoleCommandSender consoleCmdSender;
	
	public BukkitConsoleCommandSender(org.bukkit.command.ConsoleCommandSender consoleCmdSender) {
		this.consoleCmdSender = consoleCmdSender;
	}

	@Override
	public void print(String msg) {
		this.consoleCmdSender.sendMessage(msg);
	}

}
