package tokyo.nakanaka.commadHelp;

import java.util.ArrayList;
import java.util.List;

public class RootCommandHelp implements CommandHelp {
	private String label;
	private String description;
	private List<CommandHelp> subList = new ArrayList<>();
	
	public RootCommandHelp(String label, String description) {
		super();
		this.label = label;
		this.description = description;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void addSub(CommandHelp cmdHelp) {
		this.subList.add(cmdHelp);
	}
	
	public List<CommandHelp> getSubList() {
		return new ArrayList<>(this.subList);
	}
}
