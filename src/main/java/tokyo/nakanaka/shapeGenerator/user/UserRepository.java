package tokyo.nakanaka.shapeGenerator.user;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserRepository {
	private UserOld console = new UserOld(UUID.randomUUID());
	private Map<UUID, UserOld> humanMap = new HashMap<>();
	private Map<String, UserOld> blockMap = new HashMap<>();
	
	public UserOld getConsoleUser() {
		return this.console;
	}
	
	public UserOld getHumanUser(UUID uid) {
		return this.humanMap.get(uid);
	}
	
	public void registerHumanUser(UserOld user) {
		this.humanMap.put(user.getUniqueId(), user);
	}
	
	public UserOld getBlockUser(String name) {
		return this.blockMap.get(name);
	}
	
	public void registerBlockPlayer(UserOld user) {
		this.blockMap.put(user.getName(), user);
	}
	
}
