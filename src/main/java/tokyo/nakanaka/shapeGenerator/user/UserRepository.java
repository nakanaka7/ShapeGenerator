package tokyo.nakanaka.shapeGenerator.user;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserRepository {
	private UserData console = new UserData(UUID.randomUUID());
	private Map<UUID, UserData> humanMap = new HashMap<>();
	private Map<String, UserData> blockMap = new HashMap<>();
	
	public UserData getConsoleUser() {
		return this.console;
	}
	
	public UserData getHumanUser(UUID uid) {
		return this.humanMap.get(uid);
	}
	
	public void registerHumanUser(UserData user) {
		this.humanMap.put(user.getUniqueId(), user);
	}
	
	public UserData getBlockUser(String name) {
		return this.blockMap.get(name);
	}
	
	public void registerBlockPlayer(UserData user) {
		this.blockMap.put(user.getName(), user);
	}
	
}
