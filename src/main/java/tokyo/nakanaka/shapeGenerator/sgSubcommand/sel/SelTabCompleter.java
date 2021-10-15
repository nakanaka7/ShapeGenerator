package tokyo.nakanaka.shapeGenerator.sgSubcommand.sel;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.SelectionShape;
import tokyo.nakanaka.shapeGenerator.SelectionShapeStrategyRepository;
import tokyo.nakanaka.shapeGenerator.SgSubcommandTabCompleter;
import tokyo.nakanaka.shapeGenerator.SubCommandHandler;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SelTabCompleter implements SgSubcommandTabCompleter {
    private Map<String, SubCommandHandler> commonMap = new HashMap<>();
    private Map<SelectionShape, Map<String, SubCommandHandler>> properMapMap;

    public SelTabCompleter(SelectionShapeStrategyRepository shapeStrtgRepo,
                           Map<SelectionShape, Map<String, SubCommandHandler>> properMapMap  ) {
        this.commonMap.put("offset", new OffsetCommandHandler(shapeStrtgRepo));
        this.commonMap.put("reset", new ResetCommandHandler(shapeStrtgRepo));
        this.properMapMap = properMapMap;
    }

    public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
        SelectionShape shape = playerData.getSelectionShape();
        Map<String, SubCommandHandler> properMap = this.properMapMap.get(shape);
        if(args.length == 1) {
            List<String> subLabelList = new ArrayList<>(this.commonMap.keySet());
            subLabelList.addAll(properMap.keySet().stream()
                    .collect(Collectors.toList()));
            return subLabelList;
        }
        String subLabel = args[0];
        String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, args.length - 1);
        SubCommandHandler commonHandler = this.commonMap.get(subLabel);
        if(commonHandler != null) {
            return commonHandler.onTabComplete(playerData, player, subArgs);
        }
        SubCommandHandler properHandler = properMap.get(subLabel);
        if(properHandler != null) {
            return properHandler.onTabComplete(playerData, player, subArgs);
        }
        return List.of();
    }

}
