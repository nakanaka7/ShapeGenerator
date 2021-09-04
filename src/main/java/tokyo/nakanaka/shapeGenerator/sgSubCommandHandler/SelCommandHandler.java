package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SelectionDataCreator;
import tokyo.nakanaka.shapeGenerator.SelectionHandler;
import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.OffsetCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.ResetCommandHandler;
import tokyo.nakanaka.shapeGenerator.selSubCommandHandler.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.SelHelp;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Handles "/sg sel" command
 */
public class SelCommandHandler implements SgSubCommandHandler {
	private Map<String, SelSubCommandHandler> commonSelSubCmdHandlerMap = new HashMap<>();
	private SelectionHandler selHandler;
	
	{
		this.commonSelSubCmdHandlerMap.put("offset", new OffsetCommandHandler());
	}
	
	public SelCommandHandler(SelectionDataCreator selDataCreator, SelectionHandler selHandler) {
		this.commonSelSubCmdHandlerMap.put("reset", new ResetCommandHandler(selDataCreator));
		this.selHandler = selHandler;
	}

	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
		SelectionShape shape = userData.getSelectionShape();
		if(args.length == 0) {
			player.print(LogColor.RED + "Usage:" + new SelHelp().getUsage());
			player.print(LogColor.RED + "See help");
			return;
		}
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		SelSubCommandHandler commonHandler = this.commonSelSubCmdHandlerMap.get(subLabel);
		if(commonHandler != null) {
			commonHandler.onCommand(userData, player, subArgs);
			return;
		}
		Map<String, SelSubCommandHandler> selSubCmdHandlerMap = this.selHandler.selSubCommandHandlerMap(shape);
		SelSubCommandHandler properHandler = selSubCmdHandlerMap.get(subLabel);
		if(properHandler != null) {
			properHandler.onCommand(userData, player, subArgs);
			return;
		}
		player.print(LogColor.RED + "Unkown subcommand");
		player.print(LogColor.RED + "See help");	
	}

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		SelectionShape shape = userData.getSelectionShape();
		Map<String, SelSubCommandHandler> selSubCmdHandlerMap = this.selHandler.selSubCommandHandlerMap(shape);
		if(args.length == 1) {
			List<String> subLabelList = new ArrayList<>(this.commonSelSubCmdHandlerMap.keySet());
			subLabelList.addAll(selSubCmdHandlerMap.keySet().stream()
					.collect(Collectors.toList()));
			return subLabelList;
		}
		String subLabel = args[0];
		String[] subArgs = new String[args.length - 1];
		System.arraycopy(args, 1, subArgs, 0, args.length - 1);
		SelSubCommandHandler commonHandler = this.commonSelSubCmdHandlerMap.get(subLabel);
		if(commonHandler != null) {
			return commonHandler.onTabComplete(userData, player, subArgs);
		}
		SelSubCommandHandler properHandler = selSubCmdHandlerMap.get(subLabel);
		if(properHandler != null) {
			return properHandler.onTabComplete(userData, player, subArgs);
		}
		return List.of();
	}

}
