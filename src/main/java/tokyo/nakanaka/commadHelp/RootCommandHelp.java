package tokyo.nakanaka.commadHelp;

public class RootCommandHelp {
	private String label;
	private String description;
	
	public RootCommandHelp(Builder builder) {
		this.label = builder.label;
		this.description = builder.description;
	}

	public static class Builder {
		private String label;
		private String description;
		
		public Builder(String label) {
			this.label = label;
		}
		
		public Builder description(String description) {
			this.description = description;
			return this;
		}
		
		public RootCommandHelp build() {
			return new RootCommandHelp(this);
		}
	}
	
	public String getLabel() {
		return label;
	}

	public String getDescription() {
		return description;
	}

	public void register(BranchCommandHelp cmdHelp) {
		
	}
}
