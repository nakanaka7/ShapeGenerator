package tokyo.nakanaka.commadHelp;

import java.util.ArrayList;
import java.util.List;
@Deprecated
public class RootCommandHelpOld implements CommandHelp {
	private String label;
	private String description;
	private List<CommandHelp> subList = new ArrayList<>();
	
	private RootCommandHelpOld(Builder builder) {
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
		
		public RootCommandHelpOld build() {
			return new RootCommandHelpOld(this);
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
	
	public List<CommandHelp> getSubCommandHelp() {
		return new ArrayList<>(this.subList);
	}
	
	@Override
	public CommandHelp getSubHelp(String... subLabels) {
		if(subLabels.length == 0) {
			return this;
		}
		String subLabel = subLabels[0];
		CommandHelp subCmdHelp = null;
		for(CommandHelp e : this.subList) {
			if(e.getLabel().equals(subLabel)) {
				subCmdHelp = e;
				break;
			}
		}
		if(subLabels.length == 1) {
			return subCmdHelp;
		}
		if(subCmdHelp == null) {
			return null;
		}
		String[] shiftedSubLabels = new String[subLabels.length - 1];
		System.arraycopy(subLabels, 1, shiftedSubLabels, 0, subLabels.length - 1);	
		return subCmdHelp.getSubHelp(shiftedSubLabels);
	}
	
	public String getUsage() {
		return this.label + " <subcommand>";
	}
	
}
