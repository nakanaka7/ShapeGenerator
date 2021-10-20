package tokyo.nakanaka.shapeGenerator.sgSubcommand.mirror;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.SgSubcommandTabCompleter;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

import java.util.ArrayList;
import java.util.List;

public class MirrorTabCompleter implements SgSubcommandTabCompleter {

    @Override
    public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
        return switch(args.length) {
            case 1 -> List.of("x", "y", "z");
            default -> new ArrayList<>();
        };
    }
    
}
