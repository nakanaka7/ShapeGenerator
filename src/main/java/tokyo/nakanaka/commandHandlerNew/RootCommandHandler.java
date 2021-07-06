package tokyo.nakanaka.commandHandlerNew;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;

public class RootCommandHandler implements CommandHandler {
	private String label;
	private String description;
	private List<CommandHandler> subList = new ArrayList<>();

	public RootCommandHandler(String label, String description) {
		this.label = label;
		this.description = description;
	}

	@Override
	public String getLabel() {
		return this.label;
	}
	
	@Override
	public String getDescription() {
		return this.description;
	}
	
	public List<String> subLabelList(){
		return this.subList.stream()
				.map(s -> s.getLabel())
				.collect(Collectors.toList());
	}
	
	public void add(CommandHandler cmdHandler) {
		this.subList.add(cmdHandler);
	}

	public void remove(String subLabel) {
		for(CommandHandler cmdHandler : this.subList) {
			if(cmdHandler.getLabel().equals(subLabel)) {
				this.subList.remove(cmdHandler);
				break;
			}
		}
	}
	
	public void onHelp(Player player, String[] parentLabels, String[] args) {
		
	}
	
	@Override
	public void onCommand(Player player, String[] parentLabels, String[] args) {
		if(args.length != 0) {
			String subLabel = args[0];
			String[] subArgs = new String[args.length - 1];
			System.arraycopy(args, 1, subArgs, 0, args.length - 1);
			String[] joinedParentLabels = new String[parentLabels.length + 1];
			System.arraycopy(parentLabels, 0, joinedParentLabels, 0, parentLabels.length);
			joinedParentLabels[parentLabels.length] = this.label;
			for(CommandHandler cmdHandler : this.subList) {
				if(cmdHandler.getLabel().equals(subLabel)) {
					cmdHandler.onCommand(player, joinedParentLabels, subArgs);
				}
			}
		}
		Logger logger = player.getLogger();
		
		
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		if(args.length == 0) {
			return new ArrayList<>();
		}
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		for(CommandHandler cmdHandler : this.subList) {
			if(cmdHandler.getLabel().equals(subLabel)) {
				return cmdHandler.onTabComplete(subArgs);
			}
		}
		return new ArrayList<>();
	}

}
