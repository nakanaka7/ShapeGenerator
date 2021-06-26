package tokyo.nakanaka.commadHelp;

public class Parameter {
	private ParameterType type;
	private String[] labels;
	
	public Parameter(ParameterType type, String... labels)  {
		this.type = type;
		this.labels = labels;
	}
	
	public ParameterType getType() {
		return type;
	}

	public String[] getLabels() {
		return labels;
	}
	
	@Override
	public String toString() {
		String word = String.join("|", this.labels);
		switch(this.type) {
		case OPTIONAL:
			return "[" + word + "]";
		case REQUIRED:
			return "<" + word + ">";
		default:
			return null;
		}
	}
	
}
