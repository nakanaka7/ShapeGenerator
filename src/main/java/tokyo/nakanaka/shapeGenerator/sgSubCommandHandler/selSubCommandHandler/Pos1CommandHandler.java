package tokyo.nakanaka.shapeGenerator.sgSubCommandHandler.selSubCommandHandler;

import java.util.List;

import tokyo.nakanaka.BlockPosition;
import tokyo.nakanaka.Player;
import tokyo.nakanaka.shapeGenerator.SelectionData;

public class Pos1CommandHandler implements SelSubCommandHandler {

	@Override
	public void onCommand(SelectionData selData, Player player, String[] args) {
		
	}

	@Override
	public List<String> onTabComplete(SelectionData selData, Player player, String[] args) {
		BlockPosition blockPos = player.getBlockPosition();
		if(args.length == 1) {
			return List.of(String.valueOf(blockPos.x()));
		}else if(args.length == 2) {
			return List.of(String.valueOf(blockPos.y()));
		}else if(args.length == 3) {
			return List.of(String.valueOf(blockPos.z()));
		}else {
			return List.of();
		}
	}

}
