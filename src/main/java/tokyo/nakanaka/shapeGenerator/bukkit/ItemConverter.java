package tokyo.nakanaka.shapeGenerator.bukkit;

import org.bukkit.Material;

import tokyo.nakanaka.shapeGenerator.legacy.Item;

public class ItemConverter {
	public static Material convertToMaterial(Item item) {
		return Material.valueOf(item.toString());
	}
}
