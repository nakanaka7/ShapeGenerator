package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tokyo.nakanaka.commadHelp.ParameterHelp;
import tokyo.nakanaka.commadHelp.ParameterType;
import tokyo.nakanaka.logger.LogColor;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.logger.shapeGenerator.LogDesignColor;
import tokyo.nakanaka.selection.RegionBuildingData;
import tokyo.nakanaka.selection.SelectionBuildingData;
import tokyo.nakanaka.selection.SelectionMessenger;
import tokyo.nakanaka.selection.SelectionShape;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategy;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.shapeGenerator.user.User;

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
	public boolean onCommand(User user, String[] args) {
		Logger logger = user.getLogger();
		if(args.length != 1) {
			logger.print(LogColor.RED + "Usage: /sg shape <type>");
			return true;
		}
		SelectionShape shape;
		try{
			shape = SelectionShape.valueOf(args[0].toUpperCase());
		}catch(IllegalArgumentException e) {
			logger.print(LogDesignColor.ERROR + "Invalid shape");
			return true;
		}
		SelectionStrategy selStrategy = this.selStraSource.get(shape);
		if(selStrategy == null) {
			logger.print(LogDesignColor.ERROR + "Unsupported shape");
			return true;
		}	
		SelectionShape original = user.getSelectionShape();
		if(shape == original) {
			logger.print(LogDesignColor.ERROR + "Already set : Nothing to change");
			return true;
		}else {
			user.setSelectionShape(shape);
			RegionBuildingData regionData = selStrategy.newRegionBuildingData();
			SelectionBuildingData selData = new SelectionBuildingData(user.getWorld(), regionData);
			user.setSelectionBuildingData(selData);
			logger.print(LogDesignColor.NORMAL + "Set the shape -> " + shape);
			new SelectionMessenger().printClickDescription(logger, selStrategy);
			return true;
		}
	}
	
	@Override
	public List<String> onTabComplete(User user, String[] args) {
		if(args.length == 1) {
			return this.selStraSource.getShapeList().stream()
					.map(s -> s.toString().toLowerCase())
					.collect(Collectors.toList());
		}else {
			return new ArrayList<>();
		}
	}

}
