package tokyo.nakanaka.commadHelp;

import java.util.ArrayList;
import java.util.List;

public class RootCommandHelp implements CommandHelp {
	private String label;
	private String description;
	private List<CommandHelp> subList = new ArrayList<>();
	
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
	
	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void register(CommandHelp cmdHelp) {
		this.subList.add(cmdHelp);
	}
}
