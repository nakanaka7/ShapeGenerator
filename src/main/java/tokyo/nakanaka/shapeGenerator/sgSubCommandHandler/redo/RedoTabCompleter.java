package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.redo;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.SgSubcommandTabCompleter;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

import java.util.List;

public class RedoTabCompleter implements SgSubcommandTabCompleter {

    @Override
    public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
        return switch(args.length) {
            case 1 -> List.of("1", "2", "3", "4", "5", "6", "7", "8", "9");
            default -> List.of();
        };
    }

}
