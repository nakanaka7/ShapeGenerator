package tokyo.nakanaka.shapeGenerator.sgSubcommand.rot;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.SgSubcommandTabCompleter;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

import java.util.List;

public class RotTabCompleter implements SgSubcommandTabCompleter {

    @Override
    public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
        return switch(args.length) {
            case 1 -> List.of("x", "y", "z");
            case 2 -> List.of("0.0", "90.0", "-90.0", "180.0", "270.0");
            default -> List.of();
        };
    }

}
