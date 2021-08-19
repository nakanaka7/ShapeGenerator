package tokyo.nakanaka.commadHelp;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.shapeGenerator.Pair;
import tokyo.nakanaka.shapeGenerator.commandHandler.CommandDirectory;
import tokyo.nakanaka.shapeGenerator.commandHandler.CommandEntry;
import tokyo.nakanaka.shapeGenerator.user.User;

public class RootCommandHelp {
	private CommandDirectory cmdDir;
	private String head;
	
	public RootCommandHelp(String[] parentLabels, CommandDirectory cmdDir) {
		this.cmdDir = cmdDir;
		String str = this.cmdDir.getLabel();
		if(parentLabels.length != 0) {
			String s = String.join(" ", parentLabels);
			str = s + " " + str;
		}
		this.head = "/" + str;
	}
	
	public String getSubject() {
		return this.head;
	}
	
	public String getDescription() {
		return this.cmdDir.getDescription();
	}
	
	public String getUsage() {
		return this.head + " [subcommand]";
	}
	
	/**
	 * the first element of pair is subcommand label,
	 * the second element of pair is subcommand description
	 */
	public List<Pair<String, String>> getSubCommandDescriptionList(User user){
		List<Pair<String, String>> list = new ArrayList<>();
		for(CommandEntry e : this.cmdDir.getSubList(user)) {
			String first =  e.getLabel();
			String second = e.getDescription();
			list.add(new Pair<>(first, second));
		}
		return list;
	}
	
}
