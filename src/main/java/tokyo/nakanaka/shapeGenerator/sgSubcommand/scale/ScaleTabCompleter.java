package tokyo.nakanaka.shapeGenerator.sgSubcommand.scale;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.SgSubcommandTabCompleter;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

import java.util.List;

public class ScaleTabCompleter implements SgSubcommandTabCompleter {
    @Override
    public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
        return switch(args.length) {
            case 1 -> List.of("x", "y", "z");
            case 2 -> List.of("0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0",
                    "5.0", "6.0", "6.5", "7.0", "7.5", "8.0", "8.5", "9.0", "9.5");
            default -> List.of();
        };
    }
}
