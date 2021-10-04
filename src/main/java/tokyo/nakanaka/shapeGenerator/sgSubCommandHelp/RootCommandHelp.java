package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class RootCommandHelp implements CommandHelp {
    private String label;
    private String desc;
    private LinkedHashMap<String, CommandHelp> subMap = new LinkedHashMap<>();

    public RootCommandHelp(String label, String desc) {
        this.label = label;
        this.desc = desc;
    }

    @Override
    public String label() {
        return this.label;
    }

    @Override
    public String description() {
        return this.desc;
    }

    public void addSubHelp(CommandHelp cmdHelp) {
        this.subMap.put(cmdHelp.label(), cmdHelp);
    }

    public List<CommandHelp> subHelpList() {
        return new ArrayList<>(this.subMap.values());
    }

    @Override
    public String syntax() {
        return label + " <subcommand>";
    }

}
