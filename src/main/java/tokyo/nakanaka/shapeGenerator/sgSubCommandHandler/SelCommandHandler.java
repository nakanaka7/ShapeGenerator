package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.shapeGenerator.commandHelp.SelHelp;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Handles "/sg sel" command
 */
public class SelCommandHandler implements SgSubCommandHandler {
	private Map<SelectionShape, Map<String, SelSubCommandHandler>> selSubCmdHandlerMapMap = new EnumMap<>(SelectionShape.class);
	
	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
		SelectionShape shape = userData.getSelectionShape();
		Map<String, SelSubCommandHandler> selSubCmdHandlerMap = this.selSubCmdHandlerMapMap.get(shape);
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
			player.print(LogColor.RED + "Unkown command");
			player.print(LogColor.RED + "See help");
			return;
		}
		selSubCmdHandler.onCommand(userData.getSelectionData(), player, subArgs);
	}

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		SelectionShape shape = userData.getSelectionShape();
		Map<String, SelSubCommandHandler> selSubCmdHandlerMap = this.selSubCmdHandlerMapMap.get(shape);
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		return selSubCmdHandlerMap.get(subLabel).onTabComplete(userData.getSelectionData(), player, subArgs);
	}

}
