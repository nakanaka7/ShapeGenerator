package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.regularPolygonSelSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.BaseSelSubCommandHandler;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelectionDataFactory;

public class SideCommandHandler extends BaseSelSubCommandHandler<Integer> {

	public SideCommandHandler(SelectionDataFactory selDataFactory) {
		super("side", new String[] {"<number>"}, selDataFactory);
	}

	@Override
	protected Integer parse(Player player, String[] subArgs) {
		if(subArgs.length != 1) {
			throw new IllegalArgumentException();
		}
		int value = Integer.parseInt(subArgs[0]);
		if(value < 3) {
			throw new IllegalArgumentException();
		}
		return value;
	}
	
	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return switch(args.length) {
		case 1 -> List.of("3", "4", "5", "6", "7", "8", "9");
		default -> List.of();
		};
	}

}
