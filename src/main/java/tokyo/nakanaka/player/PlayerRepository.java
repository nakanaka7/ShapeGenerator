package tokyo.nakanaka.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerRepository {
	private Player console = new Player(UUID.randomUUID());
	private Map<UUID, Player> humanMap = new HashMap<>();
	private Map<String, Player> blockMap = new HashMap<>();
	
	public Player getConsolePlayer() {
		return this.console;
	}
	
	public Player getHumanPlayer(UUID uid) {
		return this.humanMap.get(uid);
	}
	
	public void registerHumanPlayer(Player player) {
		this.humanMap.put(player.getUniqueId(), player);
	}
	
	public Player getBlockPlayer(String name) {
		return this.blockMap.get(name);
	}
	
	public void registerBlockPlayer(Player player) {
		this.blockMap.put(player.getName(), player);
	}
	
}
