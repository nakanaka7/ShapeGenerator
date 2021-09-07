package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.List;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

public abstract class LengthCommandHandler extends BaseSelSubCommandHandler<Double> {
	
	public LengthCommandHandler(String subLabel) {
		super("/sg sel " + subLabel + " <length>");
	}
		
	@Override
	protected Double parse(Player player, String[] args) {
		return switch(args.length) {
		case 1 -> Double.parseDouble(args[0]);
		default -> throw new IllegalArgumentException();
		};
	}
	
	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return switch(args.length) {
			case 1 -> List.of("0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0",
					"5.5", "6.0", "6.5", "7.0", "7.5", "8.0", "8.5", "9.0");
			default -> List.of();
		};
	}
	
}
