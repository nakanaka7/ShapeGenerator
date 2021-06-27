package tokyo.nakanaka.selection.cuboid;

import java.util.List;

import tokyo.nakanaka.commandArgument.PositionArgument;
import tokyo.nakanaka.logger.Logger;
import tokyo.nakanaka.math.BlockVector3D;
import tokyo.nakanaka.math.Vector3D;
import tokyo.nakanaka.selection.RegionBuilder;
import tokyo.nakanaka.selection.SelCommandHandler;

public class Pos1CommandHandler implements SelCommandHandler{
	private PositionArgument pos1Arg = new PositionArgument();

	@Override
	public void onCommand(RegionBuilder builder, Logger logger, BlockVector3D playerPos, String[] args) {
		Vector3D pos1;
		try {
			pos1 = this.pos1Arg.onParse(playerPos, args);
		}catch(IllegalArgumentException e) {
			return;
		}
		((CuboidRegionBuilder)builder).setPos1(pos1);
	}

	@Override
	public List<String> onTabComplete(String[] args) {
		return this.pos1Arg.onTabComplete(args);
	}

}
