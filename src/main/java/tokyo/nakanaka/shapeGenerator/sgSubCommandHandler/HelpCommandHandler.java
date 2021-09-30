package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.message.MessageUtils;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Handles "/sg help" command
 */
public class HelpCommandHandler implements SubCommandHandler {
	private static final CommandLogColor cmdLogColor = new CommandLogColor(LogColor.GOLD, LogColor.RED);
	private LinkedHashMap<String, CommandHelp> cmdHelpMap = new LinkedHashMap<>();

	public HelpCommandHandler() {
		this.cmdHelpMap.put("version", SgBranchHelpConstants.VERSION);
		this.cmdHelpMap.put("help", SgBranchHelpConstants.HELP);
		this.cmdHelpMap.put("shape", SgBranchHelpConstants.SHAPE);
		this.cmdHelpMap.put("sel", new SelHelp());
		this.cmdHelpMap.put("genr", SgBranchHelpConstants.GENR);
		this.cmdHelpMap.put("phy", SgBranchHelpConstants.PHY);
		this.cmdHelpMap.put("shift", SgBranchHelpConstants.SHIFT);
		this.cmdHelpMap.put("scale", SgBranchHelpConstants.SCALE);
		this.cmdHelpMap.put("mirror", SgBranchHelpConstants.MIRROR);
		this.cmdHelpMap.put("rot", SgBranchHelpConstants.ROT);
		this.cmdHelpMap.put("max", SgBranchHelpConstants.MAX);
		this.cmdHelpMap.put("min", SgBranchHelpConstants.MIN);
		this.cmdHelpMap.put("del", SgBranchHelpConstants.DEL);
		this.cmdHelpMap.put("undo", SgBranchHelpConstants.UNDO);
		this.cmdHelpMap.put("redo", SgBranchHelpConstants.REDO);
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
		if(args.length == 0) {
			player.print(MessageUtils.title(cmdLogColor.main() + "Quick help for " + LogColor.RESET + "/sg"));
			this.cmdHelpMap.entrySet().stream()
					.map(s -> s.getValue())
					.forEach(s -> player.print(cmdLogColor.main() + s.syntax() + ": " + LogColor.RESET + s.description()));
			player.print(cmdLogColor.main() + "Run \"/sg help <subcommand>\" for details");
		}else if(args.length == 1) {
			CommandHelp cmdHelp = this.cmdHelpMap.get(args[0]);
			if(cmdHelp != null) {
				if(cmdHelp instanceof BranchCommandHelp branchHelp){
					List<String> lines = new ArrayList<>();
					lines.add(MessageUtils.title(cmdLogColor.main() + "Help for " + LogColor.RESET + "/sg " + args[0]));
					lines.add(cmdLogColor.main() + "Description: " + LogColor.RESET + branchHelp.description());
					lines.add(cmdLogColor.main() + "Usage: " + LogColor.RESET + "/sg " + args[0] + " " + cmdLogColor.main() + String.join(" ", branchHelp.parameterSyntaxes()));
					if(branchHelp.parameterSyntaxes().length != 0){
						lines.add(cmdLogColor.main() + "Parameter: ");
						for(int i = 0; i < branchHelp.parameterSyntaxes().length; ++i){
							lines.add("  " + cmdLogColor.main() + branchHelp.parameterSyntaxes()[i] + ": "
									+ LogColor.RESET + branchHelp.parameterDescription(i));
						}
					}
					lines.forEach(player::print);
				}else if(cmdHelp instanceof  SelHelp selHelp){
					selHelp.toMultipleLines().forEach(player::print);
				}
			}else {
				player.print(cmdLogColor.error() + "Unknown subcommand");
			}
		}else {
			player.print(cmdLogColor.error() + "Usage: /sg help [subcommand]");
		}
	}

	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return switch(args.length){
			case 1 -> this.cmdHelpMap.keySet().stream().toList();
			default -> List.of();
		};
	}

}
