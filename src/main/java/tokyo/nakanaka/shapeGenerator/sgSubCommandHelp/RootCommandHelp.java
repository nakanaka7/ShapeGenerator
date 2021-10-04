package tokyo.nakanaka.shapeGenerator.sgSubCommandHelp;

public class RootCommandHelp implements CommandHelp {
    private String label;
    private String desc;

    public String label() {
        return this.label;
    }

    @Override
    public String description() {
        return this.desc;
    }

    @Override
    public String syntax() {
        return null;
    }

}
