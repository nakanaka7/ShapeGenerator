package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.commonSelSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.MessageUtils;
import tokyo.nakanaka.shapeGenerator.SelectionBuilder;
import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.regionData.CuboidRegionData;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;

public class OffsetCommandHandler implements SubCommandHandler {
	
	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
		String usage = "/sg sel offset [x] [y] [z]";
		//parse the arguments to a position
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
		if(!evtWorld.equals(playerData.getSelectionBuilder().world())) {
			RegionData cuboidRegData = new CuboidRegionData();
			SelectionBuilder newSelBuilder = new SelectionBuilder(evtWorld, cuboidRegData);
			playerData.setSelectionBuilder(newSelBuilder);
		}
		SelectionBuilder selData = playerData.getSelectionBuilder();
		selData.setCustomOffset(new Vector3D(x, y, z));
		//print the selection message
		List<String> lines = MessageUtils.selectionMessage(SelectionShape.TETRAHEDRON, selData);
		lines.stream().forEach(player::print);
	}
	
	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		BlockPosition pos = player.getBlockPosition();
		return switch(args.length) {
			case 1 -> List.of(String.valueOf(pos.x()));
			case 2 -> List.of(String.valueOf(pos.y()));
			case 3 -> List.of(String.valueOf(pos.z()));
			default -> List.of();
		};
	}

}
