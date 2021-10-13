package tokyo.nakanaka.shapeGenerator.sgSubcommand.sel;

import tokyo.nakanaka.Player;
import tokyo.nakanaka.World;
import tokyo.nakanaka.shapeGenerator.*;
import tokyo.nakanaka.shapeGenerator.message.MessageUtils;
import tokyo.nakanaka.shapeGenerator.playerData.PlayerData;

import java.util.List;

public class ResetCommandHandler implements SubCommandHandler {
	private SelectionShapeStrategyRepository shapeStrtgRepo;

	public ResetCommandHandler(SelectionShapeStrategyRepository shapeStrtgRepo) {
		this.shapeStrtgRepo = shapeStrtgRepo;
	}

	@Override
	public void onCommand(PlayerData playerData, Player player, String[] args, CommandLogColor cmdLogColor) {
		if(args.length != 0) {
			player.print(cmdLogColor.error() + "Usage: " + "/sg sel reset");
			return;
		}
		World world = player.getEntityPosition().world();
		SelectionShape shape = playerData.getSelectionShape();
		SelectionData newSelData = this.shapeStrtgRepo.get(shape)
				.newSelectionData(world);
		playerData.setSelectionData(newSelData);
		List<String> lines = MessageUtils.selectionMessage(cmdLogColor.main(), shape, newSelData);
		for(String line : lines) {
			player.print(line);
		}
	}
	
	@Override
	public List<String> onTabComplete(PlayerData playerData, Player player, String[] args) {
		return List.of();
	}

}