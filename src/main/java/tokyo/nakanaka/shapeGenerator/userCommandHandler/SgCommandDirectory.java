package tokyo.nakanaka.shapeGenerator.userCommandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.commandArgument.BlockCommandArgument;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;
import tokyo.nakanaka.shapeGenerator.user.UserData;

public class SgCommandDirectory implements CommandDirectory {
	private List<CommandEntry> subList = new ArrayList<>();
	
	public SgCommandDirectory(BlockCommandArgument blockArg, SelectionStrategySource selStraSource) {
		subList.add(new SelCommandDirectory(selStraSource));
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
