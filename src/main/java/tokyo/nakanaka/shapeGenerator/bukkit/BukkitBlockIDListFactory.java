package tokyo.nakanaka.shapeGenerator.bukkit;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Material;

import tokyo.nakanaka.NamespacedID;

/**
 * A Factory class of list of block IDs for bukkit
 */
public class BukkitBlockIDListFactory {
	List<NamespacedID> getBlockIDList(){
		return List.of(Material.values()).stream()
				.filter(s -> s.isBlock())
				.map(s -> s.toString().toLowerCase())
				.map(s -> new NamespacedID("minecraft", s))
				.collect(Collectors.toList());
	}
	
}
