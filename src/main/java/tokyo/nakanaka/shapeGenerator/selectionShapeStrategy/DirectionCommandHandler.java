package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.List;

import tokyo.nakanaka.Direction;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

public class DirectionCommandHandler extends BaseSelSubCommandHandler<Direction> {

	public DirectionCommandHandler(SelectionDataFactory selDataFactory) {
		super("direction", new String[] {"north|south|east|west|up|down"}, selDataFactory);
	}

	@Override
	protected Direction parse(Player player, String[] subArgs) {
		return switch (subArgs.length) {
		case 1 -> Direction.valueOf(subArgs[0].toUpperCase());
		default -> throw new IllegalArgumentException();
		};
	}
	
	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return switch(args.length) {
		case 1 -> List.of(Direction.values()).stream()
				.map(s -> s.toString().toLowerCase())
				.toList();
		default -> List.of(); 
		};
	}

}
