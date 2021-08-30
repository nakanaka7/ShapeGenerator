package tokyo.nakanaka.shapeGenerator.selectionStrategy.sphere;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.SelectionData;

/**
 * Handles "/sg sel radius"
 */
public class RadiusCommandHandler implements SelSubCommandHandler {

	@Override
	public void onCommand(SelectionData selData, Player player, String[] subArgs) {
		String usage = "/sg sel radius <radius>";
		double radius;
		if(subArgs.length != 1) {
			player.print(LogColor.RED + "Usage: " + usage);
			return;
		}
		try {
			radius = Double.valueOf(subArgs[0]);
		}catch(IllegalArgumentException e) {
			player.print(LogColor.RED + "Usage: " + usage);
			return;
		}
		if(radius <= 0) {
			player.print(LogColor.RED + "The radius must be positive");
			return;
		}
		World selWorld = selData.getWorld();
		World playerWorld = player.getEntityPosition().world();
		if(!playerWorld.equals(selWorld)) {
			selData.setWorld(playerWorld);
			selData.setRegionData(new SphereRegionData());
		}
		SphereRegionData sphereRegData = (SphereRegionData) selData.getRegionData();
		sphereRegData.setRadius(radius);
		List<String> msgLines = new SphereSelectionMessenger().getMessageLines(sphereRegData);
		msgLines.stream().forEach(player::print);
	}

	@Override
	public List<String> onTabComplete(SelectionData selData, Player player, String[] subArgs) {
		return switch(subArgs.length) {
			case 1 -> List.of("0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0",
						"5.5", "6.0", "6.5", "7.0", "7.5", "8.0", "8.5", "9.0", "9.5");
			default -> List.of();
		};
	}

}
