package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.tetrahedronSelSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.regionData.CuboidRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.regionData.TetrahedronRegionData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelSubCommandHandlerUtils;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class Pos2CommandHandler implements SubCommandHandler {

	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
		//parse the arguments to a position
		String usage = "/sg sel pos2 [x] [y] [z]";
		double x;
		double y;
		double z;
		BlockPosition pos = player.getBlockPosition();
		switch(args.length) {
		case 0 -> {
			x = pos.x();
			y = pos.y();
			z = pos.z();
		}
		case 3 -> {
			try {
				x = Double.parseDouble(args[0]);
				y = Double.parseDouble(args[1]);
				z = Double.parseDouble(args[2]);
			}catch(IllegalArgumentException e) {
				player.print(LogColor.RED + "Usage: " + usage);
				return;
			}
		}
		default -> {
			player.print(LogColor.RED + "Usage: " + usage);
			return;
		}	
		}
		//reset the selection data if the world changes
		World evtWorld = pos.world();
		if(!evtWorld.equals(userData.getSelectionData().getWorld())) {
			RegionData cuboidRegData = new CuboidRegionData();
			SelectionData newSelData = new SelectionData(evtWorld, cuboidRegData);
			userData.setSelectionData(newSelData);
		}
		SelectionData selData = userData.getSelectionData();
		TetrahedronRegionData tetraRegData = (TetrahedronRegionData)selData.getRegionData();
		//update the selection data
		tetraRegData.setPos2(new Vector3D(x, y, z));
		//print the selection message
		List<String> lines = SelSubCommandHandlerUtils.selectionMessage(SelectionShape.TETRAHEDRON, selData);
		lines.stream().forEach(player::print);
	}

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		BlockPosition pos = player.getBlockPosition();
		return switch(args.length) {
			case 1 -> List.of(String.valueOf(pos.x()));
			case 2 -> List.of(String.valueOf(pos.y()));
			case 3 -> List.of(String.valueOf(pos.z()));
			default -> List.of();
		};
	}
	
}
