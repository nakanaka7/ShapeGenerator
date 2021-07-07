package tokyo.nakanaka.commadHelp;

public class CommandHelp {
	private String label;
	private String description;
	private String usage;
	
	public CommandHelp(String label, String description, String usage) {
		this.label = label;
		this.description = description;
		this.usage = usage;
	}

	public String getLabel() {
		return label;
	}

	public String getDescription() {
		return description;
	}
	
	public String getUsage() {
		return usage;
	}
		
}
