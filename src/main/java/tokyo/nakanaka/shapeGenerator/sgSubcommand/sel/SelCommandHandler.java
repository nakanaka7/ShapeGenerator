package tokyo.nakanaka.shapeGenerator.sgSubcommand.sel;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.*;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.sgSubcommandHelp.SgSubcommandHelps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Handles "/sg sel" command
 */
public class SelCommandHandler {
	private Map<String, SubCommandHandler> commonMap = new HashMap<>();
	private Map<SelectionShape, Map<String, SubCommandHandler>> properMapMap = new HashMap<>();
	
	public SelCommandHandler(SelectionShapeStrategyRepository shapeStrtgRepo) {
		this.commonMap.put("offset", new OffsetCommandHandler(shapeStrtgRepo));
		this.commonMap.put("reset", new ResetCommandHandler(shapeStrtgRepo));
		for(SelectionShape selShape : shapeStrtgRepo.registeredShapes()) {
			SelectionShapeStrategy selStrtg = shapeStrtgRepo.get(selShape);
			this.properMapMap.put(selShape, selStrtg.selSubCommandHandlerMap());
		}
	}

	public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
		String usage = Main.SG + " " + SgSublabel.SEL + " " + String.join(" ", SgSubcommandHelps.SEL.parameterSyntaxes());
		SelectionShape shape = playerData.getSelectionShape();
		if(args.length == 0) {
			player.print(cmdLogColor.error() + "Usage:" + usage);
			player.print(cmdLogColor.error() + "See help");
			return;
		}
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		SubCommandHandler commonHandler = this.commonMap.get(subLabel);
		if(commonHandler != null) {
			commonHandler.onCommand(playerData, player, subArgs, cmdLogColor);
			return;
		}
		Map<String, SubCommandHandler> properMap = this.properMapMap.get(shape);
		SubCommandHandler properHandler = properMap.get(subLabel);
		if(properHandler != null) {
			properHandler.onCommand(playerData, player, subArgs, cmdLogColor);
			return;
		}
		player.print(cmdLogColor.error() + "Unkown subcommand");
		player.print(cmdLogColor.error() + "See help");
	}

	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		SelectionShape shape = playerData.getSelectionShape();
		Map<String, SubCommandHandler> properMap = this.properMapMap.get(shape);
		if(args.length == 1) {
			List<String> subLabelList = new ArrayList<>(this.commonMap.keySet());
			subLabelList.addAll(properMap.keySet().stream()
					.collect(Collectors.toList()));
			return subLabelList;
		}
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		SubCommandHandler commonHandler = this.commonMap.get(subLabel);
		if(commonHandler != null) {
			return commonHandler.onTabComplete(playerData, player, subArgs);
		}
		SubCommandHandler properHandler = properMap.get(subLabel);
		if(properHandler != null) {
			return properHandler.onTabComplete(playerData, player, subArgs);
		}
		return List.of();
	}

}
