package tokyo.nakanaka.bukkit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import tokyo.nakanaka.Main;
import tokyo.nakanaka.bukkit.commandSender.BukkitBlockCommandSender;
import tokyo.nakanaka.bukkit.commandSender.BukkitConsoleCommandSender;
import tokyo.nakanaka.bukkit.commandSender.BukkitPlayerCommandSender;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;

public class ShapeGeneratorPlugin extends JavaPlugin {
	private Main main;
	
	@Override
	public void onEnable() {
		SelectionStrategySource selStrtgSource = new SelectionStrategySource();
		this.main = new Main(new BukkitBlockArgument(), selStrtgSource);
		this.getServer().getPluginManager()
			.registerEvents(new BukkitClickBlockEventListener(this.main, new BukkitScheduler(this)), this);
	}
	
	@Override
	public boolean onCommand(org.bukkit.command.CommandSender cmdSender0, Command command, String label, String[] args) {
		CommandSender cmdSender;
		try{
			cmdSender = convertCommandSender(cmdSender0);
		}catch(IllegalArgumentException e) {
			return true;
		}
		this.main.onSgCommand(cmdSender, args);
		return true;
	}
	
	@Override
	public List<String> onTabComplete(org.bukkit.command.CommandSender cmdSender0, Command command, String alias, String[] args){
		CommandSender cmdSender;
		try{
			cmdSender = convertCommandSender(cmdSender0);
		}catch(IllegalArgumentException e) {
			return new ArrayList<>();
		}
		return this.main.onSgTabComplete(cmdSender, args);
	}
	
	private static CommandSender convertCommandSender(org.bukkit.command.CommandSender cmdSender0) {
		if(cmdSender0 instanceof Player p) {
			return new BukkitPlayerCommandSender(p);
		}else if(cmdSender0 instanceof BlockCommandSender b) {
			return new BukkitBlockCommandSender(b);
		}else if(cmdSender0 instanceof ConsoleCommandSender c) {
			return new BukkitConsoleCommandSender(c);
		}
		throw new IllegalArgumentException();
	}
	
}
