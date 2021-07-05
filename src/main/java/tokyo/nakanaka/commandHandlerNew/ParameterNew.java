package tokyo.nakanaka.commandHandlerNew;

public class ParameterNew {
	private Type type;
	private String[] labels;
	private String description;
	
	public ParameterNew(Type type, String[] labels, String description)  {
		this.type = type;
		this.labels = labels;
		this.description = description;
	}
	
	public ParameterNew(Type type, String label, String description)  {
		this.type = type;
		this.labels = new String[] {label};
		this.description = description;
	}
	
	public static enum Type {
		REQUIRED,
		OPTIONAL;
	}
	
	public Type getType() {
		return type;
	}

	public String[] getLabels() {
		return labels;
	}

	public String getDescription() {
		return description;
	}

}
