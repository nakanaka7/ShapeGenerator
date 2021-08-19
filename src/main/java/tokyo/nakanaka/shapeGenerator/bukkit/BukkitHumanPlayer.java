package tokyo.nakanaka.shapeGenerator.bukkit;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import tokyo.nakanaka.shapeGenerator.Item;
import tokyo.nakanaka.shapeGenerator.user.HumanPlayer;
import tokyo.nakanaka.shapeGenerator.user.User;

public class BukkitHumanPlayer extends User implements HumanPlayer {
	private org.bukkit.entity.Player bukkitPlayer;
	public BukkitHumanPlayer(org.bukkit.entity.Player bukkitPlayer) {
		super(bukkitPlayer.getUniqueId(), bukkitPlayer.getName());
		this.bukkitPlayer = bukkitPlayer;
	}

	@Override
	public void giveItem(Item item, int num) {
		Material material = ItemConverter.convertToMaterial(item);
		ItemStack itemStack = new ItemStack(material, num);
		this.bukkitPlayer.getInventory().addItem(itemStack);
	}
	
}
