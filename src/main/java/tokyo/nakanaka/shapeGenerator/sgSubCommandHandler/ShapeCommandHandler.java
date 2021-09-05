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
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.ShapeHelp;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Handles "/sg shape" command
 */
public class ShapeCommandHandler implements SubCommandHandler {
	private SelectionHandler selHandler;
	
	public ShapeCommandHandler(SelectionHandler selHandler) {
		this.selHandler = selHandler;
	}

	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
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
		SelectionShape original = userData.getSelectionShape();
		if(shape != original) {
			userData.setSelectionShape(shape);
			World world = player.getEntityPosition().world();
			SelectionData selData = this.selHandler.newSelectionData(shape);
			selData.setWorld(world);
			userData.setSelectionData(selData);
		}
		player.print(LogColor.GOLD + "Set the shape -> " + shape);
	}

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		return switch(args.length) {
			case 1 -> List.of(SelectionShape.values()).stream()
					.map(s -> s.toString().toLowerCase())
					.collect(Collectors.toList());
			default -> List.of();
		};
	}
	
}
