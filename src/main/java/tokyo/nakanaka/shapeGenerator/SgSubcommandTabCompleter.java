package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

import java.util.List;

@PrivateAPI
public interface SgSubcommandTabCompleter {
    List<String> onTabComplete(PlayerData playerData, Player player, String[] args);
}
