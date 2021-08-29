package tokyo.nakanaka.shapeGenerator.user;

import java.util.UUID;

/**
 * A entity who has an unique id and a name
 */
public class User {
	private UUID uid = UUID.randomUUID();
	private String name = "";
	
	/**
	 * Construct a user who has given the unique id and empty name
	 * @param uid a unique id of the user
	 */
	public User(UUID uid) {
		this.uid = uid;
	}
	/**
	 * Construct a user who has given name and random id
	 * @param name a name of the user
	 */
	public User(String name) {
		this.name = name;
	}
	/**
	 * Get the unique id of the user
	 * @return the unique id of the user
	 */
	public UUID getUid() {
		return uid;
	}
	/**
	 * Get the name of the user
	 * @return the name of the user
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}
	
}
