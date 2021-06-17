package tokyo.nakanaka.bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import tokyo.nakanaka.commandHandler.GenerateCommandHandler;
import tokyo.nakanaka.commandHandler.RootCommandHandler;
import tokyo.nakanaka.commandHandler.SelCommandHandler;
import tokyo.nakanaka.commandHandler.SelShapeCommandHandler;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.Logger;

public class ShapeGeneratorPlugin extends JavaPlugin{
	private Map<UUID, Player> humanMap = new HashMap<>();
	private RootCommandHandler rootCmdHandler = new RootCommandHandler();
	
	@Override
	public void onEnable() {
		this.rootCmdHandler.register(new SelCommandHandler());
		this.rootCmdHandler.register(new SelShapeCommandHandler());
		this.rootCmdHandler.register(new GenerateCommandHandler());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Logger logger = new BukkitLogger(sender);
		Player player;
		if(sender instanceof org.bukkit.entity.Player) {
			org.bukkit.entity.Player bukkitPlayer = (org.bukkit.entity.Player)sender;
			UUID uid = bukkitPlayer.getUniqueId();
			player = this.humanMap.get(uid);
			if(player == null) {
				player = new Player(uid, bukkitPlayer.getName());
				this.humanMap.put(uid, player);
			}
			Location loc = bukkitPlayer.getLocation();
			player.setLogger(logger);
			player.setWorld(new BukkitWorld(this.getServer(), loc.getWorld()));
			player.setX(loc.getBlockX());
			player.setY(loc.getBlockY());
			player.setZ(loc.getBlockZ());
		}else {
			return true;
		}
		if(args.length == 0) {
			return true;
		}
		String shiftAlias = args[0];
		String[] shiftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, shiftArgs, 0, shiftArgs.length);
		this.rootCmdHandler.onCommand(player, shiftAlias, shiftArgs);
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
		return new ArrayList<>();
	}
	
}
