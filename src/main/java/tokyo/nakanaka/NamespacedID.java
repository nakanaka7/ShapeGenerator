package tokyo.nakanaka;

public class NamespacedID {
	private String namespace;
	private String name;
	
	public NamespacedID(String namespace, String name) {
		this.namespace = namespace;
		this.name = name;
	}
	
	/**
	 * @param str
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static NamespacedID valueOf(String str) {
		String namespace;
		String name;
		String[] strs = str.split(":");
		if(strs.length != 2) {
			throw new IllegalArgumentException("must contain one \":\"");
		}else {
			namespace = strs[0];
			name = strs[1];
			return new NamespacedID(namespace, name);
		}
	}
	
	public String getNamespace() {
		return this.namespace;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.namespace + ":" + this.name;
	}
}
