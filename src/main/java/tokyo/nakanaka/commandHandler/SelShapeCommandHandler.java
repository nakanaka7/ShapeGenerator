package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.Pair;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.SelectionBuilder;
import tokyo.nakanaka.selection.SelectionManager;
import tokyo.nakanaka.selection.SelectionShape;

import static tokyo.nakanaka.logger.LogConstant.*;
import static tokyo.nakanaka.logger.LogColor.*;

public class SelShapeCommandHandler implements CommandHandler{
	private SelectionManager selManager;
		
	public SelShapeCommandHandler(SelectionManager selManager) {
		this.selManager = selManager;
	}

	@Override
	public String getDescription() {
		return "Set selection shape";
	}
	
	@Override
	public String getLabel() {
		return "selection_shape";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList("selshape");
	}

	@Override
	public String getUsage() {
		return "selshape <shape>";
	}
	
	@Override
	public List<Pair<String, String>> getParameterDescriptionList() {
		Pair<String, String> desShape = new Pair<>("<shape>", "selection shape");
		return Arrays.asList(desShape);
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		if(args.length != 1) {
			return false;
		}
		SelectionShape shape;
		try{
			shape = SelectionShape.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			player.getLogger().print(HEAD_ERROR + "Invalid shape");
			return true;
		}
		SelectionBuilder builder = player.getSelectionBuilder();
		SelectionShape original = this.selManager.getShape(builder);
		if(shape == original) {
			player.getLogger().print(HEAD_WARN + "Already set : Nothing to change");
			return true;
		}else {
			player.setSelectionBuilder(this.selManager.newInstance(shape));
			player.getLogger().print(HEAD_NORMAL + "Set the shape -> " + LIGHT_PURPLE + shape);
			return true;
		}
	}
	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return this.selManager.getRegisterdShape().stream()
					.map(s -> s.toString().toLowerCase())
					.collect(Collectors.toList());
		}else {
			return new ArrayList<>();
		}
	}

}
