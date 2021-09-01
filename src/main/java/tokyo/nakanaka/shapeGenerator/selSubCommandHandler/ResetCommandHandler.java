package tokyo.nakanaka.shapeGenerator.selSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.Utils;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class ResetCommandHandler implements SelSubCommandHandler {
	
	public void onCommand(UserData userData, Player player, String[] args) {
		if(args.length != 0) {
			player.print(LogColor.RED + "Usage: " + "/sg sel reset");
			return;
		}
		World world = userData.getWorld();
		SelectionShape shape = userData.getSelectionShape();
		SelectionData newSelData = shape.newSelectionData(world);
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
