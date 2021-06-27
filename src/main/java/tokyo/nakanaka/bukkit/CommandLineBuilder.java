package tokyo.nakanaka.bukkit;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

import tokyo.nakanaka.CommandLine;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.player.PlayerRepository;
import tokyo.nakanaka.selection.cuboid.CuboidSelectionBuilder;
import tokyo.nakanaka.world.World;

public class CommandLineBuilder {
	private Server server;
	private PlayerRepository repo;
	private BukkitPlayerFactory playerFactory = new BukkitPlayerFactory();
	
	public CommandLineBuilder(Server server, PlayerRepository repo) {
		this.server = server;
		this.repo = repo;
	}
	
	public CommandLine build(CommandSender sender, String alias, String[] args) {
		Logger logger = new BukkitLogger(sender);
		Player player;
		if(sender instanceof org.bukkit.entity.Player) {
			org.bukkit.entity.Player bukkitPlayer = (org.bukkit.entity.Player)sender;
			UUID uid = bukkitPlayer.getUniqueId();
			player = this.repo.getHumanPlayer(uid);
			if(player == null) {
				player = this.playerFactory.createHumanPlayer(this.server, bukkitPlayer);
				this.repo.registerHumanPlayer(player);
			}
			Location loc = bukkitPlayer.getLocation();
			player.setLogger(logger);
			World world = new BukkitWorld(this.server, loc.getWorld());
			player.setWorld(world);
			player.setX(loc.getBlockX());
			player.setY(loc.getBlockY());
			player.setZ(loc.getBlockZ());
			if(player.getSelectionBuilder() == null) {
				player.setSelectionBuilder(new CuboidSelectionBuilder(world));
			}
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
