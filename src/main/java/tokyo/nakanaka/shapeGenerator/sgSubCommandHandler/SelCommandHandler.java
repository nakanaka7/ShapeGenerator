package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SelectionHandler;
import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.commonSelSubCommandHandler.OffsetCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.commonSelSubCommandHandler.ResetCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SelHelp;

/**
 * Handles "/sg sel" command
 */
public class SelCommandHandler implements SubCommandHandler {
	private Map<String, SubCommandHandler> commonMap = new HashMap<>();
	private Map<SelectionShape, Map<String, SubCommandHandler>> properMapMap = new HashMap<>();
	
	public SelCommandHandler(SelectionHandler selHandler) {
		this.commonMap.put("offset", new OffsetCommandHandler(selHandler));
		this.commonMap.put("reset", new ResetCommandHandler(selHandler));
		for(SelectionShape selShape : selHandler.registeredShapes()) {
			this.properMapMap.put(selShape, selHandler.selSubCommandHandlerMap(selShape));
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
		Map<String, SubCommandHandler> selSubCmdHandlerMap = this.properMapMap.get(shape);
		SubCommandHandler properHandler = selSubCmdHandlerMap.get(subLabel);
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
		Map<String, SubCommandHandler> selSubCmdHandlerMap = this.properMapMap.get(shape);
		if(args.length == 1) {
			List<String> subLabelList = new ArrayList<>(this.commonMap.keySet());
			subLabelList.addAll(selSubCmdHandlerMap.keySet().stream()
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
		SubCommandHandler properHandler = selSubCmdHandlerMap.get(subLabel);
		if(properHandler != null) {
			return properHandler.onTabComplete(playerData, player, subArgs);
		}
		return List.of();
	}

}
