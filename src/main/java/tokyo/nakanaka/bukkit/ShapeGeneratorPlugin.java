package tokyo.nakanaka.bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import static tokyo.nakanaka.logger.LogConstant.*;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.PlayerEntity;
import tokyo.nakanaka.commandLine.CommandLine;
import tokyo.nakanaka.commandLine.CommandLineRepository;
import tokyo.nakanaka.commandLine.GenerateCommandLine;
import tokyo.nakanaka.commandLine.SelCommandLine;
import tokyo.nakanaka.commandLine.SelShapeCommandLine;
import tokyo.nakanaka.logger.Logger;

public class ShapeGeneratorPlugin extends JavaPlugin{
	private Map<UUID, Player> humanMap = new HashMap<>();
	private CommandLineRepository cmdLineRepo = new CommandLineRepository();
	
	@Override
	public void onEnable() {
		this.cmdLineRepo.register(new SelCommandLine());
		this.cmdLineRepo.register(new SelShapeCommandLine());
		this.cmdLineRepo.register(new GenerateCommandLine());
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
		CommandLine cmdLine = this.cmdLineRepo.findBy(shiftAlias);
		if(cmdLine != null) {
			boolean success = cmdLine.onCommand(player, shiftArgs);
			if(!success) {
				player.getLogger().print(HEAD_ERROR + "Usage");
			}
			return true;
		}else {
			player.getLogger().print("Type " + "\"/" + "sg" + " help\" " + "for help");
			return true;
		}
	}
	
	
}
