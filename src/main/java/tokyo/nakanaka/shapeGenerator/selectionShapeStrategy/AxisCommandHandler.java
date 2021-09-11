package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.List;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

public class AxisCommandHandler extends BaseSelSubCommandHandler<Axis> {

	public AxisCommandHandler(SelectionDataFactory selDataFactory) {
		super("axis", new String[] {"x|y|z"}, selDataFactory);
	}

	@Override
	protected Axis parse(Player player, String[] subArgs) {
		return switch(subArgs.length) {
		case 1 -> Axis.valueOf(subArgs[0].toUpperCase());
		default -> throw new IllegalArgumentException();
		};
	}
	
	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return switch(args.length) {
		case 1 -> List.of("x", "y", "z");
		default -> List.of();
		};
	}

}
