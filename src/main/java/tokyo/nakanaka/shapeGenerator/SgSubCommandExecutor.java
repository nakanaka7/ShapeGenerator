package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

public interface SgSubCommandExecutor {
    void onCommand(PlayerData playerData, Player player, String[] args);
}
