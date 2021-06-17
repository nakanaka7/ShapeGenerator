package tokyo.nakanaka.commandHandler;

import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Player;

public class SelShapeCommandHandler implements CommandHandler{

	@Override
	public String getLabel() {
		return "selection_shape";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("selshape");
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
