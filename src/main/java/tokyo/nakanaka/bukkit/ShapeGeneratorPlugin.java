package tokyo.nakanaka.bukkit;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

import tokyo.nakanaka.Main;
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
