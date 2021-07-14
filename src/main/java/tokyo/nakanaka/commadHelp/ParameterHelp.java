package tokyo.nakanaka.commadHelp;

public class ParameterHelp {
	private ParameterType type;
	private String[] labels;
	private String description;
	
	public ParameterHelp(ParameterType type, String[] labels, String description) {
		this.type = type;
		this.labels = labels;
		this.description = description;
	}

	public ParameterHelp(ParameterType type, String label, String description) {
		this.type = type;
		this.labels = new String[] {label};
		this.description = description;
	}
	
	public ParameterType getType() {
		return type;
	}

	public String[] getLabels() {
		return labels;
	}

	public String getDescription() {
		return description;
	}
	
}
