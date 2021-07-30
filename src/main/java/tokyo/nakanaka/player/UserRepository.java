package tokyo.nakanaka.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserRepository {
	private User console = new User(UUID.randomUUID());
	private Map<UUID, User> humanMap = new HashMap<>();
	private Map<String, User> blockMap = new HashMap<>();
	
	public User getConsoleUser() {
		return this.console;
	}
	
	public User getHumanUser(UUID uid) {
		return this.humanMap.get(uid);
	}
	
	public void registerHumanUser(User user) {
		this.humanMap.put(user.getUniqueId(), user);
	}
	
	public User getBlockUser(String name) {
		return this.blockMap.get(name);
	}
	
	public void registerBlockPlayer(User user) {
		this.blockMap.put(user.getName(), user);
	}
	
}
