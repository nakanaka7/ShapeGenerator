package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.*;
import tokyo.nakanaka.shapeGenerator.message.MessageUtils;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;
import tokyo.nakanaka.shapeGenerator.selectionShapeStrategy.SelectionShapeStrategy;

import java.util.List;
import java.util.stream.Stream;

/**
 * Handles "/sg wandhelp"
 */
public class WandHelpCommandHandler implements SubCommandHandler {
    private SelectionShapeStrategyRepository strtgRepo;

    public WandHelpCommandHandler(SelectionShapeStrategyRepository strtgRepo) {
        this.strtgRepo = strtgRepo;
    }

    @Override
    public String label() {
        return "wandhelp";
    }

    @Override
    public String description() {
        return "Print wand help";
    }

    @Override
    public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
        if(args.length != 1) {
            player.print(cmdLogColor.error() + "Usage: " + "/sg wandhelp");
            return;
        }
        SelectionShape shape;
        try{
            shape = SelectionShape.valueOf(args[0].toUpperCase());
        }catch (IllegalArgumentException e){
            player.print(cmdLogColor.error() + "Invalid shape");
            return;
        }
        if(!List.of(this.strtgRepo.registeredShapes()).contains(shape)) {
            player.print(cmdLogColor.error() + "Unsupported shape");
            return;
        }
        SelectionShapeStrategy strtg = this.strtgRepo.get(shape);
        player.print(MessageUtils.title(cmdLogColor.main() + "Wand help of " + shape.toString()));
        player.print(cmdLogColor.main() + strtg.clickDescription());
    }

    @Override
    public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
        return switch(args.length) {
            case 1 -> Stream.of(this.strtgRepo.registeredShapes())
                    .map(s -> s.toString().toLowerCase())
                    .toList();
            default -> List.of();
        };
    }
}
