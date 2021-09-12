package tokyo.nakanaka.shapeGenerator.bukkit;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import tokyo.nakanaka.Scheduler;
import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.bukkit.BukkitFunctions;
import tokyo.nakanaka.bukkit.BukkitScheduler;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.shapeGenerator.Main;

@PublicAPI
public class ShapeGeneratorPlugin extends JavaPlugin {
	private Main main;
	
	@Override
	public void onEnable() {
		this.main = new Main(new BukkitBlockIDListFactory());
		Scheduler scheduler = new BukkitScheduler(this);
		Listener listener = new BukkitClickBlockEventListener(this.main, scheduler);
		this.getServer().getPluginManager().registerEvents(listener, this);
	}
	
	@Override
	public boolean onCommand(org.bukkit.command.CommandSender cmdSender0, Command command, String label, String[] args) {
		CommandSender cmdSender = BukkitFunctions.convertCommandSender(cmdSender0);
		this.main.onSgCommand(cmdSender, args);
		return true;
	}
	
	@Override
	public List<String> onTabComplete(org.bukkit.command.CommandSender cmdSender0, Command command, String alias, String[] args){
		CommandSender cmdSender = BukkitFunctions.convertCommandSender(cmdSender0);
		return this.main.onSgTabComplete(cmdSender, args);
	}
	
}
