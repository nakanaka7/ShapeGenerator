package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.commandHelp.SelHelp;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.cuboid.CuboidSelectionStrategy;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Handles "/sg sel" command
 */
public class SelCommandHandler implements SgSubCommandHandler {
	private Map<SelectionShape, SelectionStrategy> selStrtgMap = new EnumMap<>(SelectionShape.class);
	
	public SelCommandHandler() {
		this.selStrtgMap.put(SelectionShape.CUBOID, new CuboidSelectionStrategy());
	}
	
	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
		SelectionShape shape = userData.getSelectionShape();
		Map<String, SelSubCommandHandler> selSubCmdHandlerMap = this.selStrtgMap.get(shape).getSelSubCommandHandlerMap();
		if(args.length == 0) {
			player.print(LogColor.RED + "Usage:" + new SelHelp().getUsage());
			player.print(LogColor.RED + "See help");
			return;
		}
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		SelSubCommandHandler selSubCmdHandler = selSubCmdHandlerMap.get(subLabel);
		if(selSubCmdHandler == null) {
			player.print(LogColor.RED + "Unkown subcommand");
			player.print(LogColor.RED + "See help");
			return;
		}
		selSubCmdHandler.onCommand(userData.getSelectionData(), player, subArgs);
	}

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		SelectionShape shape = userData.getSelectionShape();
		Map<String, SelSubCommandHandler> selSubCmdHandlerMap = this.selStrtgMap.get(shape).getSelSubCommandHandlerMap();
		String subLabel = args[0];
		if(args.length == 1) {
			return selSubCmdHandlerMap.keySet().stream()
					.collect(Collectors.toList());
		}
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		SelSubCommandHandler selSubCmdHandler = selSubCmdHandlerMap.get(subLabel);
		if(selSubCmdHandler == null) {
			return List.of();
		}
		return selSubCmdHandler.onTabComplete(userData.getSelectionData(), player, subArgs);
	}

}
