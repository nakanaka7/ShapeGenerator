package tokyo.nakanaka.commandHandlerNew;

import java.util.List;

import tokyo.nakanaka.player.Player;

public class BranchCommandHandler implements CommandHandler {
	private BranchCommandHandleStrategy cmdStrat;

	public BranchCommandHandler(BranchCommandHandleStrategy cmdStrat) {
		this.cmdStrat = cmdStrat;
	}
	
	@Override
	public String getLabel() {
		return this.cmdStrat.getLabel();
	}
	
	@Override
	public String getDescription() {
		return this.cmdStrat.getDescription();
	}
	
	@Override
	public void onCommand(Player player, String[] parentLabels, String[] args) {
		boolean success = this.cmdStrat.onCommand(player, args);
		
	}
	
	@Override
	public List<String> onTabComplete(String[] args){
		return this.cmdStrat.onTabComplete(args);
	}
	
}
