package tokyo.nakanaka.bukkit;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import static tokyo.nakanaka.logger.LogConstant.*;
import tokyo.nakanaka.block.Block;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.world.World;

public class ShapeGeneratorPlugin extends JavaPlugin{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Logger logger = new BukkitLogger(sender);
		if(sender instanceof Player) {
			Player p = (Player)sender;
			Location loc = p.getLocation();
			org.bukkit.World bworld = loc.getWorld();
			int x = loc.getBlockX();
			int y = loc.getBlockY();
			int z = loc.getBlockZ();
			World world = new BukkitWorld(this.getServer(), bworld);
			String s = args[0];
			Block b = Block.valueOf(s);
			world.setBlock(x, y, z, b, false);
			logger.print(HEAD_NORMAL + "set");
			//"remote test"
		}
		
		
		return true;
	}
}
