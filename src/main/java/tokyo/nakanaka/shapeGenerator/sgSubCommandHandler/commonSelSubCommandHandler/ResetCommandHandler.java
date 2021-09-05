package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.commonSelSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SelectionHandler;
import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.Utils;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class ResetCommandHandler implements SubCommandHandler {
	private SelectionHandler selHandler;

	public ResetCommandHandler(SelectionHandler selHandler) {
		this.selHandler = selHandler;
	}

	public void onCommand(UserData userData, Player player, String[] args) {
		if(args.length != 0) {
			player.print(LogColor.RED + "Usage: " + "/sg sel reset");
			return;
		}
		World world = player.getEntityPosition().world();
		SelectionShape shape = userData.getSelectionShape();
		SelectionData newSelData = this.selHandler.newSelectionData(shape);
		newSelData.setWorld(world);
		userData.setSelectionData(newSelData);
		List<String> lines = Utils.getSelectionMessageLines(userData.getSelectionData());
		for(String line : lines) {
			player.print(line);
		}
	}
	
	public List<String> onTabComplete(UserData user, Player player, String[] args) {
		return List.of();
	}

}
