package tokyo.nakanaka.commadHelp;

public class CommandHelp {
	private String label;
	private String description;
	private String usage;
	
	public CommandHelp(String description, String usage) {
		this.description = description;
		this.usage = usage;
	}
	
	public CommandHelp(String label, String description, String usage) {
		this(description, usage);
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}

	public String getDescription() {
		return description;
	}
	
	public String getUsage() {
		return usage;
	}
		
}
