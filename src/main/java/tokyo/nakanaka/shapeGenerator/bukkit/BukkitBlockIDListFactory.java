package tokyo.nakanaka.shapeGenerator.bukkit;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Material;

import tokyo.nakanaka.NamespacedID;
import tokyo.nakanaka.annotation.PublicAPI;
import tokyo.nakanaka.shapeGenerator.BlockIDListFactory;

/**
 * A Factory class of list of block IDs for bukkit
 */
@PublicAPI
public class BukkitBlockIDListFactory implements BlockIDListFactory {
	
	@Override
	public List<NamespacedID> getBlockIDList() {
		return List.of(Material.values()).stream()
				.filter(s -> s.isBlock())
				.map(s -> s.toString().toLowerCase())
				.map(s -> new NamespacedID("minecraft", s))
				.collect(Collectors.toList());
	}
	
}
