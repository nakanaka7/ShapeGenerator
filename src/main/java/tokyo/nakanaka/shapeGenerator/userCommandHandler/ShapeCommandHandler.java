package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.commandSender.CommandSender;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.shapeGenerator.LogDesignColor;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionMessenger;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class ShapeCommandHandler implements UserCommandHandler {
	private SelectionStrategySource selStraSource;
	
	public ShapeCommandHandler(SelectionStrategySource selStraSource) {
		this.selStraSource = selStraSource;
		SelectionShape[] shapes = SelectionShape.values();
		String[] shapeStrs = new String[shapes.length];
		for(int i = 0; i < shapes.length; i++) {
			shapeStrs[i] = shapes[i].toString().toLowerCase();
		}
	}

	@Override
	public String getLabel() {
		return "shape";
	}
	
	@Override
	public String getDescription() {
		return "Set selection shape";
	}
	
	@Override
	public List<ParameterHelp> getParameterHelpList() {
		List<ParameterHelp> list = new ArrayList<>();
		SelectionShape[] shapes = SelectionShape.values();
		String[] shapeStrs = new String[shapes.length];
		for(int i = 0; i < shapes.length; i++) {
			shapeStrs[i] = shapes[i].toString().toLowerCase();
		}
		list.add(new ParameterHelp(ParameterType.REQUIRED, shapeStrs, ""));
		return list;
	}
	
	@Override
	public void onCommand(UserData userData, CommandSender cmdSender, String[] args) {
		if(args.length != 1) {
			cmdSender.print(LogColor.RED + "Usage: /sg shape <type>");
			return;
		}
		SelectionShape shape;
		try{
			shape = SelectionShape.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			cmdSender.print(LogDesignColor.ERROR + "Invalid shape");
			return;
		}
		SelectionStrategy selStrategy = this.selStraSource.get(shape);
		if(selStrategy == null) {
			cmdSender.print(LogDesignColor.ERROR + "Unsupported shape");
			return;
		}	
		SelectionShape original = userData.getSelectionShape();
		if(shape == original) {
			cmdSender.print(LogDesignColor.ERROR + "Already set : Nothing to change");
			return;
		}else {
			userData.setSelectionShape(shape);
			RegionBuildingData regionData = selStrategy.newRegionBuildingData();
			SelectionBuildingData selData = new SelectionBuildingData(userData.getWorld(), regionData);
			userData.setSelectionBuildingData(selData);
			cmdSender.print(LogDesignColor.NORMAL + "Set the shape -> " + shape);
			new SelectionMessenger().printClickDescription(cmdSender, selStrategy);
			return;
		}
	}
	
	@Override
	public List<String> onTabComplete(UserData userData, CommandSender cmdSender, String[] args) {
		if(args.length == 1) {
			return this.selStraSource.getShapeList().stream()
					.map(s -> s.toString().toLowerCase())
					.collect(Collectors.toList());
		}else {
			return new ArrayList<>();
		}
	}

}
