package tokyo.nakanaka.commadHelp;

import java.util.ArrayList;
import java.util.List;

public class BranchCommandHelp implements CommandHelp {
	private String label;
	private String description;
	private List<ParameterData> paramDataList = new ArrayList<>();
	
	private BranchCommandHelp(Builder builder) {
		this.label = builder.label;
		this.description = builder.description;
		this.paramDataList = builder.paramDataList;
	}
	
	public static class Builder {
		private String label;
		private String description;
		private List<ParameterData> paramDataList = new ArrayList<>();
		
		public Builder(String label) {
			this.label = label;
		}
		
		public Builder description(String description) {
			this.description = description;
			return this;
		}
		
		public Builder addParameter(ParameterType type, String[] labels) {
			this.paramDataList.add(new ParameterData(type, labels));
			return this;
		}

		public Builder addParameter(ParameterType type, String label) {
			this.paramDataList.add(new ParameterData(type, new String[] {label}));
			return this;
		}
		
		public BranchCommandHelp build() {
			return new BranchCommandHelp(this);
		}

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
		String usage = this.label;
		for(ParameterData data : this.paramDataList) {
			switch(data.getType()) {
			case OPTIONAL:
				usage += " [" + String.join("|", data.getLabels()) + "]";
				break;
			case REQUIRED:
				usage += " <" + String.join("|", data.getLabels()) + ">";
				break;
			default:
				break;
			}
		}
		return usage;
	}
		
}
