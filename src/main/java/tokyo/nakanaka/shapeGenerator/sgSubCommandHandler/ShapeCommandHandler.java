package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.shapeGenerator.SelectionData;
import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.SelectionShapeStrategyRepository;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.CommandLogColor;
import tokyo.nakanaka.shapeGenerator.sgSubCommandHelp.ShapeHelp;

import java.util.List;
import java.util.stream.Stream;

/**
 * Handles "/sg shape" command
 */
public class ShapeCommandHandler implements SubCommandHandler {
	private static final CommandLogColor cmdLogColor = new CommandLogColor(LogColor.GOLD, LogColor.RED);
	private SelectionShapeStrategyRepository shapeStrtgRepo;
	
	public ShapeCommandHandler(SelectionShapeStrategyRepository shapeStrtgRepo) {
		this.shapeStrtgRepo = shapeStrtgRepo;
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args) {
		if(args.length != 1) {
			player.print(cmdLogColor.error() + "Usage: " + new ShapeHelp().usage());
			return;
		}
		SelectionShape selShape;
		try{
			selShape = SelectionShape.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			player.print(cmdLogColor.error() + "Invalid shape");
			return;
		}
		if(!List.of(this.shapeStrtgRepo.registeredShapes()).contains(selShape)) {
			player.print(cmdLogColor.error() + "Unsupported shape");
			return;
		}
		SelectionShape original = playerData.getSelectionShape();
		if(selShape != original) {
			playerData.setSelectionShape(selShape);
			World world = player.getEntityPosition().world();
			SelectionData selData = this.shapeStrtgRepo.get(selShape).newSelectionData(world);
			playerData.setSelectionData(selData);
		}
		player.print(cmdLogColor.main() + "Set the shape -> " + selShape);
	}

	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return switch(args.length) {
			case 1 -> Stream.of(this.shapeStrtgRepo.registeredShapes())
					.map(s -> s.toString().toLowerCase())
					.toList();
			default -> List.of();
		};
	}
	
}
