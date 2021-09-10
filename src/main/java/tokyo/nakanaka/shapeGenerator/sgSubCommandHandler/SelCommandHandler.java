package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SelectionShapeStrategyRepository;
import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelectionShapeStrategy;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.commonSelSubCommandHandler.OffsetCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.commonSelSubCommandHandler.ResetCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SelHelp;

/**
 * Handles "/sg sel" command
 */
public class SelCommandHandler implements SubCommandHandler {
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

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
		SelectionShape shape = playerData.getSelectionShape();
		if(args.length == 0) {
			player.print(LogColor.RED + "Usage:" + new SelHelp().getUsage());
			player.print(LogColor.RED + "See help");
			return;
		}
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		SubCommandHandler commonHandler = this.commonMap.get(subLabel);
		if(commonHandler != null) {
			commonHandler.onCommand(playerData, player, subArgs);
			return;
		}
		Map<String, SubCommandHandler> properMap = this.properMapMap.get(shape);
		SubCommandHandler properHandler = properMap.get(subLabel);
		if(properHandler != null) {
			properHandler.onCommand(playerData, player, subArgs);
			return;
		}
		player.print(LogColor.RED + "Unkown subcommand");
		player.print(LogColor.RED + "See help");	
	}

	@Override
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
