package tokyo.nakanaka.commandHandler;

import java.util.ArrayList;
import java.util.List;

import tokyo.nakanaka.commandArgument.BlockCommandArgument;
import tokyo.nakanaka.player.Player;
import tokyo.nakanaka.selection.selectionStrategy.SelectionStrategySource;

public class SgCommandHandlerNew implements RootCommandHandler {
	private List<BranchCommandHandler> subList = new ArrayList<>();
	
	@Override
	public String getDescription() {
		return "Root command of ShapeGenerator";
	}
	
	public SgCommandHandlerNew(BlockCommandArgument blockArg, SelectionStrategySource selStraSource) {
		this.subList.add(new WandCommandHandler(selStraSource));
		this.subList.add(new ShapeCommandHandler(selStraSource));
		this.subList.add(new SelCommandHandler(selStraSource));
		this.subList.add(new GenrCommandHandler(blockArg, selStraSource));
		this.subList.add(new PhyCommandHandler());
		this.subList.add(new ShiftCommandHandler());
		this.subList.add(new ScaleCommandHandler());
		this.subList.add(new MirrorCommandHandler());
		this.subList.add(new RotCommandHandler());
		this.subList.add(new MaxXCommandHandler());
		this.subList.add(new MaxYCommandHandler());
		this.subList.add(new MaxZCommandHandler());
		this.subList.add(new MinXCommandHandler());
		this.subList.add(new MinYCommandHandler());
		this.subList.add(new MinZCommandHandler());
		this.subList.add(new DelCommandHandler());
		this.subList.add(new UndoCommandHandler());
		this.subList.add(new RedoCommandHandler());
	}
	
	@Override
	public String getLabel() {
		return "sg";
	}
	
	@Override
	public List<BranchCommandHandler> getSubList(Player player) {
		return this.subList;
	}

}
