package tokyo.nakanaka.shapeGenerator.command;

import tokyo.nakanaka.Axis;
import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.shapeGenerator.Selection;

@PrivateAPI
public class MaxCommand implements AdjustCommand {
    private GenerateCommand originalCmd;
    private GenerateCommand lastCmd;

    public MaxCommand(GenerateCommand originalCmd, Axis axis, double max, boolean physics){
        this.originalCmd = originalCmd;
        Selection originalSel = originalCmd.getSelection();
        Selection sel = originalSel.createMaxLimited(axis, max);
        this.lastCmd = new GenerateCommand(sel, originalCmd.getBlock(), physics);
    }

    @Override
    public void execute() {
        this.originalCmd.undo();
        this.lastCmd.execute();
    }

    @Override
    public void undo() {
        this.lastCmd.undo();
        this.originalCmd.redo();
    }

    @Override
    public void redo() {
        this.originalCmd.undo();
        this.lastCmd.redo();
    }

    @Override
    public GenerateCommand getLastCommand() {
        return this.lastCmd;
    }

}
