package tokyo.nakanaka.shapeGenerator.sgSubcommand.sel;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.*;
import tokyo.nakanaka.shapeGenerator.message.MessageUtils;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

import java.util.List;
import java.util.Map;

public class OffsetCommandHandler implements SubCommandHandler {
	private Map<SelectionShape, InitialSelectionDataCreator> dataCreatorMap;

	public OffsetCommandHandler(Map<SelectionShape, InitialSelectionDataCreator> dataCreatorMap) {
		this.dataCreatorMap = dataCreatorMap;
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
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
				player.print(cmdLogColor.error() + "Usage: " + usage);
				return;
			}
		}
		default -> {
			player.print(cmdLogColor.error() + "Usage: " + usage);
			return;
		}	
		}
		//reset the selection data if the world changes
		World evtWorld = pos.world();
		if(!evtWorld.equals(playerData.getSelectionData().world())) {
			SelectionData selData = this.dataCreatorMap
					.get(playerData.getSelectionShape())
					.newSelectionData(evtWorld);
			playerData.setSelectionData(selData);
		}
		SelectionData selData = playerData.getSelectionData();
		selData.setCustomOffset(new Vector3D(x, y, z));
		//print the selection message
		List<String> lines = MessageUtils.selectionMessage(cmdLogColor.main(), playerData.getSelectionShape(), selData);
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
