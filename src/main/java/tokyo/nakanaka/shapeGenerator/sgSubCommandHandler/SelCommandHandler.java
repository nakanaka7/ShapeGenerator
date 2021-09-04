package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SelectionHandler;
import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SelHelp;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Handles "/sg sel" command
 */
public class SelCommandHandler implements SgSubCommandHandler {
	private SelectionHandler selHandler;

	public SelCommandHandler(SelectionHandler selHandler) {
		this.selHandler = selHandler;
	}

	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
		SelectionShape shape = userData.getSelectionShape();
		Map<String, SelSubCommandHandler> selSubCmdHandlerMap = this.selHandler.selSubCommandHandlerMap(shape);
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
		selSubCmdHandler.onCommand(userData, player, subArgs);
	}

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		SelectionShape shape = userData.getSelectionShape();
		Map<String, SelSubCommandHandler> selSubCmdHandlerMap = shape.selSubCommandHandlerMap();
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
		return selSubCmdHandler.onTabComplete(userData, player, subArgs);
	}

}
