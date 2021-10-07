package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import static tokyo.nakanaka.shapeGenerator.SgSublabel.*;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles "/sg help" command
 */
public class HelpCommandHandler implements SubCommandHandler {
	private LinkedHashMap<String, CommandHelp> cmdHelpMap = new LinkedHashMap<>();

	public HelpCommandHandler() {
		this.cmdHelpMap.put(HELP, SgBranchHelpConstants.HELP);
		this.cmdHelpMap.put(VERSION, SgBranchHelpConstants.VERSION);
		this.cmdHelpMap.put(WAND, SgBranchHelpConstants.WAND);
		this.cmdHelpMap.put(SHAPE, SgBranchHelpConstants.SHAPE);
		this.cmdHelpMap.put(SEL, new SelHelp());
		this.cmdHelpMap.put(GENR, SgBranchHelpConstants.GENR);
		this.cmdHelpMap.put(PHY, SgBranchHelpConstants.PHY);
		this.cmdHelpMap.put(SHIFT, SgBranchHelpConstants.SHIFT);
		this.cmdHelpMap.put(SCALE, SgBranchHelpConstants.SCALE);
		this.cmdHelpMap.put(MIRROR, SgBranchHelpConstants.MIRROR);
		this.cmdHelpMap.put(ROT, SgBranchHelpConstants.ROT);
		this.cmdHelpMap.put(MAX, SgBranchHelpConstants.MAX);
		this.cmdHelpMap.put(MIN, SgBranchHelpConstants.MIN);
		this.cmdHelpMap.put(DEL, SgBranchHelpConstants.DEL);
		this.cmdHelpMap.put(UNDO, SgBranchHelpConstants.UNDO);
		this.cmdHelpMap.put(REDO, SgBranchHelpConstants.REDO);
	}

	@Override
	public String label() {
		return "help";
	}

	@Override
	public String description() {
		return "Print the command help";
	}

	public ParameterUsage[] parameterUsages() {
		var subcmd = new ParameterUsage("[subcommand]", "a subcommand to print the help");
		return new ParameterUsage[]{subcmd};
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
		if(args.length == 0) {
			var msgBuilder = new RootHelpMessageCreator.Builder(cmdLogColor.main(), "/sg")
					.description("The root command of ShapeGenerator");
			for (Map.Entry<String, CommandHelp> e : this.cmdHelpMap.entrySet()){
				String subCmdSyntax = e.getKey() + " " + String.join(" ", e.getValue().parameterSyntaxes());
				msgBuilder = msgBuilder.subcommand(subCmdSyntax , e.getValue().description());
			}
			List<String> lines = msgBuilder.build().toMessageLines();
			lines.add(cmdLogColor.main() + "Run \"/sg help <subcommand>\" for details");
			lines.forEach(player::print);
		}else if(args.length == 1) {
			CommandHelp cmdHelp = this.cmdHelpMap.get(args[0]);
			if(cmdHelp == null) {
				player.print(cmdLogColor.error() + "Unknown subcommand");
				return;
			}
			if(cmdHelp instanceof BranchCommandHelp branchHelp){
				var msgBuilder = new BranchHelpMessageCreator.Builder(cmdLogColor.main(), "/sg", args[0])
						.description(branchHelp.description());
				for(int i = 0; i < branchHelp.parameterSyntaxes().length; i++){
					msgBuilder = msgBuilder.parameter(branchHelp.parameterSyntaxes()[i]
							, branchHelp.parameterDescription(i));
				}
				List<String> lines = msgBuilder.build().toMessageLines();
				lines.forEach(player::print);
			}else if(cmdHelp instanceof SelHelp selHelp){
				selHelp.toMultipleLines(playerData.getSelectionShape()).forEach(player::print);
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
