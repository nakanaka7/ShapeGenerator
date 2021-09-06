package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.List;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.MessageUtils;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public abstract class PosCommandHandler implements SubCommandHandler {
	private String subLabel;
	
	public PosCommandHandler(String subLabel) {
		this.subLabel = subLabel;
	}

	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
		String usage = "/sg sel " + subLabel + " [x] [y] [z]";
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
		if(!evtWorld.equals(userData.getSelectionData().getWorld())) {
			RegionData newRegData = this.newRegionData();
			SelectionData newSelData = new SelectionData(evtWorld, newRegData);
			userData.setSelectionData(newSelData);
		}
		SelectionData selData = userData.getSelectionData();
		RegionData regData = selData.getRegionData();
		//update the selection data
		this.setPos(regData, new Vector3D(x, y, z));
		//print the selection message
		List<String> lines = MessageUtils.selectionMessage(userData.getSelectionShape(), selData);
		lines.stream().forEach(player::print);
	}
	
	protected abstract RegionData newRegionData();
	protected abstract void setPos(RegionData regData, Vector3D pos);

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