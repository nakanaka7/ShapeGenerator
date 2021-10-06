package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.BranchCommandHandler;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Handles "/sg help" command
 */
public class HelpCommandHandler implements BranchCommandHandler {
	private static final CommandLogColor cmdLogColor = new CommandLogColor(LogColor.GOLD, LogColor.RED);
	private LinkedHashMap<String, CommandHelp> cmdHelpMap = new LinkedHashMap<>();

	public HelpCommandHandler() {
		registerHelp(SgBranchHelpConstants.HELP);
		registerHelp(SgBranchHelpConstants.VERSION);
		registerHelp(SgBranchHelpConstants.WAND);
		registerHelp(SgBranchHelpConstants.SHAPE);
		registerHelp(new SelHelp());
		registerHelp(SgBranchHelpConstants.GENR);
		registerHelp(SgBranchHelpConstants.PHY);
		registerHelp(SgBranchHelpConstants.SHIFT);
		registerHelp(SgBranchHelpConstants.SCALE);
		registerHelp(SgBranchHelpConstants.MIRROR);
		registerHelp(SgBranchHelpConstants.ROT);
		registerHelp(SgBranchHelpConstants.MAX);
		registerHelp(SgBranchHelpConstants.MIN);
		registerHelp(SgBranchHelpConstants.DEL);
		registerHelp(SgBranchHelpConstants.UNDO);
		registerHelp(SgBranchHelpConstants.REDO);
	}

	public void registerHelp(CommandHelp cmdHelp){
		this.cmdHelpMap.put(cmdHelp.label(), cmdHelp);
	}

	@Override
	public String label() {
		return "help";
	}

	@Override
	public String description() {
		return "Print the command help";
	}

	@Override
	public ParameterUsage[] parameterUsages() {
		var subcmd = new ParameterUsage("[subcommand]", "a subcommand to print the help");
		return new ParameterUsage[]{subcmd};
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
		if(args.length == 0) {
			var msgBuilder = new RootHelpMessageCreator.Builder(cmdLogColor.main(), "/sg")
					.description("The root command of ShapeGenerator");
			for (CommandHelp e : this.cmdHelpMap.values()){
				msgBuilder = msgBuilder.subcommand(e.syntax(), e.description());
			}
			List<String> lines = msgBuilder.build().toMessageLines();
			lines.add(cmdLogColor.main() + "Run \"/sg help <subcommand>\" for details");
			lines.forEach(player::print);
		}else if(args.length == 1) {
			CommandHelp cmdHelp = this.cmdHelpMap.get(args[0]);
			if(cmdHelp != null) {
				if(cmdHelp instanceof BranchCommandHelp branchHelp){
					var msgBuilder = new BranchHelpMessageCreator.Builder(cmdLogColor.main(), "/sg", args[0])
							.description(branchHelp.description());
					for(int i = 0; i < branchHelp.parameterSyntaxes().length; i++){
						msgBuilder = msgBuilder.parameter(branchHelp.parameterSyntaxes()[i]
								, branchHelp.parameterDescription(i));
					}
					List<String> lines = msgBuilder.build().toMessageLines();
					lines.forEach(player::print);
				}else if(cmdHelp instanceof  SelHelp selHelp){
					selHelp.toMultipleLines(playerData.getSelectionShape()).forEach(player::print);
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
