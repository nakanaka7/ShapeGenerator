package tokyo.nakanaka.bukkit;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

import tokyo.nakanaka.CommandLine;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.player.PlayerRepository;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selectionStrategy.CuboidSelectionStrategy;
import tokyo.nakanaka.world.World;

public class CommandLineBuilder {
	private Server server;
	private PlayerRepository repo;
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
			Location loc = bukkitPlayer.getLocation();
			World world = new BukkitWorld(this.server, loc.getWorld());
			if(player == null) {
				player = new BukkitHumanPlayer(bukkitPlayer);
				player.setSelectionShape(SelectionShape.CUBOID);
				RegionBuildingData regionData = new CuboidSelectionStrategy().newRegionBuildingData();
				SelectionBuildingData selData = new SelectionBuildingData(world, regionData);
				player.setSelectionBuildingData(selData);		
				this.repo.registerHumanPlayer(player);
			}
			player.setLogger(logger);
			player.setWorld(world);
			int x = loc.getBlockX();
			int y = loc.getBlockY();
			int z = loc.getBlockZ();
			player.setX(x);
			player.setY(y);
			player.setZ(z);
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
