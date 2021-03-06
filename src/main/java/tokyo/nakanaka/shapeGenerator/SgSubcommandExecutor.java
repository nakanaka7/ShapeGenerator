package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

@PrivateAPI
public interface SgSubcommandExecutor {
    void onCommand(PlayerData playerData, Player player, String[] args);
}
