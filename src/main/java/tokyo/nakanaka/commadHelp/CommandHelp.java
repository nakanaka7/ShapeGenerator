package tokyo.nakanaka.commadHelp;

public interface CommandHelp {
	String getLabel();
	String getDescription();
	CommandHelp getSubHelp(String... subLabels);
}
