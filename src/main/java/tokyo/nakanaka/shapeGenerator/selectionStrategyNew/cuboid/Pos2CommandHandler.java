package tokyo.nakanaka.shapeGenerator.selectionStrategyNew.cuboid;

import java.util.List;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.selectionStrategyNew.SelSubCommandHandlerNew;

/**
 * Handles "/sg sel pos2" command
 */
public class Pos2CommandHandler implements SelSubCommandHandlerNew {

	@Override
	public void onCommand(SelectionData selData, Player player, String[] subArgs) {
		String usage = "/sg sel pos2 <x> <y> <z>";
		double x;
		double y;
		double z;
		if(subArgs.length == 0) {
			BlockPosition pos = player.getBlockPosition();
			x = pos.x();
			y = pos.y();
			z = pos.z();
		}else if(subArgs.length == 3) {
			try {
				x = Double.valueOf(subArgs[0]);
				y = Double.valueOf(subArgs[1]);
				z = Double.valueOf(subArgs[2]);
			}catch(IllegalArgumentException e) {
				player.print(LogColor.RED + "Usage: " + usage);
				return;
			}
		}else {
			player.print(LogColor.RED + "Usage: " + usage);
			return;
		}
		World selWorld = selData.getWorld();
		World playerWorld = player.getEntityPosition().world();
		if(!playerWorld.equals(selWorld)) {
			selData.setWorld(playerWorld);
			selData.setRegionData(new CuboidRegionData());
		}
		CuboidRegionData cuboidRegData = (CuboidRegionData) selData.getRegionData();
		cuboidRegData.setPos2(new Vector3D(x, y, z));
		List<String> msgLines = new CuboidSelectionMessenger().getMessageLines(cuboidRegData);
		msgLines.stream().forEach(player::print);
	}

	@Override
	public List<String> onTabComplete(SelectionData selData, Player player, String[] subArgs) {
		BlockPosition pos = player.getBlockPosition();
		return switch(subArgs.length) {
			case 1 -> List.of(String.valueOf(pos.x()));
			case 2 -> List.of(String.valueOf(pos.y()));
			case 3 -> List.of(String.valueOf(pos.z()));
			default -> List.of();
		};
	}

}