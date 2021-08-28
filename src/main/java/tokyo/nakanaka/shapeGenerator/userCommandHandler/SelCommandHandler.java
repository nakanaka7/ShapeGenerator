package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.List;
import java.util.Map;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Handles "/sg sel" command
 */
public class SelCommandHandler implements UserCommandHandler {

	

	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
		SelectionShape shape = userData.getSelectionShape();
		Map<String, SelSubCommandHandler> selSubCmdHandlerMap;
		if(args.length == 0) {
			player.print(LogColor.RED + "See help");
			return;
		}
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
	}

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		SelectionShape shape = userData.getSelectionShape();
		Map<String, SelSubCommandHandler> selSubCmdHandlerMap;
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		return null;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
