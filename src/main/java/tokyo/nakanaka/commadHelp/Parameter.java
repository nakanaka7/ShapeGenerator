package tokyo.nakanaka.commadHelp;

public class Parameter {
	private Type type;
	private String[] labels;
	
	public Parameter(Type type, String... labels)  {
		this.type = type;
		this.labels = labels;
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
