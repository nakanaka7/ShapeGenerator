package tokyo.nakanaka.commandHandler;

import static tokyo.nakanaka.logger.LogColor.LIGHT_PURPLE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import tokyo.nakanaka.commadHelp.CommandHelp;
import tokyo.nakanaka.commadHelp.Parameter;
import tokyo.nakanaka.commadHelp.Parameter.Type;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.SelectionStrategy;

public class ShapeCommandHandler implements SgSubCommandHandler {
	private Map<SelectionShape, SelectionStrategy> strategyMap;
	private CommandHelp help = new CommandHelp.Builder("shape")
			.description("Set selection shape")
			.addParameter(new Parameter(Type.REQUIRED, "shape"), "selection shape")
			.build();
	private String usage = "/sg shape <shape>";
	
	public ShapeCommandHandler(Map<SelectionShape, SelectionStrategy> strategyMap) {
		this.strategyMap = strategyMap;
	}

	@Override
	public CommandHelp getCommandHelp() {
		return this.help;
	}
	
	@Override
	public boolean onCommand(Player player, String[] args) {
		Logger logger = player.getLogger();
		if(args.length != 1) {
			logger.print(LogColor.RED + "Usage: " + this.usage);
			return true;
		}
		SelectionShape shape;
		try{
			shape = SelectionShape.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			logger.print(LogColor.RED + "Invalid shape");
			return true;
		}
		SelectionStrategy selStrategy = this.strategyMap.get(shape);
		if(selStrategy == null) {
			logger.print(LogColor.RED + "Unsupported shape");
			return true;
		}	
		SelectionShape original = player.getSelectionShape();
		if(shape == original) {
			logger.print(LogColor.YELLOW + "Already set : Nothing to change");
			return true;
		}else {
			player.setSelectionShape(shape);
			RegionBuildingData regionData = selStrategy.newRegionBuildingData();
			SelectionBuildingData selData = new SelectionBuildingData(player.getWorld(), regionData);
			player.setSelectionBuildingData(selData);
			logger.print("Set the shape -> " + LIGHT_PURPLE + shape);
			return true;
		}
	}
	
	@Override
	public List<String> onTabComplete(Player player, String[] args) {
		if(args.length == 1) {
			return this.strategyMap.keySet().stream()
					.map(s -> s.toString().toLowerCase())
					.collect(Collectors.toList());
		}else {
			return new ArrayList<>();
		}
	}
	
}
