package tokyo.nakanaka.shapeGenerator.sgSubcommand.help;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.SgSubcommandTabCompleter;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

import java.util.ArrayList;
import java.util.List;

import static tokyo.nakanaka.shapeGenerator.SgSublabel.*;

public class HelpTabCompleter implements SgSubcommandTabCompleter {
    private List<String> subCmdList = new ArrayList<>();

    public HelpTabCompleter() {
        this.subCmdList.add(HELP);
        this.subCmdList.add(VERSION);
        this.subCmdList.add(WAND);
        this.subCmdList.add(SHAPE);
        this.subCmdList.add(SEL);
        this.subCmdList.add(GENR);
        this.subCmdList.add(PHY);
        this.subCmdList.add(SHIFT);
        this.subCmdList.add(SCALE);
        this.subCmdList.add(MIRROR);
        this.subCmdList.add(ROT);
        this.subCmdList.add(MAX);
        this.subCmdList.add(MIN);
        this.subCmdList.add(DEL);
        this.subCmdList.add(UNDO);
        this.subCmdList.add(REDO);
    }

    @Override
    public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
        return switch(args.length){
            case 1 -> this.subCmdList;
            default -> List.of();
        };
    }
}
