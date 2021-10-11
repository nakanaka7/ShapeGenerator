package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.wand;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.SgSubcommandTabCompleter;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

import java.util.List;

public class WandTabCompleter implements SgSubcommandTabCompleter {

    @Override
    public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
        return List.of();
    }

}
