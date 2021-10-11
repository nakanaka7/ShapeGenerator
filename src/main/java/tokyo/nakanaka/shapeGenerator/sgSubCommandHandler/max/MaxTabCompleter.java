package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.max;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.SgSubcommandTabCompleter;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

import java.util.List;

public class MaxTabCompleter implements SgSubcommandTabCompleter {

    @Override
    public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
        return switch(args.length) {
            case 1 -> List.of("x", "y", "z");
            case 2 -> {
                Axis axis;
                try {
                    axis = Axis.valueOf(args[0].toUpperCase());
                }catch(IllegalArgumentException e) {
                    yield List.of();
                }
                double s = switch(axis) {
                    case X -> (double)player.getBlockPosition().x();
                    case Y -> (double)player.getBlockPosition().y();
                    case Z -> (double)player.getBlockPosition().z();
                };
                yield List.of(String.valueOf(s));
            }
            default -> List.of();
        };
    }

}
