package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.commonSelSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.MessageUtils;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SelectionHandler;
import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

public class ResetCommandHandler implements SubCommandHandler {
	private SelectionHandler selHandler;

	public ResetCommandHandler(SelectionHandler selHandler) {
		this.selHandler = selHandler;
	}

	public void onCommand(PlayerData playerData, Player player, String[] args) {
		if(args.length != 0) {
			player.print(LogColor.RED + "Usage: " + "/sg sel reset");
			return;
		}
		World world = player.getEntityPosition().world();
		SelectionShape shape = playerData.getSelectionShape();
		SelectionData newSelData = this.selHandler.newSelectionData(shape, world);
		playerData.setSelectionData(newSelData);
		List<String> lines = MessageUtils.selectionMessage(shape, newSelData);
		for(String line : lines) {
			player.print(line);
		}
	}
	
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return List.of();
	}

}
