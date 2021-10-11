package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.shape;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.SgSubcommandTabCompleter;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

import java.util.ArrayList;
import java.util.List;

public class ShapeTabCompleter implements SgSubcommandTabCompleter {
    private List<SelectionShape> shapeList = new ArrayList<>();

    public ShapeTabCompleter(List<SelectionShape> shapeList) {
        this.shapeList = shapeList;
    }

    @Override
    public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
        return switch(args.length) {
            case 1 -> this.shapeList.stream()
                    .map(s -> s.toString().toLowerCase())
                    .toList();
            default -> List.of();
        };
    }
}
