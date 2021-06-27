package tokyo.nakanaka.commadHelp;

import static tokyo.nakanaka.logger.LogColor.GOLD;
import static tokyo.nakanaka.logger.LogColor.RESET;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.Pair;

public class CommandHelp {
	private String label;
	private String description;
	private List<Pair<Parameter, String>> paramList;
	
	private CommandHelp(Builder builder) {
		this.label = builder.label;
		this.description = builder.description;
		this.paramList = builder.paramList;
	}
	
	public static class Builder{
		private String label;
		private String description;
		private List<Pair<Parameter, String>> paramList = new ArrayList<>();
		
		public Builder(String label) {
			this.label = label;
		}
		
		public Builder description(String description) {
			this.description = description;
			return this;
		}
		
		public Builder addParameter(Parameter param, String description) {
			this.paramList.add(new Pair<>(param, description));
			return this;
		}
		
		public CommandHelp build() {
			return new CommandHelp(this);
		}
	}

	public String getLabel() {
		return this.label;
	}
	
	public String getDescription() {
		return description;
	}

	public List<Pair<Parameter, String>> getParameterList() {
		return paramList;
	}

	public List<String> getHelp(){
		List<String> list = new ArrayList<>();
		list.add("Help for " + this.label);
		String usage = "Usage: " + this.label + " ";
		for(Pair<Parameter, String> e : this.paramList) {
			usage += e.getFirst().toString() + " ";
		}
		list.add(usage);
		list.add(this.description);
		for(Pair<Parameter, String> e : this.paramList) {
			String s = GOLD.toString() + e.getFirst() + RESET + ": " + e.getSecond();
			list.add(s);
		}
		return list;
	}

}
