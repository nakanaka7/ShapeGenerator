package tokyo.nakanaka.commadHelp;

public class BranchCommandHelp implements CommandHelp {
	private String label;
	private String description;
	private String usage;
	
	public BranchCommandHelp(String description, String usage) {
		this.description = description;
		this.usage = usage;
	}
	
	public BranchCommandHelp(String label, String description, String usage) {
		this(description, usage);
		this.label = label;
	}
	
	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	public String getUsage() {
		return usage;
	}
		
}
