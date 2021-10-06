package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

import java.util.ArrayList;
import java.util.List;

public interface SgSubTabCompleter {
    List<String> onTabComplete(PlayerData playerData, Player player, String[] args);
}
