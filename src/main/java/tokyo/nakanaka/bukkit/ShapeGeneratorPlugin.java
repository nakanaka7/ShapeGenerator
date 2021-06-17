package tokyo.nakanaka.bukkit;

import static tokyo.nakanaka.logger.LogConstant.HEAD_NORMAL;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.PlayerEntity;
import tokyo.nakanaka.block.Block;
import tokyo.nakanaka.logger.Logger;

public class ShapeGeneratorPlugin extends JavaPlugin{
	private Map<UUID, Player> playerMap = new HashMap<>(); 
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Logger logger = new BukkitLogger(sender);
		Player player = null;
		if(sender instanceof org.bukkit.entity.Player) {
			org.bukkit.entity.Player bplayer = (org.bukkit.entity.Player)sender;
			PlayerEntity pe = new BukkitPlayerEntity(this.getServer(), bplayer);
			player = this.playerMap.get(pe.getUniqueID());
			player.setLogger(logger);
			player.setWorld(pe.getWorld());
			player.setX(pe.getX());
			player.setY(pe.getY());
			player.setZ(pe.getZ());
		}
		String s = args[0];
		Block b = Block.valueOf(s);
		int x = player.getX();
		int y = player.getY();
		int z = player.getZ();
		player.getWorld().setBlock(x, y, z, b, false);
		logger.print(HEAD_NORMAL + "set");
		return true;
	}
}
