package tokyo.nakanaka.commadHelp;

public class ParameterData {
	private ParameterType type;
	private String[] labels;
	private String description;

	public ParameterData(ParameterType type, String[] labels, String description) {
		this.type = type;
		this.labels = labels;
		this.description = description;
	}
	
	public ParameterData(ParameterType type, String[] labels) {
		this(type, labels, "");
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
