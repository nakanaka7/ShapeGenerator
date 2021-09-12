package tokyo.nakanaka.shapeGenerator.selectionShapeStrategy;

import java.util.List;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

public class PosCommandHandler extends BaseSelSubCommandHandler<Vector3D> {
	
	public PosCommandHandler(String subLabel, SelectionDataFactory selDataFactory) {
		super(subLabel, new String[] {"[x]", "[y]", "[z]"}, selDataFactory);
	}

	@Override
	protected Vector3D parse(Player player, String[] subArgs) {
		return switch(subArgs.length) {
		case 0 -> {
			BlockPosition pos = player.getBlockPosition();
			yield new Vector3D(pos.x(), pos.y(), pos.z());
		}
		case 3 -> {
			double x = Double.parseDouble(subArgs[0]);
			double y = Double.parseDouble(subArgs[1]);
			double z = Double.parseDouble(subArgs[2]);
			yield new Vector3D(x, y, z);
		}
		default -> {
			throw new IllegalArgumentException();
		}
		};
	}
	
	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		BlockPosition pos = player.getBlockPosition();
		return switch(args.length) {
			case 1 -> List.of(String.valueOf(pos.x()));
			case 2 -> List.of(String.valueOf(pos.y()));
			case 3 -> List.of(String.valueOf(pos.z()));
			default -> List.of();
		};
	}

}
