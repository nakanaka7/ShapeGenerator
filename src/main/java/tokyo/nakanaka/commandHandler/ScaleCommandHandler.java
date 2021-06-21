package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.Player;

public class ScaleCommandHandler implements CommandHandler{

	@Override
	public String getDescription() {
		return "Change scale the generated blocks";
	}

	@Override
	public String getLabel() {
		return "scale";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("scale");
	}

	@Override
	public String getUsage() {
		return "scale x|y|z <scale factor>";
	}

	@Override
	public List<Pair<String, String>> getParameterDescriptionList() {
		Pair<String, String> desAxis = new Pair<>("x|y|z", "scale axis");
		Pair<String, String> desDegree = new Pair<>("<scale factor>", "scale factor (double data type)");
		return Arrays.asList(desAxis, desDegree);
	}

	@Override
	public boolean onCommand(Player player, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("x", "y", "z");
		}else if(args.length == 2) {
			return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		}else {
			return new ArrayList<>();
		}
	}

}
