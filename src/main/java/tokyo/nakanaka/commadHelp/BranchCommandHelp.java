package tokyo.nakanaka.commadHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.shapeGenerator.Pair;
import tokyo.nakanaka.shapeGenerator.userCommandHandler.UserCommandHandler;

public class BranchCommandHelp {
	private String[] parentLabels;
	private String label;
	private String description;
	private List<ParameterHelp> paramHelpList;
	
	public BranchCommandHelp(String[] parentLabels, UserCommandHandler cmdHandler) {
		this.parentLabels = parentLabels;
		this.label = cmdHandler.getLabel();
		this.description = cmdHandler.getDescription();
		this.paramHelpList = cmdHandler.getParameterHelpList();
	}
	
	public String getSubject() {
		String str = this.label;
		if(parentLabels.length != 0) {
			String s = String.join(" ", this.parentLabels);
			str = s + " " + str;
		}
		return "/" + str;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getUsage() {
		String str = this.label;
		if(parentLabels.length != 0) {
			String s = String.join(" ", this.parentLabels);
			str = s + " " + str;
		}
		str = "/" + str;
		str += " "; 
		for(ParameterHelp e : this.paramHelpList) {
			switch(e.getType()) {
			case OPTIONAL:
				str += " [" + String.join("|", e.getLabels()) + "]";
				break;
			case REQUIRED:
				str += " <" + String.join("|", e.getLabels()) + ">";
				break;
			default:
				break;
			}
		}
		return str;
	}
	
	public List<Pair<String, String>> getParameterDescriptionList() {
		List<Pair<String, String>> list = new ArrayList<>();
		for(ParameterHelp e : this.paramHelpList) {
			String first = String.join("|", e.getLabels());
			String second = e.getDescription();
			list.add(new Pair<>(first, second));
		}
		return list;
	}

}
