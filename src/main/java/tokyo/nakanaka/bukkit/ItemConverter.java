package tokyo.nakanaka.bukkit;

import org.bukkit.Material;

import tokyo.nakanaka.shapeGenerator.Item;

public class ItemConverter {
	public static Material convertToMaterial(Item item) {
		return Material.valueOf(item.toString());
	}
}
