package tokyo.nakanaka.shapeGenerator.sgSubcommand.genr;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.BlockIDListFactory;
import tokyo.nakanaka.shapeGenerator.SgSubcommandTabCompleter;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

import java.util.List;
import java.util.stream.Collectors;

public class GenrTabCompleter implements SgSubcommandTabCompleter {
    private BlockIDListFactory blockIDFactory;
    public GenrTabCompleter(BlockIDListFactory blockIDFactory) {
        this.blockIDFactory = blockIDFactory;
    }

    public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
        return switch(args.length) {
            case 1 -> this.blockIDFactory.getBlockIDList().stream()
                    .map(s -> s.toString())
                    .collect(Collectors.toList());
            default -> List.of();
        };
    }

}
