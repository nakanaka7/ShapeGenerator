package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SelectionShapeNew;
import tokyo.nakanaka.shapeGenerator.commandHelp.ShapeHelp;
import tokyo.nakanaka.shapeGenerator.user.UserData;

/**
 * Handles "/sg shape" command
 */
public class ShapeCommandHandlerNew implements SgSubCommandHandler {

	@Override
	public void onCommand(UserData userData, Player player, String[] args) {
		if(args.length != 1) {
			player.print(LogColor.RED + "Usage: " + new ShapeHelp().getUsage());
			return;
		}
		SelectionShapeNew shape;
		try{
			shape = SelectionShapeNew.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			player.print(LogColor.RED + "Invalid shape");
			return;
		}
		SelectionShapeNew original = userData.getSelectionShapeNew();
		if(shape != original) {
			userData.setSelectionShapeNew(shape);
			SelectionData selData = shape.newSelectionData(player.getEntityPosition().world());
			userData.setSelectionData(selData);
		}
		player.print(LogColor.GOLD + "Set the shape -> " + shape);
	}

	@Override
	public List<String> onTabComplete(UserData userData, Player player, String[] args) {
		return switch(args.length) {
			case 1 -> List.of(SelectionShapeNew.values()).stream()
					.map(s -> s.toString().toLowerCase())
					.collect(Collectors.toList());
			default -> List.of();
		};
	}

}
