package tokyo.nakanaka.bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.commandLine.CommandLine;
import tokyo.nakanaka.logger.Logger;

public class CommandLineBuilder {
	private Server server;
	private Map<UUID, Player> humanMap = new HashMap<>();
	
	public CommandLineBuilder(Server server) {
		this.server = server;
	}
	
	public CommandLine build(CommandSender sender, String alias, String[] args) {
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
			player.setWorld(new BukkitWorld(server, loc.getWorld()));
			player.setX(loc.getBlockX());
			player.setY(loc.getBlockY());
			player.setZ(loc.getBlockZ());
		}else {
			throw new IllegalArgumentException();
		}
		String shiftAlias;
		String[] shiftArgs;
		if(args.length == 0) {
			shiftAlias = "";
			shiftArgs = new String[0];
		}else {
			shiftAlias = args[0];
			shiftArgs = new String[args.length - 1];
			System.arraycopy(args, 1, shiftArgs, 0, shiftArgs.length);
		}
		return new CommandLine(player, shiftAlias, shiftArgs);
	}
}
