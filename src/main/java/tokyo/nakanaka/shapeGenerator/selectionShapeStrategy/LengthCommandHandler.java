package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.MessageUtils;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.regionData.RegionData;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public abstract class LengthCommandHandler implements SubCommandHandler {
private String subLabel;
	
	public LengthCommandHandler(String subLabel) {
		this.subLabel = subLabel;
	}
	
	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
		String usage = "/sg sel " + subLabel + " <length>";
		//parse the arguments to a position
		double length;
		switch(args.length) {
		case 1 -> {
			try {
				length = Double.parseDouble(args[0]);
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
		World evtWorld = player.getBlockPosition().world();
		if(!evtWorld.equals(userData.getSelectionData().getWorld())) {
			RegionData newRegData = this.newRegionData();
			SelectionData newSelData = new SelectionData(evtWorld, newRegData);
			userData.setSelectionData(newSelData);
		}
		SelectionData selData = userData.getSelectionData();
		RegionData regData = selData.getRegionData();
		//update the selection data
		this.setLength(regData, length);
		//print the selection message
		List<String> lines = MessageUtils.selectionMessage(userData.getSelectionShape(), selData);
		lines.stream().forEach(player::print);
	}
	
	protected abstract RegionData newRegionData();
	protected abstract void setLength(RegionData regData, Double length);

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		return switch(args.length) {
			case 1 -> List.of("0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0",
					"5.5", "6.0", "6.5", "7.0", "7.5", "8.0", "8.5", "9.0");
			default -> List.of();
		};
	}
	
}
