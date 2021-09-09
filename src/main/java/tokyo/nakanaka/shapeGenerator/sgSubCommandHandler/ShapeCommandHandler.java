package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SelectionHandler;
import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.ShapeHelp;

/**
 * Handles "/sg shape" command
 */
public class ShapeCommandHandler implements SubCommandHandler {
	private SelectionHandler selHandler;
	
	public ShapeCommandHandler(SelectionHandler selHandler) {
		this.selHandler = selHandler;
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
		if(args.length != 1) {
			player.print(LogColor.RED + "Usage: " + new ShapeHelp().getUsage());
			return;
		}
		SelectionShape shape;
		try{
			shape = SelectionShape.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			player.print(LogColor.RED + "Invalid shape");
			return;
		}
		SelectionShape original = playerData.getSelectionShape();
		if(shape != original) {
			playerData.setSelectionShape(shape);
			World world = player.getEntityPosition().world();
			SelectionData selBuilder = this.selHandler.newSelectionData(shape, world);
			playerData.setSelectionData(selBuilder);
		}
		player.print(LogColor.GOLD + "Set the shape -> " + shape);
	}

	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return switch(args.length) {
			case 1 -> List.of(SelectionShape.values()).stream()
					.map(s -> s.toString().toLowerCase())
					.collect(Collectors.toList());
			default -> List.of();
		};
	}
	
}
