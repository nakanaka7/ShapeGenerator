package tokyo.nakanaka.shapeGenerator;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerDataRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SgTabCompleter {
    private PlayerDataRepository playerDataRepository;
    private Map<String, SgSubTabCompleter> tabCompleterMap = new HashMap<>();

    public SgTabCompleter(PlayerDataRepository playerDataRepository) {
        this.playerDataRepository = playerDataRepository;
    }

    public List<String> onTabComplete(CommandSender cmdSender, String[] args) {
        if (!(cmdSender instanceof Player player)) {
            return List.of();
        }
        if (args.length == 1) {
            return new ArrayList<>(this.tabCompleterMap.keySet());
        }
        String subLabel = args[0];
        String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, args.length - 1);
        SgSubTabCompleter tabCompleter = this.tabCompleterMap.get(subLabel);
        if (tabCompleter != null) {
            PlayerData playerData = this.playerDataRepository.preparePlayerData(player);
            return tabCompleter.onTabComplete(playerData, player, subArgs);
        }
        return List.of();
    }

}
