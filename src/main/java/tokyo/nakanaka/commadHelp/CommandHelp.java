package tokyo.nakanaka.commadHelp;

public class CommandHelp {
	private String description;
	private String usage;
	
	public CommandHelp(String description, String usage) {
		this.description = description;
		this.usage = usage;
	}

	public String getDescription() {
		return description;
	}
	
	public String getUsage() {
		return usage;
	}
		
}
