package tokyo.nakanaka.commandLine;

import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.shapeGenerator.Player;

public class SelShapeCommandLine implements CommandLine{

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
