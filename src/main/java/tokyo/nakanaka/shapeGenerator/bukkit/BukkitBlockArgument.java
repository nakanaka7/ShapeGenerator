package tokyo.nakanaka.shapeGenerator.bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Material;

import tokyo.nakanaka.NamespacedID;
import tokyo.nakanaka.block.Block;
import tokyo.nakanaka.shapeGenerator.commandArgument.BlockCommandArgument;

public class BukkitBlockArgument implements BlockCommandArgument{

	/**
	 * @throws IllegalArgumentException
	 */
	@Override
	public Block onParsing(String arg) {
		Block block = Block.valueOf(arg);
		NamespacedID id = block.getId();
		if(!id.getNamespace().equals("minecraft")) {
			return block;
		}
		String blockName = id.getName();
		List<String> blockList = Arrays.asList(Material.values())
				.stream()
				.filter(s -> s.isBlock())
				.map(s -> s.toString().toLowerCase())
				.collect(Collectors.toList());
		if(!blockList.contains(blockName)) {
			throw new IllegalArgumentException();
		}
		return block;
	}

	@Override
	public List<String> onTabComplete(String arg) {
		if(!arg.contains(":")) {
			arg = "minecraft:" + arg;
		}
		List<String> list = Arrays.asList(Material.values())
			.stream()
			.filter(s -> s.isBlock())
			.map(s -> "minecraft:" + s.toString().toLowerCase())
			.collect(Collectors.toList());
		List<String> filtered = new ArrayList<>();
		for(String block : list) {
			if(block.startsWith(arg)) {
				filtered.add(block);
			}else if(arg.contains("[")){
				if(block.equals(arg.split("\\[", -1)[0])) {
					filtered.add(block);
				}
			}
		}
		return filtered;
	}

}
