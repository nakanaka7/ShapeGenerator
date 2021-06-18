package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static tokyo.nakanaka.logger.LogConstant.*;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.selection.SelectionBuilder;
import tokyo.nakanaka.selection.SelectionManager;
import tokyo.nakanaka.selection.SelectionShape;

public class SelResetCommandHandler implements CommandHandler{
	private SelectionManager selManager;

	public SelResetCommandHandler(SelectionManager selManager) {
		this.selManager = selManager;
	}

	@Override
	public String getLabel() {
		return "selection_reset";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("selreset");
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		SelectionBuilder buider = player.getSelectionBuilder();
		SelectionShape shape = this.selManager.getSelectionShape(buider);
		SelectionBuilder newBuilder = this.selManager.getNewSelectionBuilderInstance(shape);
		player.setSelectionBuilder(newBuilder);
		player.getLogger().print(HEAD_NORMAL + "Reset selection build");
		return true;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("reset");
		}else {
			return new ArrayList<>();
		}
	}

}
