package tokyo.nakanaka.bukkit;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import tokyo.nakanaka.Item;
import tokyo.nakanaka.player.HumanPlayer;
import tokyo.nakanaka.player.Player;

public class BukkitHumanPlayer extends Player implements HumanPlayer {
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
