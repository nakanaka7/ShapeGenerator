package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.shapeGenerator.commandHelp.SelHelp;
import tokyo.nakanaka.shapeGenerator.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Handles "/sg sel" command
 */
public class SelCommandHandler implements SgSubCommandHandler {
	private Map<SelectionShape, SelectionStrategy> selStrtgMap = new EnumMap<>(SelectionShape.class);
	
	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
		SelectionShape shape = userData.getSelectionShape();
		SelSubCommandHandler selSubCmdHandler = this.selStrtgMap.get(shape).getSelSubCommandHandler();
		if(args.length == 0) {
			player.print(LogColor.RED + "Usage:" + new SelHelp().getUsage());
			player.print(LogColor.RED + "See help");
			return;
		}
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		selSubCmdHandler.onCommand(userData.getSelectionData(), player, subLabel, subArgs);
	}

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		SelectionShape shape = userData.getSelectionShape();
		SelSubCommandHandler selSubCmdHandler = this.selStrtgMap.get(shape).getSelSubCommandHandler();	
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		return selSubCmdHandler.onTabComplete(userData.getSelectionData(), player, subLabel, subArgs);
	}

}
