package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.commandArgument.BlockCommandArgument;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class SgCommandDirectory implements CommandDirectory {
	private List<CommandEntry> subList = new ArrayList<>();
	
	public SgCommandDirectory(BlockCommandArgument blockArg, SelectionStrategySource selStraSource) {
		subList.add(new ShapeCommandHandler(selStraSource));
		subList.add(new SelCommandDirectory(selStraSource));
		subList.add(new GenrCommandHandler(blockArg, selStraSource));
		subList.add(new PhyCommandHandler());
		subList.add(new ShiftCommandHandler());
		subList.add(new ScaleCommandHandler());
		subList.add(new MirrorCommandHandler());
		subList.add(new RotCommandHandler());
		subList.add(new MaxXCommandHandler());
		subList.add(new MaxYCommandHandler());
		subList.add(new MaxZCommandHandler());
		subList.add(new MinXCommandHandler());
		subList.add(new MinYCommandHandler());
		subList.add(new MinZCommandHandler());
		subList.add(new DelCommandHandler());
		subList.add(new UndoCommandHandler());
		subList.add(new RedoCommandHandler());
	}
	
	@Override
	public String getLabel() {
		return "sg";
	}
	
	@Override
	public String getDescription() {
		return "Root command of ShapeGenerator";
	}
	
	@Override
	public List<CommandEntry> getSubList(UserData user) {
		return subList;
	}
	
}
